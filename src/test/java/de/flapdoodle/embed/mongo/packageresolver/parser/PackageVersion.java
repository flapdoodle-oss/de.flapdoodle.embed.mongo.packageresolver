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

import de.flapdoodle.embed.mongo.packageresolver.NumericVersion;
import org.immutables.value.Value;

@Value.Immutable
public abstract class PackageVersion implements Comparable<PackageVersion> {
	public abstract String version();
	
	@Value.Default
	public boolean devVersion() {
		return false;
	}

	@Override
	@Value.Auxiliary
	public int compareTo(PackageVersion other) {
		int compDev = Boolean.compare(devVersion(), other.devVersion());

		return compDev != 0
			? -compDev
			: -NumericVersion.of(version()).compareTo(NumericVersion.of(other.version()));
	}
	public static PackageVersion of(String version, boolean isDevVersion) {
		return ImmutablePackageVersion.builder()
			.version(version)
			.devVersion(isDevVersion)
			.build();
	}
}
