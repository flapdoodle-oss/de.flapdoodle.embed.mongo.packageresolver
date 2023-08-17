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
import de.flapdoodle.os.linux.DebianVersion;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DebianPackageFinderTest {
  /**
   * Debian 9 x64
   * https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-debian92-{}.tgz
   * "5.0.18 -> 5.0.19", "5.0.12 -> 5.0.15", "5.0.5 -> 5.0.6", "5.0.0 -> 5.0.2", "4.4.22 -> 4.4.23", "4.4.16 -> 4.4.19", "4.4.13", "4.4.11", "4.4.0 -> 4.4.9", "4.2.22 -> 4.2.24", "4.2.18 -> 4.2.19", "4.2.5 -> 4.2.16", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.5 -> 3.6.23"
   */
  @ParameterizedTest
  @ValueSource(strings = {"5.0.18 -> 5.0.19", "5.0.12 -> 5.0.15", "5.0.5 -> 5.0.6", "5.0.0 -> 5.0.2", "4.4.22 -> 4.4.23", "4.4.16 -> 4.4.19", "4.4.13", "4.4.11", "4.4.0 -> 4.4.9", "4.2.22 -> 4.2.24", "4.2.18 -> 4.2.19", "4.2.5 -> 4.2.16", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.5 -> 3.6.23"})
  public void debian9x64(String version) {
    assertThat(linuxWith(CommonArchitecture.X86_64, DebianVersion.DEBIAN_9), version)
            .resolvesTo("/linux/mongodb-linux-x86_64-debian92-{}.tgz");
  }

  /**
   * Debian 10 x64
   * https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-debian10-{}.tgz
   * "6.0.8", "6.0.1 -> 6.0.6", "5.0.18 -> 5.0.19", "5.0.12 -> 5.0.15", "5.0.5 -> 5.0.6", "5.0.0 -> 5.0.2", "4.4.22 -> 4.4.23", "4.4.16 -> 4.4.19", "4.4.13", "4.4.11", "4.4.0 -> 4.4.9", "4.2.22 -> 4.2.24", "4.2.18 -> 4.2.19", "4.2.5 -> 4.2.16", "4.2.1 -> 4.2.3"
   */
  @ParameterizedTest
  @ValueSource(strings = {"6.0.8", "6.0.1 -> 6.0.6", "5.0.18 -> 5.0.19", "5.0.12 -> 5.0.15", "5.0.5 -> 5.0.6", "5.0.0 -> 5.0.2", "4.4.22 -> 4.4.23", "4.4.16 -> 4.4.19", "4.4.13", "4.4.11", "4.4.0 -> 4.4.9", "4.2.22 -> 4.2.24", "4.2.18 -> 4.2.19", "4.2.5 -> 4.2.16", "4.2.1 -> 4.2.3"})
  public void debian10x64(String version) {
    assertThat(linuxWith(CommonArchitecture.X86_64, DebianVersion.DEBIAN_10), version)
            .resolvesTo("/linux/mongodb-linux-x86_64-debian10-{}.tgz");
  }

  /**
   * Debian 10 x64 dev
   * https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-debian10-{}.tgz
   * "7.0.0-rc2", "7.0.0-rc1", "6.3.1 -> 6.3.2"
   */
  @ParameterizedTest
  @ValueSource(strings = {"7.0.0-rc2", "7.0.0-rc1", "6.3.1 -> 6.3.2"})
  public void debian10x64dev(String version) {
    assertThat(linuxWith(CommonArchitecture.X86_64, DebianVersion.DEBIAN_10), version)
      .resolveDevPackageTo("/linux/mongodb-linux-x86_64-debian10-{}.tgz");
  }

  /**
   * Debian 11 x64
   * https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-debian11-{}.tgz
   * "6.0.8", "6.0.1 -> 6.0.6", "5.0.18 -> 5.0.19", "5.0.12 -> 5.0.15"
   */
  @ParameterizedTest
  @ValueSource(strings = {"6.0.8", "6.0.1 -> 6.0.6", "5.0.18 -> 5.0.19", "5.0.12 -> 5.0.15"})
  public void debian11x64(String version) {
    assertThat(linuxWith(CommonArchitecture.X86_64, DebianVersion.DEBIAN_11), version)
      .resolvesTo("/linux/mongodb-linux-x86_64-debian11-{}.tgz");
  }

  /**
   * Debian 11 x64 dev
   * https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-debian11-{}.tgz
   * "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc1", "6.3.1 -> 6.3.2"
   */
  @ParameterizedTest
  @ValueSource(strings = {"7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc1", "6.3.1 -> 6.3.2"})
  public void debian11x64dev(String version) {
    assertThat(linuxWith(CommonArchitecture.X86_64, DebianVersion.DEBIAN_11), version)
      .resolveDevPackageTo("/linux/mongodb-linux-x86_64-debian11-{}.tgz");
  }

  @ParameterizedTest
  @ValueSource(strings = {"6.0.8", "6.0.1 -> 6.0.6", "5.0.18 -> 5.0.19", "5.0.12 -> 5.0.15"})
  public void debian12x64(String version) {
    assertThat(linuxWith(CommonArchitecture.X86_64, DebianVersion.DEBIAN_12), version)
      .resolvesTo("/linux/mongodb-linux-x86_64-debian11-{}.tgz");
  }

  @ParameterizedTest
  @ValueSource(strings = {"7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc1", "6.3.1 -> 6.3.2"})
  public void debian12x64dev(String version) {
    assertThat(linuxWith(CommonArchitecture.X86_64, DebianVersion.DEBIAN_11), version)
      .resolveDevPackageTo("/linux/mongodb-linux-x86_64-debian11-{}.tgz");
  }

  @ParameterizedTest
  @ValueSource(strings = {"5.0.5 -> 5.0.6", "5.0.0 -> 5.0.2", "4.4.16 -> 4.4.19", "4.4.13", "4.4.11", "4.4.0 -> 4.4.9", "4.2.22 -> 4.2.24", "4.2.18 -> 4.2.19", "4.2.5 -> 4.2.16", "4.2.1 -> 4.2.3"})
  public void debian11x64_with_debian10_archives(String version) {
    assertThat(linuxWith(CommonArchitecture.X86_64, DebianVersion.DEBIAN_11), version)
      .resolvesTo("/linux/mongodb-linux-x86_64-debian10-{}.tgz");
  }

  @ParameterizedTest
  @ValueSource(strings = {"5.0.5 -> 5.0.6", "5.0.0 -> 5.0.2", "4.4.16 -> 4.4.19", "4.4.13", "4.4.11", "4.4.0 -> 4.4.9", "4.2.22 -> 4.2.24", "4.2.18 -> 4.2.19", "4.2.5 -> 4.2.16", "4.2.1 -> 4.2.3"})
  public void debian12x64_with_debian10_archives(String version) {
    assertThat(linuxWith(CommonArchitecture.X86_64, DebianVersion.DEBIAN_12), version)
      .resolvesTo("/linux/mongodb-linux-x86_64-debian10-{}.tgz");
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
            new DebianPackageFinder(Command.Mongo),
            version -> Distribution.of(Version.of(version), platform),
            versionList);
  }

}