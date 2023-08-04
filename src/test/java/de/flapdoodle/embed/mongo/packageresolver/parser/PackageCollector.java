package de.flapdoodle.embed.mongo.packageresolver.parser;

import de.flapdoodle.embed.mongo.packageresolver.MongoPackages;
import de.flapdoodle.os.OS;

import java.util.*;
import java.util.stream.Collectors;

public class PackageCollector {

	private static final Comparator<Map.Entry<PackageOsAndVersionType, PlatformCollector>> COMPARATOR = Map.Entry.comparingByKey(
		Comparator.comparing(PackageOsAndVersionType::os, Comparator.comparing(OS::name)));

	private final Map<PackageOsAndVersionType, PlatformCollector> map=new LinkedHashMap<>();
	private final Set<String> skipped=new TreeSet<>();

	public PackageCollector add(PackagePlatform platform, String version, boolean devVersion, String normalizedUrl) {
		PackageOsAndVersionType osAndVersionType = PackageOsAndVersionType.of(platform);

		map.compute(osAndVersionType, (key,val) -> (val != null ? val : new PlatformCollector(key))
			.add(platform, PackageVersion.of(version, devVersion), normalizedUrl));
		
		return this;
	}

	public void skip(String name) {
		skipped.add(name);
	}

	public void dump() {
		map.entrySet()
			.stream()
			.sorted(COMPARATOR)
			.forEachOrdered(entry -> {
				System.out.println(entry.getKey());
				entry.getValue().dump();
			});
	}

	static class PlatformCollector {
		private static final Comparator<Map.Entry<PackagePlatform, UrlVersionCollector>> COMPARATOR = Map.Entry.comparingByKey(
			PackagePlatform.versionByOrdinalOrNameComparator()
				.thenComparing(PackagePlatform::cpuType)
				.thenComparing(PackagePlatform::bitSize).reversed());

		private final PackageOsAndVersionType osAndVersionType;
		private final Map<PackagePlatform, UrlVersionCollector> map=new LinkedHashMap<>();

		public PlatformCollector(PackageOsAndVersionType osAndVersionType) {
			this.osAndVersionType = osAndVersionType;
		}
		
		public PlatformCollector add(PackagePlatform platform, PackageVersion version, String normalizedUrl) {
			if (!PackageOsAndVersionType.of(platform).equals(osAndVersionType)) {
				throw new IllegalArgumentException("something went wrong");
			}

//			System.out.println(platform);
			map.compute(platform, (key,val) -> (val != null ? val : new UrlVersionCollector())
				.add(version, normalizedUrl));

			return this;
		}

		public void dump() {
			map.entrySet().stream()
					.sorted(COMPARATOR)
						.forEachOrdered(entry ->  {
				System.out.println("  "+entry.getKey());
				entry.getValue().dump();
			});
		}
	}

	static class UrlVersionCollector {
		private Map<String, Set<PackageVersion>> map=new LinkedHashMap<>();

		public UrlVersionCollector add(PackageVersion version, String normalizedUrl) {
			map.compute(normalizedUrl, (key, val) -> {
				Set<PackageVersion> versions = val!=null ? val : new LinkedHashSet<>();
				versions.add(version);
				return versions;
			});
			
			return this;
		}
		public void dump() {
			map.forEach((url, versions) -> {
				System.out.println("    "+url);

				String versionList = MongoPackages.rangesAsString(MongoPackages.compressedVersionsList(
					versions.stream().filter(it -> !it.devVersion()).map(PackageVersion::version).collect(Collectors.toList())));
				String devVersionList = MongoPackages.rangesAsString(MongoPackages.compressedVersionsList(
					versions.stream().filter(it -> it.devVersion()).map(PackageVersion::version).collect(Collectors.toList())));
				if (!versionList.isEmpty()) {
					System.out.println("      "+versionList);
				}
				if (!devVersionList.isEmpty()) {
					System.out.println("      (DEV) " + devVersionList);
				}

			});
		}
	}
}
