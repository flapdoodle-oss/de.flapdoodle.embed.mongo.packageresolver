package de.flapdoodle.embed.mongo.packageresolver.parser;

import org.immutables.value.Value;

@Value.Immutable
public abstract class PackageVersion {
	public abstract String version();
	
	@Value.Default
	public boolean devVersion() {
		return false;
	}

	public static PackageVersion of(String version, boolean isDevVersion) {
		return ImmutablePackageVersion.builder()
			.version(version)
			.devVersion(isDevVersion)
			.build();
	}
}
