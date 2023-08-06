package de.flapdoodle.embed.mongo.packageresolver;

import de.flapdoodle.embed.mongo.packageresolver.parser.PackagePlatform;
import de.flapdoodle.embed.mongo.packageresolver.parser.PackageTree;
import de.flapdoodle.embed.mongo.packageresolver.parser.SourceCodeGenerator;
import de.flapdoodle.types.Either;

import java.io.IOException;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class MongoPackageParser {

	public static void main(String[] args) throws IOException {
		List<MongoPackages.ParsedVersion> versions = MongoPackages.dbVersionsList().stream()
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

		tree.dump();

		new SourceCodeGenerator().generate(tree);
	}
}
