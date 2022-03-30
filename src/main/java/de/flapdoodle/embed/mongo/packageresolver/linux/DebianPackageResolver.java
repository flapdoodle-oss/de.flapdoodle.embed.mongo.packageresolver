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
import de.flapdoodle.os.linux.DebianVersion;

import java.util.Optional;

public class DebianPackageResolver implements PackageFinder, HasPlatformMatchRules {

    private final ImmutablePackageFinderRules rules;

    public DebianPackageResolver(final Command command) {
        this.rules = rules(command);
    }

    @Override
    public PackageFinderRules rules() {
      return rules;
    }

  @Override
    public Optional<DistributionPackage> packageFor(final Distribution distribution) {
        return rules.packageFor(distribution);
    }

  private static PlatformMatch match(BitSize bitSize, CPUType cpuType, DebianVersion... versions) {
    return PlatformMatch.withOs(OS.Linux).withBitSize(bitSize).withCpuType(cpuType)
      .withVersion(versions);
  }

  private static ImmutablePackageFinderRules rules(final Command command) {
        final ImmutableFileSet fileSet = FileSet.builder().addEntry(FileType.Executable, command.commandName()).build();

    DistributionMatch debian9MongoVersions = DistributionMatch.any(
      VersionRange.of("5.0.5", "5.0.6"),
      VersionRange.of("5.0.0", "5.0.2"),
      VersionRange.of("4.4.11", "4.4.11"),
      VersionRange.of("4.4.13", "4.4.13"),
      VersionRange.of("4.4.0", "4.4.9"),
      VersionRange.of("4.2.18", "4.2.19"),
      VersionRange.of("4.2.5", "4.2.16"),
      VersionRange.of("4.2.0", "4.2.3"),
      VersionRange.of("4.0.0", "4.0.28"),
      VersionRange.of("3.6.5", "3.6.23")
    );
    final PackageFinderRule debian9 = PackageFinderRule.builder()
                .match(match(BitSize.B64, CPUType.X86, DebianVersion.DEBIAN_9)
                  .andThen(debian9MongoVersions))
                .finder(UrlTemplatePackageResolver.builder()
                        .fileSet(fileSet)
                        .archiveType(ArchiveType.TGZ)
                        .urlTemplate("/linux/mongodb-linux-x86_64-debian92-{version}.tgz")
                        .build())
                .build();

        final PackageFinderRule debian9tools = PackageFinderRule.builder()
                .match(match(BitSize.B64, CPUType.X86, DebianVersion.DEBIAN_9)
                  .andThen(debian9MongoVersions))
                .finder(UrlTemplatePackageResolver.builder()
                        .fileSet(fileSet)
                        .archiveType(ArchiveType.TGZ)
                        .urlTemplate("/tools/db/mongodb-database-tools-debian92-x86_64-{tools.version}.tgz")
                        .build())
                .build();

    DistributionMatch debian10MongoVersions = DistributionMatch.any(
      VersionRange.of("5.0.5", "5.0.6"),
      VersionRange.of("5.0.0", "5.0.2"),
      VersionRange.of("4.4.13", "4.4.13"),
      VersionRange.of("4.4.11", "4.4.11"),
      VersionRange.of("4.4.0", "4.4.9"),
      VersionRange.of("4.2.18", "4.2.19"),
      VersionRange.of("4.2.5", "4.2.16"),
      VersionRange.of("4.2.1", "4.2.3")
    );
    final PackageFinderRule debian10 = PackageFinderRule.builder()
                .match(match(BitSize.B64, CPUType.X86, DebianVersion.DEBIAN_10, DebianVersion.DEBIAN_11).andThen(debian10MongoVersions))
                .finder(UrlTemplatePackageResolver.builder()
                        .fileSet(fileSet)
                        .archiveType(ArchiveType.TGZ)
                        .urlTemplate("/linux/mongodb-linux-x86_64-debian10-{version}.tgz")
                        .build())
                .build();

        final PackageFinderRule debian10tools = PackageFinderRule.builder()
                .match(match(BitSize.B64, CPUType.X86, DebianVersion.DEBIAN_10, DebianVersion.DEBIAN_11).andThen(debian10MongoVersions))
                .finder(UrlTemplatePackageResolver.builder()
                        .fileSet(fileSet)
                        .archiveType(ArchiveType.TGZ)
                        .urlTemplate("/tools/db/mongodb-database-tools-debian10-x86_64-{tools.version}.tgz")
                        .build())
                .build();

        switch (command) {
            case MongoDump:
            case MongoImport:
            case MongoRestore:
                return PackageFinderRules.empty()
                        .withRules(
                                debian9tools,
                                debian10tools
                        );
            default:
                return PackageFinderRules.empty()
                        .withRules(
                                debian9,
                                debian10
                        );
        }
    }
}
