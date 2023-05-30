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
import de.flapdoodle.os.linux.UbuntuVersion;

import java.util.Optional;

public class UbuntuPackageResolver implements PackageFinder, HasPlatformMatchRules {

  private final Command command;
  private final ImmutablePackageFinderRules rules;

  public UbuntuPackageResolver(Command command) {
    this.command = command;
    this.rules = rules(command);
  }

	@Override
	public PackageFinderRules rules() {
		return rules;
	}

	@Override
  public Optional<Package> packageFor(Distribution distribution) {
    return rules.packageFor(distribution);
  }

	private static PlatformMatch match(BitSize bitSize, CPUType cpuType, UbuntuVersion... versions) {
		return PlatformMatch.withOs(CommonOS.Linux).withBitSize(bitSize).withCpuType(cpuType)
			.withVersion(versions);
	}

  private static ImmutablePackageFinderRules rules(Command command) {
    ImmutableFileSet fileSet = FileSet.builder().addEntry(FileType.Executable, command.commandName()).build();

		PlatformMatch ubuntu16x86_64 = match(BitSize.B64, CPUType.X86,
			UbuntuVersion.Ubuntu_16_04, UbuntuVersion.Ubuntu_16_10
		);

		PlatformMatch ubuntu18to20arm_64 = match(BitSize.B64, CPUType.ARM,
			UbuntuVersion.Ubuntu_18_04, UbuntuVersion.Ubuntu_18_10,
			UbuntuVersion.Ubuntu_19_04, UbuntuVersion.Ubuntu_19_10,
			UbuntuVersion.Ubuntu_20_04, UbuntuVersion.Ubuntu_20_10
		);

		PlatformMatch ubuntu18to23x86_64 = match(BitSize.B64, CPUType.X86,
			UbuntuVersion.Ubuntu_18_04, UbuntuVersion.Ubuntu_18_10,
			UbuntuVersion.Ubuntu_19_04, UbuntuVersion.Ubuntu_19_10,
			UbuntuVersion.Ubuntu_20_04, UbuntuVersion.Ubuntu_20_10,
			UbuntuVersion.Ubuntu_21_04, UbuntuVersion.Ubuntu_21_10,
			UbuntuVersion.Ubuntu_22_04, UbuntuVersion.Ubuntu_22_10,
			UbuntuVersion.Ubuntu_23_04, UbuntuVersion.Ubuntu_23_10
		);

		PlatformMatch ubuntu20to23arm_64 = match(BitSize.B64, CPUType.ARM,
			UbuntuVersion.Ubuntu_20_04, UbuntuVersion.Ubuntu_20_10,
			UbuntuVersion.Ubuntu_21_04, UbuntuVersion.Ubuntu_21_10,
			UbuntuVersion.Ubuntu_22_04, UbuntuVersion.Ubuntu_22_10,
			UbuntuVersion.Ubuntu_23_04, UbuntuVersion.Ubuntu_23_10);

		PlatformMatch ubuntu20to23x86_64 = match(BitSize.B64, CPUType.X86,
			UbuntuVersion.Ubuntu_20_04, UbuntuVersion.Ubuntu_20_10,
			UbuntuVersion.Ubuntu_21_04, UbuntuVersion.Ubuntu_21_10,
			UbuntuVersion.Ubuntu_22_04, UbuntuVersion.Ubuntu_22_10,
			UbuntuVersion.Ubuntu_23_04, UbuntuVersion.Ubuntu_23_10);

		PlatformMatch ubuntu22to23arm_64 = match(BitSize.B64, CPUType.ARM,
			UbuntuVersion.Ubuntu_22_04, UbuntuVersion.Ubuntu_22_10,
			UbuntuVersion.Ubuntu_23_04, UbuntuVersion.Ubuntu_23_10);

		PlatformMatch ubuntu22to23x86_64 = match(BitSize.B64, CPUType.X86,
			UbuntuVersion.Ubuntu_22_04, UbuntuVersion.Ubuntu_22_10,
			UbuntuVersion.Ubuntu_23_04, UbuntuVersion.Ubuntu_23_10);

		DistributionMatch ubuntu16xxArmMongoVersions = DistributionMatch.any(
			VersionRange.of("4.0.0", "4.0.28"),
			VersionRange.of("3.6.0", "3.6.23"),
			VersionRange.of("3.4.9", "3.4.24"),
			VersionRange.of("3.4.0", "3.4.7")
		);

		PackageFinderRule ubuntu1604arm = PackageFinderRule.builder()
			.match(match(BitSize.B64, CPUType.ARM, UbuntuVersion.Ubuntu_16_04, UbuntuVersion.Ubuntu_16_10)
				.andThen(ubuntu16xxArmMongoVersions
				)
			)
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/linux/mongodb-linux-arm64-ubuntu1604-{version}.tgz")
				.build())
			.build();

