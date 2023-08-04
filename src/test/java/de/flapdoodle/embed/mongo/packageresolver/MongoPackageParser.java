package de.flapdoodle.embed.mongo.packageresolver;

import de.flapdoodle.os.BitSize;
import de.flapdoodle.os.CPUType;
import de.flapdoodle.os.CommonOS;
import de.flapdoodle.os.Version;
import de.flapdoodle.os.linux.AmazonVersion;
import de.flapdoodle.os.linux.CentosVersion;
import de.flapdoodle.os.linux.DebianVersion;
import de.flapdoodle.os.linux.UbuntuVersion;
import de.flapdoodle.types.Either;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

public class MongoPackageParser {

	public static void main(String[] args) throws IOException {
		List<MongoPackages.ParsedVersion> versions = MongoPackages.dbVersionsList().stream()
			.flatMap(it -> it.stream())
			.collect(Collectors.toList());

		PlatformMatchCollector collector = new PlatformMatchCollector();

		versions.forEach(v -> {
			v.dists.forEach(d -> {
				Either<PlatformMatch, String> result = parseDist(d.name);
				
				if (result.isLeft()) {
					if (d.urls.size()!=1) {
						throw new IllegalArgumentException("more or less then one url: "+d.urls);
					}
					String url = d.urls.get(0).url.replace(v.version, "{}");
					collector.add(result.left(), v.version, v.isDevVersion, url);
				}
			});
		});

		collector.dump();
	}

	static class PlatformMatchCollector {
		private Map<PlatformMatch, VersionUrlMap> map=new LinkedHashMap<>();

		public void add(PlatformMatch platformMatch, String version, boolean isDevVersion, String normalizedUrl) {
			map.compute(platformMatch, (key,val) -> {
				VersionUrlMap versionMap = val != null ? val : new VersionUrlMap();
				versionMap.add(new Version(version, isDevVersion), normalizedUrl);
				return versionMap;
			});
		}

		public void dump() {
			map.forEach((platform, versions) -> {
				System.out.println("-----------------------------");
				System.out.println(platform);
				versions.map.forEach((url, list) -> {
					System.out.println("  "+url);
					String versionList = MongoPackages.rangesAsString(MongoPackages.compressedVersionsList(
						list.stream().filter(it -> !it.isDevVersion).map(it -> it.version).collect(Collectors.toList())));
					String devVersionList = MongoPackages.rangesAsString(MongoPackages.compressedVersionsList(
						list.stream().filter(it -> it.isDevVersion).map(it -> it.version).collect(Collectors.toList())));
					if (!versionList.isEmpty()) {
						System.out.println("    "+versionList);
					}
					if (!devVersionList.isEmpty()) {
						System.out.println("    (DEV) " + devVersionList);
					}
				});
			});
		}
	}

	static class VersionUrlMap {
		private Map<String, Set<Version>> map=new LinkedHashMap<>();

		public void add(Version version, String normalizedUrl) {
			map.compute(normalizedUrl, (key, val) -> {
				Set<Version> versions = val!=null ? val : new LinkedHashSet<>();
				versions.add(version);
				return versions;
			});
		}
	}

	static class Version {
		private final String version;
		private final boolean isDevVersion;

		public Version(String version, boolean isDevVersion) {
			this.version = version;
			this.isDevVersion = isDevVersion;
		}

		@Override
		public String toString() {
			return version + (isDevVersion ? " (DEV)" : "");
		}

		@Override
		public boolean equals(Object o) {
			if (this == o) return true;
			if (o == null || getClass() != o.getClass()) return false;
			Version version1 = (Version) o;
			return isDevVersion == version1.isDevVersion && Objects.equals(version, version1.version);
		}

		@Override
		public int hashCode() {
			return Objects.hash(version, isDevVersion);
		}
	}
//
//	static class Platform {
//
//		private final String name;
//		private final Optional<PlatformMatch> match;
//		private final boolean ignore;
//
//		public Platform(String name, Optional<PlatformMatch> match, boolean ignore) {
//			this.name = name;
//			this.match = match;
//			this.ignore = ignore;
//		}
//
//		public boolean hasMatch() {
//			return match.isPresent();
//		}
//
//		public boolean isIgnore() {
//			return ignore;
//		}
//		@Override
//		public String toString() {
//			return (match.isPresent() ? match.get().toString() : (name + (ignore ? "" : "!!!")));
//		}
//	}
//
//	static Version versionOf(MongoPackages.ParsedVersion src) {
//		return new Version(src.version, src.isDevVersion);
//	}
//
//	private static Platform distOf(MongoPackages.ParsedDist src) {
//		Optional<PlatformMatch> match = MongoPackageHtmlPageParser.asPlatformMatch(src.name);
//		boolean ignore = false;
//		if (!match.isPresent()) {
//			if (src.name.contains("SUSE")) {
//				ignore = true;
//			}
//		}
//		return new Platform(src.name, match, ignore);
//	}

	private static Either<PlatformMatch, String> parseDist(String name) {
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

	private static PlatformMatch platformOf(String name) {
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

	private static PlatformMatch parseDebian(String name) {
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

	private static PlatformMatch parseAmazon(String name) {
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

	private static PlatformMatch parseCentos(String name) {
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

	private static PlatformMatch parseUbuntu(String name) {
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

	private static PlatformMatch os(de.flapdoodle.os.OS os, CPUType cpu, BitSize bits) {
		return PlatformMatch.withOs(os)
			.withCpuType(cpu)
			.withBitSize(bits);
	}

	private static PlatformMatch linux(de.flapdoodle.os.Version version, CPUType cpu, BitSize bits) {
		return PlatformMatch.withOs(CommonOS.Linux)
			.withVersion(version)
			.withCpuType(cpu)
			.withBitSize(bits);
	}
}
