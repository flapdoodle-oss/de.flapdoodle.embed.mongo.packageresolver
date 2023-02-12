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
import de.flapdoodle.embed.process.config.store.DistributionPackage;
import de.flapdoodle.embed.process.config.store.FileSet;
import de.flapdoodle.embed.process.config.store.FileType;
import de.flapdoodle.embed.process.config.store.ImmutableFileSet;
import de.flapdoodle.embed.process.distribution.ArchiveType;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.os.BitSize;
import de.flapdoodle.os.CPUType;
import de.flapdoodle.os.OS;
import de.flapdoodle.os.Version;
import de.flapdoodle.os.linux.CentosVersion;
import de.flapdoodle.os.linux.OracleVersion;
import de.flapdoodle.os.linux.RedhatVersion;

import java.util.Optional;

public class CentosRedhatPackageResolver implements PackageFinder, HasPlatformMatchRules {

  private final Command command;
  private final ImmutablePackageFinderRules rules;

  public CentosRedhatPackageResolver(Command command) {
    this.command = command;
    this.rules = rules(command);
  }

	@Override
	public PackageFinderRules rules() {
		return rules;
	}

	@Override
  public Optional<DistributionPackage> packageFor(Distribution distribution) {
    return rules.packageFor(distribution);
  }

	private static PlatformMatch match(BitSize bitSize, CPUType cpuType, Version... versions) {
		return PlatformMatch.withOs(OS.Linux).withBitSize(bitSize).withCpuType(cpuType)
			.withVersion(versions);
	}

