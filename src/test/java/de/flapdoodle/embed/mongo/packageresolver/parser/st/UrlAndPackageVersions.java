package de.flapdoodle.embed.mongo.packageresolver.parser.st;

import de.flapdoodle.embed.mongo.packageresolver.MongoPackageHtmlPageParser;
import de.flapdoodle.embed.mongo.packageresolver.MongoPackages;
import de.flapdoodle.embed.mongo.packageresolver.parser.PackageVersions;
import de.flapdoodle.embed.process.distribution.ArchiveType;

public class UrlAndPackageVersions {
	private final int index;
	private final String url;
	private final PackageVersions versions;

	public UrlAndPackageVersions(int index, String url, PackageVersions versions) {
		this.index = index;
		this.url = url;
		this.versions = versions;
	}

	public String index() {
		return index != 0 ? "_"+index : "";
	}
	
	public String url() {
		return url;
	}

	public PackageVersions versions() {
		return versions;
	}
	
	public ArchiveType archiveType() {
		if (url.endsWith(".zip")) {
			return ArchiveType.ZIP;
		}
		if (url.endsWith(".tgz")) {
			return ArchiveType.TGZ;
		}
		throw new IllegalArgumentException("not supported: "+url);
	}
}
