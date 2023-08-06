package de.flapdoodle.embed.mongo.packageresolver.parser;

import de.flapdoodle.embed.mongo.packageresolver.parser.st.TypesafeModelAdapter;
import de.flapdoodle.embed.mongo.packageresolver.parser.st.TypesafeModelAdapters;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

public class SourceCodeGenerator {

	public void generate(PackageTree tree) {
		tree.map().forEach((osAndVersionType, packagePlatformUrlVersions) -> {
			System.out.println("generate "+packageAndClassNameOf(osAndVersionType));

			String javaCode = generateJavaCode(packageAndClassNameOf(osAndVersionType), packagePlatformUrlVersions);
			System.out.println("------------------");
			System.out.println(javaCode);
		});
	}

	private String generateJavaCode(PackageAndClassName packageAndClassName, PackagePlatformUrlVersions packagePlatformUrlVersions) {
		URL templateUrl=getClass().getResource("PackageFinderJavaTemplate.stg");
		STGroupFile groupFile=new STGroupFile(templateUrl, "UTF-8", '<','>');
		
		TypesafeModelAdapter.of(PackageAndClassName.class, (it, name) -> {
			switch (name) {
				case "name":
					return it.name();
				case "package":
					return it.packageName();
				default:
					throw new IllegalArgumentException("unknown property: "+name);
			}
		}).register(groupFile);

		TypesafeModelAdapters.registerDefaults(groupFile);

		ST template = groupFile.getInstanceOf("class");
		template.add("it",packageAndClassName);
		template.add("package",packagePlatformUrlVersions);

		return template.render();
	}

	private static PackageAndClassName packageAndClassNameOf(PackageOsAndVersionType src) {
		String osName = src.os().name();
		Optional<String> packageName= Optional.empty();
		if (!src.os().distributions().isEmpty()) {
			packageName=Optional.of(osName.toLowerCase());
		}
		String classBaseName = osName.replace("_","");
		if (src.version().isPresent()) {
			classBaseName=src.version().get().getSimpleName().replace("Version","");
		}

		return new PackageAndClassName(packageName, classBaseName+"PackageFinder");
	}

	private static class PackageAndClassName {

		private final Optional<String> packageName;
		private final String name;

		public PackageAndClassName(Optional<String> packageName, String name) {
			this.packageName = packageName;
			this.name = name;
		}

		public Optional<String> packageName() {
			return packageName;
		}

		public String name() {
			return name;
		}

		@Override
		public String toString() {
			return packageName.map(it -> it+".").orElse("")+name;
		}
	}
}
