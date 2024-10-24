/*
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
import com.google.common.io.Resources;
import de.flapdoodle.types.Pair;
import de.flapdoodle.types.Try;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public abstract class MongoPackages {

	private static List<ParsedVersion> mergeAll(List<List<ParsedVersion>> allVersions) {
		List<ParsedVersion> flatmapped = allVersions.stream().flatMap(it -> it.stream()).collect(Collectors.toList());

		Set<Pair<String, Boolean>> versions = flatmapped.stream()
			.map(it -> Pair.of(it.version, it.isDevVersion))
			.collect(Collectors.toSet());

		return versions.stream().map(pair -> new ParsedVersion(pair.first(), pair.second(), mergeDists(pair.first(), pair.second(), flatmapped)))
			.collect(Collectors.toList());
	}

	private static List<ParsedDist> mergeDists(String v, boolean isDevVersion, List<ParsedVersion> src) {
		List<ParsedDist> matchingDists = src.stream()
			.filter(pv -> v.equals(pv.version) && isDevVersion==pv.isDevVersion)
			.flatMap(pv -> pv.dists.stream())
			.collect(Collectors.toList());

		return groupByName(matchingDists);
	}

	private static List<ParsedDist> groupByName(List<ParsedDist> matchingDists) {
		Map<String, List<ParsedDist>> groupedByName = matchingDists.stream()
			.collect(Collectors.groupingBy(pd -> pd.name));

		return groupedByName.entrySet().stream()
			.map(entry -> new ParsedDist(entry.getKey(), entry.getValue().stream()
				.flatMap(it -> it.urls.stream()
					.map(u -> u.url))
				.collect(Collectors.toSet())
				.stream()
				.map(url -> new ParsedUrl(url))
				.collect(Collectors.toList())))
			.collect(Collectors.toList());
	}

	private static List<Pair<String, Boolean>> mongoDbVersions() {
		List<Pair<String, Boolean>> resources = Arrays.asList(
			Pair.of("versions/react/mongo-db-legacy-versions.html", false),
			Pair.of("versions/react/mongo-db-versions-2021-10-28.html", false),
			Pair.of("versions/react/mongo-db-versions-2022-01-16.html", false),
			Pair.of("versions/react/mongo-db-versions-2022-03-30.html", false),
			Pair.of("versions/react/mongo-db-versions-2022-09-25.html", false),
			Pair.of("versions/react/mongo-db-versions-2022-10-14.html", false),
			Pair.of("versions/react/mongo-db-versions-2022-11-27.html", false),
			Pair.of("versions/react/mongo-db-versions-2023-02-12.html", false),
			Pair.of("versions/react/mongo-db-versions-2023-03-16.html", false),
			Pair.of("versions/react/mongo-db-versions-2023-05-21.html", false),
			Pair.of("versions/react/mongo-db-versions-2023-05-21-dev.html", true),
			Pair.of("versions/react/mongo-db-versions-2023-05-30-dev.html", true),
			Pair.of("versions/react/mongo-db-versions-2023-07-25.html", false),
			Pair.of("versions/react/mongo-db-versions-2023-07-25-dev.html", true),
			Pair.of("versions/react/mongo-db-versions-2023-08-07-archive.html", false),
			Pair.of("versions/react/mongo-db-versions-2023-08-07-dev.html", true),
			Pair.of("versions/react/mongo-db-versions-2023-08-19.html", false),
			Pair.of("versions/react/mongo-db-versions-2023-08-19-archive.html", false),
			Pair.of("versions/react/mongo-db-versions-2023-10-30.html", false),
			Pair.of("versions/react/mongo-db-versions-2023-10-30-archive.html", false),
			Pair.of("versions/react/mongo-db-versions-2023-10-30-dev.html", true),
			Pair.of("versions/react/mongo-db-versions-2023-12-08.html", false),
			Pair.of("versions/react/mongo-db-versions-2023-12-08-archive.html", false),
			Pair.of("versions/react/mongo-db-versions-2023-12-08-dev.html", true),
			Pair.of("versions/react/mongo-db-versions-2024-04-03.html", false),
			Pair.of("versions/react/mongo-db-versions-2024-04-03-archive.html", false),
			Pair.of("versions/react/mongo-db-versions-2024-04-03-dev.html", true),
			Pair.of("versions/react/mongo-db-versions-2024-04-05.html", false),
			Pair.of("versions/react/mongo-db-versions-2024-04-30.html", false),
			Pair.of("versions/react/mongo-db-versions-2024-04-30-archive.html", false),
			Pair.of("versions/react/mongo-db-versions-2024-04-30-dev.html", true),
			Pair.of("versions/react/mongo-db-versions-2024-06-10.html", false),
			Pair.of("versions/react/mongo-db-versions-2024-06-10-archive.html", false),
			Pair.of("versions/react/mongo-db-versions-2024-06-10-dev.html", true),
			Pair.of("versions/react/mongo-db-versions-2024-07-04.html", false),
			Pair.of("versions/react/mongo-db-versions-2024-07-04-archive.html", false),
			Pair.of("versions/react/mongo-db-versions-2024-07-04-dev.html", true),
			Pair.of("versions/react/mongo-db-versions-2024-10-24.html", false),
			Pair.of("versions/react/mongo-db-versions-2024-10-24-archive.html", false),
			Pair.of("versions/react/mongo-db-versions-2024-10-24-dev.html", true)
		);

		return resources;
	}

	public static ParsedVersions allDbVersions() {
		List<List<ParsedVersion>> allVersions = dbVersionsList();

		return new ParsedVersions(mergeAll(allVersions));
	}

	public static List<List<ParsedVersion>> dbVersionsList() {
		return mongoDbVersions().stream()
			.map(it -> it.mapFirst(path -> Try.supplier(() -> Resources.toString(Resources.getResource(path), StandardCharsets.UTF_8))
				.mapToUncheckedException(RuntimeException::new)
				.get()))
			.map(it -> it.mapFirst(Jsoup::parse))
			.map(it -> MongoPackages.parseDBVersions(it.first(), it.second()))
			.collect(Collectors.toList());
	}

	private static List<Pair<String, Boolean>> mongoToolsVersions() {
		List<Pair<String, Boolean>> resources = Arrays.asList(
			Pair.of("versions/react/mongotools-versions-2021-10-28.html", false),
			Pair.of("versions/react/mongotools-versions-2022-09-25.html", false),
			Pair.of("versions/react/mongotools-versions-2022-10-14.html", false),
			Pair.of("versions/react/mongotools-versions-2022-11-27.html", false),
			Pair.of("versions/react/mongotools-versions-2023-02-12.html", false),
			Pair.of("versions/react/mongotools-versions-2023-03-16.html", false),
			Pair.of("versions/react/mongotools-versions-2023-07-25.html", false),
			Pair.of("versions/react/mongotools-versions-2023-08-19.html", false),
			Pair.of("versions/react/mongotools-versions-2023-10-30.html", false),
			Pair.of("versions/react/mongotools-versions-2023-12-08.html", false),
			Pair.of("versions/react/mongotools-versions-2024-04-03.html", false),
			Pair.of("versions/react/mongotools-versions-2024-04-30.html", false),
			Pair.of("versions/react/mongotools-versions-2024-06-10.html", false),
			Pair.of("versions/react/mongotools-versions-2024-07-04.html", false),
			Pair.of("versions/react/mongotools-versions-2024-10-24.html", false)
		);

		return resources;
	}

	public static ParsedVersions allToolsVersions() {
		List<List<ParsedVersion>> allVersions = toolsVersionsList();

		return new ParsedVersions(mergeAll(allVersions));
	}

	public static List<List<ParsedVersion>> toolsVersionsList() {
		return mongoToolsVersions().stream()
			.map(it -> it.mapFirst(path -> Try.supplier(() -> Resources.toString(Resources.getResource(path), StandardCharsets.UTF_8))
				.mapToUncheckedException(RuntimeException::new)
				.get()))
			.map(it -> it.mapFirst(Jsoup::parse))
			.map(it -> MongoPackages.parseToolsVersions(it.first(), it.second()))
			.collect(Collectors.toList());
	}

	static List<ParsedVersion> parseDBVersions(Document document, boolean isDevVersion) {
		List<ParsedVersion> versions = new ArrayList<>();
		Elements divs = document.select("div > div");
		for (Element div : divs) {
//      System.out.println("----------------");
			Element versionElement = div.selectFirst("h3");
			if (versionElement != null) {
				String version = versionElement.text();
//        System.out.println("Version: " + version);
//        System.out.println(div);
				List<ParsedDist> parsedDists = new ArrayList<>();
				Elements entries = div.select("div > ul > li");
				for (Element entry : entries) {
//          System.out.println("- - - - - - -");
					String name = entry.selectFirst("li > p").text();
//          System.out.println(" Name: " + name);
//          System.out.println(entry);
					List<ParsedUrl> parsedUrls = new ArrayList<>();
					Elements platforms = entry.select("li > ul > li");
					for (Element platform : platforms) {
//            System.out.println("~~~~~~~~");
//            System.out.println(platform);
						Elements packages = platform.select("li > p");
						for (Element ppackage : packages) {
							if (ppackage.text().startsWith("Archive:")) {
//                System.out.println("*********");
//                System.out.println(ppackage);
								Element urlElement = ppackage.selectFirst("a");
								String platFormUrl = urlElement.attr("href");
//                System.out.println("  Url: "+platFormUrl);
								parsedUrls.add(new ParsedUrl(platFormUrl));
							}
						}
					}
					parsedDists.add(new ParsedDist(name, parsedUrls));
				}
				versions.add(new ParsedVersion(version, isDevVersion, parsedDists));
			} else {
//        System.out.println("##############");
//        System.out.println(div);
			}
		}
		return versions;
	}
	
	static List<ParsedVersion> parseToolsVersions(Document document, boolean isDevVersion) {
		List<ParsedVersion> versions = new ArrayList<>();
		Elements divs = document.select("div > div");
		for (Element div : divs) {
//      System.out.println("----------------");
			Element versionElement = div.selectFirst("h3");
			if (versionElement != null) {
				String version = versionElement.text();
//        System.out.println("Version: " + version);
//        System.out.println(div);
				List<ParsedDist> parsedDists = new ArrayList<>();
				Elements entries = div.select("div > ul > li");
				for (Element entry : entries) {
//          System.out.println("- - - - - - -");
					String name = entry.selectFirst("li > p").text();
//          System.out.println(" Name: " + name);
//          System.out.println(entry);
					List<ParsedUrl> parsedUrls = new ArrayList<>();
					Elements platforms = entry.select("li > ul > li");
					for (Element platform : platforms) {
//            System.out.println("~~~~~~~~");
//            System.out.println(platform);
						Elements packages = platform.select("li");
						for (Element ppackage : packages) {
							if (ppackage.text().startsWith("Archive:")) {
//                System.out.println("*********");
//                System.out.println(ppackage);
								Element urlElement = ppackage.selectFirst("a");
								String platFormUrl = urlElement.attr("href");
//                System.out.println("  Url: "+platFormUrl);
								parsedUrls.add(new ParsedUrl(platFormUrl));
							}
						}
					}
					parsedDists.add(new ParsedDist(name, parsedUrls));
				}
				versions.add(new ParsedVersion(version, isDevVersion, parsedDists));
			} else {
//        System.out.println("##############");
//        System.out.println(div);
			}
		}
		return versions;
	}
	static void versionAndUrl(ParsedVersions versions) {
		versions
			.list()
			.stream()
			.sorted()
			.forEach(version -> {
				if (!version.dists.isEmpty()) {
					System.out.print(version.version);
					if (version.isDevVersion) System.out.print("(DEV)");
					System.out.println();
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
			boolean noDownloadUrl = url.isEmpty();
			System.out.println(noDownloadUrl ? "* --" : url);
			//String versionNumbers = versionList.stream().map(it -> it.version).collect(Collectors.joining(", "));
			List<String> versionNumbers = versionNumbers(versionList, false);
			String compressedVersions = compressedVersionAsString(versionNumbers);
			if (!compressedVersions.isEmpty()) {
				System.out.print(noDownloadUrl ? "* " : "");
				System.out.println(compressedVersions);
			}

			List<String> devVersionNumbers = versionNumbers(versionList, true);
			String compressedDevVersions = compressedVersionAsString(devVersionNumbers);
			if (!compressedDevVersions.isEmpty()) {
				System.out.print(noDownloadUrl ? "* " : "");
				System.out.print(compressedDevVersions);
				System.out.println(" (DEV)");
			}
		});
	}
	private static String compressedVersionAsString(List<String> versionNumbers) {
		return rangesAsString(compressedVersionsList(versionNumbers));
	}
	
	public static String rangesAsString(List<VersionRange> ranges) {
		return ranges.stream()
			.sorted(Comparator.comparing(VersionRange::min).reversed())
			.map(r -> r.min().equals(r.max())
				? quoted(asString(r.min()))
				: quoted(asString(r.min()) + " -> " + asString(r.max())))
			.collect(Collectors.joining(", "));
	}
	private static String quoted(String src) {
		return "\""+src+"\"";
	}
	static List<String> versionNumbers(List<ParsedVersion> versions, boolean devVersions) {
		return versions.stream()
			.filter(it -> it.isDevVersion == devVersions)
			.map(it -> it.version)
			.collect(Collectors.toList());
	}
	static Map<String, List<ParsedVersion>> groupByVersionLessUrl(List<ParsedVersion> versions) {
		Map<String, List<ParsedVersion>> groupedByVersionLessUrl = versions.stream()
			.sorted()
			.collect(Collectors.groupingBy(version -> {
				List<String> urls = version.dists.stream()
					.flatMap(dist -> dist.urls.stream())
					.map(packageUrl -> packageUrl.url)
					.sorted()
					.collect(Collectors.toList());

				String versionLessUrl = urls.stream().map(it -> it.replace(version.version, "{}"))
					.collect(Collectors.joining("|"));

				return versionLessUrl;
			}));
		return groupedByVersionLessUrl;
	}

	public static List<VersionRange> compressedVersionsList(Collection<String> numericVersions) {
		List<NumericVersion> versions = numericVersions.stream()
			.map(NumericVersion::of)
			.sorted(Comparator.reverseOrder())
			.collect(Collectors.toList());

		List<VersionRange> ranges = new ArrayList<>();
		if (!versions.isEmpty()) {
			int start=0;
			while (start<versions.size()) {
				NumericVersion max=versions.get(start);
				if (!max.build().isPresent()) {
					NumericVersion min = max;
					int minFoundAt = start;
					for (int i = start + 1; i < versions.size(); i++) {
						NumericVersion current = versions.get(i);
						if (current.isNextOrPrevPatch(min) && !current.build().isPresent()) {
							min = current;
							minFoundAt = i;
						}
					}
					ranges.add(VersionRange.of(min, max));
					start = minFoundAt + 1;
				} else {
					ranges.add(VersionRange.of(max, max));
					start++;
				}
			}
		}
		return ranges;
	}
	
	public static String asString(NumericVersion version) {
		if (version.build().isPresent()) {
			return version.major() + "." + version.minor() + "." + version.patch()+"-"+version.build().get();
		}
		return version.major() + "." + version.minor() + "." + version.patch();
	}
	private static Set<Map.Entry<String, Pair<String, Boolean>>> urlToVersionSet(ParsedVersion parsedVersion) {
		Set<Map.Entry<String, Pair<String, Boolean>>> ret = parsedVersion.dists
			.stream()
			.flatMap(parsedDist -> parsedDist.urls
				.stream()
				.map(parsedUrl -> parsedUrl.url)
				.collect(Collectors.toSet())
				.stream())
			.collect(Collectors.toMap(Function.identity(), entry -> Pair.of(parsedVersion.version, parsedVersion.isDevVersion)))
			.entrySet();

		return ret;
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

			Set<String> urlsAsSet = urls.stream().map(p -> p.url).collect(Collectors.toSet());
			if (urlsAsSet.size()!= urls.size()) {
				throw new IllegalArgumentException("url collisions: "+urls+"("+urlsAsSet+")");
			}
		}

		public String singleUrl() {
			if (urls.size()!=1) {
				throw new IllegalArgumentException("more or less than one url: "+urls);
			}
			return urls.get(0).url;
		}
	}

	static class ParsedVersion implements Comparable<ParsedVersion> {
		final String version;
		final boolean isDevVersion;
		final List<ParsedDist> dists;

		public ParsedVersion(String version, boolean isDevVersion, List<ParsedDist> dists) {
			this.version = version;
			this.isDevVersion = isDevVersion;
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
					return new ParsedVersion(version.version, version.isDevVersion, filtered);
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
			return MongoPackages.groupByVersionLessUrl(list);
		}

		public List<PlatformVersions> groupedByPlatform() {
			return names()
				.stream()
				.map(name -> new PlatformVersions(name, urlAndVersions(filterByName(name))))
				.collect(Collectors.toList());
		}

		private static List<UrlAndVersions> urlAndVersions(ParsedVersions filtered) {
			ImmutableSetMultimap<String, Pair<String, Boolean>> x = filtered.list.stream()
				.flatMap(parsedVersion -> urlToVersionSet(parsedVersion).stream())
				.collect(ImmutableSetMultimap.toImmutableSetMultimap(entry -> entry.getKey().replace(entry.getValue().first(),"{version}"), Map.Entry::getValue));

			return x.asMap().entrySet().stream()
				.map(entry -> new UrlAndVersions(entry.getKey(), ImmutableSet.copyOf(entry.getValue())))
				.collect(Collectors.toList());
		}
	}

	static class UrlAndVersions {
		private final String url;
		private final Set<Pair<String, Boolean>> versions;

		public UrlAndVersions(String url, Set<Pair<String, Boolean>> versions) {
			this.url = url;
			this.versions = versions;
		}

		public String url() {
			return url;
		}

		public Set<Pair<String, Boolean>> versions() {
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
