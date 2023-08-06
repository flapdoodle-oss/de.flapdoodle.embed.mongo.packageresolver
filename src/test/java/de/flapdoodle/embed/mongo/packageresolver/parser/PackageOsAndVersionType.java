package de.flapdoodle.embed.mongo.packageresolver.parser;

import de.flapdoodle.os.OS;
import de.flapdoodle.os.Version;
import org.immutables.value.Value;

import java.util.Comparator;
import java.util.Optional;

@Value.Immutable
public abstract class PackageOsAndVersionType implements Comparable<PackageOsAndVersionType> {
	private static Comparator<PackageOsAndVersionType> COMPARATOR=Comparator.comparing(PackageOsAndVersionType::os, Comparator.comparing(OS::name))
		.thenComparing(PackageOsAndVersionType::version, Versions.nullsFirst(Comparator.comparing(Class::getSimpleName)));

	public abstract OS os();
	public abstract Optional<Class<? extends Version>> version();

	@Value.Lazy
	protected Class<? extends Version> versionOrNull() {
		return version().orElse(null);
	}

	@Override
	@Value.Auxiliary
	public int compareTo(PackageOsAndVersionType other) {
		return COMPARATOR.compare(this, other);
	}

	@Value.Auxiliary
	public Optional<String> packageName() {
		String osName = os().name();
		Optional<String> packageName= Optional.empty();
		if (!os().distributions().isEmpty()) {
			packageName=Optional.of(osName.toLowerCase());
		}
		return packageName;
	};

	@Value.Auxiliary
	public String className() {
		String osName = os().name();
		String classBaseName = osName.replace("_","");
		if (version().isPresent()) {
			classBaseName=version().get().getSimpleName().replace("Version","");
		}
		return classBaseName+"PackageFinder";
	}

	public static PackageOsAndVersionType of(PackagePlatform platform) {
		return ImmutablePackageOsAndVersionType.builder()
			.os(platform.os())
			.version(platform.version().map(Version::getClass))
			.build();
	}
}
