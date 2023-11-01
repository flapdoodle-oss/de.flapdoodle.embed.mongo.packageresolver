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
import de.flapdoodle.os.linux.CentosVersion;
import de.flapdoodle.os.linux.FedoraVersion;
import de.flapdoodle.os.linux.OracleVersion;
import de.flapdoodle.os.linux.RedhatVersion;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import java.util.Optional;

/**
* this file is generated, please don't touch
*/
class CentosRedhatPackageFinderTest {
	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "6.0.9-rc1" })
	public void CentOS_9ArmDev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, CentosVersion.CentOS_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-aarch64-rhel90-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "6.0.9-rc1" })
	public void Redhat_9AsCentOS_9ArmDev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, RedhatVersion.Redhat_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-aarch64-rhel90-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "6.0.9-rc1" })
	public void Oracle_9AsCentOS_9ArmDev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, OracleVersion.Oracle_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-aarch64-rhel90-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "6.0.9-rc1" })
	public void Fedora_38AsCentOS_9ArmDev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, FedoraVersion.Fedora_38), version)
			.resolveDevPackageTo("/linux/mongodb-linux-aarch64-rhel90-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "7.0.0 -> 7.0.2", "6.0.7 -> 6.0.11" })
	public void CentOS_9Arm(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, CentosVersion.CentOS_9), version)
			.resolvesTo("/linux/mongodb-linux-aarch64-rhel90-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.0.0 -> 7.0.2", "6.0.7 -> 6.0.11" })
	public void Redhat_9AsCentOS_9Arm(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, RedhatVersion.Redhat_9), version)
			.resolvesTo("/linux/mongodb-linux-aarch64-rhel90-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.0.0 -> 7.0.2", "6.0.7 -> 6.0.11" })
	public void Oracle_9AsCentOS_9Arm(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, OracleVersion.Oracle_9), version)
			.resolvesTo("/linux/mongodb-linux-aarch64-rhel90-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.0.0 -> 7.0.2", "6.0.7 -> 6.0.11" })
	public void Fedora_38AsCentOS_9Arm(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, FedoraVersion.Fedora_38), version)
			.resolvesTo("/linux/mongodb-linux-aarch64-rhel90-{}.tgz");
	}



	@ParameterizedTest
	@ValueSource(strings = { "100.9.0", "100.8.0", "100.7.2 -> 100.7.5" })
	public void CentOS_9ArmTools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, CentosVersion.CentOS_9), version)
			.resolvesTo("/tools/db/mongodb-database-tools-rhel90-aarch64-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "100.7.1" })
	public void CentOS_9Arm_1Tools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, CentosVersion.CentOS_9), version)
			.resolvesTo("/tools/db/mongodb-database-tools-rhel90-arm64-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1" })
	public void CentOS_9Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, CentosVersion.CentOS_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel90-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1" })
	public void Redhat_9AsCentOS_9Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel90-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1" })
	public void Oracle_9AsCentOS_9Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, OracleVersion.Oracle_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel90-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1" })
	public void Fedora_38AsCentOS_9Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, FedoraVersion.Fedora_38), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel90-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "7.0.0 -> 7.0.2", "6.0.4 -> 6.0.11" })
	public void CentOS_9(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, CentosVersion.CentOS_9), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel90-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.0.0 -> 7.0.2", "6.0.4 -> 6.0.11" })
	public void Redhat_9AsCentOS_9(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_9), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel90-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.0.0 -> 7.0.2", "6.0.4 -> 6.0.11" })
	public void Oracle_9AsCentOS_9(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, OracleVersion.Oracle_9), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel90-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.0.0 -> 7.0.2", "6.0.4 -> 6.0.11" })
	public void Fedora_38AsCentOS_9(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, FedoraVersion.Fedora_38), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel90-{}.tgz");
	}



	@ParameterizedTest
	@ValueSource(strings = { "100.9.0", "100.8.0", "100.7.0 -> 100.7.5", "100.6.0 -> 100.6.1" })
	public void CentOS_9Tools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, CentosVersion.CentOS_9), version)
			.resolvesTo("/tools/db/mongodb-database-tools-rhel90-x86_64-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1", "5.0.20-rc1", "4.4.24-rc0" })
	public void CentOS_8ArmDev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, CentosVersion.CentOS_8), version)
			.resolveDevPackageTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1", "5.0.20-rc1", "4.4.24-rc0" })
	public void Redhat_8AsCentOS_8ArmDev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, RedhatVersion.Redhat_8), version)
			.resolveDevPackageTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1", "5.0.20-rc1", "4.4.24-rc0" })
	public void Oracle_8AsCentOS_8ArmDev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, OracleVersion.Oracle_8), version)
			.resolveDevPackageTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { /*"7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10",*/ "7.0.0-rc1", "6.3.1 -> 6.3.2", /*"6.0.9-rc1",*/ "5.0.20-rc1", "4.4.24-rc0" })
	public void CentOS_9AsCentOS_8ArmDev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, CentosVersion.CentOS_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { /*"7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10",*/ "7.0.0-rc1", "6.3.1 -> 6.3.2", /*"6.0.9-rc1",*/ "5.0.20-rc1", "4.4.24-rc0" })
	public void Redhat_9AsCentOS_8ArmDev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, RedhatVersion.Redhat_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { /*"7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10",*/ "7.0.0-rc1", "6.3.1 -> 6.3.2", /*"6.0.9-rc1",*/ "5.0.20-rc1", "4.4.24-rc0" })
	public void Oracle_9AsCentOS_8ArmDev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, OracleVersion.Oracle_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { /*"7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10",*/ "7.0.0-rc1", "6.3.1 -> 6.3.2", /*"6.0.9-rc1",*/ "5.0.20-rc1", "4.4.24-rc0" })
	public void Fedora_38AsCentOS_8ArmDev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, FedoraVersion.Fedora_38), version)
			.resolveDevPackageTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11", "5.0.0 -> 5.0.22", "4.4.4 -> 4.4.25" })
	public void CentOS_8Arm(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, CentosVersion.CentOS_8), version)
			.resolvesTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11", "5.0.0 -> 5.0.22", "4.4.4 -> 4.4.25" })
	public void Redhat_8AsCentOS_8Arm(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, RedhatVersion.Redhat_8), version)
			.resolvesTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11", "5.0.0 -> 5.0.22", "4.4.4 -> 4.4.25" })
	public void Oracle_8AsCentOS_8Arm(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, OracleVersion.Oracle_8), version)
			.resolvesTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { /*"7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11",*/ "5.0.0 -> 5.0.22", "4.4.4 -> 4.4.25" })
	public void CentOS_9AsCentOS_8Arm(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, CentosVersion.CentOS_9), version)
			.resolvesTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { /*"7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11",*/ "5.0.0 -> 5.0.22", "4.4.4 -> 4.4.25" })
	public void Redhat_9AsCentOS_8Arm(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, RedhatVersion.Redhat_9), version)
			.resolvesTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { /*"7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11",*/ "5.0.0 -> 5.0.22", "4.4.4 -> 4.4.25" })
	public void Oracle_9AsCentOS_8Arm(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, OracleVersion.Oracle_9), version)
			.resolvesTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { /*"7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11",*/ "5.0.0 -> 5.0.22", "4.4.4 -> 4.4.25" })
	public void Fedora_38AsCentOS_8Arm(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, FedoraVersion.Fedora_38), version)
			.resolvesTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
	}



	@ParameterizedTest
	@ValueSource(strings = { "100.7.0 -> 100.7.1", "100.6.0 -> 100.6.1", "100.5.0 -> 100.5.4", "100.4.0 -> 100.4.1" })
	public void CentOS_8ArmTools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, CentosVersion.CentOS_8), version)
			.resolvesTo("/tools/db/mongodb-database-tools-rhel82-arm64-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "100.9.0", "100.8.0", "100.7.2 -> 100.7.5" })
	public void CentOS_8Arm_1Tools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, CentosVersion.CentOS_8), version)
			.resolvesTo("/tools/db/mongodb-database-tools-rhel82-aarch64-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1", "5.0.20-rc1", "4.4.24-rc0" })
	public void CentOS_8Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, CentosVersion.CentOS_8), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel80-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1", "5.0.20-rc1", "4.4.24-rc0" })
	public void Redhat_8AsCentOS_8Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_8), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel80-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1", "5.0.20-rc1", "4.4.24-rc0" })
	public void Oracle_8AsCentOS_8Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, OracleVersion.Oracle_8), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel80-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { /*"7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1",*/ "5.0.20-rc1", "4.4.24-rc0" })
	public void CentOS_9AsCentOS_8Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, CentosVersion.CentOS_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel80-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { /*"7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1",*/ "5.0.20-rc1", "4.4.24-rc0" })
	public void Redhat_9AsCentOS_8Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel80-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { /*"7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1",*/ "5.0.20-rc1", "4.4.24-rc0" })
	public void Oracle_9AsCentOS_8Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, OracleVersion.Oracle_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel80-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { /*"7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1",*/ "5.0.20-rc1", "4.4.24-rc0" })
	public void Fedora_38AsCentOS_8Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, FedoraVersion.Fedora_38), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel80-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11", "5.0.0 -> 5.0.22", "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.1 -> 4.2.3", "4.0.14 -> 4.0.28", "3.6.17 -> 3.6.23", "3.4.24" })
	public void CentOS_8(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, CentosVersion.CentOS_8), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel80-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11", "5.0.0 -> 5.0.22", "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.1 -> 4.2.3", "4.0.14 -> 4.0.28", "3.6.17 -> 3.6.23", "3.4.24" })
	public void Redhat_8AsCentOS_8(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_8), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel80-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11", "5.0.0 -> 5.0.22", "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.1 -> 4.2.3", "4.0.14 -> 4.0.28", "3.6.17 -> 3.6.23", "3.4.24" })
	public void Oracle_8AsCentOS_8(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, OracleVersion.Oracle_8), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel80-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { /*"7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11",*/ "5.0.0 -> 5.0.22", "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.1 -> 4.2.3", "4.0.14 -> 4.0.28", "3.6.17 -> 3.6.23", "3.4.24" })
	public void CentOS_9AsCentOS_8(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, CentosVersion.CentOS_9), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel80-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { /*"7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11",*/ "5.0.0 -> 5.0.22", "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.1 -> 4.2.3", "4.0.14 -> 4.0.28", "3.6.17 -> 3.6.23", "3.4.24" })
	public void Redhat_9AsCentOS_8(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_9), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel80-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { /*"7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11",*/ "5.0.0 -> 5.0.22", "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.1 -> 4.2.3", "4.0.14 -> 4.0.28", "3.6.17 -> 3.6.23", "3.4.24" })
	public void Oracle_9AsCentOS_8(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, OracleVersion.Oracle_9), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel80-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { /*"7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11",*/ "5.0.0 -> 5.0.22", "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.1 -> 4.2.3", "4.0.14 -> 4.0.28", "3.6.17 -> 3.6.23", "3.4.24" })
	public void Fedora_38AsCentOS_8(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, FedoraVersion.Fedora_38), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel80-{}.tgz");
	}



	@ParameterizedTest
	@ValueSource(strings = { "100.9.0", "100.8.0", "100.7.0 -> 100.7.5", "100.6.0 -> 100.6.1", "100.5.0 -> 100.5.4", "100.4.0 -> 100.4.1", "100.3.0 -> 100.3.1", "100.2.0 -> 100.2.1", "100.1.0 -> 100.1.1", "100.0.0 -> 100.0.2", "99.0.0" })
	public void CentOS_8Tools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, CentosVersion.CentOS_8), version)
			.resolvesTo("/tools/db/mongodb-database-tools-rhel80-x86_64-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1", "5.0.20-rc1", "4.4.24-rc0" })
	public void CentOS_7Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, CentosVersion.CentOS_7), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1", "5.0.20-rc1", "4.4.24-rc0" })
	public void Redhat_7AsCentOS_7Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_7), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1", "5.0.20-rc1", "4.4.24-rc0" })
	public void Oracle_7AsCentOS_7Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, OracleVersion.Oracle_7), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1", "5.0.20-rc1", "4.4.24-rc0" })
	@Disabled
	public void CentOS_8AsCentOS_7Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, CentosVersion.CentOS_8), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1", "5.0.20-rc1", "4.4.24-rc0" })
	@Disabled
	public void Redhat_8AsCentOS_7Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_8), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1", "5.0.20-rc1", "4.4.24-rc0" })
	@Disabled
	public void Oracle_8AsCentOS_7Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, OracleVersion.Oracle_8), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1", "5.0.20-rc1", "4.4.24-rc0" })
	@Disabled
	public void CentOS_9AsCentOS_7Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, CentosVersion.CentOS_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1", "5.0.20-rc1", "4.4.24-rc0" })
	@Disabled
	public void Redhat_9AsCentOS_7Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1", "5.0.20-rc1", "4.4.24-rc0" })
	@Disabled
	public void Oracle_9AsCentOS_7Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, OracleVersion.Oracle_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.1.0", "7.0.3-rc1", "7.0.0-rc8", "7.0.0-rc2", "7.0.0-rc10", "7.0.0-rc1", "6.3.1 -> 6.3.2", "6.0.9-rc1", "5.0.20-rc1", "4.4.24-rc0" })
	@Disabled
	public void Fedora_38AsCentOS_7Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, FedoraVersion.Fedora_38), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11", "5.0.0 -> 5.0.22", "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	public void CentOS_7(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, CentosVersion.CentOS_7), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11", "5.0.0 -> 5.0.22", "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	public void Redhat_7AsCentOS_7(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_7), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11", "5.0.0 -> 5.0.22", "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	public void Oracle_7AsCentOS_7(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, OracleVersion.Oracle_7), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { /*"7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11", "5.0.0 -> 5.0.22", "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24",*/ "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	public void CentOS_8AsCentOS_7(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, CentosVersion.CentOS_8), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { /*"7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11", "5.0.0 -> 5.0.22", "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24",*/ "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	public void Redhat_8AsCentOS_7(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_8), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { /*"7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11", "5.0.0 -> 5.0.22", "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24",*/ "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	public void Oracle_8AsCentOS_7(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, OracleVersion.Oracle_8), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { /*"7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11", "5.0.0 -> 5.0.22", "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24",*/ "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	public void CentOS_9AsCentOS_7(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, CentosVersion.CentOS_9), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { /*"7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11", "5.0.0 -> 5.0.22", "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24",*/ "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	public void Redhat_9AsCentOS_7(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_9), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { /*"7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11", "5.0.0 -> 5.0.22", "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24",*/ "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	public void Oracle_9AsCentOS_7(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, OracleVersion.Oracle_9), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { /*"7.0.0 -> 7.0.2", "6.0.0 -> 6.0.11", "5.0.0 -> 5.0.22", "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24",*/ "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	public void Fedora_38AsCentOS_7(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, FedoraVersion.Fedora_38), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
	}



	@ParameterizedTest
	@ValueSource(strings = { "100.9.0", "100.8.0", "100.7.0 -> 100.7.5", "100.6.0 -> 100.6.1", "100.5.0 -> 100.5.4", "100.4.0 -> 100.4.1", "100.3.0 -> 100.3.1", "100.2.0 -> 100.2.1", "100.1.0 -> 100.1.1", "100.0.0 -> 100.0.2", "99.0.0" })
	public void CentOS_7Tools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, CentosVersion.CentOS_7), version)
			.resolvesTo("/tools/db/mongodb-database-tools-rhel70-x86_64-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "4.4.24-rc0" })
	public void CentOS_6Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, CentosVersion.CentOS_6), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "4.4.24-rc0" })
	public void Redhat_6AsCentOS_6Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_6), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "4.4.24-rc0" })
	public void Oracle_6AsCentOS_6Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, OracleVersion.Oracle_6), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "4.4.24-rc0" })
	@Disabled
	public void CentOS_7AsCentOS_6Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, CentosVersion.CentOS_7), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "4.4.24-rc0" })
	@Disabled
	public void Redhat_7AsCentOS_6Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_7), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "4.4.24-rc0" })
  @Disabled
	public void Oracle_7AsCentOS_6Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, OracleVersion.Oracle_7), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "4.4.24-rc0" })
	@Disabled
	public void CentOS_8AsCentOS_6Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, CentosVersion.CentOS_8), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "4.4.24-rc0" })
	@Disabled
	public void Redhat_8AsCentOS_6Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_8), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "4.4.24-rc0" })
	@Disabled
	public void Oracle_8AsCentOS_6Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, OracleVersion.Oracle_8), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "4.4.24-rc0" })
	@Disabled
	public void CentOS_9AsCentOS_6Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, CentosVersion.CentOS_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "4.4.24-rc0" })
	@Disabled
	public void Redhat_9AsCentOS_6Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "4.4.24-rc0" })
	@Disabled
	public void Oracle_9AsCentOS_6Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, OracleVersion.Oracle_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "4.4.24-rc0" })
	@Disabled
	public void Fedora_38AsCentOS_6Dev(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, FedoraVersion.Fedora_38), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	public void CentOS_6(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, CentosVersion.CentOS_6), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	public void Redhat_6AsCentOS_6(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_6), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	public void Oracle_6AsCentOS_6(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, OracleVersion.Oracle_6), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	@Disabled
	public void CentOS_7AsCentOS_6(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, CentosVersion.CentOS_7), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	@Disabled
	public void Redhat_7AsCentOS_6(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_7), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	@Disabled
	public void Oracle_7AsCentOS_6(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, OracleVersion.Oracle_7), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	@Disabled
	public void CentOS_8AsCentOS_6(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, CentosVersion.CentOS_8), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	@Disabled
	public void Redhat_8AsCentOS_6(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_8), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	@Disabled
	public void Oracle_8AsCentOS_6(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, OracleVersion.Oracle_8), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	@Disabled
	public void CentOS_9AsCentOS_6(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, CentosVersion.CentOS_9), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	@Disabled
	public void Redhat_9AsCentOS_6(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, RedhatVersion.Redhat_9), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	@Disabled
	public void Oracle_9AsCentOS_6(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, OracleVersion.Oracle_9), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}
	@ParameterizedTest
	@ValueSource(strings = { "4.4.0 -> 4.4.25", "4.2.5 -> 4.2.24", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	@Disabled
	public void Fedora_38AsCentOS_6(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, FedoraVersion.Fedora_38), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}



	@ParameterizedTest
	@ValueSource(strings = { "100.9.0", "100.8.0", "100.7.0 -> 100.7.5", "100.6.0 -> 100.6.1", "100.5.0 -> 100.5.4", "100.4.0 -> 100.4.1", "100.3.0 -> 100.3.1", "100.2.0 -> 100.2.1", "100.1.0 -> 100.1.1", "100.0.0 -> 100.0.2", "99.0.0" })
	public void CentOS_6Tools(String version) {
		assertThatTools(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, CentosVersion.CentOS_6), version)
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
			new CentosRedhatPackageFinder(Command.Mongo),
			version -> Distribution.of(Version.of(version), platform),
			versionList);
	}

	private static HtmlParserResultTester assertThatTools(Platform platform, String versionList) {
		return HtmlParserResultTester.with(
			new CentosRedhatPackageFinder(Command.MongoDump),
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
