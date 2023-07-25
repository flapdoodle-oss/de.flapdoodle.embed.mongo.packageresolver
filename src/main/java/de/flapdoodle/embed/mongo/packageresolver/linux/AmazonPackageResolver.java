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

import de.flapdoodle.embed.mongo.packageresolver.*;
import de.flapdoodle.embed.process.config.store.FileSet;
import de.flapdoodle.embed.process.config.store.FileType;
import de.flapdoodle.embed.process.config.store.ImmutableFileSet;
import de.flapdoodle.embed.process.config.store.Package;
import de.flapdoodle.embed.process.distribution.ArchiveType;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.os.BitSize;
import de.flapdoodle.os.CPUType;
import de.flapdoodle.os.CommonOS;
import de.flapdoodle.os.linux.AmazonVersion;

import java.util.Optional;

public class AmazonPackageResolver implements PackageFinder, HasPlatformMatchRules {

	private final ImmutablePackageFinderRules rules;

	public AmazonPackageResolver(final Command command) {
		this.rules = rules(command);
	}

	@Override
	public PackageFinderRules rules() {
		return rules;
	}
	
	@Override
	public Optional<Package> packageFor(final Distribution distribution) {
		return rules.packageFor(distribution);
	}

	private static ImmutablePackageFinderRules rules(final Command command) {
		final ImmutableFileSet fileSet = FileSet.builder().addEntry(FileType.Executable, command.commandName()).build();

		DistributionMatch amazon2023ArmDevMongoVersions = DistributionMatch.any(
			VersionRange.of("7.0.0-rc8"),
			VersionRange.of("7.0.0-rc2")
		);

		final PackageFinderRule amazon2023ArmDev = PackageFinderRule.builder()
			.match(match(BitSize.B64,CPUType.ARM,AmazonVersion.AmazonLinux2023).andThen(amazon2023ArmDevMongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/linux/mongodb-linux-aarch64-amazon2023-{version}.tgz")
				.isDevVersion(true)
				.build())
			.build();

		DistributionMatch amazon2023DevMongoVersions = DistributionMatch.any(
			VersionRange.of("7.0.0-rc8"),
			VersionRange.of("7.0.0-rc2")
		);

		final PackageFinderRule amazon2023Dev = PackageFinderRule.builder()
			.match(match(BitSize.B64,CPUType.X86,AmazonVersion.AmazonLinux2023).andThen(amazon2023DevMongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/linux/mongodb-linux-x86_64-amazon2023-{version}.tgz")
				.isDevVersion(true)
				.build())
			.build();


		DistributionMatch amazon2ArmDevMongoVersions = DistributionMatch.any(
			VersionRange.of("7.0.0-rc8"),
			VersionRange.of("7.0.0-rc2"),
			VersionRange.of("7.0.0-rc1"),
			VersionRange.of("6.3.1", "6.3.2")
		);

		final PackageFinderRule amazon2ArmDev = PackageFinderRule.builder()
			.match(match(BitSize.B64,CPUType.ARM,AmazonVersion.AmazonLinux2).andThen(amazon2ArmDevMongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/linux/mongodb-linux-aarch64-amazon2-{version}.tgz")
				.isDevVersion(true)
				.build())
			.build();

		DistributionMatch amazon2ArmMongoVersions = DistributionMatch.any(
			VersionRange.of("6.0.8"),
			VersionRange.of("6.0.1", "6.0.6"),
			VersionRange.of("5.0.18", "5.0.19"),
			VersionRange.of("5.0.12", "5.0.15"),
			VersionRange.of("5.0.5", "5.0.6"),
			VersionRange.of("5.0.0", "5.0.2"),
			VersionRange.of("4.4.22", "4.4.23"),
			VersionRange.of("4.4.16", "4.4.19"),
			VersionRange.of("4.4.13", "4.4.13"),
			VersionRange.of("4.4.11", "4.4.11"),
			VersionRange.of("4.4.4", "4.4.9"),
			VersionRange.of("4.2.22", "4.2.24"),
			VersionRange.of("4.2.18", "4.2.19"),
			VersionRange.of("4.2.13", "4.2.16")
		);

		final PackageFinderRule amazon2Arm = PackageFinderRule.builder()
			.match(match(BitSize.B64,CPUType.ARM,AmazonVersion.AmazonLinux2).andThen(amazon2ArmMongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/linux/mongodb-linux-aarch64-amazon2-{version}.tgz")
				.build())
			.build();

		final PackageFinderRule amazon2ArmTools = PackageFinderRule.builder()
			.match(match(BitSize.B64,CPUType.ARM,AmazonVersion.AmazonLinux2).andThen(amazon2ArmMongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/tools/db/mongodb-database-tools-amazon2-arm64-{tools.version}.tgz")
				.build())
			.build();

		DistributionMatch amazon2DevMongoVersions = DistributionMatch.any(
			VersionRange.of("7.0.0-rc8"),
			VersionRange.of("7.0.0-rc2"),
			VersionRange.of("7.0.0-rc1"),
			VersionRange.of("6.3.1")
		);

		final PackageFinderRule amazon2Dev = PackageFinderRule.builder()
			.match(match(BitSize.B64,CPUType.X86,AmazonVersion.AmazonLinux2).andThen(amazon2DevMongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/linux/mongodb-linux-x86_64-amazon2-{version}.tgz")
				.isDevVersion(true)
				.build())
			.build();

		DistributionMatch amazon2MongoVersions = DistributionMatch.any(
			VersionRange.of("6.0.8"),
			VersionRange.of("6.0.1", "6.0.6"),
			VersionRange.of("5.0.18","5.0.19"),
			VersionRange.of("5.0.12", "5.0.15"),
			VersionRange.of("5.0.5", "5.0.6"),
			VersionRange.of("5.0.0", "5.0.2"),
			VersionRange.of("4.4.22","4.4.23"),
			VersionRange.of("4.4.16", "4.4.19"),
			VersionRange.of("4.4.13", "4.4.13"),
			VersionRange.of("4.4.11", "4.4.11"),
			VersionRange.of("4.4.0", "4.4.9"),
			VersionRange.of("4.2.22", "4.2.24"),
			VersionRange.of("4.2.18", "4.2.19"),
			VersionRange.of("4.2.5", "4.2.16"),
			VersionRange.of("4.2.0", "4.2.3"),
			VersionRange.of("4.0.0", "4.0.28"),
			VersionRange.of("3.6.22", "3.6.23")
		);
		final PackageFinderRule amazon2 = PackageFinderRule.builder()
			.match(match(BitSize.B64,CPUType.X86,AmazonVersion.AmazonLinux2).andThen(amazon2MongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/linux/mongodb-linux-x86_64-amazon2-{version}.tgz")
				.build())
			.build();

		final PackageFinderRule amazon2tools = PackageFinderRule.builder()
			.match(match(BitSize.B64,CPUType.X86,AmazonVersion.AmazonLinux2).andThen(amazon2MongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/tools/db/mongodb-database-tools-amazon2-x86_64-{tools.version}.tgz")
				.build())
			.build();


		DistributionMatch amazonMongoVersions = DistributionMatch.any(
			VersionRange.of("5.0.18","5.0.19"),
			VersionRange.of("5.0.12", "5.0.15"),
			VersionRange.of("5.0.5", "5.0.6"),
			VersionRange.of("5.0.0", "5.0.2"),
			VersionRange.of("4.4.22","4.4.23"),
			VersionRange.of("4.4.16", "4.4.19"),
			VersionRange.of("4.4.13", "4.4.13"),
			VersionRange.of("4.4.11", "4.4.11"),
			VersionRange.of("4.4.0", "4.4.9"),
			VersionRange.of("4.2.22", "4.2.24"),
			VersionRange.of("4.2.18", "4.2.19"),
			VersionRange.of("4.2.5", "4.2.16"),
			VersionRange.of("4.2.0", "4.2.3"),
			VersionRange.of("4.0.0", "4.0.28"),
			VersionRange.of("3.6.0", "3.6.23"),
			VersionRange.of("3.4.9", "3.4.24"),
			VersionRange.of("3.4.0", "3.4.7"),
			VersionRange.of("3.2.0", "3.2.22"),
			VersionRange.of("3.0.0", "3.0.15")
		);

		final PackageFinderRule amazon = PackageFinderRule.builder()
			.match(match(BitSize.B64,CPUType.X86,AmazonVersion.AmazonLinux).andThen(amazonMongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/linux/mongodb-linux-x86_64-amazon-{version}.tgz")
				.build())
			.build();

		final PackageFinderRule amazontools = PackageFinderRule.builder()
			.match(match(BitSize.B64,CPUType.X86,AmazonVersion.AmazonLinux).andThen(amazonMongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/tools/db/mongodb-database-tools-amazon-x86_64-{tools.version}.tgz")
				.build())
			.build();

		switch (command) {
			case MongoDump:
			case MongoImport:
			case MongoRestore:
				return PackageFinderRules.empty()
					.withRules(
						amazon2ArmTools,
						amazon2tools,
						amazontools
					);
			default:
				return PackageFinderRules.empty()
					.withRules(
						amazon2023ArmDev,
						amazon2ArmDev,
						amazon2Arm,
						amazon2023Dev,
						amazon2Dev,
						amazon2,
						amazon
					);
		}
	}

	private static ImmutablePlatformMatch match(BitSize bitSize, CPUType cpuType, AmazonVersion version) {
		return PlatformMatch
			.withOs(CommonOS.Linux)
			.withVersion(version)
			.withBitSize(bitSize)
			.withCpuType(cpuType);
	}
}
