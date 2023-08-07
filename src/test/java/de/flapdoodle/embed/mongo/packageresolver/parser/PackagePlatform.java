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
package de.flapdoodle.embed.mongo.packageresolver.parser;

import de.flapdoodle.embed.mongo.packageresolver.NumericVersion;
import de.flapdoodle.os.*;
import de.flapdoodle.os.linux.*;
import de.flapdoodle.types.Either;
import org.immutables.value.Value;

import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Value.Immutable
public abstract class PackagePlatform implements Comparable<PackagePlatform> {
	private static Comparator<PackagePlatform> COMPARATOR=Comparator.comparing(PackagePlatform::os, Comparator.comparing(OS::name))
		.thenComparing(PackagePlatform::version, Versions.versionByPrioOrdinalOrNameComparator())
		.thenComparing(Comparator.comparing(PackagePlatform::cpuType).reversed())
		.thenComparing(Comparator.comparing(PackagePlatform::bitSize).reversed());

	public abstract OS os();
	public abstract Optional<Version> version();
	public abstract CPUType cpuType();
	public abstract BitSize bitSize();

	public static ImmutablePackagePlatform.Builder builder() {
		return ImmutablePackagePlatform.builder();
	}

	public static ImmutablePackagePlatform with(OS os) {
		return builder().os(os).build();
	}

	@Value.Auxiliary
	public List<Version> versions() {
		if (version().isPresent()) {
			return upgradeableVersions(version().get());
		}
		return Collections.emptyList();
	}

	@Override
	public int compareTo(PackagePlatform other) {
		return COMPARATOR.compare(this, other);
	}

