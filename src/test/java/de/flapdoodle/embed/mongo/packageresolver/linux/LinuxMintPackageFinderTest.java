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

import de.flapdoodle.embed.mongo.packageresolver.Command;
import de.flapdoodle.embed.mongo.packageresolver.HtmlParserResultTester;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.distribution.Version;
import de.flapdoodle.os.*;
import de.flapdoodle.os.linux.LinuxMintVersion;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class LinuxMintPackageFinderTest {
  /*
    Ubuntu 18.04 x64
    https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-ubuntu1804-{}.tgz
    6.0.1 -> 6.0.5, 5.0.12 -> 5.0.15, 5.0.5 -> 5.0.6, 5.0.0 -> 5.0.2, 4.4.16 -> 4.4.19, 4.4.13, 4.4.11, 4.4.0 -> 4.4.9, 4.2.22 -> 4.2.24, 4.2.18 -> 4.2.19, 4.2.5 -> 4.2.16, 4.2.0 -> 4.2.3, 4.0.1 -> 4.0.28, 3.6.20 -> 3.6.23
  */
  @ParameterizedTest
  @ValueSource(strings = {"6.0.1 -> 6.0.5", "5.0.12 -> 5.0.15", "5.0.5 -> 5.0.6", "5.0.0 -> 5.0.2", "4.4.16 -> 4.4.19", "4.4.13", "4.4.11", "4.4.0 -> 4.4.9", "4.2.22 -> 4.2.24", "4.2.18 -> 4.2.19", "4.2.5 -> 4.2.16", "4.2.0 -> 4.2.3", "4.0.1 -> 4.0.28", "3.6.20 -> 3.6.23"})
  public void ubuntu18x64(String version) {
    assertThat(linuxWith(CommonArchitecture.X86_64, LinuxMintVersion.LINUX_MINT_19_0), version)
            .resolvesTo("/linux/mongodb-linux-x86_64-ubuntu1804-{}.tgz");
  }

  /*
    Ubuntu 20.04 x64
    https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-ubuntu2004-{}.tgz
    6.0.1 -> 6.0.5, 5.0.12 -> 5.0.15, 5.0.5 -> 5.0.6, 5.0.0 -> 5.0.2, 4.4.16 -> 4.4.19, 4.4.13, 4.4.11, 4.4.0 -> 4.4.9, 4.2.22 -> 4.2.24, 4.2.18 -> 4.2.19, 4.2.5 -> 4.2.16, 4.2.0 -> 4.2.3, 4.0.1 -> 4.0.28, 3.6.20 -> 3.6.23
  */
  @ParameterizedTest
  @ValueSource(strings = {"6.0.1 -> 6.0.3", "5.0.12 -> 5.0.15", "5.0.5 -> 5.0.6", "5.0.0 -> 5.0.2", "4.4.16 -> 4.4.19", "4.4.13", "4.4.11", "4.4.0 -> 4.4.9"})
  public void ubuntu20x64(String version) {
    assertThat(linuxWith(CommonArchitecture.X86_64, LinuxMintVersion.LINUX_MINT_20_0), version)
            .resolvesTo("/linux/mongodb-linux-x86_64-ubuntu2004-{}.tgz");
  }

  /*
    Ubuntu 22.04 x64
    https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-ubuntu2204-{}.tgz
    6.0.8, 6.0.4 -> 6.0.6
  */
  @ParameterizedTest
  @ValueSource(strings = {"6.0.8", "6.0.4 -> 6.0.6"})
  public void ubuntu22x64(String version) {
    assertThat(linuxWith(CommonArchitecture.X86_64, LinuxMintVersion.LINUX_MINT_21_0), version)
      .resolvesTo("/linux/mongodb-linux-x86_64-ubuntu2204-{}.tgz");
  }

  private static Platform linuxWith(CommonArchitecture architecture, de.flapdoodle.os.Version version) {
    return ImmutablePlatform.builder()
            .operatingSystem(CommonOS.Linux)
            .architecture(architecture)
            .version(version)
            .build();
  }

  private static HtmlParserResultTester assertThat(Platform platform, String versionList) {
    return HtmlParserResultTester.with(
            new LinuxMintPackageFinder(new UbuntuPackageFinder(Command.Mongo)),
            version -> Distribution.of(Version.of(version), platform),
            versionList);
  }

}