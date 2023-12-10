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
import de.flapdoodle.embed.process.config.store.Package;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.os.CommonOS;
import de.flapdoodle.os.ImmutablePlatform;
import de.flapdoodle.os.linux.RedhatVersion;
import de.flapdoodle.os.linux.UbuntuVersion;

import java.util.Comparator;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class RedhatFallbackToOlderVersionPackageFinder implements PackageFinder, HasExplanation, HasLabel {

	private final RedhatPackageFinder redhatPackageFinder;

	public RedhatFallbackToOlderVersionPackageFinder(RedhatPackageFinder redhatPackageFinder) {
		this.redhatPackageFinder = redhatPackageFinder;
	}

	@Override
	public Optional<Package> packageFor(Distribution distribution) {
		if (platformMatch().match(distribution)) {
			if (!distribution.platform().version().isPresent()) throw new RuntimeException("version not set: "+distribution);
			RedhatVersion startVersion = (RedhatVersion) distribution.platform().version().get();
			RedhatVersion currentVersion = startVersion;

			Optional<Package> matchingPackage;
			do {
				Distribution asUbuntudistribution = Distribution.of(distribution.version(),
					ImmutablePlatform.copyOf(distribution.platform()).withVersion(currentVersion));
				matchingPackage = redhatPackageFinder.packageFor(asUbuntudistribution);
				if (!matchingPackage.isPresent()) {
					currentVersion=downgradeVersionFrom(currentVersion).orElse(null);
				}
			} while (!matchingPackage.isPresent() && currentVersion!=null);

			return matchingPackage;
		}

		return Optional.empty();
	}

	private static Optional<RedhatVersion> downgradeVersionFrom(RedhatVersion currentVersion) {
		RedhatVersion[] values = RedhatVersion.values();
		for (int i = values.length - 1; i >= 0; i--) {
			RedhatVersion version = values[i];
			if (version.ordinal() < currentVersion.ordinal()) {
				return Optional.of(version);
			}
		}
		return Optional.empty();
	}

	@Override
	public String label() {
		return "RedhatVersionDowngradePackageFinder";
	}

	@Override
	public String explain() {
		return Stream.of(RedhatVersion.values())
			.sorted(Comparator.reverseOrder())
			.map(RedhatVersion::name)
			.collect(Collectors.joining(", ", "use '"+ redhatPackageFinder.label()+"' with ", " until package found."));
	}

	public static ImmutablePlatformMatch platformMatch() {
		return PlatformMatch.withOs(CommonOS.Linux)
			.withVersion(RedhatVersion.values());
	}
}
