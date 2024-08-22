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
package de.flapdoodle.embed.mongo.packageresolver.parser;

import de.flapdoodle.os.Version;
import de.flapdoodle.os.VersionWithPriority;

import java.util.Comparator;
import java.util.Optional;

public abstract class Versions {
	public static Comparator<Optional<? extends Version>> versionByPrioOrdinalOrNameComparator() {
		return nullsFirst(
			Comparator.comparing((Version version) -> -(version instanceof VersionWithPriority ? ((VersionWithPriority) version).priority() : 0))
			.thenComparing(version -> -(version instanceof Enum ? ((Enum<?>) version).ordinal() : 0))
			.thenComparing(Version::name));
	}

	public static Comparator<Optional<? extends Enum<?>>> byOrdinal() {
		return nullsFirst(Comparator.comparing(version -> -(version != null ? ((Enum<?>) version).ordinal() : 0)));
	}

	public static <T> Comparator<Optional<? extends T>> nullsFirst(Comparator<? super T> nonNullComparator) {
		return Comparator.comparing(opt -> opt.orElse(null), Comparator.nullsFirst(nonNullComparator));
	}
}
