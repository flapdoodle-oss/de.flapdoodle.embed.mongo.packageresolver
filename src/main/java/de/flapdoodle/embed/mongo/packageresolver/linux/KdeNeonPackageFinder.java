package de.flapdoodle.embed.mongo.packageresolver.linux;

import de.flapdoodle.embed.mongo.packageresolver.HasExplanation;
import de.flapdoodle.embed.mongo.packageresolver.PackageFinder;
import de.flapdoodle.embed.mongo.packageresolver.PlatformMatch;
import de.flapdoodle.embed.process.config.store.Package;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.os.CommonOS;
import de.flapdoodle.os.ImmutablePlatform;
import de.flapdoodle.os.Version;
import de.flapdoodle.os.linux.KdeNeonVersion;
import de.flapdoodle.os.linux.UbuntuVersion;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

public class KdeNeonPackageFinder implements PackageFinder, HasExplanation {

	private final UbuntuFallbackToOlderVersionPackageFinder ubuntuPackageFinder;

	public KdeNeonPackageFinder(UbuntuFallbackToOlderVersionPackageFinder ubuntuPackageFinder) {
		this.ubuntuPackageFinder = ubuntuPackageFinder;
	}

	@Override
	public Optional<Package> packageFor(Distribution distribution) {
		if (PlatformMatch.withOs(CommonOS.Linux).withVersion(KdeNeonVersion.values()).match(distribution)) {
			if (!distribution.platform().version().isPresent()) throw new RuntimeException("version not set: "+distribution);
			Version currentVersion = distribution.platform().version().get();
			if (currentVersion instanceof KdeNeonVersion) {
				Distribution asUbuntudistribution = Distribution.of(distribution.version(),
					ImmutablePlatform.copyOf(distribution.platform()).withVersion(((KdeNeonVersion) currentVersion).matchingUbuntuVersion()));
				return ubuntuPackageFinder.packageFor(asUbuntudistribution);
			} else {
				throw new IllegalArgumentException("Version is not a "+KdeNeonVersion.class+": "+currentVersion);
			}
		}

		return Optional.empty();
	}

	@Override
	public String explain() {
		List<UbuntuVersion> ubuntuVersions = Arrays.stream(KdeNeonVersion.values()).map(KdeNeonVersion::matchingUbuntuVersion)
			.distinct()
			.collect(Collectors.toList());

		return ubuntuVersions.stream()
			.map(uv -> Arrays.stream(KdeNeonVersion.values())
				.filter(v -> v.matchingUbuntuVersion() == uv)
				.map(KdeNeonVersion::name)
				.collect(Collectors.joining(", ", uv.name() + " for ", "")))
			.collect(Collectors.joining(" and ", "use '"+ubuntuPackageFinder.label()+"' with ", ""));
	}
}
