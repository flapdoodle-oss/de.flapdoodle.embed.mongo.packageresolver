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
import de.flapdoodle.os.linux.OracleVersion;
import de.flapdoodle.os.linux.RedhatVersion;
import org.junit.jupiter.api.Test;

class OraclePackageFinderTest extends AbstractVersionMappedPackageFinderTest<OracleVersion, RedhatVersion> {

	public OraclePackageFinderTest() {
		super(new OraclePackageFinder(new RedhatFallbackToOlderVersionPackageFinder(new RedhatPackageFinder(Command.MongoD))));
	}
	
	@Test
	public void oracleToRedHatMapping() {
		assertMappedVersion(OracleVersion.Oracle_6, RedhatVersion.Redhat_6);
		assertMappedVersion(OracleVersion.Oracle_7, RedhatVersion.Redhat_7);
		assertMappedVersion(OracleVersion.Oracle_8, RedhatVersion.Redhat_8);
		assertMappedVersion(OracleVersion.Oracle_9, RedhatVersion.Redhat_9);
	}
}
