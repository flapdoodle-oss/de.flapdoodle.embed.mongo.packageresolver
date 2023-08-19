package de.flapdoodle.embed.mongo.packageresolver.linux;

import de.flapdoodle.embed.mongo.packageresolver.Command;
import de.flapdoodle.embed.mongo.packageresolver.HtmlParserResultTester;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.distribution.Version;
import de.flapdoodle.os.CommonArchitecture;
import de.flapdoodle.os.CommonOS;
import de.flapdoodle.os.ImmutablePlatform;
import de.flapdoodle.os.Platform;
import de.flapdoodle.os.linux.DebianVersion;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class Debian12UsesUbuntuPackageFinderTest {
	@ParameterizedTest
	@ValueSource(strings = {"6.0.4 -> 6.0.5"})
	public void ubuntu22x64(String version) {
		assertThat(linuxWith(CommonArchitecture.X86_64, DebianVersion.DEBIAN_12), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-ubuntu2204-{}.tgz");
	}

	private static Platform linuxWith(CommonArchitecture architecture, de.flapdoodle.os.Version version) {
		return ImmutablePlatform.builder()
			.operatingSystem(CommonOS.Linux)
			.architecture(architecture)
			.version(version)
			.build();
	}

	private static HtmlParserResultTester assertThat(Platform platform, String versionList) {
		return HtmlParserResultTester.with(
			new Debian12UsesUbuntuPackageFinder(new UbuntuPackageFinder(Command.Mongo)),
			version -> Distribution.of(Version.of(version), platform),
			versionList);
	}

}