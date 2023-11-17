package de.flapdoodle.embed.mongo.packageresolver.linux;

import de.flapdoodle.checks.Preconditions;
import de.flapdoodle.embed.mongo.packageresolver.HasExplanation;
import de.flapdoodle.embed.mongo.packageresolver.PackageFinder;
import de.flapdoodle.embed.mongo.packageresolver.PlatformMatch;
import de.flapdoodle.embed.process.config.store.Package;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.os.CommonOS;
import de.flapdoodle.os.ImmutablePlatform;
import de.flapdoodle.os.Version;
import de.flapdoodle.types.Pair;

import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public abstract class AbstractVersionMappedPackageFinder<S extends Version, D extends Version> implements PackageFinder, HasExplanation {
	private final PackageFinder delegate;
	private final List<Pair<S, D>> mappings;
	private final Map<S, D> map;

	protected AbstractVersionMappedPackageFinder(PackageFinder delegate, Pair<S, D> ... mappings) {
		this.delegate = delegate;
		this.mappings = Arrays.asList(mappings);
		this.map = this.mappings.stream()
			.collect(Collectors.toMap(Pair::first, Pair::second));

		Preconditions.checkArgument(mappings.length==this.map.keySet().size(),"invalid mapping: %s",Arrays.asList(mappings));
	}

	@Override
	public Optional<Package> packageFor(Distribution distribution) {
		return mapDistribution(distribution)
			.flatMap(delegate::packageFor);
	}

	protected Optional<Distribution> mapDistribution(Distribution distribution) {
		if (PlatformMatch.withOs(CommonOS.Linux).withVersion(map.keySet()).match(distribution)) {
			if (!distribution.platform().version().isPresent()) throw new RuntimeException("version not set: "+distribution);

			Version currentVersion = distribution.platform().version().get();
			D destinationVersion = map.get(currentVersion);
			Preconditions.checkNotNull(destinationVersion,"could not find mapping for %s in %s", currentVersion, map);

			Distribution asDestinationDist = Distribution.of(distribution.version(),
				ImmutablePlatform.copyOf(distribution.platform()).withVersion(destinationVersion));

			return Optional.of(asDestinationDist);
		}
		return Optional.empty();
	}

	@Override
	public String explain() {
		List<D> destinationVersions = mappings.stream()
			.map(Pair::second)
			.distinct()
			.collect(Collectors.toList());

		return destinationVersions.stream()
			.map(uv -> mappings.stream()
				.filter(v -> v.second() == uv)
				.map(it -> it.first().name())
				.collect(Collectors.joining(", ", "" + uv.name() + " for ", "")))
			.collect(Collectors.joining(" and ", "use ", ""));
	}
}
