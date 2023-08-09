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

import de.flapdoodle.embed.mongo.packageresolver.MongoPackages;
import de.flapdoodle.embed.mongo.packageresolver.NumericVersion;
import de.flapdoodle.embed.mongo.packageresolver.VersionRange;
import org.immutables.value.Value;

import java.util.Comparator;
import java.util.List;
import java.util.SortedSet;
import java.util.stream.Collectors;

@Value.Immutable
public abstract class PackageVersions {
	@Value.NaturalOrder
	public abstract SortedSet<PackageVersion> list();

	@Value.Derived
	public boolean hasDevVersions() {
		return list().stream().anyMatch(PackageVersion::devVersion);
	}

	@Value.Derived
	public boolean hasVersions() {
		return list().stream().anyMatch(version -> !version.devVersion());
	}

	@Value.Auxiliary
	public boolean hasVersionOlderThan(NumericVersion version) {
		return list().stream().anyMatch(v -> NumericVersion.of(v.version()).isOlder(version));
	}

	@Value.Auxiliary
	public List<VersionRange> versionRanges(boolean devVersion) {
//		String versionList = MongoPackages.rangesAsString(ranges);
		return MongoPackages.compressedVersionsList(
			list()
				.stream().filter(it -> it.devVersion()==devVersion)
				.map(PackageVersion::version)
				.collect(Collectors.toSet()))
			.stream()
			.sorted(Comparator.comparing(VersionRange::min).reversed())
			.collect(Collectors.toList());
	}

	public static PackageVersions of(Iterable<? extends PackageVersion> list) {
		return ImmutablePackageVersions.builder()
			.addAllList(list)
			.build();
	}
}
