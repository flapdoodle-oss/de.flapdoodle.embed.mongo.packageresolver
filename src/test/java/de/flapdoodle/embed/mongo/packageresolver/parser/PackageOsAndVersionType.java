package de.flapdoodle.embed.mongo.packageresolver.parser;

import de.flapdoodle.os.OS;
import de.flapdoodle.os.Version;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
public abstract class PackageOsAndVersionType {
	public abstract OS os();
	public abstract Optional<Class<? extends Version>> version();

	public static PackageOsAndVersionType of(PackagePlatform platform) {
		return ImmutablePackageOsAndVersionType.builder()
			.os(platform.os())
			.version(platform.version().map(Version::getClass))
			.build();
	}
}
