package de.flapdoodle.embed.mongo.packageresolver;

import de.flapdoodle.embed.process.config.store.Package;
import de.flapdoodle.embed.process.distribution.Distribution;

import java.util.Optional;

public abstract class AbstractPackageFinder implements PackageFinder, HasPlatformMatchRules {

	private final Command command;
	private final PackageFinderRules rules;

	public AbstractPackageFinder(final Command command, PackageFinderRules rules) {
		this.command = command;
		this.rules = rules;
	}

	@Override
	public final PackageFinderRules rules() {
		return rules;
	}

	@Override
	public final Optional<Package> packageFor(final Distribution distribution) {
		return rules.packageFor(distribution);
	}
}
