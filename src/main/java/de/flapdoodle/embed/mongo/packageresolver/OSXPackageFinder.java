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
package de.flapdoodle.embed.mongo.packageresolver;

import de.flapdoodle.embed.process.config.store.DistributionPackage;
import de.flapdoodle.embed.process.config.store.FileSet;
import de.flapdoodle.embed.process.config.store.FileType;
import de.flapdoodle.embed.process.distribution.ArchiveType;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.os.BitSize;
import de.flapdoodle.os.CPUType;
import de.flapdoodle.os.OS;
import de.flapdoodle.os.linux.UbuntuVersion;

import java.util.Optional;

public class OSXPackageFinder implements PackageFinder, HasPlatformMatchRules {
  private final Command command;
  private final ImmutablePackageFinderRules rules;

  public OSXPackageFinder(Command command) {
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

  private static FileSet fileSetOf(Command command) {
    return FileSet.builder()
            .addEntry(FileType.Executable, command.commandName())
            .build();
  }

  private static PlatformMatch match(BitSize bitSize) {
    return PlatformMatch.withOs(OS.OS_X).withBitSize(bitSize);
  }

  private static PlatformMatch match(BitSize bitSize, CPUType cpuType) {
    return PlatformMatch.withOs(OS.OS_X).withBitSize(bitSize).withCpuType(cpuType);
  }


  private static ImmutablePackageFinderRules rules(Command command) {
    FileSet fileSet = fileSetOf(command);
    ArchiveType archiveType = ArchiveType.TGZ;

    ImmutablePackageFinderRule armRule = PackageFinderRule.builder()
            .match(match(BitSize.B64, CPUType.ARM).andThen(DistributionMatch.any(
                VersionRange.of("6.0.1", "6.0.4")
            )))
            .finder(UrlTemplatePackageResolver.builder()
                .fileSet(fileSet)
                .archiveType(archiveType)
                .urlTemplate("/osx/mongodb-macos-arm64-{version}.tgz")
                .build())
            .build();

    ImmutablePackageFinderRule firstRule = PackageFinderRule.builder()
            .match(match(BitSize.B64).andThen(DistributionMatch.any(
                            VersionRange.of("4.0.0", "4.0.28"),
                            VersionRange.of("3.6.0", "3.6.23"),
                            VersionRange.of("3.4.9", "3.4.24"),
                            VersionRange.of("3.4.0", "3.4.7"),
                            VersionRange.of("3.2.0", "3.2.22"),
                            VersionRange.of("3.0.4", "3.0.15")
                    )))
            .finder(UrlTemplatePackageResolver.builder()
                    .fileSet(fileSet)
                    .archiveType(archiveType)
                    .urlTemplate("/osx/mongodb-osx-ssl-x86_64-{version}.tgz")
                    .build())
            .build();

    ImmutablePackageFinderRule thirdRule = PackageFinderRule.builder()
            .match(match(BitSize.B64).andThen(DistributionMatch.any(
                            VersionRange.of("3.5.5", "3.5.5"), // missing in overview
                            VersionRange.of("3.4.9", "3.4.24"),
                            VersionRange.of("3.4.0", "3.4.7"),
                            VersionRange.of("3.3.1", "3.3.1"), // missing in overview
                            VersionRange.of("3.2.0", "3.2.22"),
                            VersionRange.of("3.0.0", "3.0.15"),
                            VersionRange.of("2.6.0", "2.6.12")
                    )))
            .finder(UrlTemplatePackageResolver.builder()
                    .fileSet(fileSet)
                    .archiveType(archiveType)
                    .urlTemplate("/osx/mongodb-osx-x86_64-{version}.tgz")
                    .build())
            .build();

    ImmutablePackageFinderRule fourthRule = PackageFinderRule.builder()
            .match(match(BitSize.B64).andThen(DistributionMatch.any(
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
                            VersionRange.of("4.2.0", "4.2.3")
                    )))
            .finder(UrlTemplatePackageResolver.builder()
                    .fileSet(fileSet)
                    .archiveType(archiveType)
                    .urlTemplate("/osx/mongodb-macos-x86_64-{version}.tgz")
                    .build())
            .build();

      ImmutablePackageFinderRule toolsRule = PackageFinderRule.builder()
          .match(match(BitSize.B64).andThen(DistributionMatch.any(
                  VersionRange.of("6.0.1", "6.0.4"),
                  VersionRange.of("5.0.12", "5.0.14"),
                  VersionRange.of("5.0.5", "5.0.6"),
                  VersionRange.of("5.0.0", "5.0.2"),
                  VersionRange.of("4.4.16", "4.4.18"),
                  VersionRange.of("4.4.13", "4.4.13"),
                  VersionRange.of("4.4.11", "4.4.11"),
                  VersionRange.of("4.4.0", "4.4.9")
              )))
          .finder(UrlTemplatePackageResolver.builder()
              .fileSet(fileSet)
              .archiveType(ArchiveType.ZIP)
              .urlTemplate("/tools/db/mongodb-database-tools-macos-x86_64-{tools.version}.zip")
              .build())
          .build();

      PackageFinderRule failIfNothingMatches = PackageFinderRule.builder()
            .match(PlatformMatch.withOs(OS.OS_X))
            .finder(PackageFinder.failWithMessage(distribution -> "osx distribution not supported: " + distribution))
            .build();

      switch (command) {
          case MongoDump:
          case MongoImport:
          case MongoRestore:
              return PackageFinderRules.empty()
                  .withRules(
                      toolsRule,
                      firstRule,
                      thirdRule,
                      fourthRule,
                      failIfNothingMatches
                  );
      }

    return PackageFinderRules.empty()
            .withRules(
                    armRule,
                    firstRule,
                    thirdRule,
                    fourthRule,
                    failIfNothingMatches
            );
  }

}
