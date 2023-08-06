package de.flapdoodle.embed.mongo.packageresolver.parser;

import de.flapdoodle.embed.mongo.packageresolver.MongoPackages;
import de.flapdoodle.embed.mongo.packageresolver.VersionRange;
import org.immutables.value.Value;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Value.Immutable
public abstract class PackageVersions {
	public abstract List<PackageVersion> list();

	@Value.Derived
	public boolean hasDevVersions() {
		return list().stream().anyMatch(PackageVersion::devVersion);
	}

	@Value.Auxiliary
	public List<VersionRange> versionRanges(boolean devVersion) {
//		String versionList = MongoPackages.rangesAsString(ranges);
		return MongoPackages.compressedVersionsList(
			list()
				.stream().filter(it -> it.devVersion()==devVersion)
				.map(PackageVersion::version)
				.collect(Collectors.toList()))
			.stream()
			.sorted(Comparator.comparing(VersionRange::min).reversed())
			.collect(Collectors.toList());
	}

	public static PackageVersions of(Iterable<? extends PackageVersion> list) {
		return ImmutablePackageVersions.builder()
			.addAllList(list)
			.build();
	}
}
