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
package de.flapdoodle.embed.mongo.packageresolver.parser;

import de.flapdoodle.os.CPUType;
import de.flapdoodle.os.CommonOS;
import de.flapdoodle.os.OS;
import de.flapdoodle.os.Version;
import de.flapdoodle.os.linux.*;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public abstract class HiddenRules {
	static List<Version> upgradeableVersions(Version version) {
		if (version instanceof UbuntuVersion) {
			return upgradeableUbuntuVersions((UbuntuVersion) version);
		}
		if (version instanceof DebianVersion) {
			return upgradeableDebianVersions((DebianVersion) version);
		}
		if (version instanceof CentosVersion) {
			return upgradeableCentosVersions((CentosVersion) version);
		}

		return Collections.singletonList(version);
	}
	private static List<Version> upgradeableCentosVersions(CentosVersion version) {
		List<CentosVersion> all = Arrays.asList(CentosVersion.values());

		return all.stream()
			.filter(it -> it.ordinal()>=version.ordinal())
			.flatMap(it -> {
				switch (it) {
					case CentOS_6:
						return Stream.of(CentosVersion.CentOS_6, RedhatVersion.Redhat_6, OracleVersion.Oracle_6);
					case CentOS_7:
						return Stream.of(CentosVersion.CentOS_7, RedhatVersion.Redhat_7, OracleVersion.Oracle_7);
					case CentOS_8:
						return Stream.of(CentosVersion.CentOS_8, RedhatVersion.Redhat_8, OracleVersion.Oracle_8);
					case CentOS_9:
						return Stream.of(CentosVersion.CentOS_9, RedhatVersion.Redhat_9, OracleVersion.Oracle_9, FedoraVersion.Fedora_38);
				}
				return Stream.of(it);
			})
			.collect(Collectors.toList());
	}
	private static List<Version> upgradeableDebianVersions(DebianVersion version) {
		List<DebianVersion> all = Arrays.asList(DebianVersion.values());

		return all.stream()
			.filter(it -> it.ordinal()>=version.ordinal())
			.collect(Collectors.toList());
	}
	private static List<Version> upgradeableUbuntuVersions(UbuntuVersion version) {
		List<UbuntuVersion> all = Arrays.asList(UbuntuVersion.values());

		return all.stream()
			.filter(it -> it.ordinal()>=version.ordinal() /*&& (hasLibCrypt1_1(version) == hasLibCrypt1_1(it))*/)
			.collect(Collectors.toList());
	}

	public static boolean ignoreCPUType(OS os, CPUType cpuType) {
		return os == CommonOS.OS_X && cpuType == CPUType.X86;
	}
}
