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
package de.flapdoodle.embed.mongo.packageresolver;

import de.flapdoodle.embed.mongo.packageresolver.parser.PackagePlatform;
import de.flapdoodle.embed.mongo.packageresolver.parser.PackageTree;
import de.flapdoodle.embed.mongo.packageresolver.parser.SourceCodeGenerator;
import de.flapdoodle.types.Either;

import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MongoPackageParser {

	public static void main(String[] args) throws IOException {
		Path targetDirectory = Paths.get("").toAbsolutePath()
			.resolve("target");
		if (args.length>0) {
			targetDirectory = Paths.get(args[0]);
		}

		List<MongoPackages.ParsedVersion> versions = MongoPackages.dbVersionsList().stream()
			.flatMap(Collection::stream)
			.collect(Collectors.toList());

		List<MongoPackages.ParsedVersion> toolsVersions = MongoPackages.toolsVersionsList().stream()
			.flatMap(Collection::stream)
			.collect(Collectors.toList());


		PackageTree tree = PackageTree.empty();

		for (MongoPackages.ParsedVersion v : versions) {
			for (MongoPackages.ParsedDist d : v.dists) {
				Either<PackagePlatform, String> result = PackagePlatform.parse(d.name);
				if (result.isLeft()) {
					String url = d.singleUrl()
						.replace(v.version, "{version}")
						.replace("https://fastdl.mongodb.org","");
					tree = tree.add(result.left(), v.version, v.isDevVersion, url);
				} else {
					tree = tree.skip(result.right());
				}
			}
		}

		for (MongoPackages.ParsedVersion v : toolsVersions) {
			for (MongoPackages.ParsedDist d : v.dists) {
				Either<PackagePlatform, String> result = PackagePlatform.parse(d.name);
				if (result.isLeft()) {
					String url = d.singleUrl()
						.replace(v.version, "{tools.version}")
						.replace("https://fastdl.mongodb.org","");
					tree = tree.addTools(result.left(), v.version, url);
				} else {
					tree = tree.skipTools(result.right());
				}
			}
		}

		tree.dump();

		new SourceCodeGenerator(targetDirectory).generate(tree);
	}
}
