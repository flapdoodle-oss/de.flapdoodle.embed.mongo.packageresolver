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
package de.flapdoodle.embed.mongo.packageresolver.parser;

import com.google.common.collect.*;
import de.flapdoodle.os.Version;
import de.flapdoodle.types.Pair;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.immutables.value.Value;

import java.util.*;
import java.util.stream.Collectors;

@Value.Immutable
public abstract class PackagePlatformUrlVersions {
	@Value.Parameter
	public abstract PackageOsAndVersionType osAndVersionType();

	@Value.NaturalOrder
	public abstract SortedMap<PackagePlatform, UrlVersions> map();

	@Value.Auxiliary
	public PackagePlatformUrlVersions add(PackagePlatform platform, PackageVersion version, String normalizedUrl) {
		UrlVersions current = Optional.ofNullable(map().get(platform))
			.orElseGet(UrlVersions::empty)
			.put(normalizedUrl, version);

		return ImmutablePackagePlatformUrlVersions.builder()
			.osAndVersionType(osAndVersionType())
			.putMap(platform, current)
			.putAllMap(Maps.filterEntries(map(), it -> !it.getKey().equals(platform)))
			.build();
	}

	@Value.Auxiliary
	public PackagePlatformUrlVersions addTools(PackagePlatform platform, String version, String normalizedUrl) {
		UrlVersions current = Optional.ofNullable(map().get(platform))
			.orElseGet(UrlVersions::empty)
			.putTools(normalizedUrl, version);

		return ImmutablePackagePlatformUrlVersions.builder()
			.osAndVersionType(osAndVersionType())
			.putMap(platform, current)
			.putAllMap(Maps.filterEntries(map(), it -> !it.getKey().equals(platform)))
			.build();
	}

	@Value.Auxiliary
	public void dump() {
		map().forEach((platform, urlVersions) -> {
			System.out.println("  " + platform);
			urlVersions.dump();
		});
	}

	@Value.Lazy
	public List<Pair<PackagePlatform, UrlVersions>> entries() {
		// TODO remove this
		filteredEntries();

		return map().entrySet().stream()
			.map(it -> Pair.of(it.getKey(), it.getValue()))
			.collect(Collectors.toList());
	}

	@Value.Lazy
	public List<Pair<PackagePlatform, UrlVersions>> filteredEntries() {
		System.out.println("---------------------");
		System.out.println(osAndVersionType());
		System.out.println("---------------------");
		return filterUnreachableVersions(map().entrySet().stream()
			.map(it -> Pair.of(it.getKey(), it.getValue()))
			.collect(Collectors.toList()));
	}

	public static ImmutablePackagePlatformUrlVersions of(PackageOsAndVersionType osAndVersionType) {
		return ImmutablePackagePlatformUrlVersions.of(osAndVersionType);
	}

	private static List<Pair<PackagePlatform, UrlVersions>> filterUnreachableVersions(List<Pair<PackagePlatform, UrlVersions>> src) {
		Multimap<PackageVersion, PackagePlatform> versionMatchedByPlatform = Multimaps.newMultimap(new LinkedHashMap<>(), LinkedHashSet::new);

		ImmutableList.Builder<Pair<PackagePlatform, UrlVersions>> builder=ImmutableList.builder();

		for (Pair<PackagePlatform, UrlVersions> packageAndUrlVersions : src) {
			PackagePlatform platform = packageAndUrlVersions.first();
			boolean debug=false && platform.version().isPresent() && platform.version().get().name().contains("Ubuntu");

			List<PackagePlatform> expandedPlatforms = expandVersions(platform);
			UrlVersions urlVersions = packageAndUrlVersions.second();

			if (debug) System.out.println("- - - - - - - -");
			if (debug) System.out.println(platform);
			if (debug) System.out.println("- - - - - - - -");

			urlVersions.entries().forEach(pair -> {
				String url=pair.first();
				PackageVersions versions = pair.second();
				
				if (debug) System.out.println("- - - - - - - -");
				if (debug) System.out.println(url);
				if (debug) System.out.println("- - - - - - - -");

				versions.list().forEach(version -> {
					List<PackagePlatform> platformsStilActive = expandedPlatforms.stream()
						.filter(p -> !versionMatchedByPlatform.containsKey(version) || !versionMatchedByPlatform.get(version).contains(p))
						.collect(Collectors.toList());

					if (!platformsStilActive.isEmpty()) {
						if (debug) System.out.println("version " + version + " still active for " + platformsStilActive);

						platformsStilActive.forEach(p -> {
							versionMatchedByPlatform.put(version, p);
						});
					} else {
						if (debug) System.out.println("version " + version + " not active for any platform");
					}
				});
			});
		}

		if (false) {
			return builder.build();
		}

//		Multimap<PackageVersion, PackagePlatform> versionFirstUsed = Multimaps.newMultimap(new LinkedHashMap<>(), LinkedHashSet::new);
//		Multimap<String, PackagePlatform> toolVersionFirstUsed = Multimaps.newMultimap(new LinkedHashMap<>(), LinkedHashSet::new);
//
//		List<Pair<PackagePlatform, UrlVersions>> ret=new ArrayList<>();
//
//		for (Pair<PackagePlatform, UrlVersions> packageAndUrlVersions : src) {
//			List<PackagePlatform> platforms = expandVersions(packageAndUrlVersions.first());
//			UrlVersions urlVersions = packageAndUrlVersions.second();
//
//			UrlVersions fixedUrlVersions = UrlVersions.empty();
//
//			for (Map.Entry<String, Collection<PackageVersion>> entry : urlVersions.map().asMap().entrySet()) {
//				String url = entry.getKey();
//				System.out.println("url -> "+url);
//				Collection<PackageVersion> packageVersions = entry.getValue();
//
//				for (PackageVersion packageVersion : packageVersions) {
//					if (!versionFirstUsed.containsKey(packageVersion)) {
//						System.out.println(packageVersion+" first used in "+platforms);
//						fixedUrlVersions = fixedUrlVersions.put(url, packageVersion);
//
//						for (PackagePlatform platform : platforms) {
//							versionFirstUsed.put(packageVersion, platform);
//						}
//					} else {
//						// TODO filter platforms
//						System.out.println(packageVersion+" for "+packageAndUrlVersions.first()+" already used in "+versionFirstUsed.get(packageVersion));
//					}
//				}
//			}
//			System.out.println("- - - - - - - -");
//		}

		return src;
	}

	private static List<PackagePlatform> expandVersions(PackagePlatform src) {
		List<Version> versions = src.versions();
		return versions.isEmpty()
			? Collections.singletonList(src)
			: versions.stream()
				.map(v -> ImmutablePackagePlatform.copyOf(src)
					.withVersion(v)
					.withVersions())
				.collect(Collectors.toList());
	}
}
