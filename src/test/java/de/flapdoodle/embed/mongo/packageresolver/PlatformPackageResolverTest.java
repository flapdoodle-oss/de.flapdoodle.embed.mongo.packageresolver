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

import com.google.common.io.Resources;
import org.assertj.core.api.Assertions;
import org.assertj.core.util.URLs;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;

class PlatformPackageResolverTest {

  @Test
  public void explainSnapshotMustNotChangeWithoutNotice() {
    Assertions.assertThat(new PlatformPackageResolver(Command.Mongo).explain())
      .isEqualToIgnoringNewLines(URLs.contentOf(Resources.getResource(PlatformPackageResolverTest.class,"explainedSnapshot.txt"), StandardCharsets.UTF_8));
  }

  @Test
  public void explainToolsSnapshotMustNotChangeWithoutNotice() {
    Assertions.assertThat(new PlatformPackageResolver(Command.MongoImport).explain())
      .isEqualToIgnoringNewLines(URLs.contentOf(Resources.getResource(PlatformPackageResolverTest.class,"explainedToolsSnapshot.txt"), StandardCharsets.UTF_8));
  }
}