  private static ImmutablePackageFinderRules rules(Command command) {
    ImmutableFileSet fileSet = FileSet.builder().addEntry(FileType.Executable, command.commandName()).build();

		DistributionMatch centos6mongoVersions = DistributionMatch.any(
			VersionRange.of("4.4.16", "4.4.18"),
			VersionRange.of("4.4.13", "4.4.13"),
			VersionRange.of("4.4.11", "4.4.11"),
			VersionRange.of("4.4.0", "4.4.9"),
			VersionRange.of("4.2.22", "4.2.23"),
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
		PackageFinderRule centos6 = PackageFinderRule.builder()
			.match(match(BitSize.B64, CPUType.X86,
				CentosVersion.CentOS_6, RedhatVersion.Redhat_6, OracleVersion.Oracle_6).andThen(centos6mongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/linux/mongodb-linux-x86_64-rhel62-{version}.tgz")
				.build())
			.build();

		PackageFinderRule tools_centos6 = PackageFinderRule.builder()
			.match(match(BitSize.B64, CPUType.X86,
					CentosVersion.CentOS_6, RedhatVersion.Redhat_6, OracleVersion.Oracle_6
				).andThen(centos6mongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/tools/db/mongodb-database-tools-rhel62-x86_64-{tools.version}.tgz")
				.build())
			.build();

		DistributionMatch centos7MongoVersions = DistributionMatch.any(
			VersionRange.of("6.0.1", "6.0.4"),
			VersionRange.of("5.0.12", "5.0.14"),
			VersionRange.of("5.0.5", "5.0.6"),
			VersionRange.of("5.0.0", "5.0.2"),
			VersionRange.of("4.4.16", "4.4.18"),
			VersionRange.of("4.4.13", "4.4.13"),
			VersionRange.of("4.4.11", "4.4.11"),
			VersionRange.of("4.4.0", "4.4.9"),
			VersionRange.of("4.2.22", "4.2.23"),
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

		PackageFinderRule centos7 = PackageFinderRule.builder()
			.match(match(BitSize.B64, CPUType.X86,
					CentosVersion.CentOS_7, RedhatVersion.Redhat_7, OracleVersion.Oracle_7
				).andThen(centos7MongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/linux/mongodb-linux-x86_64-rhel70-{version}.tgz")
				.build())
			.build();

		PackageFinderRule tools_centos7 = PackageFinderRule.builder()
			.match(match(BitSize.B64, CPUType.X86,
					CentosVersion.CentOS_7, RedhatVersion.Redhat_7, OracleVersion.Oracle_7
				).andThen(centos7MongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/tools/db/mongodb-database-tools-rhel70-x86_64-{tools.version}.tgz")
				.build())
			.build();


		DistributionMatch centos8MongoVersions = DistributionMatch.any(
			VersionRange.of("6.0.1", "6.0.4"),
			VersionRange.of("5.0.12", "5.0.14"),
			VersionRange.of("5.0.5", "5.0.6"),
			VersionRange.of("5.0.0", "5.0.2"),
			VersionRange.of("4.4.16", "4.4.18"),
			VersionRange.of("4.4.13", "4.4.13"),
			VersionRange.of("4.4.11", "4.4.11"),
			VersionRange.of("4.4.0", "4.4.9"),
			VersionRange.of("4.2.22", "4.2.23"),
			VersionRange.of("4.2.18", "4.2.19"),
			VersionRange.of("4.2.5", "4.2.16"),
			VersionRange.of("4.2.1", "4.2.3"),
			VersionRange.of("4.0.14", "4.0.28"),
			VersionRange.of("3.6.17", "3.6.23"),
			VersionRange.of("3.4.24", "3.4.24")
		);
		PackageFinderRule centos8 = PackageFinderRule.builder()
			.match(match(BitSize.B64, CPUType.X86,
					CentosVersion.CentOS_8, RedhatVersion.Redhat_8, OracleVersion.Oracle_8,
					CentosVersion.CentOS_9, RedhatVersion.Redhat_9, OracleVersion.Oracle_9
				).andThen(centos8MongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/linux/mongodb-linux-x86_64-rhel80-{version}.tgz")
				.build())
			.build();

		PackageFinderRule tools_centos8 = PackageFinderRule.builder()
			.match(match(BitSize.B64, CPUType.X86,
					CentosVersion.CentOS_8, RedhatVersion.Redhat_8, OracleVersion.Oracle_8,
					CentosVersion.CentOS_9, RedhatVersion.Redhat_9, OracleVersion.Oracle_9
				).andThen(centos8MongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/tools/db/mongodb-database-tools-rhel80-x86_64-{tools.version}.tgz")
				.build())
			.build();

		DistributionMatch centos8ArmMongoVersions = DistributionMatch.any(
			VersionRange.of("6.0.1", "6.0.4"),
			VersionRange.of("5.0.12", "5.0.14"),
			VersionRange.of("5.0.5", "5.0.6"),
			VersionRange.of("5.0.0", "5.0.2"),
			VersionRange.of("4.4.16", "4.4.18"),
			VersionRange.of("4.4.13", "4.4.13"),
			VersionRange.of("4.4.11", "4.4.11"),
			VersionRange.of("4.4.4", "4.4.9")
		);
		PackageFinderRule centos8arm = PackageFinderRule.builder()
					.match(match(BitSize.B64, CPUType.ARM,
						CentosVersion.CentOS_8, RedhatVersion.Redhat_8, OracleVersion.Oracle_8,
						CentosVersion.CentOS_9, RedhatVersion.Redhat_9, OracleVersion.Oracle_9
					).andThen(centos8ArmMongoVersions))
					.finder(UrlTemplatePackageResolver.builder()
							.fileSet(fileSet)
							.archiveType(ArchiveType.TGZ)
							.urlTemplate("/linux/mongodb-linux-aarch64-rhel82-{version}.tgz")
							.build())
					.build();

			PackageFinderRule tools_centos8arm = PackageFinderRule.builder()
					.match(match(BitSize.B64, CPUType.ARM,
							CentosVersion.CentOS_8, RedhatVersion.Redhat_8, OracleVersion.Oracle_8,
							CentosVersion.CentOS_9, RedhatVersion.Redhat_9, OracleVersion.Oracle_9
						).andThen(centos8ArmMongoVersions))
					.finder(UrlTemplatePackageResolver.builder()
							.fileSet(fileSet)
							.archiveType(ArchiveType.TGZ)
							.urlTemplate("/tools/db/mongodb-database-tools-rhel82-arm64-{tools.version}.tgz")
							.build())
					.build();

		DistributionMatch centos9MongoVersions = DistributionMatch.any(
			VersionRange.of("6.0.4")
		);
		PackageFinderRule centos9 = PackageFinderRule.builder()
			.match(match(BitSize.B64, CPUType.X86,
				CentosVersion.CentOS_9, RedhatVersion.Redhat_9, OracleVersion.Oracle_9
			).andThen(centos9MongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/linux/mongodb-linux-x86_64-rhel90-{version}.tgz")
				.build())
			.build();

		PackageFinderRule tools_centos9 = PackageFinderRule.builder()
			.match(match(BitSize.B64, CPUType.X86,
				CentosVersion.CentOS_9, RedhatVersion.Redhat_9, OracleVersion.Oracle_9
			).andThen(centos9MongoVersions))
			.finder(UrlTemplatePackageResolver.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/tools/db/mongodb-database-tools-rhel90-x86_64-{tools.version}.tgz")
				.build())
			.build();

			switch (command) {
					case MongoDump:
					case MongoImport:
					case MongoRestore:
							return PackageFinderRules.empty()
									.withRules(
										tools_centos9,
											tools_centos6, tools_centos7, tools_centos8, tools_centos8arm
									);
			}

    return PackageFinderRules.empty()
            .withRules(
							centos9,
                    centos6, centos7, centos8, centos8arm
            );
  }
}
