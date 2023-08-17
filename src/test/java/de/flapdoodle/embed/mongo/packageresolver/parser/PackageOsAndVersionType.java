/*
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
package de.flapdoodle.embed.mongo.packageresolver.parser;

import de.flapdoodle.os.CommonOS;
import de.flapdoodle.os.OS;
import de.flapdoodle.os.Version;
import de.flapdoodle.os.linux.CentosVersion;
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
			if (version().get()== CentosVersion.class) {
				classBaseName="CentosRedhat";
			}
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
