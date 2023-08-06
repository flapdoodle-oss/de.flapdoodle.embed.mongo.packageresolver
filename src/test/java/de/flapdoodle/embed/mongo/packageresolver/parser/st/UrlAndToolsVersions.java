package de.flapdoodle.embed.mongo.packageresolver.parser.st;

import de.flapdoodle.embed.mongo.packageresolver.parser.PackageVersions;
import de.flapdoodle.embed.process.distribution.ArchiveType;

public class UrlAndToolsVersions {
	private final int index;
	private final String url;
	private final ToolsVersions versions;

	public UrlAndToolsVersions(int index, String url, ToolsVersions versions) {
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

	public ToolsVersions versions() {
		return versions;
	}

	@Override
	public String toString() {
		return "UrlAndToolsVersions{" +
			"index=" + index +
			", url='" + url + '\'' +
			", versions=" + versions +
			'}';
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
