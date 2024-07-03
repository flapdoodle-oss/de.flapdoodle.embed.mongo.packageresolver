/*
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
package de.flapdoodle.embed.mongo.packageresolver.linux;

import de.flapdoodle.embed.mongo.packageresolver.*;
import de.flapdoodle.os.CommonOS;
import de.flapdoodle.os.linux.AlpineVersion;

public class AlpinePackageFinder extends AbstractPackageFinder implements HasLabel {

	public AlpinePackageFinder(Command command) {
		super(command, rules(command));
	}
	@Override
	public String label() {
		return getClass().getSimpleName();
	}

	private static ImmutablePackageFinderRules rules(final Command command) {
		return PackageFinderRules.empty()
			.withRules(PackageFinderRule.builder()
				.match(PlatformMatch.withOs(CommonOS.Linux).withVersion(AlpineVersion.values()))
				.finder(PackageFinder.failWithMessage(dist -> "there is no mongodb distribution for alpine linux"))
				.build());
	}
}
