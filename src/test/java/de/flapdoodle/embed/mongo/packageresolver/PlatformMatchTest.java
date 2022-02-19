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
import de.flapdoodle.os.CommonArchitecture;
import de.flapdoodle.os.ImmutablePlatform;
import de.flapdoodle.os.OS;
import de.flapdoodle.os.linux.UbuntuVersion;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class PlatformMatchTest {

  @Test
  void differentVersionsMustNotMatch() {
    ImmutablePlatform platform = ImmutablePlatform.builder()
            .operatingSystem(OS.Linux)
            .architecture(CommonArchitecture.X86_64)
            .build();
    
    boolean result = PlatformMatch.any().withVersion(UbuntuVersion.Ubuntu_18_04)
            .match(Distribution.of(Version.of("1.2.3"), platform));

    assertThat(result).isFalse();
  }

  @Test
  void sameVersionMustNotMatch() {
    ImmutablePlatform platform = ImmutablePlatform.builder()
            .operatingSystem(OS.Linux)
            .architecture(CommonArchitecture.X86_64)
            .version(UbuntuVersion.Ubuntu_18_04)
            .build();

    boolean result = PlatformMatch.any().withVersion(UbuntuVersion.Ubuntu_18_04, UbuntuVersion.Ubuntu_19_04)
            .match(Distribution.of(Version.of("1.2.3"), platform));

    assertThat(result).isTrue();
  }

  @Test
  void noVersionMustNotMatch() {
    ImmutablePlatform platform = ImmutablePlatform.builder()
            .operatingSystem(OS.Linux)
            .architecture(CommonArchitecture.X86_64)
            .version(UbuntuVersion.Ubuntu_18_04)
            .build();

    boolean result = PlatformMatch.any()
            .match(Distribution.of(Version.of("1.2.3"), platform));

    assertThat(result).isTrue();
  }
}