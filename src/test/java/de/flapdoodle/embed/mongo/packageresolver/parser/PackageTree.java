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
package de.flapdoodle.embed.mongo.packageresolver.parser;

import com.google.common.collect.Maps;
import org.immutables.value.Value;

import java.util.Optional;
import java.util.SortedMap;
import java.util.SortedSet;

@Value.Immutable
public abstract class PackageTree {
	@Value.NaturalOrder
	public abstract SortedMap<PackageOsAndVersionType, PackagePlatformUrlVersions> map();
	@Value.NaturalOrder
	public abstract SortedSet<String> skipped();
	@Value.NaturalOrder
	public abstract SortedSet<String> skippedTools();

	@Value.Auxiliary
	public PackageTree add(PackagePlatform platform, String version, boolean devVersion, String normalizedUrl) {
		PackageOsAndVersionType osAndVersionType = PackageOsAndVersionType.of(platform);

		PackagePlatformUrlVersions current =  Optional.ofNullable(map().get(osAndVersionType))
			.orElseGet(() -> PackagePlatformUrlVersions.of(osAndVersionType))
			.add(platform, PackageVersion.of(version, devVersion), normalizedUrl);

		return ImmutablePackageTree.builder()
			.putAllMap(Maps.filterEntries(map(), it -> !it.getKey().equals(osAndVersionType)))
			.putMap(osAndVersionType, current)
			.addAllSkipped(skipped())
			.build();
	}

	@Value.Auxiliary
	public PackageTree addTools(PackagePlatform platform, String version, String normalizedUrl) {
		PackageOsAndVersionType osAndVersionType = PackageOsAndVersionType.of(platform);

		PackagePlatformUrlVersions current =  Optional.ofNullable(map().get(osAndVersionType))
			.orElseGet(() -> PackagePlatformUrlVersions.of(osAndVersionType))
			.addTools(platform, version, normalizedUrl);

		return ImmutablePackageTree.builder()
			.putAllMap(Maps.filterEntries(map(), it -> !it.getKey().equals(osAndVersionType)))
			.putMap(osAndVersionType, current)
			.addAllSkipped(skipped())
			.build();
	}

	public PackageTree skip(String name) {
		return ImmutablePackageTree.builder()
			.from(this)
			.addSkipped(name)
			.build();
	}

	public PackageTree skipTools(String name) {
		return ImmutablePackageTree.builder()
			.from(this)
			.addSkippedTools(name)
			.build();
	}

	@Value.Auxiliary
	public void dump() {
		map().forEach((osAndVersionType, packagePlatformUrlVersions) -> {
			System.out.println(osAndVersionType);
			packagePlatformUrlVersions.dump();
		});

		System.out.println("-----------------------");
		System.out.println("skipped: "+skipped());
		System.out.println("skippedTools: "+skippedTools());
		System.out.println("-----------------------");
	}

	public static ImmutablePackageTree empty() {
		return ImmutablePackageTree.builder().build();
	}
}
