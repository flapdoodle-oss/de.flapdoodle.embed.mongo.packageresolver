/**
 * Copyright (C) 2011
 *   Michael Mosmann <michael@mosmann.de>
 *   Martin Jöhren <m.joehren@googlemail.com>
 *
 * with contributions from
 * 	konstantin-ba@github,Archimedes Trajano	(trajano@github)
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *         http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package de.flapdoodle.embed.mongo.packageresolver.parser.st;

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