		PackageFinderRule tools_ubuntu1604arm = PackageFinderRule.builder()
			.match(match(BitSize.B64, CPUType.ARM,
				UbuntuVersion.Ubuntu_16_04, UbuntuVersion.Ubuntu_16_10
			).andThen(ubuntu16xxArmMongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/tools/db/mongodb-database-tools-ubuntu1604-arm64-{tools.version}.tgz")
				.build())
			.build();



		DistributionMatch ubuntu16xxMongoVersions = DistributionMatch.any(
			VersionRange.of("4.4.22"),
			VersionRange.of("4.4.16", "4.4.19"),
			VersionRange.of("4.4.13"),
			VersionRange.of("4.4.11"),
			VersionRange.of("4.4.0", "4.4.9"),
			VersionRange.of("4.2.22", "4.2.24"),
			VersionRange.of("4.2.18", "4.2.19"),
			VersionRange.of("4.2.5", "4.2.16"),
			VersionRange.of("4.2.0", "4.2.3"),
			VersionRange.of("4.0.0", "4.0.28"),
			VersionRange.of("3.6.0", "3.6.23"),
			VersionRange.of("3.4.9", "3.4.24"),
			VersionRange.of("3.4.0", "3.4.7"),
			VersionRange.of("3.2.7", "3.2.22")
		);

		PackageFinderRule ubuntu1604x64 = PackageFinderRule.builder()
			.match(ubuntu16x86_64.andThen(ubuntu16xxMongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/linux/mongodb-linux-x86_64-ubuntu1604-{version}.tgz")
				.build())
			.build();

		PackageFinderRule tools_ubuntu1604x64 = PackageFinderRule.builder()
			.match(ubuntu16x86_64.andThen(ubuntu16xxMongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/tools/db/mongodb-database-tools-ubuntu1604-x86_64-{tools.version}.tgz")
				.build())
			.build();



		DistributionMatch ubuntu18xxArmMongoVersions = DistributionMatch.any(
			VersionRange.of("6.0.1", "6.0.6"),
			VersionRange.of("5.0.18"),
			VersionRange.of("5.0.12", "5.0.15"),
			VersionRange.of("5.0.5", "5.0.6"),
			VersionRange.of("5.0.0", "5.0.2"),
			VersionRange.of("4.4.22"),
			VersionRange.of("4.4.16", "4.4.19"),
			VersionRange.of("4.4.13", "4.4.13"),
			VersionRange.of("4.4.11", "4.4.11"),
			VersionRange.of("4.4.0", "4.4.9"),
			VersionRange.of("4.2.22", "4.2.24"),
			VersionRange.of("4.2.18", "4.2.19"),
			VersionRange.of("4.2.5", "4.2.16"),
			VersionRange.of("4.2.0", "4.2.3")
		);

		PackageFinderRule ubuntu1804arm = PackageFinderRule.builder()
			.match(ubuntu18to20arm_64
				.andThen(ubuntu18xxArmMongoVersions
				)
			)
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/linux/mongodb-linux-aarch64-ubuntu1804-{version}.tgz")
				.build())
			.build();

		DistributionMatch ubuntu18xxArmDevMongoVersions = DistributionMatch.any(
			VersionRange.of("7.0.0"),
			VersionRange.of("6.3.1")
			);

		PackageFinderRule ubuntu1804armDev = PackageFinderRule.builder()
			.match(ubuntu18to20arm_64
				.andThen(ubuntu18xxArmDevMongoVersions
				)
			)
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/linux/mongodb-linux-aarch64-ubuntu1804-{version}.tgz")
				.isDevVersion(true)
				.build())
			.build();

			PackageFinderRule tools_ubuntu1804arm = PackageFinderRule.builder()
					.match(ubuntu18to20arm_64.andThen(ubuntu18xxArmMongoVersions))
					.finder(UrlTemplatePackageResolver.builder()
							.fileSet(fileSet)
							.archiveType(ArchiveType.TGZ)
							.urlTemplate("/tools/db/mongodb-database-tools-ubuntu1804-arm64-{tools.version}.tgz")
							.build())
					.build();

		DistributionMatch ubuntu18xxMongoVersions = DistributionMatch.any(
			VersionRange.of("6.0.1", "6.0.6"),
			VersionRange.of("5.0.18"),
			VersionRange.of("5.0.12", "5.0.15"),
			VersionRange.of("5.0.5", "5.0.6"),
			VersionRange.of("5.0.0", "5.0.2"),
			VersionRange.of("4.4.22"),
			VersionRange.of("4.4.16", "4.4.19"),
			VersionRange.of("4.4.13", "4.4.13"),
			VersionRange.of("4.4.11", "4.4.11"),
			VersionRange.of("4.4.0", "4.4.9"),
			VersionRange.of("4.2.22", "4.2.24"),
			VersionRange.of("4.2.18", "4.2.19"),
			VersionRange.of("4.2.5", "4.2.16"),
			VersionRange.of("4.2.0", "4.2.3"),
			VersionRange.of("4.0.1", "4.0.28"),
			VersionRange.of("3.6.20", "3.6.23")
		);

		PackageFinderRule ubuntu1804x64 = PackageFinderRule.builder()
					.match(ubuntu18to23x86_64.andThen(ubuntu18xxMongoVersions))
					.finder(UrlTemplatePackageResolver.builder()
							.fileSet(fileSet)
							.archiveType(ArchiveType.TGZ)
							.urlTemplate("/linux/mongodb-linux-x86_64-ubuntu1804-{version}.tgz")
							.build())
					.build();

		DistributionMatch ubuntu18xxDevMongoVersions = DistributionMatch.any(
			VersionRange.of("7.0.0"),
			VersionRange.of("6.3.1")
		);

		PackageFinderRule ubuntu1804x64dev = PackageFinderRule.builder()
			.match(ubuntu18to23x86_64.andThen(ubuntu18xxDevMongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/linux/mongodb-linux-x86_64-ubuntu1804-{version}.tgz")
				.isDevVersion(true)
				.build())
			.build();

		PackageFinderRule tools_ubuntu1804x64 = PackageFinderRule.builder()
					.match(ubuntu18to23x86_64.andThen(ubuntu18xxMongoVersions))
					.finder(UrlTemplatePackageResolver.builder()
							.fileSet(fileSet)
							.archiveType(ArchiveType.TGZ)
							.urlTemplate("/tools/db/mongodb-database-tools-ubuntu1804-x86_64-{tools.version}.tgz")
							.build())
					.build();

		DistributionMatch ubuntu20xxMongoVersions = DistributionMatch.any(
			VersionRange.of("6.0.1", "6.0.6"),
			VersionRange.of("5.0.18"),
			VersionRange.of("5.0.12", "5.0.15"),
			VersionRange.of("5.0.5", "5.0.6"),
			VersionRange.of("5.0.0", "5.0.2"),
			VersionRange.of("4.4.22"),
			VersionRange.of("4.4.16", "4.4.19"),
			VersionRange.of("4.4.13", "4.4.13"),
			VersionRange.of("4.4.11", "4.4.11"),
			VersionRange.of("4.4.0", "4.4.9")
		);

		PackageFinderRule ubuntu20to22arm = PackageFinderRule.builder()
					.match(ubuntu20to23arm_64
						.andThen(ubuntu20xxMongoVersions))
					.finder(UrlTemplatePackageResolver.builder()
							.fileSet(fileSet)
							.archiveType(ArchiveType.TGZ)
							.urlTemplate("/linux/mongodb-linux-aarch64-ubuntu2004-{version}.tgz")
							.build())
					.build();

		DistributionMatch ubuntu20xxDevMongoVersions = DistributionMatch.any(
			VersionRange.of("7.0.0"),
			VersionRange.of("6.3.1")
		);

		PackageFinderRule ubuntu20to22armDev = PackageFinderRule.builder()
			.match(ubuntu20to23arm_64
				.andThen(ubuntu20xxDevMongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/linux/mongodb-linux-aarch64-ubuntu2004-{version}.tgz")
				.isDevVersion(true)
				.build())
			.build();

		PackageFinderRule tools_ubuntu20to22arm = PackageFinderRule.builder()
					.match(ubuntu20to23arm_64
						.andThen(ubuntu20xxMongoVersions))
					.finder(UrlTemplatePackageResolver.builder()
							.fileSet(fileSet)
							.archiveType(ArchiveType.TGZ)
							.urlTemplate("/tools/db/mongodb-database-tools-ubuntu2004-arm64-{tools.version}.tgz")
							.build())
					.build();

		PackageFinderRule ubuntu20To22x64 = PackageFinderRule.builder()
					.match(ubuntu20to23x86_64
						.andThen(ubuntu20xxMongoVersions))
					.finder(UrlTemplatePackageResolver.builder()
							.fileSet(fileSet)
							.archiveType(ArchiveType.TGZ)
							.urlTemplate("/linux/mongodb-linux-x86_64-ubuntu2004-{version}.tgz")
							.build())
					.build();

		PackageFinderRule ubuntu20To22x64dev = PackageFinderRule.builder()
			.match(ubuntu20to23x86_64
				.andThen(ubuntu20xxDevMongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/linux/mongodb-linux-x86_64-ubuntu2004-{version}.tgz")
				.isDevVersion(true)
				.build())
			.build();

		PackageFinderRule tools_ubuntu20to22x64 = PackageFinderRule.builder()
					.match(ubuntu20to23x86_64
						.andThen(ubuntu20xxMongoVersions))
					.finder(UrlTemplatePackageResolver.builder()
							.fileSet(fileSet)
							.archiveType(ArchiveType.TGZ)
							.urlTemplate("/tools/db/mongodb-database-tools-ubuntu2004-x86_64-{tools.version}.tgz")
							.build())
					.build();

		DistributionMatch ubuntu22xxMongoVersions = DistributionMatch.any(
			VersionRange.of("6.0.4", "6.0.6")
		);

		PackageFinderRule ubuntu22arm = PackageFinderRule.builder()
			.match(ubuntu22to23arm_64
				.andThen(ubuntu22xxMongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/linux/mongodb-linux-aarch64-ubuntu2204-{version}.tgz")
				.build())
			.build();

		DistributionMatch ubuntu22xxDevMongoVersions = DistributionMatch.any(
			VersionRange.of("7.0.0"),
			VersionRange.of("6.3.1")
		);

		PackageFinderRule ubuntu22armDev = PackageFinderRule.builder()
			.match(ubuntu22to23arm_64
				.andThen(ubuntu22xxDevMongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/linux/mongodb-linux-aarch64-ubuntu2204-{version}.tgz")
				.isDevVersion(true)
				.build())
			.build();

		PackageFinderRule tools_ubuntu22arm = PackageFinderRule.builder()
			.match(ubuntu22to23arm_64
				.andThen(ubuntu22xxMongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/tools/db/mongodb-database-tools-ubuntu2204-arm64-{tools.version}.tgz")
				.build())
			.build();

		PackageFinderRule ubuntu22x64 = PackageFinderRule.builder()
			.match(ubuntu22to23x86_64
				.andThen(ubuntu22xxMongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/linux/mongodb-linux-x86_64-ubuntu2204-{version}.tgz")
				.build())
			.build();

		PackageFinderRule ubuntu22x64dev = PackageFinderRule.builder()
			.match(ubuntu22to23x86_64
				.andThen(ubuntu22xxDevMongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/linux/mongodb-linux-x86_64-ubuntu2204-{version}.tgz")
				.isDevVersion(true)
				.build())
			.build();

		PackageFinderRule tools_ubuntu22x64 = PackageFinderRule.builder()
			.match(ubuntu22to23x86_64
				.andThen(ubuntu22xxMongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/tools/db/mongodb-database-tools-ubuntu2204-x86_64-{tools.version}.tgz")
				.build())
			.build();


		switch (command) {
					case MongoDump:
					case MongoImport:
					case MongoRestore:
							return PackageFinderRules.empty()
									.withRules(
											tools_ubuntu22arm, tools_ubuntu22x64,
											tools_ubuntu20to22arm, tools_ubuntu20to22x64,
											tools_ubuntu1804arm, tools_ubuntu1804x64,
											tools_ubuntu1604arm, tools_ubuntu1604x64
									);
			}

    return PackageFinderRules.empty()
            .withRules(
										ubuntu22armDev, ubuntu22arm, ubuntu22x64dev, ubuntu22x64,
										ubuntu20to22armDev, ubuntu20to22arm, ubuntu20To22x64dev, ubuntu20To22x64,
										ubuntu1804armDev, ubuntu1804arm, ubuntu1804x64dev, ubuntu1804x64,
										ubuntu1604arm, ubuntu1604x64
            );
  }
}
