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
import de.flapdoodle.os.linux.FedoraVersion;
import de.flapdoodle.os.linux.LinuxMintVersion;
import de.flapdoodle.os.linux.RedhatVersion;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FedoraPackageFinderTest {
  @ParameterizedTest
  @ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "6.0.9-rc1" })
  public void Fedora38AsRedHat_9ArmDev(String version) {
    assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, FedoraVersion.Fedora_38), version)
      .resolveDevPackageTo("/linux/mongodb-linux-aarch64-rhel90-{}.tgz");
  }

  @ParameterizedTest
  @ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1" })
  public void Fedora38AsCentOS_9Dev(String version) {
    assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, FedoraVersion.Fedora_38), version)
      .resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel90-{}.tgz");
  }

  private static HtmlParserResultTester assertThat(Platform platform, String versionList) {
    return HtmlParserResultTester.with(
            new FedoraPackageFinder(new CentosRedhatPackageFinder(Command.Mongo)),
            version -> Distribution.of(Version.of(version), platform),
            versionList);
  }

  private static Platform withPlatform(OS os, CommonArchitecture architecture, de.flapdoodle.os.Version version) {
    return ImmutablePlatform.builder()
      .operatingSystem(os)
      .architecture(architecture)
      .version(version)
      .build();
  }
}