package de.flapdoodle.embed.mongo.packageresolver.linux;

import de.flapdoodle.embed.mongo.packageresolver.Command;
import de.flapdoodle.embed.mongo.packageresolver.HtmlParserResultTester;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.distribution.Version;
import de.flapdoodle.os.CommonArchitecture;
import de.flapdoodle.os.CommonOS;
import de.flapdoodle.os.ImmutablePlatform;
import de.flapdoodle.os.Platform;
import de.flapdoodle.os.linux.KdeNeonVersion;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

class KdeNeonPackageFinderTest {
  /*
    Ubuntu 22.04 x64
    https://fastdl.mongodb.org/linux/mongodb-linux-x86_64-ubuntu2204-{}.tgz
    6.0.8, 6.0.4 -> 6.0.6
  */
  @ParameterizedTest
  @ValueSource(strings = {"6.0.8", "6.0.4 -> 6.0.6"})
  public void ubuntu22x64(String version) {
    assertThat(linuxWith(CommonArchitecture.X86_64, KdeNeonVersion.KDE_NEON_6_0), version)
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
            new KdeNeonPackageFinder(new UbuntuFallbackToOlderVersionPackageFinder(new UbuntuPackageFinder(Command.MongoD))),
            version -> Distribution.of(Version.of(version), platform),
            versionList);
  }

}