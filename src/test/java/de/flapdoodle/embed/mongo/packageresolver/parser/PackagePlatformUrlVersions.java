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
import de.flapdoodle.types.Pair;
import org.immutables.value.Value;

import java.util.List;
import java.util.Optional;
import java.util.SortedMap;
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
			System.out.println("  "+platform);
			urlVersions.dump();
		});
	}

	public List<Pair<PackagePlatform, UrlVersions>> entries() {
		return map().entrySet().stream()
			.map(it -> Pair.of(it.getKey(), it.getValue()))
			.collect(Collectors.toList());
	}

	public static ImmutablePackagePlatformUrlVersions of(PackageOsAndVersionType osAndVersionType) {
		return ImmutablePackagePlatformUrlVersions.of(osAndVersionType);
	}
}
