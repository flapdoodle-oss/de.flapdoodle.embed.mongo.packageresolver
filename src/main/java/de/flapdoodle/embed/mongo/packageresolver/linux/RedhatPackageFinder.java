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
import de.flapdoodle.embed.process.config.store.Package;
import de.flapdoodle.embed.process.distribution.ArchiveType;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.os.BitSize;
import de.flapdoodle.os.CPUType;
import de.flapdoodle.os.CommonOS;
import de.flapdoodle.os.linux.*;
import de.flapdoodle.os.linux.RedhatVersion;


import java.util.Optional;

/**
* this file is generated, please don't touch
*/
public class RedhatPackageFinder extends AbstractPackageFinder implements HasLabel {

  public RedhatPackageFinder(final Command command) {
    super(command, rules(command));
  }

  @Override
  public String label() {
    return getClass().getSimpleName();
  }

  private static FileSet fileSetOf(Command command) {
    return FileSet.builder()
            .addEntry(FileType.Executable, command.commandName())
            .build();
  }

  private static ImmutablePackageFinderRules rules(final Command command) {
    FileSet fileSet = fileSetOf(command);

    PackageFinderRule devRule_Redhat_9_ARM_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.ARM, RedhatVersion.Redhat_9)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("8.0.0-rc9"),
                  VersionRange.of("8.0.0-rc7"),
                  VersionRange.of("8.0.0-rc3"),
                  VersionRange.of("7.3.3-rc0"),
                  VersionRange.of("7.3.0", "7.3.3"),
                  VersionRange.of("7.2.0-rc3"),
                  VersionRange.of("7.1.0", "7.1.1"),
                  VersionRange.of("7.0.18-rc0"),
                  VersionRange.of("7.0.15-rc1"),
                  VersionRange.of("7.0.8-rc0"),
                  VersionRange.of("7.0.3-rc1"),
                  VersionRange.of("7.0.0-rc8"),
                  VersionRange.of("7.0.0-rc2"),
                  VersionRange.of("7.0.0-rc10"),
                  VersionRange.of("6.0.21-rc1"),
                  VersionRange.of("6.0.16-rc0"),
                  VersionRange.of("6.0.9-rc1"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-aarch64-rhel90-{version}.tgz")
            .isDevVersion(true)
            .build())
        .build();

