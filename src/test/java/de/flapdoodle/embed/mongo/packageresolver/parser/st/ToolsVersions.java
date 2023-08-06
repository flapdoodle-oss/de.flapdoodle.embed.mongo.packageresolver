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
package de.flapdoodle.embed.mongo.packageresolver.parser.st;

import de.flapdoodle.embed.mongo.packageresolver.MongoPackages;
import de.flapdoodle.embed.mongo.packageresolver.VersionRange;
import org.immutables.value.Value;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Value.Immutable
public abstract class ToolsVersions {
	public abstract Set<String> list();

	@Value.Derived
	public boolean hasVersions() {
		return !list().isEmpty();
	}

	@Value.Auxiliary
	public List<VersionRange> versionRanges() {
//		String versionList = MongoPackages.rangesAsString(ranges);
		return MongoPackages.compressedVersionsList(list())
			.stream()
			.sorted(Comparator.comparing(VersionRange::min).reversed())
			.collect(Collectors.toList());
	}

	public static ToolsVersions of(Iterable<String> list) {
		return ImmutableToolsVersions.builder()
			.addAllList(list)
			.build();
	}
}
