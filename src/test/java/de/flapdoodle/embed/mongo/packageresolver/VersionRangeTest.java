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

import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.distribution.Version;
import de.flapdoodle.os.Platform;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class VersionRangeTest {

  @Test
  public void mustMatchVersionRange() {
    VersionRange range = VersionRange.of("4.0.0", "4.0.26");
    boolean result = range.match(Distribution.of(Version.of("4.0.12"), Platform.detect()));
    assertThat(result).isTrue();
  }

  @Test
  public void mustMatchVersionIfSingleValue() {
    VersionRange range = VersionRange.of("3.3.1", "3.3.1");
    boolean result = range.match(Distribution.of(Version.of("3.3.1"), Platform.detect()));
    assertThat(result).isTrue();
  }
}