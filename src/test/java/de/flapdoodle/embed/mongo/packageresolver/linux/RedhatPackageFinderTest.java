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
import de.flapdoodle.os.linux.RedhatVersion;

import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

/**
* this file is generated, please don't touch
*/
class RedhatPackageFinderTest {
	@ParameterizedTest
	@ValueSource(strings = { "8.0.0-rc9", "8.0.0-rc7", "8.0.0-rc3", "7.3.3-rc0", "7.3.0 -> 7.3.3", "7.2.0-rc3", "7.1.0 -> 7.1.1", "7.0.18-rc0", "7.0.15-rc1", "7.0.8-rc0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "6.0.21-rc1", "6.0.16-rc0", "6.0.9-rc1" })
	public void Redhat_9ArmDev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, RedhatVersion.Redhat_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-aarch64-rhel90-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.0.14 -> 7.0.17", "7.0.11 -> 7.0.12", "7.0.0 -> 7.0.9", "6.0.7 -> 6.0.21" })
	public void Redhat_9Arm(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, RedhatVersion.Redhat_9), version)
			.resolvesTo("/linux/mongodb-linux-aarch64-rhel90-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "8.1.0-rc0", "8.0.6-rc2" })
	public void Redhat_9Arm_1Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, RedhatVersion.Redhat_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-aarch64-rhel93-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "8.0.3 -> 8.0.5", "8.0.0 -> 8.0.1" })
	public void Redhat_9Arm_1(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, RedhatVersion.Redhat_9), version)
			.resolvesTo("/linux/mongodb-linux-aarch64-rhel93-{}.tgz");
	}


	@ParameterizedTest
	@ValueSource(strings = { "100.9.0 -> 100.9.5", "100.8.0", "100.7.2 -> 100.7.5" })
	public void Redhat_9ArmTools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, RedhatVersion.Redhat_9), version)
			.resolvesTo("/tools/db/mongodb-database-tools-rhel90-aarch64-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "100.7.1" })
	public void Redhat_9Arm_1Tools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, RedhatVersion.Redhat_9), version)
			.resolvesTo("/tools/db/mongodb-database-tools-rhel90-arm64-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "100.11.0", "100.10.0" })
	public void Redhat_9Arm_2Tools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, RedhatVersion.Redhat_9), version)
			.resolvesTo("/tools/db/mongodb-database-tools-rhel93-aarch64-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "8.0.0-rc9", "8.0.0-rc7", "8.0.0-rc3", "7.3.3-rc0", "7.3.0 -> 7.3.3", "7.2.0-rc3", "7.1.0 -> 7.1.1", "7.0.18-rc0", "7.0.15-rc1", "7.0.8-rc0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.21-rc1", "6.0.16-rc0", "6.0.9-rc1" })
	public void Redhat_9Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel90-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.0.14 -> 7.0.17", "7.0.11 -> 7.0.12", "7.0.0 -> 7.0.9", "6.0.4 -> 6.0.21" })
	public void Redhat_9(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_9), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel90-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "8.1.0-rc0", "8.0.6-rc2" })
	public void Redhat_9_1Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel93-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "8.0.3 -> 8.0.5", "8.0.0 -> 8.0.1" })
	public void Redhat_9_1(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_9), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel93-{}.tgz");
	}


	@ParameterizedTest
	@ValueSource(strings = { "100.9.0 -> 100.9.5", "100.8.0", "100.7.0 -> 100.7.5", "100.6.0 -> 100.6.1" })
	public void Redhat_9Tools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_9), version)
			.resolvesTo("/tools/db/mongodb-database-tools-rhel90-x86_64-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "100.11.0", "100.10.0" })
	public void Redhat_9_1Tools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_9), version)
			.resolvesTo("/tools/db/mongodb-database-tools-rhel93-x86_64-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "8.0.0-rc9", "8.0.0-rc7", "8.0.0-rc3", "7.3.3-rc0", "7.3.0 -> 7.3.3", "7.2.0-rc3", "7.1.0 -> 7.1.1", "7.0.8-rc0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.16-rc0", "6.0.9-rc1", "5.0.28-rc0", "5.0.20-rc1", "4.4.27-rc0", "4.4.24-rc0" })
	public void Redhat_8ArmDev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, RedhatVersion.Redhat_8), version)
			.resolveDevPackageTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.0.11 -> 7.0.12", "7.0.0 -> 7.0.9", "6.0.0 -> 6.0.16", "5.0.0 -> 5.0.28", "4.4.4 -> 4.4.29" })
	public void Redhat_8Arm(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, RedhatVersion.Redhat_8), version)
			.resolvesTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
	}

	@ParameterizedTest
	// partial
	@ValueSource(strings = { "8.1.0-rc0", "8.0.6-rc2", /*"7.0.18-rc0", "7.0.15-rc1", "6.0.21-rc1"*/ })
	public void Redhat_8Arm_1Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, RedhatVersion.Redhat_8), version)
			.resolveDevPackageTo("/linux/mongodb-linux-aarch64-rhel8-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "8.0.3 -> 8.0.5", "8.0.0 -> 8.0.1", "7.0.14 -> 7.0.17", "6.0.17 -> 6.0.21", "5.0.29 -> 5.0.31" })
	public void Redhat_8Arm_1(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, RedhatVersion.Redhat_8), version)
			.resolvesTo("/linux/mongodb-linux-aarch64-rhel8-{}.tgz");
	}


	@ParameterizedTest
	@ValueSource(strings = { "100.7.0 -> 100.7.1", "100.6.0 -> 100.6.1", "100.5.0 -> 100.5.4", "100.4.0 -> 100.4.1" })
	public void Redhat_8ArmTools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, RedhatVersion.Redhat_8), version)
			.resolvesTo("/tools/db/mongodb-database-tools-rhel82-arm64-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "100.9.0 -> 100.9.5", "100.8.0", "100.7.2 -> 100.7.5" })
	public void Redhat_8Arm_1Tools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, RedhatVersion.Redhat_8), version)
			.resolvesTo("/tools/db/mongodb-database-tools-rhel82-aarch64-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "100.11.0", "100.10.0" })
	public void Redhat_8Arm_2Tools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, RedhatVersion.Redhat_8), version)
			.resolvesTo("/tools/db/mongodb-database-tools-rhel88-aarch64-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "8.0.0-rc9", "8.0.0-rc7", "8.0.0-rc3", "7.3.3-rc0", "7.3.0 -> 7.3.3", "7.2.0-rc3", "7.1.0 -> 7.1.1", "7.0.8-rc0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.16-rc0", "6.0.9-rc1", "5.0.28-rc0", "5.0.20-rc1", "4.4.27-rc0", "4.4.24-rc0" })
	public void Redhat_8Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_8), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel80-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.0.11 -> 7.0.12", "7.0.0 -> 7.0.9", "6.0.0 -> 6.0.16", "5.0.0 -> 5.0.28", "4.4.0 -> 4.4.29", "4.2.5 -> 4.2.25", "4.2.1 -> 4.2.3", "4.0.14 -> 4.0.28", "3.6.17 -> 3.6.23", "3.4.24" })
	public void Redhat_8(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_8), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel80-{}.tgz");
	}

	@ParameterizedTest
	// partial
	@ValueSource(strings = { "8.1.0-rc0", "8.0.6-rc2", /*"7.0.18-rc0", "7.0.15-rc1", "6.0.21-rc1"*/ })
	public void Redhat_8_1Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_8), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel8-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "8.0.3 -> 8.0.5", "8.0.0 -> 8.0.1", "7.0.14 -> 7.0.17", "6.0.17 -> 6.0.21", "5.0.29 -> 5.0.31" })
	public void Redhat_8_1(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_8), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel8-{}.tgz");
	}


	@ParameterizedTest
	@ValueSource(strings = { "100.9.0 -> 100.9.5", "100.8.0", "100.7.0 -> 100.7.5", "100.6.0 -> 100.6.1", "100.5.0 -> 100.5.4", "100.4.0 -> 100.4.1", "100.3.0 -> 100.3.1", "100.2.0 -> 100.2.1", "100.1.0 -> 100.1.1", "100.0.0 -> 100.0.2", "99.0.0" })
	public void Redhat_8Tools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_8), version)
			.resolvesTo("/tools/db/mongodb-database-tools-rhel80-x86_64-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "100.11.0", "100.10.0" })
	public void Redhat_8_1Tools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_8), version)
			.resolvesTo("/tools/db/mongodb-database-tools-rhel88-x86_64-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "7.3.3-rc0", "7.3.0 -> 7.3.3", "7.1.0 -> 7.1.1", "7.0.18-rc0", "7.0.15-rc1", "7.0.8-rc0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.21-rc1", "6.0.16-rc0", "6.0.9-rc1", "5.0.28-rc0", "5.0.20-rc1", "4.4.27-rc0", "4.4.24-rc0" })
	public void Redhat_7Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_7), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.0.14 -> 7.0.17", "7.0.11 -> 7.0.12", "7.0.0 -> 7.0.9", "6.0.0 -> 6.0.21", "5.0.0 -> 5.0.31", "4.4.0 -> 4.4.29", "4.2.5 -> 4.2.25", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	public void Redhat_7(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_7), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
	}


	@ParameterizedTest
	@ValueSource(strings = { "100.11.0", "100.10.0", "100.9.0 -> 100.9.5", "100.8.0", "100.7.0 -> 100.7.5", "100.6.0 -> 100.6.1", "100.5.0 -> 100.5.4", "100.4.0 -> 100.4.1", "100.3.0 -> 100.3.1", "100.2.0 -> 100.2.1", "100.1.0 -> 100.1.1", "100.0.0 -> 100.0.2", "99.0.0" })
	public void Redhat_7Tools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_7), version)
			.resolvesTo("/tools/db/mongodb-database-tools-rhel70-x86_64-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "4.4.27-rc0", "4.4.24-rc0" })
	public void Redhat_6Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_6), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "4.4.0 -> 4.4.29", "4.2.5 -> 4.2.25", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	public void Redhat_6(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_6), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}


	@ParameterizedTest
	@ValueSource(strings = { "100.10.0", "100.9.0 -> 100.9.5", "100.8.0", "100.7.0 -> 100.7.5", "100.6.0 -> 100.6.1", "100.5.0 -> 100.5.4", "100.4.0 -> 100.4.1", "100.3.0 -> 100.3.1", "100.2.0 -> 100.2.1", "100.1.0 -> 100.1.1", "100.0.0 -> 100.0.2", "99.0.0" })
	public void Redhat_6Tools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_6), version)
			.resolvesTo("/tools/db/mongodb-database-tools-rhel62-x86_64-{}.tgz");
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
			new RedhatPackageFinder(Command.MongoD),
			version -> Distribution.of(Version.of(version), platform),
			versionList);
	}

	private static HtmlParserResultTester assertThatTools(Platform platform, String versionList) {
		return HtmlParserResultTester.with(
			new RedhatPackageFinder(Command.MongoDump),
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
