package de.flapdoodle.embed.mongo.packageresolver.parser;

import de.flapdoodle.embed.mongo.packageresolver.parser.st.TypesafeModelAdapters;
import de.flapdoodle.os.OS;
import org.stringtemplate.v4.ST;
import org.stringtemplate.v4.STGroupFile;

import java.net.URL;

public class SourceCodeGenerator {

	public void generate(PackageTree tree) {
		tree.map().forEach((osAndVersionType, packagePlatformUrlVersions) -> {

			if (osAndVersionType.className().startsWith("Amazon")/* || osAndVersionType.className().startsWith("Windows")*/) {
				System.out.println("generate " + osAndVersionType);

				String javaCode = generateJavaCode(osAndVersionType, packagePlatformUrlVersions);
				System.out.println("------------------");
				System.out.println(javaCode);
			}
		});
	}

	private String generateJavaCode(PackageOsAndVersionType packageAndClassName, PackagePlatformUrlVersions packagePlatformUrlVersions) {
		URL templateUrl=getClass().getResource("PackageFinderJavaTemplate.stg");
		STGroupFile groupFile=new STGroupFile(templateUrl, "UTF-8", '<','>');
		TypesafeModelAdapters.registerDefaults(groupFile);

		ST template = groupFile.getInstanceOf("class");
		template.add("it",packageAndClassName);
		template.add("package",packagePlatformUrlVersions);

		return template.render();
	}
}
