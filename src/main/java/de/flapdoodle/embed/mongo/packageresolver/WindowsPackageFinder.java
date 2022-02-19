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

public class WindowsPackageFinder implements PackageFinder, HasPlatformMatchRules {
  private final Command command;
  private final ImmutablePlatformMatchRules rules;

  public WindowsPackageFinder(Command command) {
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
            .addEntry(FileType.Executable, command.commandName() + ".exe")
            .build();
  }

  private static PlatformMatch match(BitSize bitSize) {
    return PlatformMatch.withOs(OS.Windows).withBitSize(bitSize);
  }

  private static ImmutablePlatformMatchRules rules(Command command) {
    FileSet fileSet = fileSetOf(command);
    ArchiveType archiveType = ArchiveType.ZIP;

    ImmutablePlatformMatchRule windowsServer_2008_rule = PlatformMatchRule.builder()
            .match(match(BitSize.B64).andThen(DistributionMatch.any(
                    VersionRange.of("3.4.9", "3.4.24"),
                    VersionRange.of("3.4.0", "3.4.7"),
                    VersionRange.of("3.2.0", "3.2.22"),
                    VersionRange.of("3.0.0", "3.0.15"),
                    VersionRange.of("2.6.0", "2.6.12")
            )))
            .finder(UrlTemplatePackageResolver.builder()
                    .fileSet(fileSet)
                    .archiveType(archiveType)
                    .urlTemplate("/win32/mongodb-win32-x86_64-2008plus-{version}.zip")
                    .build())
            .build();

    DistributionMatch windows64MongoVersions = DistributionMatch.any(
      VersionRange.of("5.0.5", "5.0.5"),
      VersionRange.of("5.0.0", "5.0.2"),
      VersionRange.of("4.4.11", "4.4.11"),
      VersionRange.of("4.4.0", "4.4.9")
    );
    ImmutablePlatformMatchRule windows_x64_rule = PlatformMatchRule.builder()
            .match(match(BitSize.B64).andThen(windows64MongoVersions))
            .finder(UrlTemplatePackageResolver.builder()
                    .fileSet(fileSet)
                    .archiveType(archiveType)
                    .urlTemplate("/windows/mongodb-windows-x86_64-{version}.zip")
                    .build())
            .build();

      ImmutablePlatformMatchRule tools_windows_x64_rule = PlatformMatchRule.builder()
          .match(match(BitSize.B64).andThen(windows64MongoVersions))
          .finder(UrlTemplatePackageResolver.builder()
              .fileSet(fileSet)
              .archiveType(archiveType)
              .urlTemplate("/tools/db/mongodb-database-tools-windows-x86_64-{tools.version}.zip")
              .build())
          .build();

      ImmutablePlatformMatchRule windows_x64_2008ssl_rule = PlatformMatchRule.builder()
            .match(match(BitSize.B64).andThen(DistributionMatch.any(
                    VersionRange.of("4.0.0", "4.0.27"),
                    VersionRange.of("3.6.0", "3.6.23"),
                    VersionRange.of("3.4.9", "3.4.24"),
                    VersionRange.of("3.4.0", "3.4.7"),
                    VersionRange.of("3.2.0", "3.2.22"),
                    VersionRange.of("3.0.0", "3.0.15")
            )))
            .finder(UrlTemplatePackageResolver.builder()
                    .fileSet(fileSet)
                    .archiveType(archiveType)
                    .urlTemplate("/win32/mongodb-win32-x86_64-2008plus-ssl-{version}.zip")
                    .build())
            .build();

    ImmutablePlatformMatchRule windows_x64_2012ssl_rule = PlatformMatchRule.builder()
            .match(match(BitSize.B64).andThen(DistributionMatch.any(
                    VersionRange.of("4.2.18", "4.2.18"),
                    VersionRange.of("4.2.5", "4.2.16"),
                    VersionRange.of("4.2.0", "4.2.3")
            )))
            .finder(UrlTemplatePackageResolver.builder()
                    .fileSet(fileSet)
                    .archiveType(archiveType)
                    .urlTemplate("/win32/mongodb-win32-x86_64-2012plus-{version}.zip")
                    .build())
            .build();

    ImmutablePlatformMatchRule win32rule = PlatformMatchRule.builder()
            .match(match(BitSize.B32).andThen(DistributionMatch.any(
                            VersionRange.of("3.2.0", "3.2.22"),
                            VersionRange.of("3.0.0", "3.0.15"),
                            VersionRange.of("2.6.0", "2.6.12")
                    )))
            .finder(UrlTemplatePackageResolver.builder()
                    .fileSet(fileSet)
                    .archiveType(archiveType)
                    .urlTemplate("/win32/mongodb-win32-i386-{version}.zip")
                    .build())
            .build();

    ImmutablePlatformMatchRule hiddenLegacyWin32rule = PlatformMatchRule.builder()
            .match(match(BitSize.B32).andThen(DistributionMatch.any(
                            VersionRange.of("3.3.1", "3.3.1"),
                            VersionRange.of("3.5.5", "3.5.5")
                    )))
            .finder(UrlTemplatePackageResolver.builder()
                    .fileSet(fileSet)
                    .archiveType(archiveType)
                    .urlTemplate("/win32/mongodb-win32-i386-{version}.zip")
                    .build())
            .build();

    ImmutablePlatformMatchRule win_x86_64 = PlatformMatchRule.builder()
            .match(match(BitSize.B64).andThen(DistributionMatch.any(
                    VersionRange.of("3.4.9", "3.4.24"),
                    VersionRange.of("3.4.0", "3.4.7"),
                    VersionRange.of("3.2.0", "3.2.22"),
                    VersionRange.of("3.0.0", "3.0.15"),
                    VersionRange.of("2.6.0", "2.6.12")
            )))
            .finder(UrlTemplatePackageResolver.builder()
                    .fileSet(fileSet)
                    .archiveType(archiveType)
                    .urlTemplate("/win32/mongodb-win32-x86_64-{version}.zip")
                    .build())
            .build();

    ImmutablePlatformMatchRule hiddenLegacyWin_x86_64 = PlatformMatchRule.builder()
            .match(match(BitSize.B64).andThen(DistributionMatch.any(
                    VersionRange.of("3.3.1", "3.3.1"),
                    VersionRange.of("3.5.5", "3.5.5")
            )))
            .finder(UrlTemplatePackageResolver.builder()
                    .fileSet(fileSet)
                    .archiveType(archiveType)
                    .urlTemplate("/win32/mongodb-win32-x86_64-{version}.zip")
                    .build())
            .build();

    ImmutablePlatformMatchRule failIfNothingMatches = PlatformMatchRule.builder()
            .match(PlatformMatch.withOs(OS.Windows))
            .finder(PackageFinder.failWithMessage(distribution -> "windows distribution not supported: " + distribution))
            .build();

      switch (command) {
          case MongoDump:
          case MongoImport:
          case MongoRestore:
              return PlatformMatchRules.empty()
                  .withRules(
                      tools_windows_x64_rule,
                      win_x86_64,
                      windows_x64_rule,
                      windows_x64_2012ssl_rule,
                      windows_x64_2008ssl_rule,
                      windowsServer_2008_rule,
                      hiddenLegacyWin_x86_64,
                      win32rule,
                      hiddenLegacyWin32rule,
                      failIfNothingMatches
                  );
      }


      return PlatformMatchRules.empty()
            .withRules(
                    win_x86_64,
                    windows_x64_rule,
                    windows_x64_2012ssl_rule,
                    windows_x64_2008ssl_rule,
                    windowsServer_2008_rule,
                    hiddenLegacyWin_x86_64,
                    win32rule,
                    hiddenLegacyWin32rule,
                    failIfNothingMatches
            );
  }

}
