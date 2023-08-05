package de.flapdoodle.embed.mongo.packageresolver.parser;

import com.google.common.collect.Multimap;
import de.flapdoodle.embed.mongo.packageresolver.MongoPackages;
import org.immutables.value.Value;

import java.util.stream.Collectors;

@Value.Immutable
public abstract class UrlVersions {
	public abstract Multimap<String, PackageVersion> map();

	@Value.Auxiliary
	public ImmutableUrlVersions put(String url, PackageVersion version) {
		return ImmutableUrlVersions.builder()
			.from(this)
			.putMap(url, version)
			.build();
	}

	@Value.Auxiliary
	public void dump() {
		map().asMap().forEach((url, versions) -> {
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

	public static ImmutableUrlVersions empty() {
		return ImmutableUrlVersions.builder().build();
	}
}
