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
import de.flapdoodle.os.OS;

import java.util.Optional;

public class SolarisPackageFinder implements PackageFinder, HasPlatformMatchRules {
  private final Command command;
  private final PlatformMatchRules rules;

  public SolarisPackageFinder(Command command) {
    this.command = command;
    this.rules = rules(command);
  }

  @Override
  public PlatformMatchRules rules() {
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
    return PlatformMatch.withOs(OS.Solaris).withBitSize(bitSize);
  }

  private static PlatformMatchRules rules(Command command) {
    FileSet fileSet = fileSetOf(command);
    ArchiveType archiveType = ArchiveType.TGZ;

    ImmutablePlatformMatchRule firstRule = PlatformMatchRule.builder()
            .match(match(BitSize.B64).andThen(DistributionMatch.any(
                            VersionRange.of("3.4.0", "3.4.5"),
                            VersionRange.of("3.2.0", "3.2.14"),
                            VersionRange.of("3.0.0", "3.0.15"),
                            VersionRange.of("2.6.0", "2.6.12")
                    )))
            .finder(UrlTemplatePackageResolver.builder()
                    .fileSet(fileSet)
                    .archiveType(archiveType)
                    .urlTemplate("/sunos5/mongodb-sunos5-x86_64-{version}.tgz")
                    .build())
            .build();

    ImmutablePlatformMatchRule hiddenLegacyRule = PlatformMatchRule.builder()
            .match(match(BitSize.B64).andThen(DistributionMatch.any(
                            VersionRange.of("3.3.1", "3.3.1"),
                            VersionRange.of("3.5.5", "3.5.5")
                    )))
            .finder(UrlTemplatePackageResolver.builder()
                    .fileSet(fileSet)
                    .archiveType(archiveType)
                    .urlTemplate("/sunos5/mongodb-sunos5-x86_64-{version}.tgz")
                    .build())
            .build();

    PlatformMatchRule failIfNothingMatches = PlatformMatchRule.builder()
            .match(PlatformMatch.withOs(OS.Solaris))
            .finder(PackageFinder.failWithMessage(distribution -> "solaris distribution not supported: " + distribution))
            .build();


    return PlatformMatchRules.empty()
            .withRules(
                    firstRule,
                    hiddenLegacyRule,
                    failIfNothingMatches
            );
  }

}
