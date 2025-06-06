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

import de.flapdoodle.os.linux.ManjaroVersion;
import de.flapdoodle.os.linux.UbuntuVersion;
import de.flapdoodle.types.Pair;

public class ManjaroPackageFinder extends AbstractVersionMappedPackageFinder<ManjaroVersion, UbuntuVersion> {
	protected ManjaroPackageFinder(UbuntuFallbackToOlderVersionPackageFinder delegate) {
		super(delegate,
			Pair.of(ManjaroVersion.MANJARO_24_0, UbuntuVersion.Ubuntu_24_04),
			Pair.of(ManjaroVersion.MANJARO_24_1, UbuntuVersion.Ubuntu_24_04),
			Pair.of(ManjaroVersion.MANJARO_24_2, UbuntuVersion.Ubuntu_24_04),
			Pair.of(ManjaroVersion.MANJARO_24_3, UbuntuVersion.Ubuntu_24_04)
		);
	}
}
