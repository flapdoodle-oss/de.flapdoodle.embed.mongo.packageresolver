package de.flapdoodle.embed.mongo.packageresolver.parser;

import de.flapdoodle.checks.Preconditions;
import de.flapdoodle.embed.mongo.packageresolver.parser.st.TypesafeModelAdapters;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.LinkOption;
import java.nio.file.Path;
import java.nio.file.StandardOpenOption;

public class SourceCodeGenerator {
	private final Path targetDirectory;
	private final Path baseDirectory;

	public SourceCodeGenerator(Path targetDirectory) {
		this.targetDirectory = targetDirectory;
		Preconditions.checkArgument(Files.exists(targetDirectory, LinkOption.NOFOLLOW_LINKS), "target directory does not exist");
		Preconditions.checkArgument(Files.isDirectory(targetDirectory, LinkOption.NOFOLLOW_LINKS), "target directory is not a directory: %s", targetDirectory);
		this.baseDirectory = createDirectory(targetDirectory,"generated-package-finder");
	}

	public void generate(PackageTree tree) {
		tree.map().forEach((osAndVersionType, packagePlatformUrlVersions) -> {
			System.out.println("generate " + osAndVersionType.className());

			Path packageDir = baseDirectory;
			if (osAndVersionType.packageName().isPresent()) {
				packageDir=createDirectory(baseDirectory,osAndVersionType.packageName().get());
			}
			String javaCode = generateJavaCode(osAndVersionType, packagePlatformUrlVersions);
			writeJavaCode(packageDir, osAndVersionType.className() + ".java", javaCode);
		});
	}

	private String generateJavaCode(PackageOsAndVersionType packageAndClassName, PackagePlatformUrlVersions packagePlatformUrlVersions) {
		URL templateUrl = getClass().getResource("PackageFinderJavaTemplate.stg");
		STGroupFile groupFile = new STGroupFile(templateUrl, "UTF-8", '<', '>');
		TypesafeModelAdapters.registerDefaults(groupFile);

		ST template = groupFile.getInstanceOf("class");
		template.add("it", packageAndClassName);
		template.add("package", packagePlatformUrlVersions);

		return template.render();
	}

	private static Path createDirectory(Path baseDirectory, String name) {
		Path dir = baseDirectory.resolve(name);
		if (!Files.exists(dir)) {
			try {
				Files.createDirectory(dir);
			}
			catch (IOException e) {
				throw new RuntimeException(e);
			}
		} else {
			Preconditions.checkArgument(Files.isDirectory(dir),"is not a directory: %s", dir);
		}
		return dir;
	}

	private static void writeJavaCode(Path packageDir, String filename, String content) {
		Path file = packageDir.resolve(filename);
		try {
			Files.write(file, content.getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		}
		catch (IOException e) {
			throw new RuntimeException(e);
		}
	}
}
