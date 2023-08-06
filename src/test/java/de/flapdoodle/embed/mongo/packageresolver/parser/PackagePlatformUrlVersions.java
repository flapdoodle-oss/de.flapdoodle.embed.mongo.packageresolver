package de.flapdoodle.embed.mongo.packageresolver.parser;

import com.google.common.collect.Maps;
import de.flapdoodle.types.Pair;
import org.immutables.value.Value;

import java.util.*;
import java.util.stream.Collectors;

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
	public PackagePlatformUrlVersions addTools(PackagePlatform platform, String version, String normalizedUrl) {
		UrlVersions current = Optional.ofNullable(map().get(platform))
			.orElseGet(UrlVersions::empty)
			.putTools(normalizedUrl, version);

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

	public List<Pair<PackagePlatform, UrlVersions>> entries() {
		return map().entrySet().stream()
			.map(it -> Pair.of(it.getKey(), it.getValue()))
			.collect(Collectors.toList());
	}

	public static ImmutablePackagePlatformUrlVersions of(PackageOsAndVersionType osAndVersionType) {
		return ImmutablePackagePlatformUrlVersions.of(osAndVersionType);
	}
}
