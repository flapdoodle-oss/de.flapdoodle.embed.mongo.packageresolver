package de.flapdoodle.embed.mongo.packageresolver.parser.st;

import de.flapdoodle.embed.mongo.packageresolver.ImmutableToolVersionRange;
import de.flapdoodle.embed.mongo.packageresolver.MongoPackages;
import de.flapdoodle.embed.mongo.packageresolver.VersionRange;
import de.flapdoodle.embed.mongo.packageresolver.parser.ImmutablePackageVersions;
import de.flapdoodle.embed.mongo.packageresolver.parser.PackageVersion;
import org.immutables.value.Value;

import java.util.Comparator;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Value.Immutable
public abstract class ToolsVersions {
	public abstract Set<String> list();

	@Value.Derived
	public boolean hasVersions() {
		return !list().isEmpty();
	}

	@Value.Auxiliary
	public List<VersionRange> versionRanges() {
//		String versionList = MongoPackages.rangesAsString(ranges);
		return MongoPackages.compressedVersionsList(list())
			.stream()
			.sorted(Comparator.comparing(VersionRange::min).reversed())
			.collect(Collectors.toList());
	}

	public static ToolsVersions of(Iterable<String> list) {
		return ImmutableToolsVersions.builder()
			.addAllList(list)
			.build();
	}
}
