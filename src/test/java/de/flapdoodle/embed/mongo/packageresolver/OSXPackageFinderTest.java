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

import de.flapdoodle.embed.mongo.packageresolver.linux.UbuntuPackageResolver;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.distribution.Version;
import de.flapdoodle.os.*;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class OSXPackageFinderTest {

  /*
    https://fastdl.mongodb.org/osx/mongodb-macos-arm64-{}.tgz
    6.0.1 -> 6.0.4
   */
  @ParameterizedTest
  @ValueSource(strings = {"6.0.1 -> 6.0.4"})
  public void armSet(String version) {
    assertThat(osx(CommonArchitecture.ARM_64), version)
      .resolvesTo("/osx/mongodb-macos-arm64-{}.tgz");
  }

  /*
    https://fastdl.mongodb.org/osx/mongodb-osx-ssl-x86_64-{}.tgz
    4.0.0 -> 4.0.28, 3.6.0 -> 3.6.23
  */
  @ParameterizedTest
  @ValueSource(strings = {"4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23"})
  public void firstSet(String version) {
    assertThat(version)
            .resolvesTo("/osx/mongodb-osx-ssl-x86_64-{}.tgz");
  }

  /*
    https://fastdl.mongodb.org/osx/mongodb-osx-ssl-x86_64-{}.tgz|https://fastdl.mongodb.org/osx/mongodb-osx-x86_64-{}.tgz
    3.4.9 -> 3.4.24, 3.4.0 -> 3.4.7, 3.2.0 -> 3.2.22, 3.0.4 -> 3.0.15
  */
  @ParameterizedTest
  @ValueSource(strings = {"3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.4 -> 3.0.15"})
  public void secondSet(String version) {
    assertThat(version)
            .resolvesTo("/osx/mongodb-osx-ssl-x86_64-{}.tgz");
  }

  /*
    https://fastdl.mongodb.org/osx/mongodb-osx-x86_64-{}.tgz
    3.0.0 -> 3.0.3, 2.6.0 -> 2.6.12
  */
  @ParameterizedTest
  @ValueSource(strings = {"3.0.0 -> 3.0.3", "2.6.0 -> 2.6.12"})
  public void thirdSet(String version) {
    assertThat(version)
            .resolvesTo("/osx/mongodb-osx-x86_64-{}.tgz");
  }

  /*
    https://fastdl.mongodb.org/osx/mongodb-macos-x86_64-{}.tgz
    6.0.1 -> 6.0.4, 5.0.12 -> 5.0.14, 5.0.5 -> 5.0.6, 5.0.0 -> 5.0.2, 4.4.16 -> 4.4.18, 4.4.13, 4.4.11, 4.4.0 -> 4.4.9, 4.2.22 -> 4.2.23, 4.2.18 -> 4.2.19, 4.2.5 -> 4.2.16, 4.2.0 -> 4.2.3
  */
  @ParameterizedTest
  @ValueSource(strings = {"6.0.1 -> 6.0.4", "5.0.12 -> 5.0.14", "5.0.5 -> 5.0.6", "5.0.0 -> 5.0.2", "4.4.16 -> 4.4.18", "4.4.13", "4.4.11", "4.4.0 -> 4.4.9", "4.2.22 -> 4.2.23", "4.2.18 -> 4.2.19", "4.2.5 -> 4.2.16", "4.2.0 -> 4.2.3"})
  public void fourthSet(String version) {
    assertThat(version)
            .resolvesTo("/osx/mongodb-macos-x86_64-{}.tgz");
  }


  private static Platform osx() {
    return osx(CommonArchitecture.X86_64);
  }

  private static Platform osx(Architecture architecture) {
    return ImmutablePlatform.builder()
      .operatingSystem(OS.OS_X)
      .architecture(architecture)
      .build();
  }

  private static HtmlParserResultTester assertThat(String versionList) {
    return assertThat(osx(), versionList);
  }

  private static HtmlParserResultTester assertThat(Platform platform, String versionList) {
    return HtmlParserResultTester.with(
      new OSXPackageFinder(Command.Mongo),
      version -> Distribution.of(Version.of(version), platform),
      versionList);
  }


}