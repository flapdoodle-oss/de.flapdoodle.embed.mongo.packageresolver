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
import de.flapdoodle.os.linux.OracleVersion;
import de.flapdoodle.os.linux.RedhatVersion;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class FedoraPackageFinderTest extends AbstractVersionMappedPackageFinderTest<FedoraVersion, RedhatVersion> {

  public FedoraPackageFinderTest() {
    super(new FedoraPackageFinder(new RedhatPackageFinder(Command.Mongo)));
  }

  @Test
  public void fedoraToRedHatMapping() {
    assertMappedVersion(FedoraVersion.Fedora_38, RedhatVersion.Redhat_9);
    assertMappedVersion(FedoraVersion.Fedora_39, RedhatVersion.Redhat_9);
    assertMappedVersion(FedoraVersion.Fedora_40, RedhatVersion.Redhat_9);
    assertMappedVersion(FedoraVersion.Fedora_41, RedhatVersion.Redhat_9);
  }
}