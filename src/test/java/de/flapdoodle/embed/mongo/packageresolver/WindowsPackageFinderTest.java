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
import de.flapdoodle.os.Platform;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class WindowsPackageFinderTest {

  /*
  -----------------------------------
  Windows Server 2008 R2+, without SSL x64
  https://fastdl.mongodb.org/win32/mongodb-win32-x86_64-2008plus-{}.zip
  3.4.9 -> 3.4.24, 3.4.0 -> 3.4.7, 3.2.0 -> 3.2.22, 3.0.0 -> 3.0.15, 2.6.0 -> 2.6.12

  */
  @Disabled("resolves to window_x86_64")
  @ParameterizedTest
  @ValueSource(strings = {"3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15", "2.6.0 -> 2.6.12"})
  public void windowsServer_2008_R2plus_without_SSL_x64(String version) {
    assertThat(windowsWith(CommonArchitecture.X86_64), version)
            .resolvesTo("/win32/mongodb-win32-x86_64-2008plus-{}.zip");
  }

  /*
  Windows x64
  https://fastdl.mongodb.org/windows/mongodb-windows-x86_64-{}.zip
  6.0.1 -> 6.0.3, 5.0.12 -> 5.0.14, 5.0.5 -> 5.0.6, 5.0.0 -> 5.0.2, 4.4.16 -> 4.4.18, 4.4.13, 4.4.11, 4.4.0 -> 4.4.9
  https://fastdl.mongodb.org/win32/mongodb-win32-x86_64-2008plus-ssl-{}.zip
  4.0.0 -> 4.0.28, 3.6.0 -> 3.6.23, 3.4.9 -> 3.4.24, 3.4.0 -> 3.4.7, 3.2.0 -> 3.2.22, 3.0.0 -> 3.0.15
  https://fastdl.mongodb.org/win32/mongodb-win32-x86_64-2012plus-{}.zip
  4.2.22, 4.2.18 -> 4.2.19, 4.2.5 -> 4.2.16, 4.2.0 -> 4.2.3
  */
  @ParameterizedTest
  @ValueSource(strings = {"6.0.1 -> 6.0.3", "5.0.12 -> 5.0.14", "5.0.5 -> 5.0.6", "5.0.0 -> 5.0.2", "4.4.16 -> 4.4.18", "4.4.13", "4.4.11", "4.4.0 -> 4.4.9"})
  public void windows_x64(String version) {
    assertThat(windowsWith(CommonArchitecture.X86_64), version)
            .resolvesTo("/windows/mongodb-windows-x86_64-{}.zip");
  }

  @ParameterizedTest
  @ValueSource(strings = {"4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", /*"3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15"*/})
  public void windows_x64_2008plus_ssl(String version) {
    assertThat(windowsWith(CommonArchitecture.X86_64), version)
            .resolvesTo("/win32/mongodb-win32-x86_64-2008plus-ssl-{}.zip");
  }

  @ParameterizedTest
  @ValueSource(strings = {"4.2.22", "4.2.18 -> 4.2.19", "4.2.5 -> 4.2.16", "4.2.0 -> 4.2.3"})
  public void windows_x64_2012plus(String version) {
    assertThat(windowsWith(CommonArchitecture.X86_64), version)
            .resolvesTo("/win32/mongodb-win32-x86_64-2012plus-{}.zip");
  }

  /*
  -----------------------------------
  windows_i686 undefined
  https://fastdl.mongodb.org/win32/mongodb-win32-i386-{}.zip
  3.2.0 -> 3.2.22, 3.0.0 -> 3.0.15, 2.6.0 -> 2.6.12
  */
  @ParameterizedTest
  @ValueSource(strings = {"3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15", "2.6.0 -> 2.6.12"})
  public void windows_i686(String version) {
    assertThat(windowsWith(CommonArchitecture.X86_32), version)
            .resolvesTo("/win32/mongodb-win32-i386-{}.zip");
  }

  /*
  windows_x86_64 x64
  https://fastdl.mongodb.org/win32/mongodb-win32-x86_64-{}.zip
  3.4.9 -> 3.4.24, 3.4.0 -> 3.4.7, 3.2.0 -> 3.2.22, 3.0.0 -> 3.0.15, 2.6.0 -> 2.6.12
  */
  @ParameterizedTest
  @ValueSource(strings = {"3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15", "2.6.0 -> 2.6.12"})
  public void windows_x86_64(String version) {
    assertThat(windowsWith(CommonArchitecture.X86_64), version)
            .resolvesTo("/win32/mongodb-win32-x86_64-{}.zip");
  }

  private static Platform windowsWith(CommonArchitecture architecture) {
    return ImmutablePlatform.builder()
            .operatingSystem(OS.Windows)
            .architecture(architecture)
            .build();
  }

  private static HtmlParserResultTester assertThat(Platform platform, String versionList) {
    return HtmlParserResultTester.with(
            new WindowsPackageFinder(Command.Mongo),
            version -> Distribution.of(Version.of(version), platform),
            versionList);
  }
}