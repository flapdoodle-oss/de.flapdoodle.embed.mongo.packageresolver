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

import de.flapdoodle.embed.mongo.packageresolver.Command;
import de.flapdoodle.embed.mongo.packageresolver.HasMongotoolsPackage;
import de.flapdoodle.embed.mongo.packageresolver.HtmlParserResultTester;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.distribution.Version;
import de.flapdoodle.os.*;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

/**
* this file is generated, please don't touch
*/
class OSXPackageFinderTest {
	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1" })
	public void OS_XArmDev(String version) {
		assertThat(withPlatform(CommonOS.OS_X, CommonArchitecture.ARM_64), version)
		  .resolvesTo("/osx/mongodb-macos-arm64-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11" })
	public void OS_XArm(String version) {
		assertThat(withPlatform(CommonOS.OS_X, CommonArchitecture.ARM_64), version)
		  .resolvesTo("/osx/mongodb-macos-arm64-{}.tgz");
	}


	@ParameterizedTest
	@ValueSource(strings = { "100.9.0", "100.8.0", "100.7.1 -> 100.7.5" })
	public void OS_XArmTools(String version) {
		assertThatTools(withPlatform(CommonOS.OS_X, CommonArchitecture.ARM_64), version)
			.resolvesTo("/tools/db/mongodb-database-tools-macos-arm64-{}.zip");
	}

	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1", "5.0.20-rc1", "4.4.24-rc0" })
	public void OS_XDev(String version) {
		assertThat(withPlatform(CommonOS.OS_X, CommonArchitecture.X86_64), version)
		  .resolvesTo("/osx/mongodb-macos-x86_64-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11", "5.0.0 -> 5.0.22", "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.0 -> 4.2.3" })
	public void OS_X(String version) {
		assertThat(withPlatform(CommonOS.OS_X, CommonArchitecture.X86_64), version)
		  .resolvesTo("/osx/mongodb-macos-x86_64-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.4 -> 3.0.15" })
	public void OS_X_1(String version) {
		assertThat(withPlatform(CommonOS.OS_X, CommonArchitecture.X86_64), version)
		  .resolvesTo("/osx/mongodb-osx-ssl-x86_64-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { /*"3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15",*/ "2.6.0 -> 2.6.12" })
	public void OS_X_2(String version) {
		assertThat(withPlatform(CommonOS.OS_X, CommonArchitecture.X86_64), version)
		  .resolvesTo("/osx/mongodb-osx-x86_64-{}.tgz");
	}


	@ParameterizedTest
	@ValueSource(strings = { "100.9.0", "100.8.0", "100.7.0 -> 100.7.5", "100.6.0 -> 100.6.1", "100.5.0 -> 100.5.4", "100.4.0 -> 100.4.1", "100.3.0 -> 100.3.1", "100.2.0 -> 100.2.1", "100.1.0 -> 100.1.1" })
	public void OS_XTools(String version) {
		assertThatTools(withPlatform(CommonOS.OS_X, CommonArchitecture.X86_64), version)
			.resolvesTo("/tools/db/mongodb-database-tools-macos-x86_64-{}.zip");
	}
	@ParameterizedTest
	@ValueSource(strings = { "100.0.0-alpha1 -> 100.0.2", "100.0.0", "99.0.0" })
	public void OS_X_1Tools(String version) {
		assertThatTools(withPlatform(CommonOS.OS_X, CommonArchitecture.X86_64), version)
			.resolvesTo("/tools/db/mongodb-database-tools-macos-x86_64-{}.tgz");
	}


	private static Platform withPlatform(OS os, CommonArchitecture architecture) {
		return ImmutablePlatform.builder()
			.operatingSystem(os)
			.architecture(architecture)
			.build();
	}

	private static Platform withPlatform(OS os, CommonArchitecture architecture, de.flapdoodle.os.Version version) {
		return ImmutablePlatform.builder()
			.operatingSystem(os)
			.architecture(architecture)
			.version(version)
			.build();
	}

	private static HtmlParserResultTester assertThat(Platform platform, String versionList) {
		return HtmlParserResultTester.with(
			new OSXPackageFinder(Command.Mongo),
			version -> Distribution.of(Version.of(version), platform),
			versionList);
	}

	private static HtmlParserResultTester assertThatTools(Platform platform, String versionList) {
		return HtmlParserResultTester.with(
			new OSXPackageFinder(Command.MongoDump),
			version -> Distribution.of(ToolsVersion.of(Version.of(version)), platform),
			versionList);
	}

	static class ToolsVersion implements Version, HasMongotoolsPackage {
		private final Version toolsVersion;

		public ToolsVersion(Version toolsVersion) {
			this.toolsVersion = toolsVersion;
		}

		@Override
		public String asInDownloadPath() {
			return "any";
		}

		@Override
		public Optional<? extends Version> mongotoolsVersion() {
			return Optional.of(toolsVersion);
		}

		private static ToolsVersion of(Version toolsVersion) {
			return new ToolsVersion(toolsVersion);
		}
	}
}
