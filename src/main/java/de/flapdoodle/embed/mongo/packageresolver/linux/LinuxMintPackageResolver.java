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
package de.flapdoodle.embed.mongo.packageresolver.linux;

import de.flapdoodle.embed.mongo.packageresolver.HasExplanation;
import de.flapdoodle.embed.mongo.packageresolver.PackageFinder;
import de.flapdoodle.embed.mongo.packageresolver.PlatformMatch;
import de.flapdoodle.embed.process.config.store.DistributionPackage;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.os.ImmutablePlatform;
import de.flapdoodle.os.OS;
import de.flapdoodle.os.Version;
import de.flapdoodle.os.linux.LinuxMintVersion;
import de.flapdoodle.os.linux.UbuntuVersion;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class LinuxMintPackageResolver implements PackageFinder, HasExplanation {

	private final UbuntuPackageResolver ubuntuPackageResolver;

	public LinuxMintPackageResolver(UbuntuPackageResolver ubuntuPackageResolver) {
		this.ubuntuPackageResolver = ubuntuPackageResolver;
	}

	@Override
	public Optional<DistributionPackage> packageFor(Distribution distribution) {
		if (PlatformMatch.withOs(OS.Linux).withVersion(LinuxMintVersion.values()).match(distribution)) {
			if (!distribution.platform().version().isPresent()) throw new RuntimeException("version not set: "+distribution);
			Version currentVersion = distribution.platform().version().get();
			if (currentVersion instanceof LinuxMintVersion) {
				Distribution asUbuntudistribution = Distribution.of(distribution.version(),
					ImmutablePlatform.copyOf(distribution.platform()).withVersion(((LinuxMintVersion) currentVersion).matchingUbuntuVersion()));
				return ubuntuPackageResolver.packageFor(asUbuntudistribution);
			} else {
				throw new IllegalArgumentException("Version is not a "+LinuxMintVersion.class+": "+currentVersion);
			}
		}

		return Optional.empty();
	}

	@Override
	public String explain() {
		List<UbuntuVersion> ubuntuVersions = Arrays.stream(LinuxMintVersion.values()).map(LinuxMintVersion::matchingUbuntuVersion)
			.distinct()
			.collect(Collectors.toList());

		return ubuntuVersions.stream()
			.map(uv -> Arrays.stream(LinuxMintVersion.values())
				.filter(v -> v.matchingUbuntuVersion() == uv)
				.map(LinuxMintVersion::name)
				.collect(Collectors.joining(", ", "" + uv.name() + " for ", "")))
			.collect(Collectors.joining(" and ", "use ", ""));
	}
}
