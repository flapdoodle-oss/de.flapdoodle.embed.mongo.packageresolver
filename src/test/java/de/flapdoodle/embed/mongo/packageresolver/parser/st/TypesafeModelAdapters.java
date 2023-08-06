/**
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Martin Jöhren <m.joehren@googlemail.com>
 *
 * with contributions from
 * 	konstantin-ba@github,Archimedes Trajano	(trajano@github)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.flapdoodle.embed.mongo.packageresolver.parser.st;

import de.flapdoodle.embed.mongo.packageresolver.MongoPackages;
import de.flapdoodle.embed.mongo.packageresolver.NumericVersion;
import de.flapdoodle.embed.mongo.packageresolver.VersionRange;
import de.flapdoodle.embed.mongo.packageresolver.parser.*;
import de.flapdoodle.os.CommonOS;
import de.flapdoodle.types.Pair;
import org.stringtemplate.v4.STGroup;

import java.util.*;
import java.util.stream.Collectors;

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

	public static TypesafeModelAdapter<UrlAndToolsVersions> urlAndToolsVersions() {
		return TypesafeModelAdapter.of(UrlAndToolsVersions.class, (it, name) -> {
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
				case "osName":
					return osAndVersionType.os().name();
				case "executableExtension":
					return osAndVersionType.os() == CommonOS.Windows ? ".exe" : null;
				case "imports":
					return osAndVersionType.imports();
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
				case "os":
					return it.os().name();
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
				case "toolEntries": {
					List<Pair<String, Collection<String>>> src = urlVersions.toolEntries();
					ArrayList<UrlAndToolsVersions> ret = new ArrayList<>();
					for (int i = 0; i < src.size(); i++) {
						Pair<String, Collection<String>> pair = src.get(i);
						ret.add(new UrlAndToolsVersions(i, pair.first(), ToolsVersions.of(pair.second())));
					}
					return ret;
				}
				case "toolsBundled": {
					return urlVersions.entries().stream()
						.anyMatch(pair -> pair.second().hasVersionOlderThan(PackagePlatform.firstMongoDbVersionWithoutBundledTools()));
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
				case "hasVersions":
					return versions.hasVersions();
				case "hasDevVersions":
					return versions.hasDevVersions();
				case "toolsBundled": {
					return versions.hasVersionOlderThan(PackagePlatform.firstMongoDbVersionWithoutBundledTools());
				}

			}
			throw new IllegalArgumentException("unknown property: '" + name + "'");
		});
	}

	public static TypesafeModelAdapter<ToolsVersions> toolsVersions() {
		return TypesafeModelAdapter.of(ToolsVersions.class, (versions, name) -> {
			switch (name) {
				case "versions":
					return versions.versionRanges();
				case "hasVersions":
					return versions.hasVersions();
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
					return packagePlatformUrlVersions.entries().stream()
						.map(pair -> new PackagePlatformAndUrlVersions(pair.first(), pair.second()))
						.collect(Collectors.toList());
			}
			throw new IllegalArgumentException("unknown property: '" + name + "'");
		});
	}

	public static TypesafeModelAdapter<PackagePlatformAndUrlVersions> packagePlatformAndUrlVersions() {
		return TypesafeModelAdapter.of(PackagePlatformAndUrlVersions.class, (it, name) -> {
			switch (name) {
				case "platform":
					return it.platform();
				case "urlVersions":
					return it.urlVersions();
			}
			throw new IllegalArgumentException("unknown property: '" + name + "'");
		});
	}

	public static void registerDefaults(STGroup group) {
		Arrays.asList(
				optionals(),
				pairs(),
				urlAndPackageVersions(),
				urlAndToolsVersions(),
				enums(),
				packageOsAndVersionType(),
				packagePlatform(),
				packagePlatformUrlVersions(),
				packagePlatformAndUrlVersions(),
				urlVersions(),
				packageVersions(),
				toolsVersions(),
				packageVersion(),
				versionRange()
			)
			.forEach(it -> it.register(group));

		group.registerRenderer(NumericVersion.class, new TypedAttributeRenderer<>(NumericVersion.class, (it, format, locale) -> MongoPackages.asString(it)));
	}
}
