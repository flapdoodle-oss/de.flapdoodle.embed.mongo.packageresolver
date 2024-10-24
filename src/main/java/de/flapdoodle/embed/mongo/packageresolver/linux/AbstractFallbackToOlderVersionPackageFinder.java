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
import de.flapdoodle.os.Version;

import java.util.*;
import java.util.function.Function;
import java.util.stream.Collectors;

public class AbstractFallbackToOlderVersionPackageFinder<S extends Version> implements PackageFinder, HasExplanation {
	private final PackageFinder packageFinder;
	private final Function<S, Integer> ordinalOf;
	private final List<S> versions;

	@SafeVarargs
	public AbstractFallbackToOlderVersionPackageFinder(PackageFinder packageFinder, Function<S, Integer> ordinalOf, S ... versions) {
		this.packageFinder = packageFinder;
		// this hack is needed because of an java8 compiler bug
		// <S extends Enum<S> & Version> does trigger it.. wtf...
		this.ordinalOf = ordinalOf;
		this.versions = Arrays.asList(versions);
	}

	@Override
	public final Optional<Package> packageFor(Distribution distribution) {
		if (platformMatch().match(distribution)) {
			if (!distribution.platform().version().isPresent()) throw new RuntimeException("version not set: "+distribution);
			@SuppressWarnings("unchecked")
			S currentVersion = (S) distribution.platform().version().get();

			Optional<Package> matchingPackage;
			do {
				Distribution asUbuntudistribution = Distribution.of(distribution.version(),
					ImmutablePlatform.copyOf(distribution.platform()).withVersion(currentVersion));
				matchingPackage = packageFinder.packageFor(asUbuntudistribution);
				if (!matchingPackage.isPresent()) {
					currentVersion=downgradeVersionFrom(versions, ordinalOf, currentVersion).orElse(null);
				}
			} while (!matchingPackage.isPresent() && currentVersion!=null);

			return matchingPackage;
		}

		return Optional.empty();
	}

	private static <S extends Version> Optional<S> downgradeVersionFrom(List<S> values, Function<S, Integer> ordinalOf, S currentVersion) {
		for (int i = values.size() - 1; i >= 0; i--) {
			S version = values.get(i);
			if (ordinalOf.apply(version) < ordinalOf.apply(currentVersion)) {
				return Optional.of(version);
			}
		}
		return Optional.empty();
	}

	@Override
	public final String explain() {
		ArrayList<S> copy = new ArrayList<>(versions);
		Collections.reverse(copy);

		return copy.stream()
//			.sorted(Comparator.reverseOrder())
			.map(S::name)
			.collect(Collectors.joining(", ", "use '"+ ExplainRules.finderLabel(packageFinder)+"' with ", " until package found."));
	}

	public ImmutablePlatformMatch platformMatch() {
		return PlatformMatch.withOs(CommonOS.Linux).withVersion(versions);
	}
}
