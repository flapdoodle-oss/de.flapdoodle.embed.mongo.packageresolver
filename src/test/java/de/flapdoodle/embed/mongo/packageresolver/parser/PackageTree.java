package de.flapdoodle.embed.mongo.packageresolver.parser;

import com.google.common.collect.Maps;
import org.immutables.value.Value;

import java.util.Optional;
import java.util.SortedMap;
import java.util.SortedSet;

@Value.Immutable
public abstract class PackageTree {
	@Value.NaturalOrder
	public abstract SortedMap<PackageOsAndVersionType, PackagePlatformUrlVersions> map();
	@Value.NaturalOrder
	public abstract SortedSet<String> skipped();
	@Value.NaturalOrder
	public abstract SortedSet<String> skippedTools();

	@Value.Auxiliary
	public PackageTree add(PackagePlatform platform, String version, boolean devVersion, String normalizedUrl) {
		PackageOsAndVersionType osAndVersionType = PackageOsAndVersionType.of(platform);

		PackagePlatformUrlVersions current =  Optional.ofNullable(map().get(osAndVersionType))
			.orElseGet(() -> PackagePlatformUrlVersions.of(osAndVersionType))
			.add(platform, PackageVersion.of(version, devVersion), normalizedUrl);

		return ImmutablePackageTree.builder()
			.putAllMap(Maps.filterEntries(map(), it -> !it.getKey().equals(osAndVersionType)))
			.putMap(osAndVersionType, current)
			.addAllSkipped(skipped())
			.build();
	}

	@Value.Auxiliary
	public PackageTree addTools(PackagePlatform platform, String version, String normalizedUrl) {
		PackageOsAndVersionType osAndVersionType = PackageOsAndVersionType.of(platform);

		PackagePlatformUrlVersions current =  Optional.ofNullable(map().get(osAndVersionType))
			.orElseGet(() -> PackagePlatformUrlVersions.of(osAndVersionType))
			.addTools(platform, version, normalizedUrl);

		return ImmutablePackageTree.builder()
			.putAllMap(Maps.filterEntries(map(), it -> !it.getKey().equals(osAndVersionType)))
			.putMap(osAndVersionType, current)
			.addAllSkipped(skipped())
			.build();
	}

	public PackageTree skip(String name) {
		return ImmutablePackageTree.builder()
			.from(this)
			.addSkipped(name)
			.build();
	}

	public PackageTree skipTools(String name) {
		return ImmutablePackageTree.builder()
			.from(this)
			.addSkippedTools(name)
			.build();
	}

	@Value.Auxiliary
	public void dump() {
		map().forEach((osAndVersionType, packagePlatformUrlVersions) -> {
			System.out.println(osAndVersionType);
			packagePlatformUrlVersions.dump();
		});

		System.out.println("skipped: "+skipped());
	}

	public static ImmutablePackageTree empty() {
		return ImmutablePackageTree.builder().build();
	}
}
