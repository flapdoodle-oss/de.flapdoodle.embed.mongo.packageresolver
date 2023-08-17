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

import java.util.Optional;

/**
* this file is generated, please don't touch
*/
public class LinuxLegacyPackageFinder extends AbstractPackageFinder {

  public LinuxLegacyPackageFinder(final Command command) {
    super(command, rules(command));
  }

  private static FileSet fileSetOf(Command command) {
    return FileSet.builder()
            .addEntry(FileType.Executable, command.commandName())
            .build();
  }

  private static ImmutablePackageFinderRules rules(final Command command) {
    FileSet fileSet = fileSetOf(command);

    PackageFinderRule rule_Linux_X86_B64 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B64, CPUType.X86)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("4.0.0", "4.0.28"),
                  VersionRange.of("3.6.0", "3.6.23"),
                  VersionRange.of("3.5.5"),
                  VersionRange.of("3.4.9", "3.4.24"),
                  VersionRange.of("3.4.0", "3.4.7"),
                  VersionRange.of("3.3.1"),
                  VersionRange.of("3.2.0", "3.2.22"),
                  VersionRange.of("3.0.0", "3.0.15"),
                  VersionRange.of("2.6.0", "2.6.12"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-x86_64-{version}.tgz")
            .build())
        .build();

 
    PackageFinderRule rule_Linux_X86_B32 = PackageFinderRule.builder()
        .match(match(CommonOS.Linux, BitSize.B32, CPUType.X86)
            .andThen(
                DistributionMatch.any(
                  VersionRange.of("3.5.5"),
                  VersionRange.of("3.3.1"),
                  VersionRange.of("3.2.0", "3.2.22"),
                  VersionRange.of("3.0.0", "3.0.15"),
                  VersionRange.of("2.6.0", "2.6.12"))
        ))
        .finder(UrlTemplatePackageFinder.builder()
            .fileSet(fileSet)
            .archiveType(ArchiveType.TGZ)
            .urlTemplate("/linux/mongodb-linux-i686-{version}.tgz")
            .build())
        .build();

 
    switch (command) {
      case MongoDump:
      case MongoImport:
      case MongoRestore:
        return PackageFinderRules.empty()
            .withAdditionalRules(
                rule_Linux_X86_B64
            )
            .withAdditionalRules(
                rule_Linux_X86_B32
            );
      default:
        return PackageFinderRules.empty()
            .withAdditionalRules(
                rule_Linux_X86_B64
            ).withAdditionalRules(
                rule_Linux_X86_B32
            );
    }
  }
}
