package de.flapdoodle.embed.mongo.packageresolver.parser;

import de.flapdoodle.os.CommonOS;
import de.flapdoodle.os.OS;
import de.flapdoodle.os.Version;
import org.immutables.value.Value;

import java.util.*;

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
		if (os()== CommonOS.Linux && !version().isPresent()) {
			classBaseName="LinuxLegacy";
		}
		if (version().isPresent()) {
			classBaseName=version().get().getSimpleName().replace("Version","");
		}
		return classBaseName+"PackageFinder";
	}

	@Value.Auxiliary
	public List<String> imports() {
		if (version().isPresent()) {
			return Arrays.asList(asImportClassName(version().get()));
		}
		return Collections.emptyList();
	}

	private static String asImportClassName(Class<?> clazz) {
		return clazz.getPackage().getName()+"."+clazz.getSimpleName();
	}

	public static PackageOsAndVersionType of(PackagePlatform platform) {
		return ImmutablePackageOsAndVersionType.builder()
			.os(platform.os())
			.version(platform.version().map(Version::getClass))
			.build();
	}
}
