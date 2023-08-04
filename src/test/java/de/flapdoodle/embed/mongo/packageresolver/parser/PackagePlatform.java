package de.flapdoodle.embed.mongo.packageresolver.parser;

import de.flapdoodle.os.*;
import org.immutables.value.Value;

import java.util.Comparator;
import java.util.Optional;

@Value.Immutable
public abstract class PackagePlatform {
	public abstract OS os();
	public abstract Optional<Version> version();
	public abstract CPUType cpuType();
	public abstract BitSize bitSize();

	public static ImmutablePackagePlatform.Builder builder() {
		return ImmutablePackagePlatform.builder();
	}

	public static ImmutablePackagePlatform of(OS os) {
		return builder().os(os).build();
	}

	public static Comparator<PackagePlatform> versionByOrdinalOrNameComparator() {
		return Comparator.comparing((PackagePlatform p) -> {
			Optional<Version> version = p.version();
			return version.map(v -> v instanceof VersionWithPriority ? ((VersionWithPriority) v).priority() : 0)
				.orElse(0);
		}).thenComparing((PackagePlatform p) -> {
			Optional<Version> version = p.version();
			return version.map(v -> v instanceof Enum ? ((Enum<?>) v).ordinal() : 0).orElse(0);
		}).thenComparing((PackagePlatform p) -> p.version().map(Version::name).orElse(""));
	}
}