	private static List<Version> upgradeableVersions(Version version) {
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

//	private static boolean hasLibCrypt1_1(UbuntuVersion version) {
//		switch (version) {
//			case Ubuntu_23_04:
//			case Ubuntu_23_10:
//				return false;
//		}
//		return true;
//	}

	private static List<Version> upgradeableUbuntuVersions(UbuntuVersion version) {
		List<UbuntuVersion> all = Arrays.asList(UbuntuVersion.values());

		return all.stream()
			.filter(it -> it.ordinal()>=version.ordinal() /*&& (hasLibCrypt1_1(version) == hasLibCrypt1_1(it))*/)
			.collect(Collectors.toList());
	}

	public static NumericVersion firstMongoDbVersionWithoutBundledTools() {
		return NumericVersion.of(4,4,0);
	}

	public static Either<PackagePlatform, String> parse(String name) {
		switch (name) {
			case "Ubuntu 12.04 x64":
			case "Ubuntu 14.04 x64":
			case "ubuntu1410-clang x64":
			case "Ubuntu 18.04 s390x":
			// Tools
			case "Ubuntu 16.04 ppc64le":
			case "Ubuntu 18.04 ppc64le":
			case "Ubuntu 16.04 s390x":

			case "SUSE 11 x64":
			case "SUSE 12 x64":
			case "SUSE 12 s390x":
			case "SUSE 15 x64":

			case "RedHat / CentOS 5.5 x64":
			case "RedHat / CentOS 6.7 s390x":
			case "RedHat / CentOS 7.2 s390x":
			// Tools
			case "RedHat / CentOS 8.3 s390x":
				
			// Tools
			case "RedHat / CentOS 7.1 ppc64le":
			case "RedHat / CentOS 8.1 ppc64le":

			case "Debian 7.1 x64":
			case "Debian 8.1 x64":

			// Tools
			case "x64":
				return Either.right(name);
			default:
				return Either.left(platformOf(name));
		}
	}

	private static PackagePlatform platformOf(String name) {
		if (name.startsWith("Ubuntu") || name.startsWith("ubuntu")) {
			return parseUbuntu(name);
		}
		if (name.startsWith("RedHat") || name.startsWith("rhel")) {
			return parseCentos(name);
		}
		if (name.startsWith("Debian")) {
			return parseDebian(name);
		}
		if (name.startsWith("Amazon") || name.startsWith("amazon")) {
			return parseAmazon(name);
		}

		switch (name) {
			case "Windows x64":
			case "windows_x86_64 x64":
			case "Windows Server 2008 R2+, without SSL x64":
				return with(CommonOS.Windows, CPUType.X86, BitSize.B64);
			case "windows_i686 undefined":
				return with(CommonOS.Windows, CPUType.X86, BitSize.B32);
			case "macOS ARM 64":
				return with(CommonOS.OS_X, CPUType.ARM, BitSize.B64);
			case "macOS x64":
				return with(CommonOS.OS_X, CPUType.X86, BitSize.B64);
			case "sunos x64":
			case "sunos5 x64":
				return with(CommonOS.Solaris, CPUType.X86, BitSize.B64);
			case "Linux (legacy) x64":
				return with(CommonOS.Linux, CPUType.X86, BitSize.B64);
			case "Linux (legacy) undefined":
				return with(CommonOS.Linux, CPUType.X86, BitSize.B32);
			default:
				throw new IllegalArgumentException("could not parse "+ name);
		}
	}

	private static PackagePlatform parseDebian(String name) {
		switch (name) {
			case "Debian 9.2 x64":
				return linux(DebianVersion.DEBIAN_9, CPUType.X86, BitSize.B64);
			case "Debian 10.0 x64":
				return linux(DebianVersion.DEBIAN_10, CPUType.X86, BitSize.B64);
			case "Debian 11.0 x64":
				return linux(DebianVersion.DEBIAN_11, CPUType.X86, BitSize.B64);
			default:
				throw new IllegalArgumentException("could not parse "+name);
		}
	}

	private static PackagePlatform parseAmazon(String name) {
		switch (name) {
			case "Amazon Linux x64":
				return linux(AmazonVersion.AmazonLinux, CPUType.X86, BitSize.B64);
			case "Amazon Linux 2 x64":
				return linux(AmazonVersion.AmazonLinux2, CPUType.X86, BitSize.B64);
			case "Amazon Linux 2 ARM 64":
				return linux(AmazonVersion.AmazonLinux2, CPUType.ARM, BitSize.B64);
			case "Amazon Linux 2023 x64":
			case "amazon2023 x64":
				return linux(AmazonVersion.AmazonLinux2023, CPUType.X86, BitSize.B64);
			case "Amazon Linux 2023 ARM 64":
			case "amazon2023 ARM 64":
				return linux(AmazonVersion.AmazonLinux2023, CPUType.ARM, BitSize.B64);
			default:
				throw new IllegalArgumentException("could not parse "+name);
		}
	}

	private static PackagePlatform parseCentos(String name) {
		switch (name) {
			case "RedHat / CentOS 6.2+ x64":
				return linux(CentosVersion.CentOS_6, CPUType.X86, BitSize.B64);
			case "RedHat / CentOS 7.0 x64":
				return linux(CentosVersion.CentOS_7, CPUType.X86, BitSize.B64);
			case "RedHat / CentOS 8.0 x64":
				return linux(CentosVersion.CentOS_8, CPUType.X86, BitSize.B64);
			case "RedHat / CentOS 8.2 ARM 64":
				return linux(CentosVersion.CentOS_8, CPUType.ARM, BitSize.B64);
			case "RedHat / CentOS 9.0 x64":
			case "rhel90 x64":
				return linux(CentosVersion.CentOS_9, CPUType.X86, BitSize.B64);
			case "RedHat / CentOS 9.0 ARM 64":
				return linux(CentosVersion.CentOS_9, CPUType.ARM, BitSize.B64);
			default:
				throw new IllegalArgumentException("could not parse "+name);
		}
	}

	private static PackagePlatform parseUbuntu(String name) {
		switch (name) {
			case "Ubuntu 16.04 x64":
				return linux(UbuntuVersion.Ubuntu_16_04, CPUType.X86, BitSize.B64);
			case "Ubuntu 16.04 ARM 64":
				return linux(UbuntuVersion.Ubuntu_16_04, CPUType.ARM, BitSize.B64);
			case "Ubuntu 18.04 x64":
				return linux(UbuntuVersion.Ubuntu_18_04, CPUType.X86, BitSize.B64);
			case "Ubuntu 18.04 ARM 64":
				return linux(UbuntuVersion.Ubuntu_18_04, CPUType.ARM, BitSize.B64);
			case "Ubuntu 20.04 x64":
				return linux(UbuntuVersion.Ubuntu_20_04, CPUType.X86, BitSize.B64);
			case "Ubuntu 20.04 ARM 64":
				return linux(UbuntuVersion.Ubuntu_20_04, CPUType.ARM, BitSize.B64);
			case "Ubuntu 22.04 x64":
				return linux(UbuntuVersion.Ubuntu_22_04, CPUType.X86, BitSize.B64);
			case "Ubuntu 22.04 ARM 64":
				return linux(UbuntuVersion.Ubuntu_22_04, CPUType.ARM, BitSize.B64);
			default:
				throw new IllegalArgumentException("could not parse "+name);
		}
	}

	private static PackagePlatform with(de.flapdoodle.os.OS os, CPUType cpu, BitSize bits) {
		return PackagePlatform.builder()
			.os(os)
			.cpuType(cpu)
			.bitSize(bits)
			.build();
	}

	private static PackagePlatform linux(de.flapdoodle.os.Version version, CPUType cpu, BitSize bits) {
		return PackagePlatform.builder()
			.os(CommonOS.Linux)
			.version(version)
			.cpuType(cpu)
			.bitSize(bits)
			.build();
	}
}
