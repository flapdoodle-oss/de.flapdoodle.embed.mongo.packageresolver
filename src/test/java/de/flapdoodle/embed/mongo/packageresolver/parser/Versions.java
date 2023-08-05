package de.flapdoodle.embed.mongo.packageresolver.parser;

import de.flapdoodle.os.Version;
import de.flapdoodle.os.VersionWithPriority;

import java.util.Comparator;
import java.util.Optional;

public abstract class Versions {
	public static Comparator<Optional<? extends Version>> versionByPrioOrdinalOrNameComparator() {
		return nullsFirst(
			Comparator.comparing((Version version) -> -(version instanceof VersionWithPriority ? ((VersionWithPriority) version).priority() : 0))
			.thenComparing(version -> -(version instanceof Enum ? ((Enum<?>) version).ordinal() : 0))
			.thenComparing(Version::name));
	}

	public static <T> Comparator<Optional<? extends T>> nullsFirst(Comparator<? super T> nonNullComparator) {
		return Comparator.comparing(opt -> opt.orElse(null), Comparator.nullsFirst(nonNullComparator));
	}
}
