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
import de.flapdoodle.embed.process.config.store.FileSet;
import de.flapdoodle.embed.process.config.store.FileType;
import de.flapdoodle.embed.process.config.store.ImmutableFileSet;
import de.flapdoodle.embed.process.config.store.Package;
import de.flapdoodle.embed.process.distribution.ArchiveType;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.os.BitSize;
import de.flapdoodle.os.CommonOS;
import de.flapdoodle.os.ImmutablePlatform;
import de.flapdoodle.os.Version;
import de.flapdoodle.os.linux.*;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class LinuxPackageFinder extends AbstractPackageFinder {

	private static final Logger LOGGER = LoggerFactory.getLogger(LinuxPackageFinder.class);

	public LinuxPackageFinder(Command command) {
		super(command, rules(command));
	}

	private static ImmutablePackageFinderRules rules(Command command) {
		ImmutableFileSet fileSet = FileSet.builder().addEntry(FileType.Executable, command.commandName()).build();

    UbuntuPackageFinder ubuntuPackageFinder = new UbuntuPackageFinder(command);

    final ImmutablePackageFinderRule ubuntuRule = PackageFinderRule.builder()
			.match(PlatformMatch.withOs(CommonOS.Linux)
				.withVersion(UbuntuVersion.values()))
			.finder(ubuntuPackageFinder)
			.build();

		final ImmutablePackageFinderRule linuxMintRule = PackageFinderRule.builder()
			.match(PlatformMatch.withOs(CommonOS.Linux)
				.withVersion(LinuxMintVersion.values()))
			.finder(new LinuxMintPackageFinder(ubuntuPackageFinder))
			.build();

		final ImmutablePackageFinderRule popOsRule = PackageFinderRule.builder()
			.match(PlatformMatch.withOs(CommonOS.Linux)
				.withVersion(PopOSVersion.values()))
			.finder(new PopOSPackageFinder(ubuntuPackageFinder))
			.build();

		final ImmutablePackageFinderRule debianRule = PackageFinderRule.builder()
			.match(PlatformMatch.withOs(CommonOS.Linux).withVersion(DebianVersion.values()))
			.finder(new DebianPackageFinder(command))
			.build();

		CentosRedhatPackageFinder centosRedhatPackageFinder = new CentosRedhatPackageFinder(command);

		ImmutablePackageFinderRule centosRedhatOracleRule = PackageFinderRule.builder()
			.match(PlatformMatch.withOs(CommonOS.Linux)
				.withVersion(CentosRedhatPackageFinder.knownVersions()))
			.finder(centosRedhatPackageFinder)
			.build();

		ImmutablePackageFinderRule amazonRule = PackageFinderRule.builder()
			.match(PlatformMatch.withOs(CommonOS.Linux)
				.withVersion(AmazonVersion.values()))
			.finder(new AmazonPackageFinder(command))
			.build();

		ImmutablePackageFinderRule linuxLegacyRule = PackageFinderRule.builder()
			.match(PlatformMatch.withOs(CommonOS.Linux))
			.finder(new LinuxLegacyPackageFinder(command))
			.build();

		PackageFinderRule failIfNothingMatches = PackageFinderRule.builder()
			.match(PlatformMatch.withOs(CommonOS.Linux))
			.finder(new FallbackToUbuntuOrFailPackageFinder(ubuntuPackageFinder))
			.build();

		return PackageFinderRules.empty()
			.withRules(
				ubuntuRule,
				linuxMintRule,
				popOsRule,
				debianRule,
				centosRedhatOracleRule,
				amazonRule,
				linuxLegacyRule,
				failIfNothingMatches
			);
	}

	static class FallbackToUbuntuOrFailPackageFinder implements PackageFinder, HasExplanation {
		private final UbuntuPackageFinder ubuntuPackageFinder;
		private final UbuntuVersion fallbackUbuntuVersion = UbuntuVersion.Ubuntu_20_04;

		public FallbackToUbuntuOrFailPackageFinder(UbuntuPackageFinder ubuntuPackageFinder) {
			this.ubuntuPackageFinder = ubuntuPackageFinder;
		}

		@Override
		public Optional<Package> packageFor(Distribution distribution) {
			if (distribution.platform().distribution().isPresent()) {
				// only fallback if no linux dist is detected
				return Optional.empty();
			}

			Distribution ubuntuLTSFallback = Distribution.of(distribution.version(),
				ImmutablePlatform.copyOf(distribution.platform())
					.withVersion(fallbackUbuntuVersion));

			LOGGER.warn("because there is no package for " + distribution + " we fall back to " + ubuntuLTSFallback);

			Optional<Package> resolvedPackage = ubuntuPackageFinder.packageFor(ubuntuLTSFallback);
			if (!resolvedPackage.isPresent()) {
				throw new IllegalArgumentException("linux distribution not supported: " + distribution + "(with fallback to " + ubuntuLTSFallback + ")");
			}
			return resolvedPackage;
		}

		@Override public String explain() {
			return "fallback to "+fallbackUbuntuVersion;
		}
	}
}
