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
package de.flapdoodle.embed.mongo.packageresolver.parser;

import com.google.common.collect.Multimap;
import com.google.common.collect.SetMultimap;
import de.flapdoodle.embed.mongo.packageresolver.MongoPackages;
import de.flapdoodle.types.Pair;
import org.immutables.value.Value;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Value.Immutable
public abstract class UrlVersions {
	protected abstract SetMultimap<String, PackageVersion> map();
	protected abstract SetMultimap<String, String> toolsMap();

	@Value.Auxiliary
	public ImmutableUrlVersions put(String url, PackageVersion version) {
		return ImmutableUrlVersions.builder()
			.from(this)
			.putMap(url, version)
			.build();
	}

	@Value.Auxiliary
	public ImmutableUrlVersions putTools(String url, String version) {
		return ImmutableUrlVersions.builder()
			.from(this)
			.putToolsMap(url, version)
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

		toolsMap().asMap().forEach((url, versions) -> {
			System.out.println("    "+url);
			String versionList = MongoPackages.rangesAsString(MongoPackages.compressedVersionsList(versions));
			System.out.println("      "+versionList);
		});
	}

	@Value.Auxiliary
	public List<Pair<String, PackageVersions>> entries() {
		return map().asMap().entrySet().stream()
			.map(it -> Pair.of(it.getKey(), PackageVersions.of(it.getValue())))
			.collect(Collectors.toList());
	}

	@Value.Auxiliary
	public List<Pair<String, Collection<String>>> toolEntries() {
		return toolsMap().asMap().entrySet().stream()
			.map(it -> Pair.of(it.getKey(), it.getValue()))
			.collect(Collectors.toList());
	}

	public static ImmutableUrlVersions empty() {
		return ImmutableUrlVersions.builder().build();
	}
}
