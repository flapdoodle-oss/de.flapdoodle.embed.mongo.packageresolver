package de.flapdoodle.embed.mongo.packageresolver.parser.st;

import de.flapdoodle.embed.mongo.packageresolver.parser.PackagePlatform;
import de.flapdoodle.embed.mongo.packageresolver.parser.UrlVersions;

public class PackagePlatformAndUrlVersions {
	private final PackagePlatform platform;
	private final UrlVersions urlVersions;

	public PackagePlatformAndUrlVersions(PackagePlatform platform, UrlVersions urlVersions) {
		this.platform = platform;
		this.urlVersions = urlVersions;
	}

	public PackagePlatform platform() {
		return platform;
	}

	public UrlVersions urlVersions() {
		return urlVersions;
	}
}
