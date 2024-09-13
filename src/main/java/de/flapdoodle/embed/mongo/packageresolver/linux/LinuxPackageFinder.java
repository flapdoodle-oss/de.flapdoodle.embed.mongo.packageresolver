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
		UbuntuFallbackToOlderVersionPackageFinder ubuntuDowngradingPackageFinder = new UbuntuFallbackToOlderVersionPackageFinder(ubuntuPackageFinder);

		final ImmutablePackageFinderRule ubuntuRule = PackageFinderRule.builder()
			.match(PlatformMatch.withOs(CommonOS.Linux)
				.withVersion(UbuntuVersion.values()))
			.finder(ubuntuPackageFinder)
			.build();

		final ImmutablePackageFinderRule ubuntuDowngradeRule = PackageFinderRule.builder()
			.match(ubuntuDowngradingPackageFinder.platformMatch())
			.finder(ubuntuDowngradingPackageFinder)
			.build();

		final ImmutablePackageFinderRule linuxMintRule = PackageFinderRule.builder()
			.match(PlatformMatch.withOs(CommonOS.Linux)
				.withVersion(LinuxMintVersion.values()))
			.finder(new LinuxMintPackageFinder(ubuntuDowngradingPackageFinder))
			.build();

		final ImmutablePackageFinderRule popOsRule = PackageFinderRule.builder()
			.match(PlatformMatch.withOs(CommonOS.Linux)
				.withVersion(PopOSVersion.values()))
			.finder(new PopOSPackageFinder(ubuntuDowngradingPackageFinder))
			.build();

		final ImmutablePackageFinderRule kdeNeonRule = PackageFinderRule.builder()
			.match(PlatformMatch.withOs(CommonOS.Linux)
				.withVersion(KdeNeonVersion.values()))
			.finder(new KdeNeonPackageFinder(ubuntuDowngradingPackageFinder))
			.build();

//		final ImmutablePackageFinderRule debian12DevRule = PackageFinderRule.builder()
//			.match(PlatformMatch.withOs(CommonOS.Linux).withVersion(DebianVersion.DEBIAN_12, DebianVersion.DEBIAN_13))
//			.finder(new Debian12DevPackageFinder(command))
//			.build();

