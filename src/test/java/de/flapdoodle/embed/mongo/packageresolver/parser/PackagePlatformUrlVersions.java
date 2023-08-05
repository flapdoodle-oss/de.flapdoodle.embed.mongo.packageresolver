package de.flapdoodle.embed.mongo.packageresolver.parser;

import com.google.common.collect.Maps;
import org.immutables.value.Value;

import java.util.Comparator;
import java.util.Map;
import java.util.Optional;
import java.util.SortedMap;

@Value.Immutable
public abstract class PackagePlatformUrlVersions {
	@Value.Parameter
	public abstract PackageOsAndVersionType osAndVersionType();
	@Value.NaturalOrder
	public abstract SortedMap<PackagePlatform, UrlVersions> map();

	@Value.Auxiliary
	public PackagePlatformUrlVersions add(PackagePlatform platform, PackageVersion version, String normalizedUrl) {
		UrlVersions current = Optional.ofNullable(map().get(platform))
			.orElseGet(UrlVersions::empty)
			.put(normalizedUrl, version);

		return ImmutablePackagePlatformUrlVersions.builder()
			.osAndVersionType(osAndVersionType())
			.putMap(platform, current)
			.putAllMap(Maps.filterEntries(map(), it -> !it.getKey().equals(platform)))
			.build();
	}

	@Value.Auxiliary
	public void dump() {
		map().forEach((platform, urlVersions) -> {
			System.out.println("  "+platform);
			urlVersions.dump();
		});
	}
	
	public static ImmutablePackagePlatformUrlVersions of(PackageOsAndVersionType osAndVersionType) {
		return ImmutablePackagePlatformUrlVersions.of(osAndVersionType);
	}
}
