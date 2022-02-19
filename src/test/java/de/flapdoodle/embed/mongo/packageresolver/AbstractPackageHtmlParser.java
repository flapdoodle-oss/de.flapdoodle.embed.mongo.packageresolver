/**
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Martin Jöhren <m.joehren@googlemail.com>
 *
 * with contributions from
 * 	konstantin-ba@github,Archimedes Trajano	(trajano@github)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.flapdoodle.embed.mongo.packageresolver;

import com.google.common.collect.ImmutableSet;
import com.google.common.collect.ImmutableSetMultimap;

import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class AbstractPackageHtmlParser {

	static List<ParsedVersion> mergeAll(List<List<ParsedVersion>> allVersions) {
		List<ParsedVersion> flatmapped = allVersions.stream().flatMap(it -> it.stream()).collect(Collectors.toList());

		Set<String> versions = flatmapped.stream()
			.map(it -> it.version)
			.collect(Collectors.toSet());

		return versions.stream().map(v -> new ParsedVersion(v, mergeDists(v, flatmapped)))
			.collect(Collectors.toList());
	}

	private static List<ParsedDist> mergeDists(String v, List<ParsedVersion> src) {
		List<ParsedDist> matchingDists = src.stream()
			.filter(pv -> v.equals(pv.version))
			.flatMap(pv -> pv.dists.stream())
			.collect(Collectors.toList());

		return groupByName(matchingDists);
	}

	private static List<ParsedDist> groupByName(List<ParsedDist> matchingDists) {
		Map<String, List<ParsedDist>> groupedByName = matchingDists.stream()
			.collect(Collectors.groupingBy(pd -> pd.name));

		return groupedByName.entrySet().stream()
			.map(entry -> new ParsedDist(entry.getKey(), entry.getValue().stream()
				.flatMap(it -> it.urls.stream())
				.collect(Collectors.toList())))
			.collect(Collectors.toList());
	}



	static void versionAndUrl(ParsedVersions versions) {
		versions
			.list()
			.stream()
			.sorted()
			.forEach(version -> {
				if (!version.dists.isEmpty()) {
					System.out.println(version.version);
					version.dists.forEach(dist -> {
						dist.urls.forEach(packageUrl -> {
							System.out.println("  " + packageUrl.url);
						});
					});
				}
			});
	}

	static void compressedVersionAndUrl(ParsedVersions versions) {
		Map<String, List<ParsedVersion>> groupedByVersionLessUrl = versions.groupByVersionLessUrl();

		groupedByVersionLessUrl.forEach((url, versionList) -> {
			System.out.println(url.isEmpty() ? "--" : url);
			//String versionNumbers = versionList.stream().map(it -> it.version).collect(Collectors.joining(", "));
			List<String> versionNumbers = versionNumbers(versionList);

			String compressedVersions = compressedVersionAsString(versionNumbers);

			System.out.println(compressedVersions);
		});
	}

	private static String compressedVersionAsString(List<String> versionNumbers) {
		return rangesAsString(compressedVersionsList(versionNumbers));
	}

	private static String rangesAsString(List<VersionRange> ranges) {
		return ranges.stream()
			.sorted(Comparator.comparing(VersionRange::min).reversed())
			.map(r -> r.min().equals(r.max())
				? asString(r.min())
				: asString(r.max()) + " - " + asString(r.min()))
			.collect(Collectors.joining(", "));
	}

	static List<String> versionNumbers(List<ParsedVersion> versions) {
		return versions.stream().map(it -> it.version).collect(Collectors.toList());
	}

	static Map<String, List<ParsedVersion>> groupByVersionLessUrl(List<ParsedVersion> versions) {
		Map<String, List<ParsedVersion>> groupedByVersionLessUrl = versions.stream()
			.sorted()
			.collect(Collectors.groupingBy(version -> {
				List<String> urls = version.dists.stream()
					.flatMap(dist -> dist.urls.stream())
					.map(packageUrl -> packageUrl.url)
					.collect(Collectors.toList());

				String versionLessUrl = urls.stream().map(it -> it.replace(version.version, "{}"))
					.collect(Collectors.joining("|"));

				return versionLessUrl;
			}));
		return groupedByVersionLessUrl;
	}

	static List<VersionRange> compressedVersionsList(Collection<String> numericVersions) {
		List<NumericVersion> versions = numericVersions.stream().map(NumericVersion::of)
			.sorted(Comparator.reverseOrder())
			.collect(Collectors.toList());

		List<VersionRange> ranges = new ArrayList<>();
		if (!versions.isEmpty()) {
			int start=0;
			while (start<versions.size()) {
				NumericVersion max=versions.get(start);
				NumericVersion min=max;
				int minFoundAt=start;
				for (int i=start+1;i<versions.size();i++) {
					NumericVersion current=versions.get(i);
					if (current.isNextOrPrevPatch(min)) {
						min=current;
						minFoundAt=i;
					}
				}
				ranges.add(VersionRange.of(min, max));
				start=minFoundAt+1;
			}
		}
		return ranges;
	}

	static String asString(NumericVersion version) {
		return version.major() + "." + version.minor() + "." + version.patch();
	}

	static void dump(List<ParsedVersion> versions) {
		versions.forEach(version -> {
			System.out.println(version.version);
			version.dists.forEach(dist -> {
				System.out.println(" " + dist.name);
				dist.urls.forEach(packageUrl -> {
					System.out.println("  " + packageUrl.url);
				});
			});
		});
	}

	static class ParsedUrl {
		final String url;

		public ParsedUrl(String url) {
			this.url = url;
		}
	}

	static class ParsedDist {
		final String name;
		final List<ParsedUrl> urls;

		public ParsedDist(String name, List<ParsedUrl> urls) {
			this.name = name;
			this.urls = urls;
		}
	}

	static class ParsedVersion implements Comparable<ParsedVersion> {
		final String version;
		final List<ParsedDist> dists;

		public ParsedVersion(String version, List<ParsedDist> dists) {
			this.version = version;
			this.dists = dists;
		}

		@Override
		public int compareTo(ParsedVersion other) {
			return -1 * NumericVersion.of(version).compareTo(NumericVersion.of(other.version));
		}
	}

	static class ParsedVersions {
		private final List<ParsedVersion> list;

		public ParsedVersions(List<ParsedVersion> list) {
			this.list = list;
		}

		public Set<String> names() {
			return list.stream()
				.flatMap(it -> it.dists.stream().map(dist -> dist.name))
				.sorted(Comparator.naturalOrder())
				.collect(Collectors.toCollection(LinkedHashSet::new));
		}

		static List<ParsedVersion> filter(List<ParsedVersion> src,	Predicate<ParsedDist> distFilter) {
			return src.stream()
				.map(version -> {
					List<ParsedDist> filtered = version.dists.stream().filter(distFilter).collect(Collectors.toList());
					return new ParsedVersion(version.version, filtered);
				})
				.collect(Collectors.toList());
		}

		public ParsedVersions filterByName(String name) {
			return new ParsedVersions(filter(list, it -> it.name.equals(name)));
		}
		
		@Deprecated
		public List<ParsedVersion> list() {
			return list;
		}

		public Map<String, List<ParsedVersion>> groupByVersionLessUrl() {
			return AbstractPackageHtmlParser.groupByVersionLessUrl(list);
		}

		public List<PlatformVersions> groupedByPlatform() {
			return names()
				.stream()
				.map(name -> new PlatformVersions(name, urlAndVersions(filterByName(name))))
				.collect(Collectors.toList());
		}

		private static List<UrlAndVersions> urlAndVersions(ParsedVersions filtered) {
			ImmutableSetMultimap<String, String> x = filtered.list.stream()
				.flatMap(parsedVersion -> parsedVersion.dists
					.stream()
					.flatMap(parsedDist -> parsedDist.urls
						.stream()
						.map(parsedUrl -> parsedUrl.url))
					.collect(Collectors.toMap(Function.identity(), entry -> parsedVersion.version))
					.entrySet().stream())
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(entry -> entry.getKey().replace(entry.getValue(),"{version}"), Map.Entry::getValue));

			return x.asMap().entrySet().stream()
				.map(entry -> new UrlAndVersions(entry.getKey(), ImmutableSet.copyOf(entry.getValue())))
				.collect(Collectors.toList());
		}

		private static Set<String> versionsForUrl(List<ParsedVersion> list, String url) {
			return list.stream()
				.filter(parsedVersion -> parsedVersion.dists.stream().anyMatch(parsedDist -> parsedDist.urls.stream().anyMatch(parsedUrl -> parsedUrl.url.equals(url))))
				.map(parsedVersion -> parsedVersion.version)
				.sorted()
				.collect(Collectors.toCollection(LinkedHashSet::new));
		}
	}

	static class UrlAndVersions {
		private final String url;
		private final Set<String> versions;

		public UrlAndVersions(String url, Set<String> versions) {
			this.url = url;
			this.versions = versions;
		}

		public String url() {
			return url;
		}

		public Set<String> versions() {
			return versions;
		}

		@Override public String toString() {
			return "UrlAndVersions{" +
				"url='" + url + '\'' +
				", versions=" + versions +
				'}';
		}
	}

	static class PlatformVersions {
		private final String name;
		private final List<UrlAndVersions> urlAndVersions;

		public PlatformVersions(String name, List<UrlAndVersions> urlAndVersions) {
			this.name = name;
			this.urlAndVersions = urlAndVersions;
		}

		public String name() {
			return name;
		}

		public List<UrlAndVersions> urlAndVersions() {
			return urlAndVersions;
		}
	}
}