//		final ImmutablePackageFinderRule debianUsesUbuntuRule = PackageFinderRule.builder()
//			.match(DebianUsesUbuntuPackageFinder.platformMatch())
//			.finder(new DebianUsesUbuntuPackageFinder(ubuntuDowngradingPackageFinder))
//			.build();

		DebianPackageFinder debianPackageFinder = new DebianPackageFinder(command);
		DebianFallbackToOlderVersionPackageFinder debianDowngradingPackageFinder = new DebianFallbackToOlderVersionPackageFinder(debianPackageFinder);
		DebianUsesUbuntuOnArmPackageFinder debianUsesUbuntuOnArmPackageFinder = new DebianUsesUbuntuOnArmPackageFinder(ubuntuDowngradingPackageFinder);

		final ImmutablePackageFinderRule debianRule = PackageFinderRule.builder()
			.match(PlatformMatch.withOs(CommonOS.Linux).withVersion(DebianVersion.values()))
			.finder(debianPackageFinder)
			.build();

		final ImmutablePackageFinderRule debianDowngradeRule = PackageFinderRule.builder()
			.match(debianDowngradingPackageFinder.platformMatch())
			.finder(debianDowngradingPackageFinder)
			.build();

		final ImmutablePackageFinderRule debianArmUsesUbuntuRule = PackageFinderRule.builder()
			.match(debianUsesUbuntuOnArmPackageFinder.platformMatch())
			.finder(debianUsesUbuntuOnArmPackageFinder)
			.build();

		RedhatPackageFinder redhatPackageFinder = new RedhatPackageFinder(command);
		RedhatFallbackToOlderVersionPackageFinder redhatDowngradingPackageFinder = new RedhatFallbackToOlderVersionPackageFinder(redhatPackageFinder);

		ImmutablePackageFinderRule redhatRule = PackageFinderRule.builder()
			.match(PlatformMatch.withOs(CommonOS.Linux)
				.withVersion(RedhatVersion.values()))
			.finder(redhatPackageFinder)
			.build();

		final ImmutablePackageFinderRule redhatDowngradeRule = PackageFinderRule.builder()
			.match(redhatDowngradingPackageFinder.platformMatch())
			.finder(redhatDowngradingPackageFinder)
			.build();

		ImmutablePackageFinderRule fedoraRule = PackageFinderRule.builder()
			.match(PlatformMatch.withOs(CommonOS.Linux)
				.withVersion(FedoraVersion.values()))
			.finder(new FedoraPackageFinder(redhatDowngradingPackageFinder))
			.build();

		ImmutablePackageFinderRule oracleRule = PackageFinderRule.builder()
			.match(PlatformMatch.withOs(CommonOS.Linux)
				.withVersion(OracleVersion.values()))
			.finder(new OraclePackageFinder(redhatDowngradingPackageFinder))
			.build();

		ImmutablePackageFinderRule centosRule = PackageFinderRule.builder()
			.match(PlatformMatch.withOs(CommonOS.Linux)
				.withVersion(CentosVersion.values()))
			.finder(new CentosPackageFinder(redhatDowngradingPackageFinder))
			.build();

		ImmutablePackageFinderRule almaRule = PackageFinderRule.builder()
			.match(PlatformMatch.withOs(CommonOS.Linux)
				.withVersion(AlmaVersion.values()))
			.finder(new AlmaPackageFinder(redhatDowngradingPackageFinder))
			.build();

		ImmutablePackageFinderRule rockyRule = PackageFinderRule.builder()
			.match(PlatformMatch.withOs(CommonOS.Linux)
				.withVersion(RockyVersion.values()))
			.finder(new RockyPackageFinder(redhatDowngradingPackageFinder))
			.build();

		ImmutablePackageFinderRule amazonRule = PackageFinderRule.builder()
			.match(PlatformMatch.withOs(CommonOS.Linux)
				.withVersion(AmazonVersion.values()))
			.finder(new AmazonPackageFinder(command))
			.build();

		ImmutablePackageFinderRule alpineRule = PackageFinderRule.builder()
			.match(PlatformMatch.withOs(CommonOS.Linux)
				.withVersion(AlpineVersion.values()))
			.finder(new AlpinePackageFinder(command))
			.build();

		ImmutablePackageFinderRule linuxLegacyRule = PackageFinderRule.builder()
			.match(PlatformMatch.withOs(CommonOS.Linux))
			.finder(new LinuxLegacyPackageFinder(command))
			.build();

		PackageFinderRule failIfNothingMatches = PackageFinderRule.builder()
			.match(PlatformMatch.withOs(CommonOS.Linux))
			.finder(new FallbackToUbuntuOrFailPackageFinder(ubuntuDowngradingPackageFinder))
			.build();

		return PackageFinderRules.empty()
			.withRules(
				ubuntuRule,
				ubuntuDowngradeRule,
				linuxMintRule,
				popOsRule,
				kdeNeonRule,
//				debian12DevRule,
//				debianUsesUbuntuRule,
				debianArmUsesUbuntuRule,
				debianRule,
				debianDowngradeRule,
				redhatRule,
				redhatDowngradeRule,
				fedoraRule,
				oracleRule,
				centosRule,
				almaRule,
				rockyRule,
				amazonRule,
				alpineRule,
				linuxLegacyRule,
				failIfNothingMatches
			);
	}

	static class FallbackToUbuntuOrFailPackageFinder implements PackageFinder, HasExplanation {
		private final UbuntuFallbackToOlderVersionPackageFinder ubuntuPackageFinder;
		private final UbuntuVersion fallbackUbuntuVersion = UbuntuVersion.Ubuntu_20_04;

		public FallbackToUbuntuOrFailPackageFinder(UbuntuFallbackToOlderVersionPackageFinder ubuntuPackageFinder) {
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
			return "fallback to "+fallbackUbuntuVersion+" using '"+ubuntuPackageFinder.label()+"'";
		}
	}
}
