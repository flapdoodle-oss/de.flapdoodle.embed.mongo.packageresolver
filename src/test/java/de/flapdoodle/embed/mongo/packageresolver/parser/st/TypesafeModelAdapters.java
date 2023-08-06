package de.flapdoodle.embed.mongo.packageresolver.parser.st;

import de.flapdoodle.embed.mongo.packageresolver.MongoPackages;
import de.flapdoodle.embed.mongo.packageresolver.NumericVersion;
import de.flapdoodle.embed.mongo.packageresolver.VersionRange;
import de.flapdoodle.embed.mongo.packageresolver.parser.*;
import de.flapdoodle.types.Pair;
import org.stringtemplate.v4.STGroup;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

public class TypesafeModelAdapters {
	public static TypesafeModelAdapter<Optional> optionals() {
		return TypesafeModelAdapter.of(Optional.class, (o, name) -> {
			if (name.equals("is")) {
				return o.isPresent();
			}
			if (name.equals("get")) {
				return o.get();
			}
			throw new IllegalArgumentException("unknown property: '" + name + "' - use 'is' or 'get'");
		});
	}

	public static TypesafeModelAdapter<Enum> enums() {
		return TypesafeModelAdapter.of(Enum.class, (o, name) -> {
			if (name.equals("className")) {
				return o.getClass().getSimpleName();
			}
			if (name.equals("name")) {
				return o.name();
			}
			throw new IllegalArgumentException("unknown property: '" + name + "' - use 'className' or 'name'");
		});
	}

	public static TypesafeModelAdapter<Pair> pairs() {
		return TypesafeModelAdapter.of(Pair.class, (pair, name) -> {
			if (name.equals("first")) {
				return pair.first();
			}
			if (name.equals("second")) {
				return pair.second();
			}
			throw new IllegalArgumentException("unknown property: '" + name + "' - use 'first' or 'second'");
		});
	}

	public static TypesafeModelAdapter<UrlAndPackageVersions> urlAndPackageVersions() {
		return TypesafeModelAdapter.of(UrlAndPackageVersions.class, (it, name) -> {
			if (name.equals("index")) {
				return it.index();
			}
			if (name.equals("url")) {
				return it.url();
			}
			if (name.equals("versions")) {
				return it.versions();
			}
			if (name.equals("archiveType")) {
				return it.archiveType();
			}
			throw new IllegalArgumentException("unknown property: '" + name + "' - use 'first', 'second' or 'index'");
		});
	}

	public static TypesafeModelAdapter<PackageOsAndVersionType> packageOsAndVersionType() {
		return TypesafeModelAdapter.of(PackageOsAndVersionType.class, (osAndVersionType, name) -> {
			switch (name) {
				case "package":
					return osAndVersionType.packageName();
				case "name":
					return osAndVersionType.className();
			}
			throw new IllegalArgumentException("unknown property: '" + name + "'");
		});
	}

	public static TypesafeModelAdapter<PackagePlatform> packagePlatform() {
		return TypesafeModelAdapter.of(PackagePlatform.class, (it, name) -> {
			switch (name) {
				case "variableName":
					return (it.version().isPresent()
						? it.version().get().name()
						: it.os().name())
						+"_"+it.cpuType().name()
						+"_"+it.bitSize().name();
				case "bitSize":
					return it.bitSize();
				case "cpuType":
					return it.cpuType();
				case "versions":
					return it.versions();
			}
			throw new IllegalArgumentException("unknown property: '" + name + "'");
		});
	}

	public static TypesafeModelAdapter<UrlVersions> urlVersions() {
		return TypesafeModelAdapter.of(UrlVersions.class, (urlVersions, name) -> {
			switch (name) {
				case "entries": {
					List<Pair<String, PackageVersions>> src = urlVersions.entries();
					ArrayList<UrlAndPackageVersions> ret = new ArrayList<>();
					for (int i = 0; i < src.size(); i++) {
						Pair<String, PackageVersions> pair = src.get(i);
						ret.add(new UrlAndPackageVersions(i, pair.first(), pair.second()));
					}
					return ret;
				}
			}
			throw new IllegalArgumentException("unknown property: '" + name + "'");
		});
	}

	public static TypesafeModelAdapter<PackageVersions> packageVersions() {
		return TypesafeModelAdapter.of(PackageVersions.class, (versions, name) -> {
			switch (name) {
				case "versions":
					return versions.versionRanges(false);
				case "devVersions":
					return versions.versionRanges(true);
				case "hasDevVersions":
					return versions.hasDevVersions();
			}
			throw new IllegalArgumentException("unknown property: '" + name + "'");
		});
	}

	public static TypesafeModelAdapter<PackageVersion> packageVersion() {
		return TypesafeModelAdapter.of(PackageVersion.class, (version, name) -> {
			switch (name) {
				case "value":
					return version.version();
			}
			throw new IllegalArgumentException("unknown property: '" + name + "'");
		});
	}

	public static TypesafeModelAdapter<VersionRange> versionRange() {
		return TypesafeModelAdapter.of(VersionRange.class, (version, name) -> {
			switch (name) {
				case "min":
					return version.min();
				case "max":
					return version.max();
				case "isExact":
					return version.isExact();
			}
			throw new IllegalArgumentException("unknown property: '" + name + "'");
		});
	}

	public static TypesafeModelAdapter<PackagePlatformUrlVersions> packagePlatformUrlVersions() {
		return TypesafeModelAdapter.of(PackagePlatformUrlVersions.class, (packagePlatformUrlVersions, name) -> {
			switch (name) {
				case "entries":
					return packagePlatformUrlVersions.entries();
			}
			throw new IllegalArgumentException("unknown property: '" + name + "'");
		});
	}

	public static void registerDefaults(STGroup group) {
		Arrays.asList(
				optionals(),
				pairs(),
				urlAndPackageVersions(),
				enums(),
				packageOsAndVersionType(),
				packagePlatform(),
				packagePlatformUrlVersions(),
				urlVersions(),
				packageVersions(),
				packageVersion(),
				versionRange()
			)
			.forEach(it -> it.register(group));

		group.registerRenderer(NumericVersion.class, new TypedAttributeRenderer<>(NumericVersion.class, (it, format, locale) -> MongoPackages.asString(it)));
	}
}
