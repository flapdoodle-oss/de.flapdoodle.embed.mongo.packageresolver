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
package de.flapdoodle.embed.mongo.packageresolver;

import de.flapdoodle.embed.process.config.store.Package;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.os.*;

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

	public static PlatformMatch match(OS os, BitSize bitSize, CPUType cpuType, Version... versions) {
		return PlatformMatch.withOs(os).withBitSize(bitSize).withCpuType(cpuType)
			.withVersion(versions);
	}

	protected static PlatformMatch match(OS os, BitSize bitSize) {
		return PlatformMatch.withOs(os).withBitSize(bitSize);
	}

	protected static PlatformMatch match(BitSize bitSize, CPUType cpuType, Version... versions) {
		return PlatformMatch.withOs(CommonOS.Linux).withBitSize(bitSize).withCpuType(cpuType)
			.withVersion(versions);
	}

}
