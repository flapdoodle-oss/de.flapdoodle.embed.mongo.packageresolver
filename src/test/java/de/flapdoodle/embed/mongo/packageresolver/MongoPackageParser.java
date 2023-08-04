package de.flapdoodle.embed.mongo.packageresolver;

import de.flapdoodle.embed.mongo.packageresolver.parser.PackageCollector;
import de.flapdoodle.embed.mongo.packageresolver.parser.PackagePlatform;
import de.flapdoodle.os.BitSize;
import de.flapdoodle.os.CPUType;
import de.flapdoodle.os.CommonOS;
import de.flapdoodle.os.linux.AmazonVersion;
import de.flapdoodle.os.linux.CentosVersion;
import de.flapdoodle.os.linux.DebianVersion;
import de.flapdoodle.os.linux.UbuntuVersion;
import de.flapdoodle.types.Either;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MongoPackageParser {

	public static void main(String[] args) throws IOException {
		List<MongoPackages.ParsedVersion> versions = MongoPackages.dbVersionsList().stream()
			.flatMap(Collection::stream)
			.collect(Collectors.toList());

		PackageCollector collector=new PackageCollector();

		versions.forEach(v -> v.dists.forEach(d -> {
			Either<PackagePlatform, String> result = parseDist(d.name);
			if (result.isLeft()) {
				String url = d.singleUrl().replace(v.version, "{}");
				collector.add(result.left(), v.version, v.isDevVersion, url);
			} else {
				collector.skip(result.right());
			}
		}));

//		collector.dump();
//		System.out.println("Skipped: "+skipped);
		collector.dump();
	}

	private static Either<PackagePlatform, String> parseDist(String name) {
		switch (name) {
			case "Ubuntu 12.04 x64":
			case "Ubuntu 14.04 x64":
			case "ubuntu1410-clang x64":
			case "Ubuntu 18.04 s390x":

			case "SUSE 11 x64":
			case "SUSE 12 x64":
			case "SUSE 12 s390x":
			case "SUSE 15 x64":

			case "RedHat / CentOS 5.5 x64":
			case "RedHat / CentOS 6.2+ x64":
			case "RedHat / CentOS 6.7 s390x":
			case "RedHat / CentOS 7.2 s390x":

			case "Debian 7.1 x64":
			case "Debian 8.1 x64":
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
				return os(CommonOS.Windows, CPUType.X86, BitSize.B64);
			case "windows_i686 undefined":
				return os(CommonOS.Windows, CPUType.X86, BitSize.B32);
			case "macOS ARM 64":
				return os(CommonOS.OS_X, CPUType.ARM, BitSize.B64);
			case "macOS x64":
				return os(CommonOS.OS_X, CPUType.X86, BitSize.B64);
			case "sunos x64":
			case "sunos5 x64":
				return os(CommonOS.Solaris, CPUType.X86, BitSize.B64);
			case "Linux (legacy) x64":
				return os(CommonOS.Linux, CPUType.X86, BitSize.B64);
			case "Linux (legacy) undefined":
				return os(CommonOS.Linux, CPUType.X86, BitSize.B32);
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

	private static PackagePlatform os(de.flapdoodle.os.OS os, CPUType cpu, BitSize bits) {
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
