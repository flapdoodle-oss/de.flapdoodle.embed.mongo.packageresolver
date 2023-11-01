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
class WindowsPackageFinderTest {
	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1", "5.0.20-rc1", "4.4.24-rc0" })
	public void WindowsDev(String version) {
		assertThat(withPlatform(CommonOS.Windows, CommonArchitecture.X86_64), version)
		  .resolvesTo("/windows/mongodb-windows-x86_64-{}.zip");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11", "5.0.0 -> 5.0.22", "4.4.0 -> 4.4.25" })
	public void Windows(String version) {
		assertThat(withPlatform(CommonOS.Windows, CommonArchitecture.X86_64), version)
		  .resolvesTo("/windows/mongodb-windows-x86_64-{}.zip");
	}

	@ParameterizedTest
	@ValueSource(strings = { "4.2.5 -> 4.2.24", "4.2.0 -> 4.2.3" })
	public void Windows_1(String version) {
		assertThat(withPlatform(CommonOS.Windows, CommonArchitecture.X86_64), version)
		  .resolvesTo("/win32/mongodb-win32-x86_64-2012plus-{}.zip");
	}

	@ParameterizedTest
	@ValueSource(strings = { "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	public void Windows_2(String version) {
		assertThat(withPlatform(CommonOS.Windows, CommonArchitecture.X86_64), version)
		  .resolvesTo("/win32/mongodb-win32-x86_64-2008plus-ssl-{}.zip");
	}

	@ParameterizedTest
	@ValueSource(strings = { /*"3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15",*/ "2.6.0 -> 2.6.12" })
	public void Windows_3(String version) {
		assertThat(withPlatform(CommonOS.Windows, CommonArchitecture.X86_64), version)
		  .resolvesTo("/win32/mongodb-win32-x86_64-2008plus-{}.zip");
	}

	@ParameterizedTest
	@Disabled("resolves to mongodb-win32-x86_64-2008plus-{}.zip")
	@ValueSource(strings = { "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15", "2.6.0 -> 2.6.12" })
	public void Windows_4(String version) {
		assertThat(withPlatform(CommonOS.Windows, CommonArchitecture.X86_64), version)
		  .resolvesTo("/win32/mongodb-win32-x86_64-{}.zip");
	}


	@ParameterizedTest
	@ValueSource(strings = { "100.9.0", "100.8.0", "100.7.0 -> 100.7.5", "100.6.0 -> 100.6.1", "100.5.0 -> 100.5.4", "100.4.0 -> 100.4.1", "100.3.0 -> 100.3.1", "100.2.0 -> 100.2.1", "100.1.0 -> 100.1.1", "100.0.0 -> 100.0.2", "99.0.0" })
	public void WindowsTools(String version) {
		assertThatTools(withPlatform(CommonOS.Windows, CommonArchitecture.X86_64), version)
			.resolvesTo("/tools/db/mongodb-database-tools-windows-x86_64-{}.zip");
	}

	@ParameterizedTest
	@ValueSource(strings = { "3.5.5", "3.3.1", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15", "2.6.0 -> 2.6.12" })
	public void Windows32(String version) {
		assertThat(withPlatform(CommonOS.Windows, CommonArchitecture.X86_32), version)
		  .resolvesTo("/win32/mongodb-win32-i386-{}.zip");
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
			new WindowsPackageFinder(Command.Mongo),
			version -> Distribution.of(Version.of(version), platform),
			versionList);
	}

	private static HtmlParserResultTester assertThatTools(Platform platform, String versionList) {
		return HtmlParserResultTester.with(
			new WindowsPackageFinder(Command.MongoDump),
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
