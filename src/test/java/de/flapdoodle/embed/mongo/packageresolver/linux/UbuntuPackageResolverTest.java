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
package de.flapdoodle.embed.mongo.packageresolver.linux;

import de.flapdoodle.embed.mongo.packageresolver.Command;
import de.flapdoodle.embed.mongo.packageresolver.HtmlParserResultTester;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.distribution.Version;
import de.flapdoodle.os.CommonArchitecture;
import de.flapdoodle.os.ImmutablePlatform;
import de.flapdoodle.os.OS;
import de.flapdoodle.os.Platform;
import de.flapdoodle.os.linux.UbuntuVersion;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.stream.Stream;

class UbuntuPackageResolverTest {
  /*
    Ubuntu 18.04 ARM 64
    https://fastdl.mongodb.org/linux/mongodb-linux-aarch64-ubuntu1804-{}.tgz
    5.0.2 - 5.0.0, 4.4.9 - 4.4.0, 4.2.16 - 4.2.5, 4.2.3 - 4.2.0
  */
  @ParameterizedTest
  @ValueSource(strings = {"5.0.2 - 5.0.0", "4.4.9 - 4.4.0", "4.2.16 - 4.2.5", "4.2.3 - 4.2.0"})
  public void ubuntu18arm(String version) {
    assertThat(linuxWith(CommonArchitecture.ARM_64, UbuntuVersion.Ubuntu_18_04), version)
            .resolvesTo("/linux/mongodb-linux-aarch64-ubuntu1804-{}.tgz");
  }

  /*
    Ubuntu 18.04 x64
    https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-ubuntu1804-{}.tgz
    5.0.2 - 5.0.0, 4.4.9 - 4.4.0, 4.2.16 - 4.2.5, 4.2.3 - 4.2.0, 4.0.26 - 4.0.1, 3.6.22 - 3.6.20
  */
  @ParameterizedTest
  @ValueSource(strings = {"5.0.2 - 5.0.0", "4.4.9 - 4.4.0", "4.2.16 - 4.2.5", "4.2.3 - 4.2.0", "4.0.26 - 4.0.1", "3.6.22 - 3.6.20"})
  public void ubuntu18x64(String version) {
    assertThat(linuxWith(CommonArchitecture.X86_64, UbuntuVersion.Ubuntu_18_04), version)
            .resolvesTo("/linux/mongodb-linux-x86_64-ubuntu1804-{}.tgz");
  }

  /*
    all combinations of ubuntu2xxxOsToMongoVersion for ARM 64
    https://fastdl.mongodb.org/linux/mongodb-linux-aarch64-ubuntu2004-{}.tgz
  */
  @ParameterizedTest
  @MethodSource("ubuntu2xxxOsToMongoVersion")
  void ubuntu20arm(UbuntuVersion ubuntuVersion, String version) {
    assertThat(linuxWith(CommonArchitecture.ARM_64, ubuntuVersion), version)
            .resolvesTo("/linux/mongodb-linux-aarch64-ubuntu2004-{}.tgz");
  }

  /*
    all combinations of ubuntu2xxxOsToMongoVersion for x64
    https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-ubuntu2004-{}.tgz
  */
  @ParameterizedTest
  @MethodSource("ubuntu2xxxOsToMongoVersion")
  void ubuntuAndx64(UbuntuVersion ubuntuVersion, String version) {
    assertThat(linuxWith(CommonArchitecture.X86_64, ubuntuVersion), version)
            .resolvesTo("/linux/mongodb-linux-x86_64-ubuntu2004-{}.tgz");
  }

  private static Stream<Arguments> ubuntu2xxxOsToMongoVersion() {
    return Stream.of(
        Arguments.of(UbuntuVersion.Ubuntu_20_04, "4.4.9 - 4.4.0"),
        Arguments.of(UbuntuVersion.Ubuntu_20_04, "5.0.2 - 5.0.0"),
        Arguments.of(UbuntuVersion.Ubuntu_20_04, "5.0.5 - 5.0.5"),
        Arguments.of(UbuntuVersion.Ubuntu_20_10, "4.4.9 - 4.4.0"),
        Arguments.of(UbuntuVersion.Ubuntu_20_10, "5.0.2 - 5.0.0"),
        Arguments.of(UbuntuVersion.Ubuntu_20_10, "5.0.5 - 5.0.5"),
        Arguments.of(UbuntuVersion.Ubuntu_21_10, "4.4.9 - 4.4.0"),
        Arguments.of(UbuntuVersion.Ubuntu_21_10, "5.0.2 - 5.0.0"),
        Arguments.of(UbuntuVersion.Ubuntu_21_10, "5.0.5 - 5.0.5")
    );
  }

  private static Platform linuxWith(CommonArchitecture architecture, de.flapdoodle.os.Version version) {
    return ImmutablePlatform.builder()
            .operatingSystem(OS.Linux)
            .architecture(architecture)
            .version(version)
            .build();
  }

  private static HtmlParserResultTester assertThat(Platform platform, String versionList) {
    return HtmlParserResultTester.with(
            new UbuntuPackageResolver(Command.Mongo),
            version -> Distribution.of(Version.of(version), platform),
            versionList);
  }

}
