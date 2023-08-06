package de.flapdoodle.embed.mongo.packageresolver.parser;

import de.flapdoodle.os.*;
import de.flapdoodle.os.linux.AmazonVersion;
import de.flapdoodle.os.linux.CentosVersion;
import de.flapdoodle.os.linux.DebianVersion;
import de.flapdoodle.os.linux.UbuntuVersion;
import de.flapdoodle.types.Either;
import org.checkerframework.checker.units.qual.A;
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
		return Collections.singletonList(version);
	}

	private static boolean hasLibCrypt1_1(UbuntuVersion version) {
		switch (version) {
			case Ubuntu_23_04:
			case Ubuntu_23_10:
			case Ubuntu_22_04:
			case Ubuntu_22_10:
				return false;
		}
		return true;
	}

	private static List<Version> upgradeableUbuntuVersions(UbuntuVersion version) {
		List<UbuntuVersion> all = Arrays.asList(UbuntuVersion.values());

		return all.stream()
			.filter(it -> it.ordinal()>=version.ordinal() && (hasLibCrypt1_1(version) == hasLibCrypt1_1(it)))
			.collect(Collectors.toList());
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
			case "RedHat / CentOS 6.2+ x64":
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