    PackageFinderRule rule_Redhat_9_ARM_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.ARM, RedhatVersion.Redhat_9)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("7.0.14", "7.0.17"),
                  VersionRange.of("7.0.11", "7.0.12"),
                  VersionRange.of("7.0.0", "7.0.9"),
                  VersionRange.of("6.0.7", "6.0.20"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-aarch64-rhel90-{version}.tgz")
            .build())
        .build();

    PackageFinderRule devRule_Redhat_9_ARM_B64_1 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.ARM, RedhatVersion.Redhat_9)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("8.0.6-rc2"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-aarch64-rhel93-{version}.tgz")
            .isDevVersion(true)
            .build())
        .build();

    PackageFinderRule rule_Redhat_9_ARM_B64_1 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.ARM, RedhatVersion.Redhat_9)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("8.0.3", "8.0.5"),
                  VersionRange.of("8.0.0", "8.0.1"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-aarch64-rhel93-{version}.tgz")
            .build())
        .build();

 
    PackageFinderRule tools_Redhat_9_ARM_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.ARM, RedhatVersion.Redhat_9)
            .andThen(
                DistributionMatch.any(
                  ToolVersionRange.of("100.9.0", "100.9.5"),
                  ToolVersionRange.of("100.8.0"),
                  ToolVersionRange.of("100.7.2", "100.7.5"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/tools/db/mongodb-database-tools-rhel90-aarch64-{tools.version}.tgz")
            .build())
        .build();

    PackageFinderRule tools_Redhat_9_ARM_B64_1 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.ARM, RedhatVersion.Redhat_9)
            .andThen(
                DistributionMatch.any(
                  ToolVersionRange.of("100.7.1"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/tools/db/mongodb-database-tools-rhel90-arm64-{tools.version}.tgz")
            .build())
        .build();

    PackageFinderRule tools_Redhat_9_ARM_B64_2 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.ARM, RedhatVersion.Redhat_9)
            .andThen(
                DistributionMatch.any(
                  ToolVersionRange.of("100.11.0"),
                  ToolVersionRange.of("100.10.0"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/tools/db/mongodb-database-tools-rhel93-aarch64-{tools.version}.tgz")
            .build())
        .build();


    PackageFinderRule devRule_Redhat_9_X86_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, RedhatVersion.Redhat_9)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("8.0.0-rc9"),
                  VersionRange.of("8.0.0-rc7"),
                  VersionRange.of("8.0.0-rc3"),
                  VersionRange.of("7.3.3-rc0"),
                  VersionRange.of("7.3.0", "7.3.3"),
                  VersionRange.of("7.2.0-rc3"),
                  VersionRange.of("7.1.0", "7.1.1"),
                  VersionRange.of("7.0.18-rc0"),
                  VersionRange.of("7.0.15-rc1"),
                  VersionRange.of("7.0.8-rc0"),
                  VersionRange.of("7.0.3-rc1"),
                  VersionRange.of("7.0.0-rc8"),
                  VersionRange.of("7.0.0-rc2"),
                  VersionRange.of("7.0.0-rc10"),
                  VersionRange.of("7.0.0-rc1"),
                  VersionRange.of("6.3.1", "6.3.2"),
                  VersionRange.of("6.0.21-rc1"),
                  VersionRange.of("6.0.16-rc0"),
                  VersionRange.of("6.0.9-rc1"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-x86_64-rhel90-{version}.tgz")
            .isDevVersion(true)
            .build())
        .build();

    PackageFinderRule rule_Redhat_9_X86_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, RedhatVersion.Redhat_9)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("7.0.14", "7.0.17"),
                  VersionRange.of("7.0.11", "7.0.12"),
                  VersionRange.of("7.0.0", "7.0.9"),
                  VersionRange.of("6.0.4", "6.0.20"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-x86_64-rhel90-{version}.tgz")
            .build())
        .build();

    PackageFinderRule devRule_Redhat_9_X86_B64_1 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, RedhatVersion.Redhat_9)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("8.0.6-rc2"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-x86_64-rhel93-{version}.tgz")
            .isDevVersion(true)
            .build())
        .build();

    PackageFinderRule rule_Redhat_9_X86_B64_1 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, RedhatVersion.Redhat_9)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("8.0.3", "8.0.5"),
                  VersionRange.of("8.0.0", "8.0.1"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-x86_64-rhel93-{version}.tgz")
            .build())
        .build();

 
    PackageFinderRule tools_Redhat_9_X86_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, RedhatVersion.Redhat_9)
            .andThen(
                DistributionMatch.any(
                  ToolVersionRange.of("100.9.0", "100.9.5"),
                  ToolVersionRange.of("100.8.0"),
                  ToolVersionRange.of("100.7.0", "100.7.5"),
                  ToolVersionRange.of("100.6.0", "100.6.1"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/tools/db/mongodb-database-tools-rhel90-x86_64-{tools.version}.tgz")
            .build())
        .build();

    PackageFinderRule tools_Redhat_9_X86_B64_1 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, RedhatVersion.Redhat_9)
            .andThen(
                DistributionMatch.any(
                  ToolVersionRange.of("100.11.0"),
                  ToolVersionRange.of("100.10.0"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/tools/db/mongodb-database-tools-rhel93-x86_64-{tools.version}.tgz")
            .build())
        .build();


    PackageFinderRule devRule_Redhat_8_ARM_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.ARM, RedhatVersion.Redhat_8)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("8.0.0-rc9"),
                  VersionRange.of("8.0.0-rc7"),
                  VersionRange.of("8.0.0-rc3"),
                  VersionRange.of("7.3.3-rc0"),
                  VersionRange.of("7.3.0", "7.3.3"),
                  VersionRange.of("7.2.0-rc3"),
                  VersionRange.of("7.1.0", "7.1.1"),
                  VersionRange.of("7.0.8-rc0"),
                  VersionRange.of("7.0.3-rc1"),
                  VersionRange.of("7.0.0-rc8"),
                  VersionRange.of("7.0.0-rc2"),
                  VersionRange.of("7.0.0-rc10"),
                  VersionRange.of("7.0.0-rc1"),
                  VersionRange.of("6.3.1", "6.3.2"),
                  VersionRange.of("6.0.16-rc0"),
                  VersionRange.of("6.0.9-rc1"),
                  VersionRange.of("5.0.28-rc0"),
                  VersionRange.of("5.0.20-rc1"),
                  VersionRange.of("4.4.27-rc0"),
                  VersionRange.of("4.4.24-rc0"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-aarch64-rhel82-{version}.tgz")
            .isDevVersion(true)
            .build())
        .build();

    PackageFinderRule rule_Redhat_8_ARM_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.ARM, RedhatVersion.Redhat_8)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("7.0.11", "7.0.12"),
                  VersionRange.of("7.0.0", "7.0.9"),
                  VersionRange.of("6.0.0", "6.0.16"),
                  VersionRange.of("5.0.0", "5.0.28"),
                  VersionRange.of("4.4.4", "4.4.29"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-aarch64-rhel82-{version}.tgz")
            .build())
        .build();

    PackageFinderRule devRule_Redhat_8_ARM_B64_1 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.ARM, RedhatVersion.Redhat_8)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("8.0.6-rc2"),
                  VersionRange.of("7.0.18-rc0"),
                  VersionRange.of("7.0.15-rc1"),
                  VersionRange.of("6.0.21-rc1"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-aarch64-rhel8-{version}.tgz")
            .isDevVersion(true)
            .build())
        .build();

    PackageFinderRule rule_Redhat_8_ARM_B64_1 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.ARM, RedhatVersion.Redhat_8)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("8.0.3", "8.0.5"),
                  VersionRange.of("8.0.0", "8.0.1"),
                  VersionRange.of("7.0.14", "7.0.17"),
                  VersionRange.of("6.0.17", "6.0.20"),
                  VersionRange.of("5.0.29", "5.0.31"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-aarch64-rhel8-{version}.tgz")
            .build())
        .build();

 
    PackageFinderRule tools_Redhat_8_ARM_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.ARM, RedhatVersion.Redhat_8)
            .andThen(
                DistributionMatch.any(
                  ToolVersionRange.of("100.7.0", "100.7.1"),
                  ToolVersionRange.of("100.6.0", "100.6.1"),
                  ToolVersionRange.of("100.5.0", "100.5.4"),
                  ToolVersionRange.of("100.4.0", "100.4.1"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/tools/db/mongodb-database-tools-rhel82-arm64-{tools.version}.tgz")
            .build())
        .build();

    PackageFinderRule tools_Redhat_8_ARM_B64_1 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.ARM, RedhatVersion.Redhat_8)
            .andThen(
                DistributionMatch.any(
                  ToolVersionRange.of("100.9.0", "100.9.5"),
                  ToolVersionRange.of("100.8.0"),
                  ToolVersionRange.of("100.7.2", "100.7.5"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/tools/db/mongodb-database-tools-rhel82-aarch64-{tools.version}.tgz")
            .build())
        .build();

    PackageFinderRule tools_Redhat_8_ARM_B64_2 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.ARM, RedhatVersion.Redhat_8)
            .andThen(
                DistributionMatch.any(
                  ToolVersionRange.of("100.11.0"),
                  ToolVersionRange.of("100.10.0"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/tools/db/mongodb-database-tools-rhel88-aarch64-{tools.version}.tgz")
            .build())
        .build();


    PackageFinderRule devRule_Redhat_8_X86_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, RedhatVersion.Redhat_8)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("8.0.0-rc9"),
                  VersionRange.of("8.0.0-rc7"),
                  VersionRange.of("8.0.0-rc3"),
                  VersionRange.of("7.3.3-rc0"),
                  VersionRange.of("7.3.0", "7.3.3"),
                  VersionRange.of("7.2.0-rc3"),
                  VersionRange.of("7.1.0", "7.1.1"),
                  VersionRange.of("7.0.8-rc0"),
                  VersionRange.of("7.0.3-rc1"),
                  VersionRange.of("7.0.0-rc8"),
                  VersionRange.of("7.0.0-rc2"),
                  VersionRange.of("7.0.0-rc10"),
                  VersionRange.of("7.0.0-rc1"),
                  VersionRange.of("6.3.1", "6.3.2"),
                  VersionRange.of("6.0.16-rc0"),
                  VersionRange.of("6.0.9-rc1"),
                  VersionRange.of("5.0.28-rc0"),
                  VersionRange.of("5.0.20-rc1"),
                  VersionRange.of("4.4.27-rc0"),
                  VersionRange.of("4.4.24-rc0"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-x86_64-rhel80-{version}.tgz")
            .isDevVersion(true)
            .build())
        .build();

    PackageFinderRule rule_Redhat_8_X86_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, RedhatVersion.Redhat_8)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("7.0.11", "7.0.12"),
                  VersionRange.of("7.0.0", "7.0.9"),
                  VersionRange.of("6.0.0", "6.0.16"),
                  VersionRange.of("5.0.0", "5.0.28"),
                  VersionRange.of("4.4.0", "4.4.29"),
                  VersionRange.of("4.2.5", "4.2.25"),
                  VersionRange.of("4.2.1", "4.2.3"),
                  VersionRange.of("4.0.14", "4.0.28"),
                  VersionRange.of("3.6.17", "3.6.23"),
                  VersionRange.of("3.4.24"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-x86_64-rhel80-{version}.tgz")
            .build())
        .build();

    PackageFinderRule devRule_Redhat_8_X86_B64_1 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, RedhatVersion.Redhat_8)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("8.0.6-rc2"),
                  VersionRange.of("7.0.18-rc0"),
                  VersionRange.of("7.0.15-rc1"),
                  VersionRange.of("6.0.21-rc1"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-x86_64-rhel8-{version}.tgz")
            .isDevVersion(true)
            .build())
        .build();

    PackageFinderRule rule_Redhat_8_X86_B64_1 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, RedhatVersion.Redhat_8)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("8.0.3", "8.0.5"),
                  VersionRange.of("8.0.0", "8.0.1"),
                  VersionRange.of("7.0.14", "7.0.17"),
                  VersionRange.of("6.0.17", "6.0.20"),
                  VersionRange.of("5.0.29", "5.0.31"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-x86_64-rhel8-{version}.tgz")
            .build())
        .build();

 
    PackageFinderRule tools_Redhat_8_X86_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, RedhatVersion.Redhat_8)
            .andThen(
                DistributionMatch.any(
                  ToolVersionRange.of("100.9.0", "100.9.5"),
                  ToolVersionRange.of("100.8.0"),
                  ToolVersionRange.of("100.7.0", "100.7.5"),
                  ToolVersionRange.of("100.6.0", "100.6.1"),
                  ToolVersionRange.of("100.5.0", "100.5.4"),
                  ToolVersionRange.of("100.4.0", "100.4.1"),
                  ToolVersionRange.of("100.3.0", "100.3.1"),
                  ToolVersionRange.of("100.2.0", "100.2.1"),
                  ToolVersionRange.of("100.1.0", "100.1.1"),
                  ToolVersionRange.of("100.0.0", "100.0.2"),
                  ToolVersionRange.of("99.0.0"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/tools/db/mongodb-database-tools-rhel80-x86_64-{tools.version}.tgz")
            .build())
        .build();

    PackageFinderRule tools_Redhat_8_X86_B64_1 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, RedhatVersion.Redhat_8)
            .andThen(
                DistributionMatch.any(
                  ToolVersionRange.of("100.11.0"),
                  ToolVersionRange.of("100.10.0"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/tools/db/mongodb-database-tools-rhel88-x86_64-{tools.version}.tgz")
            .build())
        .build();


    PackageFinderRule devRule_Redhat_7_X86_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, RedhatVersion.Redhat_7)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("7.3.3-rc0"),
                  VersionRange.of("7.3.0", "7.3.3"),
                  VersionRange.of("7.1.0", "7.1.1"),
                  VersionRange.of("7.0.18-rc0"),
                  VersionRange.of("7.0.15-rc1"),
                  VersionRange.of("7.0.8-rc0"),
                  VersionRange.of("7.0.3-rc1"),
                  VersionRange.of("7.0.0-rc8"),
                  VersionRange.of("7.0.0-rc2"),
                  VersionRange.of("7.0.0-rc10"),
                  VersionRange.of("7.0.0-rc1"),
                  VersionRange.of("6.3.1", "6.3.2"),
                  VersionRange.of("6.0.21-rc1"),
                  VersionRange.of("6.0.16-rc0"),
                  VersionRange.of("6.0.9-rc1"),
                  VersionRange.of("5.0.28-rc0"),
                  VersionRange.of("5.0.20-rc1"),
                  VersionRange.of("4.4.27-rc0"),
                  VersionRange.of("4.4.24-rc0"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-x86_64-rhel70-{version}.tgz")
            .isDevVersion(true)
            .build())
        .build();

    PackageFinderRule rule_Redhat_7_X86_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, RedhatVersion.Redhat_7)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("7.0.14", "7.0.17"),
                  VersionRange.of("7.0.11", "7.0.12"),
                  VersionRange.of("7.0.0", "7.0.9"),
                  VersionRange.of("6.0.0", "6.0.20"),
                  VersionRange.of("5.0.0", "5.0.31"),
                  VersionRange.of("4.4.0", "4.4.29"),
                  VersionRange.of("4.2.5", "4.2.25"),
                  VersionRange.of("4.2.0", "4.2.3"),
                  VersionRange.of("4.0.0", "4.0.28"),
                  VersionRange.of("3.6.0", "3.6.23"),
                  VersionRange.of("3.4.9", "3.4.24"),
                  VersionRange.of("3.4.0", "3.4.7"),
                  VersionRange.of("3.2.0", "3.2.22"),
                  VersionRange.of("3.0.0", "3.0.15"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-x86_64-rhel70-{version}.tgz")
            .build())
        .build();

 
    PackageFinderRule tools_Redhat_7_X86_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, RedhatVersion.Redhat_7)
            .andThen(
                DistributionMatch.any(
                  ToolVersionRange.of("100.11.0"),
                  ToolVersionRange.of("100.10.0"),
                  ToolVersionRange.of("100.9.0", "100.9.5"),
                  ToolVersionRange.of("100.8.0"),
                  ToolVersionRange.of("100.7.0", "100.7.5"),
                  ToolVersionRange.of("100.6.0", "100.6.1"),
                  ToolVersionRange.of("100.5.0", "100.5.4"),
                  ToolVersionRange.of("100.4.0", "100.4.1"),
                  ToolVersionRange.of("100.3.0", "100.3.1"),
                  ToolVersionRange.of("100.2.0", "100.2.1"),
                  ToolVersionRange.of("100.1.0", "100.1.1"),
                  ToolVersionRange.of("100.0.0", "100.0.2"),
                  ToolVersionRange.of("99.0.0"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/tools/db/mongodb-database-tools-rhel70-x86_64-{tools.version}.tgz")
            .build())
        .build();


    PackageFinderRule devRule_Redhat_6_X86_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, RedhatVersion.Redhat_6)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("4.4.27-rc0"),
                  VersionRange.of("4.4.24-rc0"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-x86_64-rhel62-{version}.tgz")
            .isDevVersion(true)
            .build())
        .build();

    PackageFinderRule rule_Redhat_6_X86_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, RedhatVersion.Redhat_6)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("4.4.0", "4.4.29"),
                  VersionRange.of("4.2.5", "4.2.25"),
                  VersionRange.of("4.2.0", "4.2.3"),
                  VersionRange.of("4.0.0", "4.0.28"),
                  VersionRange.of("3.6.0", "3.6.23"),
                  VersionRange.of("3.4.9", "3.4.24"),
                  VersionRange.of("3.4.0", "3.4.7"),
                  VersionRange.of("3.2.0", "3.2.22"),
                  VersionRange.of("3.0.0", "3.0.15"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-x86_64-rhel62-{version}.tgz")
            .build())
        .build();

 
    PackageFinderRule tools_Redhat_6_X86_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, RedhatVersion.Redhat_6)
            .andThen(
                DistributionMatch.any(
                  ToolVersionRange.of("100.10.0"),
                  ToolVersionRange.of("100.9.0", "100.9.5"),
                  ToolVersionRange.of("100.8.0"),
                  ToolVersionRange.of("100.7.0", "100.7.5"),
                  ToolVersionRange.of("100.6.0", "100.6.1"),
                  ToolVersionRange.of("100.5.0", "100.5.4"),
                  ToolVersionRange.of("100.4.0", "100.4.1"),
                  ToolVersionRange.of("100.3.0", "100.3.1"),
                  ToolVersionRange.of("100.2.0", "100.2.1"),
                  ToolVersionRange.of("100.1.0", "100.1.1"),
                  ToolVersionRange.of("100.0.0", "100.0.2"),
                  ToolVersionRange.of("99.0.0"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/tools/db/mongodb-database-tools-rhel62-x86_64-{tools.version}.tgz")
            .build())
        .build();


    switch (command) {
      case MongoDump:
      case MongoImport:
      case MongoRestore:
        return PackageFinderRules.empty()
            .withAdditionalRules(
                tools_Redhat_9_ARM_B64, tools_Redhat_9_ARM_B64_1, tools_Redhat_9_ARM_B64_2
            )
            .withAdditionalRules(
                tools_Redhat_9_X86_B64, tools_Redhat_9_X86_B64_1
            )
            .withAdditionalRules(
                tools_Redhat_8_ARM_B64, tools_Redhat_8_ARM_B64_1, tools_Redhat_8_ARM_B64_2
            )
            .withAdditionalRules(
                tools_Redhat_8_X86_B64, tools_Redhat_8_X86_B64_1
            )
            .withAdditionalRules(
                tools_Redhat_7_X86_B64
            )
            .withAdditionalRules(
                tools_Redhat_6_X86_B64
            )
            .withAdditionalRules(
                devRule_Redhat_8_X86_B64, rule_Redhat_8_X86_B64
            )
            .withAdditionalRules(
                devRule_Redhat_7_X86_B64, rule_Redhat_7_X86_B64
            )
            .withAdditionalRules(
                devRule_Redhat_6_X86_B64, rule_Redhat_6_X86_B64
            );
      default:
        return PackageFinderRules.empty()
            .withAdditionalRules(
                devRule_Redhat_9_ARM_B64, rule_Redhat_9_ARM_B64, devRule_Redhat_9_ARM_B64_1, rule_Redhat_9_ARM_B64_1
            ).withAdditionalRules(
                devRule_Redhat_9_X86_B64, rule_Redhat_9_X86_B64, devRule_Redhat_9_X86_B64_1, rule_Redhat_9_X86_B64_1
            ).withAdditionalRules(
                devRule_Redhat_8_ARM_B64, rule_Redhat_8_ARM_B64, devRule_Redhat_8_ARM_B64_1, rule_Redhat_8_ARM_B64_1
            ).withAdditionalRules(
                devRule_Redhat_8_X86_B64, rule_Redhat_8_X86_B64, devRule_Redhat_8_X86_B64_1, rule_Redhat_8_X86_B64_1
            ).withAdditionalRules(
                devRule_Redhat_7_X86_B64, rule_Redhat_7_X86_B64
            ).withAdditionalRules(
                devRule_Redhat_6_X86_B64, rule_Redhat_6_X86_B64
            );
    }
  }
}
