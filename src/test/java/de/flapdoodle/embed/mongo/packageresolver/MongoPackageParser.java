package de.flapdoodle.embed.mongo.packageresolver;

import de.flapdoodle.embed.mongo.packageresolver.parser.PackagePlatform;
import de.flapdoodle.embed.mongo.packageresolver.parser.PackageTree;
import de.flapdoodle.embed.mongo.packageresolver.parser.SourceCodeGenerator;
import de.flapdoodle.types.Either;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.LinkOption;
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
