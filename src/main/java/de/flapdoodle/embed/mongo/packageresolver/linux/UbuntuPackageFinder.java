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
import de.flapdoodle.os.linux.UbuntuVersion;


import java.util.Optional;

/**
* this file is generated, please don't touch
*/
public class UbuntuPackageFinder extends AbstractPackageFinder implements HasLabel {

  public UbuntuPackageFinder(final Command command) {
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

    PackageFinderRule devRule_Ubuntu_22_04_ARM_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.ARM, UbuntuVersion.Ubuntu_22_04)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("8.0.0-rc7"),
                  VersionRange.of("8.0.0-rc3"),
                  VersionRange.of("7.3.3-rc0"),
                  VersionRange.of("7.3.2-rc1"),
                  VersionRange.of("7.3.0", "7.3.2"),
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
                  VersionRange.of("6.0.9-rc1"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-aarch64-ubuntu2204-{version}.tgz")
            .isDevVersion(true)
            .build())
        .build();

    PackageFinderRule rule_Ubuntu_22_04_ARM_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.ARM, UbuntuVersion.Ubuntu_22_04)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("7.0.11"),
                  VersionRange.of("7.0.0", "7.0.9"),
                  VersionRange.of("6.0.4", "6.0.15"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-aarch64-ubuntu2204-{version}.tgz")
            .build())
        .build();

 
    PackageFinderRule tools_Ubuntu_22_04_ARM_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.ARM, UbuntuVersion.Ubuntu_22_04)
            .andThen(
                DistributionMatch.any(
                  ToolVersionRange.of("100.9.0", "100.9.4"),
                  ToolVersionRange.of("100.8.0"),
                  ToolVersionRange.of("100.7.0", "100.7.5"),
                  ToolVersionRange.of("100.6.0", "100.6.1"),
                  ToolVersionRange.of("100.5.4"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/tools/db/mongodb-database-tools-ubuntu2204-arm64-{tools.version}.tgz")
            .build())
        .build();


    PackageFinderRule devRule_Ubuntu_22_04_X86_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, UbuntuVersion.Ubuntu_22_04)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("8.0.0-rc7"),
                  VersionRange.of("8.0.0-rc3"),
                  VersionRange.of("7.3.3-rc0"),
                  VersionRange.of("7.3.2-rc1"),
                  VersionRange.of("7.3.0", "7.3.2"),
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
                  VersionRange.of("6.0.9-rc1"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-x86_64-ubuntu2204-{version}.tgz")
            .isDevVersion(true)
            .build())
        .build();

    PackageFinderRule rule_Ubuntu_22_04_X86_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, UbuntuVersion.Ubuntu_22_04)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("7.0.11"),
                  VersionRange.of("7.0.0", "7.0.9"),
                  VersionRange.of("6.0.4", "6.0.15"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-x86_64-ubuntu2204-{version}.tgz")
            .build())
        .build();

 
    PackageFinderRule tools_Ubuntu_22_04_X86_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, UbuntuVersion.Ubuntu_22_04)
            .andThen(
                DistributionMatch.any(
                  ToolVersionRange.of("100.9.0", "100.9.4"),
                  ToolVersionRange.of("100.8.0"),
                  ToolVersionRange.of("100.7.0", "100.7.5"),
                  ToolVersionRange.of("100.6.0", "100.6.1"),
                  ToolVersionRange.of("100.5.4"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/tools/db/mongodb-database-tools-ubuntu2204-x86_64-{tools.version}.tgz")
            .build())
        .build();


    PackageFinderRule devRule_Ubuntu_20_04_ARM_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.ARM, UbuntuVersion.Ubuntu_20_04)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("8.0.0-rc7"),
                  VersionRange.of("8.0.0-rc3"),
                  VersionRange.of("7.3.3-rc0"),
                  VersionRange.of("7.3.2-rc1"),
                  VersionRange.of("7.3.0", "7.3.2"),
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
                  VersionRange.of("5.0.20-rc1"),
                  VersionRange.of("4.4.27-rc0"),
                  VersionRange.of("4.4.24-rc0"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-aarch64-ubuntu2004-{version}.tgz")
            .isDevVersion(true)
            .build())
        .build();

    PackageFinderRule rule_Ubuntu_20_04_ARM_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.ARM, UbuntuVersion.Ubuntu_20_04)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("7.0.11"),
                  VersionRange.of("7.0.0", "7.0.9"),
                  VersionRange.of("6.0.0", "6.0.15"),
                  VersionRange.of("5.0.0", "5.0.27"),
                  VersionRange.of("4.4.0", "4.4.29"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-aarch64-ubuntu2004-{version}.tgz")
            .build())
        .build();

 
    PackageFinderRule tools_Ubuntu_20_04_ARM_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.ARM, UbuntuVersion.Ubuntu_20_04)
            .andThen(
                DistributionMatch.any(
                  ToolVersionRange.of("100.9.0", "100.9.4"),
                  ToolVersionRange.of("100.8.0"),
                  ToolVersionRange.of("100.7.0", "100.7.5"),
                  ToolVersionRange.of("100.6.0", "100.6.1"),
                  ToolVersionRange.of("100.5.0", "100.5.4"),
                  ToolVersionRange.of("100.4.0", "100.4.1"),
                  ToolVersionRange.of("100.3.0", "100.3.1"),
                  ToolVersionRange.of("100.2.0", "100.2.1"),
                  ToolVersionRange.of("100.1.0", "100.1.1"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/tools/db/mongodb-database-tools-ubuntu2004-arm64-{tools.version}.tgz")
            .build())
        .build();


    PackageFinderRule devRule_Ubuntu_20_04_X86_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, UbuntuVersion.Ubuntu_20_04)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("8.0.0-rc7"),
                  VersionRange.of("8.0.0-rc3"),
                  VersionRange.of("7.3.3-rc0"),
                  VersionRange.of("7.3.2-rc1"),
                  VersionRange.of("7.3.0", "7.3.2"),
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
                  VersionRange.of("5.0.20-rc1"),
                  VersionRange.of("4.4.27-rc0"),
                  VersionRange.of("4.4.24-rc0"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-x86_64-ubuntu2004-{version}.tgz")
            .isDevVersion(true)
            .build())
        .build();

    PackageFinderRule rule_Ubuntu_20_04_X86_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, UbuntuVersion.Ubuntu_20_04)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("7.0.11"),
                  VersionRange.of("7.0.0", "7.0.9"),
                  VersionRange.of("6.0.0", "6.0.15"),
                  VersionRange.of("5.0.0", "5.0.27"),
                  VersionRange.of("4.4.0", "4.4.29"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-x86_64-ubuntu2004-{version}.tgz")
            .build())
        .build();

 
    PackageFinderRule tools_Ubuntu_20_04_X86_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, UbuntuVersion.Ubuntu_20_04)
            .andThen(
                DistributionMatch.any(
                  ToolVersionRange.of("100.9.0", "100.9.4"),
                  ToolVersionRange.of("100.8.0"),
                  ToolVersionRange.of("100.7.0", "100.7.5"),
                  ToolVersionRange.of("100.6.0", "100.6.1"),
                  ToolVersionRange.of("100.5.0", "100.5.4"),
                  ToolVersionRange.of("100.4.0", "100.4.1"),
                  ToolVersionRange.of("100.3.0", "100.3.1"),
                  ToolVersionRange.of("100.2.0", "100.2.1"),
                  ToolVersionRange.of("100.1.0", "100.1.1"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/tools/db/mongodb-database-tools-ubuntu2004-x86_64-{tools.version}.tgz")
            .build())
        .build();


    PackageFinderRule devRule_Ubuntu_18_04_ARM_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.ARM, UbuntuVersion.Ubuntu_18_04)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("7.0.0-rc2"),
                  VersionRange.of("7.0.0-rc1"),
                  VersionRange.of("6.3.1", "6.3.2"),
                  VersionRange.of("6.0.16-rc0"),
                  VersionRange.of("6.0.9-rc1"),
                  VersionRange.of("5.0.20-rc1"),
                  VersionRange.of("4.4.27-rc0"),
                  VersionRange.of("4.4.24-rc0"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-aarch64-ubuntu1804-{version}.tgz")
            .isDevVersion(true)
            .build())
        .build();

    PackageFinderRule rule_Ubuntu_18_04_ARM_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.ARM, UbuntuVersion.Ubuntu_18_04)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("6.0.0", "6.0.15"),
                  VersionRange.of("5.0.0", "5.0.27"),
                  VersionRange.of("4.4.0", "4.4.29"),
                  VersionRange.of("4.2.5", "4.2.25"),
                  VersionRange.of("4.2.0", "4.2.3"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-aarch64-ubuntu1804-{version}.tgz")
            .build())
        .build();

 
    PackageFinderRule tools_Ubuntu_18_04_ARM_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.ARM, UbuntuVersion.Ubuntu_18_04)
            .andThen(
                DistributionMatch.any(
                  ToolVersionRange.of("100.9.0", "100.9.4"),
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
            .urlTemplate("/tools/db/mongodb-database-tools-ubuntu1804-arm64-{tools.version}.tgz")
            .build())
        .build();


    PackageFinderRule devRule_Ubuntu_18_04_X86_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, UbuntuVersion.Ubuntu_18_04)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("7.0.0-rc2"),
                  VersionRange.of("7.0.0-rc1"),
                  VersionRange.of("6.3.1", "6.3.2"),
                  VersionRange.of("6.0.16-rc0"),
                  VersionRange.of("6.0.9-rc1"),
                  VersionRange.of("5.0.20-rc1"),
                  VersionRange.of("4.4.27-rc0"),
                  VersionRange.of("4.4.24-rc0"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-x86_64-ubuntu1804-{version}.tgz")
            .isDevVersion(true)
            .build())
        .build();

    PackageFinderRule rule_Ubuntu_18_04_X86_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, UbuntuVersion.Ubuntu_18_04)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("6.0.0", "6.0.15"),
                  VersionRange.of("5.0.0", "5.0.27"),
                  VersionRange.of("4.4.0", "4.4.29"),
                  VersionRange.of("4.2.5", "4.2.25"),
                  VersionRange.of("4.2.0", "4.2.3"),
                  VersionRange.of("4.0.1", "4.0.28"),
                  VersionRange.of("3.6.20", "3.6.23"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-x86_64-ubuntu1804-{version}.tgz")
            .build())
        .build();

 
    PackageFinderRule tools_Ubuntu_18_04_X86_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, UbuntuVersion.Ubuntu_18_04)
            .andThen(
                DistributionMatch.any(
                  ToolVersionRange.of("100.9.0", "100.9.4"),
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
            .urlTemplate("/tools/db/mongodb-database-tools-ubuntu1804-x86_64-{tools.version}.tgz")
            .build())
        .build();


    PackageFinderRule rule_Ubuntu_16_04_ARM_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.ARM, UbuntuVersion.Ubuntu_16_04)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("4.0.0", "4.0.28"),
                  VersionRange.of("3.6.0", "3.6.23"),
                  VersionRange.of("3.4.9", "3.4.24"),
                  VersionRange.of("3.4.0", "3.4.7"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-arm64-ubuntu1604-{version}.tgz")
            .build())
        .build();

 
    PackageFinderRule tools_Ubuntu_16_04_ARM_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.ARM, UbuntuVersion.Ubuntu_16_04)
            .andThen(
                DistributionMatch.any(
                  ToolVersionRange.of("100.9.0", "100.9.4"),
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
            .urlTemplate("/tools/db/mongodb-database-tools-ubuntu1604-arm64-{tools.version}.tgz")
            .build())
        .build();


    PackageFinderRule devRule_Ubuntu_16_04_X86_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, UbuntuVersion.Ubuntu_16_04)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("4.4.27-rc0"),
                  VersionRange.of("4.4.24-rc0"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-x86_64-ubuntu1604-{version}.tgz")
            .isDevVersion(true)
            .build())
        .build();

    PackageFinderRule rule_Ubuntu_16_04_X86_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, UbuntuVersion.Ubuntu_16_04)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("4.4.0", "4.4.29"),
                  VersionRange.of("4.2.5", "4.2.25"),
                  VersionRange.of("4.2.0", "4.2.3"),
                  VersionRange.of("4.0.0", "4.0.28"),
                  VersionRange.of("3.6.0", "3.6.23"),
                  VersionRange.of("3.4.9", "3.4.24"),
                  VersionRange.of("3.4.0", "3.4.7"),
                  VersionRange.of("3.2.7", "3.2.22"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-x86_64-ubuntu1604-{version}.tgz")
            .build())
        .build();

 
    PackageFinderRule tools_Ubuntu_16_04_X86_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, UbuntuVersion.Ubuntu_16_04)
            .andThen(
                DistributionMatch.any(
                  ToolVersionRange.of("100.9.0", "100.9.4"),
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
            .urlTemplate("/tools/db/mongodb-database-tools-ubuntu1604-x86_64-{tools.version}.tgz")
            .build())
        .build();


    switch (command) {
      case MongoDump:
      case MongoImport:
      case MongoRestore:
        return PackageFinderRules.empty()
            .withAdditionalRules(
                tools_Ubuntu_22_04_ARM_B64
            )
            .withAdditionalRules(
                tools_Ubuntu_22_04_X86_B64
            )
            .withAdditionalRules(
                tools_Ubuntu_20_04_ARM_B64
            )
            .withAdditionalRules(
                tools_Ubuntu_20_04_X86_B64
            )
            .withAdditionalRules(
                tools_Ubuntu_18_04_ARM_B64
            )
            .withAdditionalRules(
                tools_Ubuntu_18_04_X86_B64
            )
            .withAdditionalRules(
                tools_Ubuntu_16_04_ARM_B64
            )
            .withAdditionalRules(
                tools_Ubuntu_16_04_X86_B64
            )
            .withAdditionalRules(
                devRule_Ubuntu_18_04_ARM_B64, rule_Ubuntu_18_04_ARM_B64
            )
            .withAdditionalRules(
                devRule_Ubuntu_18_04_X86_B64, rule_Ubuntu_18_04_X86_B64
            )
            .withAdditionalRules(
                rule_Ubuntu_16_04_ARM_B64
            )
            .withAdditionalRules(
                devRule_Ubuntu_16_04_X86_B64, rule_Ubuntu_16_04_X86_B64
            );
      default:
        return PackageFinderRules.empty()
            .withAdditionalRules(
                devRule_Ubuntu_22_04_ARM_B64, rule_Ubuntu_22_04_ARM_B64
            ).withAdditionalRules(
                devRule_Ubuntu_22_04_X86_B64, rule_Ubuntu_22_04_X86_B64
            ).withAdditionalRules(
                devRule_Ubuntu_20_04_ARM_B64, rule_Ubuntu_20_04_ARM_B64
            ).withAdditionalRules(
                devRule_Ubuntu_20_04_X86_B64, rule_Ubuntu_20_04_X86_B64
            ).withAdditionalRules(
                devRule_Ubuntu_18_04_ARM_B64, rule_Ubuntu_18_04_ARM_B64
            ).withAdditionalRules(
                devRule_Ubuntu_18_04_X86_B64, rule_Ubuntu_18_04_X86_B64
            ).withAdditionalRules(
                rule_Ubuntu_16_04_ARM_B64
            ).withAdditionalRules(
                devRule_Ubuntu_16_04_X86_B64, rule_Ubuntu_16_04_X86_B64
            );
    }
  }
}
