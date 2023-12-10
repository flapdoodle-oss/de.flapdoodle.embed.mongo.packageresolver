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

import de.flapdoodle.embed.mongo.packageresolver.HasExplanation;
import de.flapdoodle.embed.mongo.packageresolver.PackageFinder;
import de.flapdoodle.embed.mongo.packageresolver.PlatformMatch;
import de.flapdoodle.embed.process.config.store.Package;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.os.CommonOS;
import de.flapdoodle.os.ImmutablePlatform;
import de.flapdoodle.os.Version;
import de.flapdoodle.os.linux.LinuxMintVersion;
import de.flapdoodle.os.linux.PopOSVersion;
import de.flapdoodle.os.linux.UbuntuVersion;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class PopOSPackageFinder implements PackageFinder, HasExplanation {

	private final UbuntuFallbackToOlderVersionPackageFinder ubuntuPackageFinder;

	public PopOSPackageFinder(UbuntuFallbackToOlderVersionPackageFinder ubuntuPackageFinder) {
		this.ubuntuPackageFinder = ubuntuPackageFinder;
	}

	@Override
	public Optional<Package> packageFor(Distribution distribution) {
		if (PlatformMatch.withOs(CommonOS.Linux).withVersion(PopOSVersion.values()).match(distribution)) {
			if (!distribution.platform().version().isPresent()) throw new RuntimeException("version not set: "+distribution);
			Version currentVersion = distribution.platform().version().get();
			if (currentVersion instanceof PopOSVersion) {
				Distribution asUbuntudistribution = Distribution.of(distribution.version(),
					ImmutablePlatform.copyOf(distribution.platform()).withVersion(((PopOSVersion) currentVersion).matchingUbuntuVersion()));
				return ubuntuPackageFinder.packageFor(asUbuntudistribution);
			} else {
				throw new IllegalArgumentException("Version is not a "+PopOSVersion.class+": "+currentVersion);
			}
		}

		return Optional.empty();
	}

	@Override
	public String explain() {
		List<UbuntuVersion> ubuntuVersions = Arrays.stream(PopOSVersion.values()).map(PopOSVersion::matchingUbuntuVersion)
			.distinct()
			.collect(Collectors.toList());

		return ubuntuVersions.stream()
			.map(uv -> Arrays.stream(PopOSVersion.values())
				.filter(v -> v.matchingUbuntuVersion() == uv)
				.map(PopOSVersion::name)
				.collect(Collectors.joining(", ", uv.name() + " for ", "")))
			.collect(Collectors.joining(" and ", "use '"+ubuntuPackageFinder.label()+"' with ", ""));
	}
}
