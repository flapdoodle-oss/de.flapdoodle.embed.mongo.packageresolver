package de.flapdoodle.embed.mongo.packageresolver.linux;

import de.flapdoodle.embed.mongo.packageresolver.Command;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.os.*;
import de.flapdoodle.os.linux.OracleVersion;
import de.flapdoodle.os.linux.RedhatVersion;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;

import java.util.Optional;

abstract class AbstractVersionMappedPackageFinderTest<S extends Version, D extends Version> {

	private final AbstractVersionMappedPackageFinder<S, D> testee;

	protected AbstractVersionMappedPackageFinderTest(AbstractVersionMappedPackageFinder<S, D> testee) {
		this.testee = testee;
	}
	
	protected void assertMappedVersion(S sourceVersion, D destinationVersion) {
		Optional<Distribution> mapped = testee.mapDistribution(
			Distribution.of(de.flapdoodle.embed.process.distribution.Version.of("foo"),
				withPlatform(CommonOS.Linux, CommonArchitecture.ARM_64, sourceVersion)));

		Assertions.assertThat(mapped)
			.isPresent()
			.map(Distribution::platform)
			.flatMap(Platform::version)
			.isPresent()
			.contains(destinationVersion);
	}

	private static <S extends Version> Platform withPlatform(OS os, CommonArchitecture architecture, S version) {
		return ImmutablePlatform.builder()
			.operatingSystem(os)
			.architecture(architecture)
			.version(version)
			.build();
	}

}