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
package de.flapdoodle.embed.mongo.packageresolver.linux;

import de.flapdoodle.embed.mongo.packageresolver.*;
import de.flapdoodle.embed.process.config.store.Package;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.os.*;
import de.flapdoodle.os.linux.DebianVersion;
import de.flapdoodle.os.linux.UbuntuVersion;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * remove if mongodb provides packages for debian arm
 */
public class DebianUsesUbuntuOnArmPackageFinder implements PackageFinder, HasExplanation, HasLabel {

	private final UbuntuFallbackToOlderVersionPackageFinder ubuntuPackageFinder;

	public DebianUsesUbuntuOnArmPackageFinder(UbuntuFallbackToOlderVersionPackageFinder ubuntuPackageFinder) {
		this.ubuntuPackageFinder = ubuntuPackageFinder;
	}

	@Override
	public Optional<Package> packageFor(Distribution distribution) {
		if (platformMatch().match(distribution)) {
			if (!distribution.platform().version().isPresent()) throw new RuntimeException("version not set: "+distribution);
			Version currentVersion = distribution.platform().version().get();
			Optional<UbuntuVersion> ubuntuVersion = matchingUbuntuVersion((DebianVersion) currentVersion);

			if (ubuntuVersion.isPresent()) {
				Distribution asUbuntudistribution = Distribution.of(distribution.version(),
					ImmutablePlatform.copyOf(distribution.platform()).withVersion(ubuntuVersion));
				return ubuntuPackageFinder.packageFor(asUbuntudistribution);
			}
		}

		return Optional.empty();
	}

	@Override
	public String explain() {
		List<UbuntuVersion> ubuntuVersions = Stream.of(DebianVersion.DEBIAN_12, DebianVersion.DEBIAN_13)
			.map(DebianUsesUbuntuOnArmPackageFinder::matchingUbuntuVersion)
			.filter(Optional::isPresent)
			.map(Optional::get)
			.distinct()
			.collect(Collectors.toList());

		return ubuntuVersions.stream()
			.map(uv -> Arrays.stream(DebianVersion.values())
				.filter(v -> {
					Optional<UbuntuVersion> ubuntuVersion = matchingUbuntuVersion(v);
					return ubuntuVersion.isPresent() && ubuntuVersion.get() == uv;
				})
				.map(DebianVersion::name)
				.collect(Collectors.joining(", ", uv.name() + " for ", "")))
			.collect(Collectors.joining(" and ", "use '"+ubuntuPackageFinder.label()+"' with ", ""));
	}

	public static ImmutablePlatformMatch platformMatch() {
		return PlatformMatch.withOs(CommonOS.Linux)
			.withCpuType(CPUType.ARM)
			.withBitSize(BitSize.B64)
			.withVersion(DebianVersion.DEBIAN_12, DebianVersion.DEBIAN_13);
	}

	private static Optional<UbuntuVersion> matchingUbuntuVersion(DebianVersion debianVersion) {
		switch (debianVersion) {
			case DEBIAN_12:
			case DEBIAN_13:
				return Optional.of(UbuntuVersion.Ubuntu_22_04);
			default:
				return Optional.empty();
		}
	}

	@Override
	public String label() {
		return getClass().getSimpleName();
	}
}
