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
import de.flapdoodle.embed.mongo.packageresolver.HasMongotoolsPackage;
import de.flapdoodle.embed.mongo.packageresolver.HtmlParserResultTester;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.distribution.Version;
import de.flapdoodle.os.*;
import de.flapdoodle.os.linux.UbuntuVersion;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Stream;

/**
* this file is generated, please don't touch
*/
class UbuntuPackageFinderTest {
	@ParameterizedTest
	@ValueSource(strings = { "7.2.0-rc3", "7.1.0 -> 7.1.1", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1" })
	public void Ubuntu_22_04ArmDev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, UbuntuVersion.Ubuntu_22_04), version)
			.resolveDevPackageTo("/linux/mongodb-linux-aarch64-ubuntu2204-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.0.0 -> 7.0.4", "6.0.4 -> 6.0.12" })
	public void Ubuntu_22_04Arm(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, UbuntuVersion.Ubuntu_22_04), version)
			.resolvesTo("/linux/mongodb-linux-aarch64-ubuntu2204-{}.tgz");
	}


	@ParameterizedTest
	@ValueSource(strings = { "100.9.0 -> 100.9.4", "100.8.0", "100.7.0 -> 100.7.5", "100.6.0 -> 100.6.1", "100.5.4" })
	public void Ubuntu_22_04ArmTools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, UbuntuVersion.Ubuntu_22_04), version)
			.resolvesTo("/tools/db/mongodb-database-tools-ubuntu2204-arm64-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "7.2.0-rc3", "7.1.0 -> 7.1.1", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1" })
	public void Ubuntu_22_04Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, UbuntuVersion.Ubuntu_22_04), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-ubuntu2204-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.0.0 -> 7.0.4", "6.0.4 -> 6.0.12" })
	public void Ubuntu_22_04(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, UbuntuVersion.Ubuntu_22_04), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-ubuntu2204-{}.tgz");
	}


	@ParameterizedTest
	@ValueSource(strings = { "100.9.0 -> 100.9.4", "100.8.0", "100.7.0 -> 100.7.5", "100.6.0 -> 100.6.1", "100.5.4" })
	public void Ubuntu_22_04Tools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, UbuntuVersion.Ubuntu_22_04), version)
			.resolvesTo("/tools/db/mongodb-database-tools-ubuntu2204-x86_64-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "7.2.0-rc3", "7.1.0 -> 7.1.1", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1", "5.0.20-rc1", "4.4.27-rc0", "4.4.24-rc0" })
	public void Ubuntu_20_04ArmDev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, UbuntuVersion.Ubuntu_20_04), version)
			.resolveDevPackageTo("/linux/mongodb-linux-aarch64-ubuntu2004-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.0.0 -> 7.0.4", "6.0.0 -> 6.0.12", "5.0.0 -> 5.0.23", "4.4.0 -> 4.4.26" })
	public void Ubuntu_20_04Arm(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, UbuntuVersion.Ubuntu_20_04), version)
			.resolvesTo("/linux/mongodb-linux-aarch64-ubuntu2004-{}.tgz");
	}


	@ParameterizedTest
	@ValueSource(strings = { "100.9.0 -> 100.9.4", "100.8.0", "100.7.0 -> 100.7.5", "100.6.0 -> 100.6.1", "100.5.0 -> 100.5.4", "100.4.0 -> 100.4.1", "100.3.0 -> 100.3.1", "100.2.0 -> 100.2.1", "100.1.0 -> 100.1.1" })
	public void Ubuntu_20_04ArmTools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, UbuntuVersion.Ubuntu_20_04), version)
			.resolvesTo("/tools/db/mongodb-database-tools-ubuntu2004-arm64-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "7.2.0-rc3", "7.1.0 -> 7.1.1", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1", "5.0.20-rc1", "4.4.27-rc0", "4.4.24-rc0" })
	public void Ubuntu_20_04Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, UbuntuVersion.Ubuntu_20_04), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-ubuntu2004-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.0.0 -> 7.0.4", "6.0.0 -> 6.0.12", "5.0.0 -> 5.0.23", "4.4.0 -> 4.4.26" })
	public void Ubuntu_20_04(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, UbuntuVersion.Ubuntu_20_04), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-ubuntu2004-{}.tgz");
	}


	@ParameterizedTest
	@ValueSource(strings = { "100.9.0 -> 100.9.4", "100.8.0", "100.7.0 -> 100.7.5", "100.6.0 -> 100.6.1", "100.5.0 -> 100.5.4", "100.4.0 -> 100.4.1", "100.3.0 -> 100.3.1", "100.2.0 -> 100.2.1", "100.1.0 -> 100.1.1" })
	public void Ubuntu_20_04Tools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, UbuntuVersion.Ubuntu_20_04), version)
			.resolvesTo("/tools/db/mongodb-database-tools-ubuntu2004-x86_64-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "7.0.0-rc2", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1", "5.0.20-rc1", "4.4.27-rc0", "4.4.24-rc0" })
	public void Ubuntu_18_04ArmDev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, UbuntuVersion.Ubuntu_18_04), version)
			.resolveDevPackageTo("/linux/mongodb-linux-aarch64-ubuntu1804-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "6.0.0 -> 6.0.12", "5.0.0 -> 5.0.23", "4.4.0 -> 4.4.26", "4.2.5 -> 4.2.25", "4.2.0 -> 4.2.3" })
	public void Ubuntu_18_04Arm(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, UbuntuVersion.Ubuntu_18_04), version)
			.resolvesTo("/linux/mongodb-linux-aarch64-ubuntu1804-{}.tgz");
	}


	@ParameterizedTest
	@ValueSource(strings = { "100.9.0 -> 100.9.4", "100.8.0", "100.7.0 -> 100.7.5", "100.6.0 -> 100.6.1", "100.5.0 -> 100.5.4", "100.4.0 -> 100.4.1", "100.3.0 -> 100.3.1", "100.2.0 -> 100.2.1", "100.1.0 -> 100.1.1", "100.0.0 -> 100.0.2", "99.0.0" })
	public void Ubuntu_18_04ArmTools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, UbuntuVersion.Ubuntu_18_04), version)
			.resolvesTo("/tools/db/mongodb-database-tools-ubuntu1804-arm64-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "7.0.0-rc2", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1", "5.0.20-rc1", "4.4.27-rc0", "4.4.24-rc0" })
	public void Ubuntu_18_04Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, UbuntuVersion.Ubuntu_18_04), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-ubuntu1804-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "6.0.0 -> 6.0.12", "5.0.0 -> 5.0.23", "4.4.0 -> 4.4.26", "4.2.5 -> 4.2.25", "4.2.0 -> 4.2.3", "4.0.1 -> 4.0.28", "3.6.20 -> 3.6.23" })
	public void Ubuntu_18_04(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, UbuntuVersion.Ubuntu_18_04), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-ubuntu1804-{}.tgz");
	}


	@ParameterizedTest
	@ValueSource(strings = { "100.9.0 -> 100.9.4", "100.8.0", "100.7.0 -> 100.7.5", "100.6.0 -> 100.6.1", "100.5.0 -> 100.5.4", "100.4.0 -> 100.4.1", "100.3.0 -> 100.3.1", "100.2.0 -> 100.2.1", "100.1.0 -> 100.1.1", "100.0.0 -> 100.0.2", "99.0.0" })
	public void Ubuntu_18_04Tools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, UbuntuVersion.Ubuntu_18_04), version)
			.resolvesTo("/tools/db/mongodb-database-tools-ubuntu1804-x86_64-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7" })
	public void Ubuntu_16_04Arm(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, UbuntuVersion.Ubuntu_16_04), version)
			.resolvesTo("/linux/mongodb-linux-arm64-ubuntu1604-{}.tgz");
	}


	@ParameterizedTest
	@ValueSource(strings = { "100.9.0 -> 100.9.4", "100.8.0", "100.7.0 -> 100.7.5", "100.6.0 -> 100.6.1", "100.5.0 -> 100.5.4", "100.4.0 -> 100.4.1", "100.3.0 -> 100.3.1", "100.2.0 -> 100.2.1", "100.1.0 -> 100.1.1", "100.0.0 -> 100.0.2", "99.0.0" })
	public void Ubuntu_16_04ArmTools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, UbuntuVersion.Ubuntu_16_04), version)
			.resolvesTo("/tools/db/mongodb-database-tools-ubuntu1604-arm64-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "4.4.27-rc0", "4.4.24-rc0" })
	public void Ubuntu_16_04Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, UbuntuVersion.Ubuntu_16_04), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-ubuntu1604-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "4.4.0 -> 4.4.26", "4.2.5 -> 4.2.25", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.7 -> 3.2.22" })
	public void Ubuntu_16_04(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, UbuntuVersion.Ubuntu_16_04), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-ubuntu1604-{}.tgz");
	}


	@ParameterizedTest
	@ValueSource(strings = { "100.9.0 -> 100.9.4", "100.8.0", "100.7.0 -> 100.7.5", "100.6.0 -> 100.6.1", "100.5.0 -> 100.5.4", "100.4.0 -> 100.4.1", "100.3.0 -> 100.3.1", "100.2.0 -> 100.2.1", "100.1.0 -> 100.1.1", "100.0.0 -> 100.0.2", "99.0.0" })
	public void Ubuntu_16_04Tools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, UbuntuVersion.Ubuntu_16_04), version)
			.resolvesTo("/tools/db/mongodb-database-tools-ubuntu1604-x86_64-{}.tgz");
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
			new UbuntuPackageFinder(Command.Mongo),
			version -> Distribution.of(Version.of(version), platform),
			versionList);
	}

	private static HtmlParserResultTester assertThatTools(Platform platform, String versionList) {
		return HtmlParserResultTester.with(
			new UbuntuPackageFinder(Command.MongoDump),
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
