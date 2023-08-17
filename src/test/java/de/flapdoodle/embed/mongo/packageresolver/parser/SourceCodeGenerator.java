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
			System.out.println("generate " + osAndVersionType.className()+" and "+osAndVersionType.className()+"Test");

			Path packageDir = baseDirectory;
			if (osAndVersionType.packageName().isPresent()) {
				packageDir=createDirectory(baseDirectory,osAndVersionType.packageName().get());
			}
			String javaCode = generateJavaCode("PackageFinderJavaTemplate.stg", osAndVersionType, packagePlatformUrlVersions);
			writeJavaCode(packageDir, osAndVersionType.className() + ".java", javaCode);
			String testCode = generateJavaCode("PackageFinderJavaTestTemplate.stg", osAndVersionType, packagePlatformUrlVersions);
			writeJavaCode(packageDir, osAndVersionType.className() + "Test.java", testCode);
			if (osAndVersionType.className().startsWith("XWindows")) {
				System.out.println("\n\n\n-------------------------------------");
				System.out.println(testCode);
				System.out.println("-------------------------------------");
			}
		});
	}

	private String generateJavaCode(String templateFile, PackageOsAndVersionType packageAndClassName, PackagePlatformUrlVersions packagePlatformUrlVersions) {
		URL templateUrl = getClass().getResource(templateFile);
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
