package de.flapdoodle.embed.mongo.packageresolver.linux;

import de.flapdoodle.embed.mongo.packageresolver.Command;
import de.flapdoodle.embed.mongo.packageresolver.HtmlParserResultTester;
import de.flapdoodle.os.*;
import de.flapdoodle.os.linux.DebianVersion;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class DebianFallbackToOlderVersionPackageFinderTest {

	@ParameterizedTest
	@ValueSource(strings = { "7.0.0 -> 7.0.4", "6.0.0 -> 6.0.15", "5.0.8 -> 5.0.26" })
	public void DEBIAN_12(String version) {
		assertThat(withPlatform(CommonOS.Linux, CommonArchitecture.X86_64, DebianVersion.DEBIAN_12), version)
			.resolvesTo("/linux/mongodb-linux-x86_64-debian11-{}.tgz");
	}

	private static Platform withPlatform(OS os, CommonArchitecture architecture, de.flapdoodle.os.Version version) {
		return ImmutablePlatform.builder()
			.operatingSystem(os)
			.architecture(architecture)
			.version(version)
			.build();
	}

	private static HtmlParserResultTester assertThat(Platform platform, String versionList) {
		return HtmlParserResultTester.with(
			new DebianFallbackToOlderVersionPackageFinder(new DebianPackageFinder(Command.Mongo)),
			version -> de.flapdoodle.embed.process.distribution.Distribution.of(de.flapdoodle.embed.process.distribution.Version.of(version), platform),
			versionList);
	}
}