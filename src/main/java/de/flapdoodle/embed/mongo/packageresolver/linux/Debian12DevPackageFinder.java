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
import de.flapdoodle.os.*;
import de.flapdoodle.os.linux.DebianVersion;
import de.flapdoodle.os.linux.UbuntuVersion;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * remove if mongodb provides released packages for debian12
 */
@Deprecated
public class Debian12DevPackageFinder extends AbstractPackageFinder {

	public Debian12DevPackageFinder(final Command command) {
		super(command, rules(command));
	}

	private static FileSet fileSetOf(Command command) {
		return FileSet.builder()
			.addEntry(FileType.Executable, command.commandName())
			.build();
	}

	private static ImmutablePackageFinderRules rules(final Command command) {
		FileSet fileSet = fileSetOf(command);

		PackageFinderRule devRule_DEBIAN_12_X86_B64 = PackageFinderRule.builder()
			.match(match(CommonOS.Linux, BitSize.B64, CPUType.X86, DebianVersion.DEBIAN_12, DebianVersion.DEBIAN_13)
				.andThen(
					DistributionMatch.any(
						VersionRange.of("7.2.0-rc3"))
				))
			.finder(UrlTemplatePackageFinder.builder()
				.fileSet(fileSet)
				.archiveType(ArchiveType.TGZ)
				.urlTemplate("/linux/mongodb-linux-x86_64-debian12-{version}.tgz")
				.isDevVersion(true)
				.build())
			.build();

		switch (command) {
			case MongoDump:
			case MongoImport:
			case MongoRestore:
				return PackageFinderRules.empty();
			default:
				return PackageFinderRules.empty()
					.withAdditionalRules(
						devRule_DEBIAN_12_X86_B64
					);
		}
	}
}
