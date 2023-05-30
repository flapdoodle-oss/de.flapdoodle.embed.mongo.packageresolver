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
package de.flapdoodle.embed.mongo.packageresolver.linux;

import de.flapdoodle.embed.mongo.packageresolver.Command;
import de.flapdoodle.embed.mongo.packageresolver.HtmlParserResultTester;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.distribution.Version;
import de.flapdoodle.os.*;
import de.flapdoodle.os.linux.CentosVersion;
import de.flapdoodle.os.linux.OracleVersion;
import de.flapdoodle.os.linux.RedhatVersion;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class CentosRedhatPackageResolverTest {
	/*
		RedHat / CentOS 6.2+ x64
		https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-rhel62-{}.tgz
		"4.4.22", "4.4.16 -> 4.4.19", "4.4.13", "4.4.11", "4.4.0 -> 4.4.9", "4.2.22 -> 4.2.24", "4.2.18 -> 4.2.19", "4.2.5 -> 4.2.16", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15"
	 */
	@ParameterizedTest
	@ValueSource(strings = { "4.4.22", "4.4.16 -> 4.4.19", "4.4.13", "4.4.11", "4.4.0 -> 4.4.9", "4.2.22 -> 4.2.24", "4.2.18 -> 4.2.19", "4.2.5 -> 4.2.16", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	public void centos6(String version) {
		assertThat(linuxWith(CommonArchitecture.X86_64, CentosVersion.CentOS_6), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
		assertThat(linuxWith(CommonArchitecture.X86_64, RedhatVersion.Redhat_6), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
		assertThat(linuxWith(CommonArchitecture.X86_64, OracleVersion.Oracle_6), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel62-{}.tgz");
	}


  /*
			RedHat / CentOS 7.0 x64
			https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-rhel70-{}.tgz
			"6.0.1 -> 6.0.6", "5.0.18", "5.0.12 -> 5.0.15", "5.0.5 -> 5.0.6", "5.0.0 -> 5.0.2", "4.4.22", "4.4.16 -> 4.4.19", "4.4.13", "4.4.11", "4.4.0 -> 4.4.9", "4.2.22 -> 4.2.24", "4.2.18 -> 4.2.19", "4.2.5 -> 4.2.16", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15"
   */
	@ParameterizedTest
	@ValueSource(strings = { "6.0.1 -> 6.0.6", "5.0.18", "5.0.12 -> 5.0.15", "5.0.5 -> 5.0.6", "5.0.0 -> 5.0.2", "4.4.22", "4.4.16 -> 4.4.19", "4.4.13", "4.4.11", "4.4.0 -> 4.4.9", "4.2.22 -> 4.2.24", "4.2.18 -> 4.2.19", "4.2.5 -> 4.2.16", "4.2.0 -> 4.2.3", "4.0.0 -> 4.0.28", "3.6.0 -> 3.6.23", "3.4.9 -> 3.4.24", "3.4.0 -> 3.4.7", "3.2.0 -> 3.2.22", "3.0.0 -> 3.0.15" })
	public void centos7(String version) {
		assertThat(linuxWith(CommonArchitecture.X86_64, CentosVersion.CentOS_7), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
		assertThat(linuxWith(CommonArchitecture.X86_64, RedhatVersion.Redhat_7), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
		assertThat(linuxWith(CommonArchitecture.X86_64, OracleVersion.Oracle_7), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
	}

	/*
			RedHat / CentOS 7.0 x64 dev
			https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-rhel70-{}.tgz
			"7.0.0-rc2", "7.0.0-rc1", "6.3.1"
	 */
	@ParameterizedTest
	@ValueSource(strings = { "7.0.0-rc2", "7.0.0-rc1", "6.3.1" })
	public void centos7dev(String version) {
		assertThat(linuxWith(CommonArchitecture.X86_64, CentosVersion.CentOS_7), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
		assertThat(linuxWith(CommonArchitecture.X86_64, RedhatVersion.Redhat_7), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
		assertThat(linuxWith(CommonArchitecture.X86_64, OracleVersion.Oracle_7), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel70-{}.tgz");
	}

  /*
 			RedHat / CentOS 8.0 x64
			https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-rhel80-{}.tgz
			"6.0.1 -> 6.0.6", "5.0.18", "5.0.12 -> 5.0.15", "5.0.5 -> 5.0.6", "5.0.0 -> 5.0.2", "4.4.22", "4.4.16 -> 4.4.19", "4.4.13", "4.4.11", "4.4.0 -> 4.4.9", "4.2.22 -> 4.2.24", "4.2.18 -> 4.2.19", "4.2.5 -> 4.2.16", "4.2.1 -> 4.2.3", "4.0.14 -> 4.0.28", "3.6.17 -> 3.6.23", "3.4.24"
   */
	@ParameterizedTest
	@ValueSource(strings = { "6.0.1 -> 6.0.6", "5.0.18", "5.0.12 -> 5.0.15", "5.0.5 -> 5.0.6", "5.0.0 -> 5.0.2", "4.4.22", "4.4.16 -> 4.4.19", "4.4.13", "4.4.11", "4.4.0 -> 4.4.9", "4.2.22 -> 4.2.24", "4.2.18 -> 4.2.19", "4.2.5 -> 4.2.16", "4.2.1 -> 4.2.3", "4.0.14 -> 4.0.28", "3.6.17 -> 3.6.23", "3.4.24" })
	public void centos8(String version) {
		assertThat(linuxWith(CommonArchitecture.X86_64, CentosVersion.CentOS_8), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel80-{}.tgz");
		assertThat(linuxWith(CommonArchitecture.X86_64, RedhatVersion.Redhat_8), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel80-{}.tgz");
		assertThat(linuxWith(CommonArchitecture.X86_64, OracleVersion.Oracle_8), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel80-{}.tgz");
	}

	/*
			 RedHat / CentOS 8.0 x64 dev
			https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-rhel80-{}.tgz
			"6.0.1 -> 6.0.6", "5.0.18", "5.0.12 -> 5.0.15", "5.0.5 -> 5.0.6", "5.0.0 -> 5.0.2", "4.4.22", "4.4.16 -> 4.4.19", "4.4.13", "4.4.11", "4.4.0 -> 4.4.9", "4.2.22 -> 4.2.24", "4.2.18 -> 4.2.19", "4.2.5 -> 4.2.16", "4.2.1 -> 4.2.3", "4.0.14 -> 4.0.28", "3.6.17 -> 3.6.23", "3.4.24"
	 */
	@ParameterizedTest
	@ValueSource(strings = { "7.0.0-rc2", "7.0.0-rc1", "6.3.1" })
	public void centos8dev(String version) {
		assertThat(linuxWith(CommonArchitecture.X86_64, CentosVersion.CentOS_8), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel80-{}.tgz");
		assertThat(linuxWith(CommonArchitecture.X86_64, RedhatVersion.Redhat_8), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel80-{}.tgz");
		assertThat(linuxWith(CommonArchitecture.X86_64, OracleVersion.Oracle_8), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel80-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "6.0.1 -> 6.0.2", "5.0.12 -> 5.0.15", "5.0.5 -> 5.0.6", "5.0.0 -> 5.0.2", "4.4.16 -> 4.4.19", "4.4.13", "4.4.11", "4.4.0 -> 4.4.9", "4.2.22 -> 4.2.24", "4.2.18 -> 4.2.19", "4.2.5 -> 4.2.16", "4.2.1 -> 4.2.3", "4.0.14 -> 4.0.28", "3.6.17 -> 3.6.23", "3.4.24" })
	public void centos9(String version) {
		assertThat(linuxWith(CommonArchitecture.X86_64, CentosVersion.CentOS_9), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel80-{}.tgz");
		assertThat(linuxWith(CommonArchitecture.X86_64, RedhatVersion.Redhat_9), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel80-{}.tgz");
		assertThat(linuxWith(CommonArchitecture.X86_64, OracleVersion.Oracle_9), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel80-{}.tgz");
	}

	/*
		rhel90 x64
		https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-rhel90-{}.tgz
		"6.0.5 -> 6.0.6"
	 */
	@ParameterizedTest
	@ValueSource(strings = { "6.0.5 -> 6.0.6" })
	public void centos9new(String version) {
		assertThat(linuxWith(CommonArchitecture.X86_64, CentosVersion.CentOS_9), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel90-{}.tgz");
		assertThat(linuxWith(CommonArchitecture.X86_64, RedhatVersion.Redhat_9), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel90-{}.tgz");
		assertThat(linuxWith(CommonArchitecture.X86_64, OracleVersion.Oracle_9), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-rhel90-{}.tgz");
	}

	/*
		rhel90 x64 dev
		https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-rhel90-{}.tgz
		"7.0.0-rc2", "7.0.0-rc1", "6.3.1"
	 */
	@ParameterizedTest
	@ValueSource(strings = { "7.0.0-rc2", "7.0.0-rc1", "6.3.1" })
	public void centos9dev(String version) {
		assertThat(linuxWith(CommonArchitecture.X86_64, CentosVersion.CentOS_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel90-{}.tgz");
		assertThat(linuxWith(CommonArchitecture.X86_64, RedhatVersion.Redhat_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel90-{}.tgz");
		assertThat(linuxWith(CommonArchitecture.X86_64, OracleVersion.Oracle_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-x86_64-rhel90-{}.tgz");
	}

	/*
			RedHat / CentOS 8.2 ARM 64
			https://fastdl.mongodb.org/linux/mongodb-linux-aarch64-rhel82-{}.tgz
			"6.0.1 -> 6.0.6", "5.0.18", "5.0.12 -> 5.0.15", "5.0.5 -> 5.0.6", "5.0.0 -> 5.0.2", "4.4.22", "4.4.16 -> 4.4.19", "4.4.13", "4.4.11", "4.4.4 -> 4.4.9"
	*/
	@ParameterizedTest
	@ValueSource(strings = { "6.0.1 -> 6.0.6", "5.0.18", "5.0.12 -> 5.0.15", "5.0.5 -> 5.0.6", "5.0.0 -> 5.0.2", "4.4.22", "4.4.16 -> 4.4.19", "4.4.13", "4.4.11", "4.4.4 -> 4.4.9" })
	public void centos8arm(String version) {
		assertThat(linuxWith(CommonArchitecture.ARM_64, CentosVersion.CentOS_8), version)
			.resolvesTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
		assertThat(linuxWith(CommonArchitecture.ARM_64, RedhatVersion.Redhat_8), version)
			.resolvesTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
		assertThat(linuxWith(CommonArchitecture.ARM_64, OracleVersion.Oracle_8), version)
			.resolvesTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
	}

	/*
			RedHat / CentOS 8.2 ARM 64 dev
			https://fastdl.mongodb.org/linux/mongodb-linux-aarch64-rhel82-{}.tgz
			"7.0.0-rc2", "7.0.0-rc1", "6.3.1"
	*/
	@ParameterizedTest
	@ValueSource(strings = { "7.0.0-rc2", "7.0.0-rc1", "6.3.1" })
	public void centos8armDev(String version) {
		assertThat(linuxWith(CommonArchitecture.ARM_64, CentosVersion.CentOS_8), version)
			.resolveDevPackageTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
		assertThat(linuxWith(CommonArchitecture.ARM_64, RedhatVersion.Redhat_8), version)
			.resolveDevPackageTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
		assertThat(linuxWith(CommonArchitecture.ARM_64, OracleVersion.Oracle_8), version)
			.resolveDevPackageTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "6.0.1 -> 6.0.6", "5.0.18", "5.0.12 -> 5.0.15", "5.0.5 -> 5.0.6", "5.0.0 -> 5.0.2", "4.4.22", "4.4.16 -> 4.4.19", "4.4.13", "4.4.11", "4.4.4 -> 4.4.9" })
	public void centos9arm(String version) {
		assertThat(linuxWith(CommonArchitecture.ARM_64, CentosVersion.CentOS_9), version)
			.resolvesTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
		assertThat(linuxWith(CommonArchitecture.ARM_64, RedhatVersion.Redhat_9), version)
			.resolvesTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
		assertThat(linuxWith(CommonArchitecture.ARM_64, OracleVersion.Oracle_9), version)
			.resolvesTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
	}

	@ParameterizedTest
	@ValueSource(strings = { "7.0.0-rc2", "7.0.0-rc1", "6.3.1" })
	public void centos9armDev(String version) {
		assertThat(linuxWith(CommonArchitecture.ARM_64, CentosVersion.CentOS_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
		assertThat(linuxWith(CommonArchitecture.ARM_64, RedhatVersion.Redhat_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
		assertThat(linuxWith(CommonArchitecture.ARM_64, OracleVersion.Oracle_9), version)
			.resolveDevPackageTo("/linux/mongodb-linux-aarch64-rhel82-{}.tgz");
	}

	private static Platform linuxWith(CommonArchitecture architecture, de.flapdoodle.os.Version version) {
		return ImmutablePlatform.builder()
			.operatingSystem(CommonOS.Linux)
			.architecture(architecture)
			.version(version)
			.build();
	}

	private static HtmlParserResultTester assertThat(Platform platform, String versionList) {
		return HtmlParserResultTester.with(
			new CentosRedhatPackageResolver(Command.Mongo),
			version -> Distribution.of(Version.of(version), platform),
			versionList);
	}

}