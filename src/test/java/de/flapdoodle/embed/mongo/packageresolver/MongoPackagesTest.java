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
package de.flapdoodle.embed.mongo.packageresolver;

import com.google.common.collect.ImmutableList;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MongoPackagesTest {

	@Test
	public void versionRanges() {
		List<VersionRange> result = MongoPackages.compressedVersionsList(ImmutableList.of(
			"7.0.3", "7.0.4", "7.0.1", "7.3.0", "7.3.1-rc2"
		));
		assertThat(result).containsExactly(
			VersionRange.of("7.3.1-rc2"),
			VersionRange.of("7.3.0"),
			VersionRange.of("7.0.3", "7.0.4"),
			VersionRange.of("7.0.1")
		);
	}
}
