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
package de.flapdoodle.embed.mongo.packageresolver;

import de.flapdoodle.embed.mongo.packageresolver.linux.LinuxPackageFinder;
import de.flapdoodle.embed.process.config.store.DistributionPackage;
import de.flapdoodle.embed.process.config.store.PackageResolver;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.os.OS;

import java.util.Optional;

/**
 * bc mongodb decided to reinvent their artifact naming which is some kind of complex
 * we have to deal with that somehow
 */
public class PlatformPackageResolver implements PackageResolver, HasPlatformMatchRules {

  private final Command command;
  private final PlatformMatchRules rules;

  public PlatformPackageResolver(Command command) {
    // TODO system property finder
    // TODO put features into this?

		// https://www.mongodb.org/dl/linux
    // https://www.mongodb.org/dl/osx
    // https://www.mongodb.org/dl/windows

    this.command = command;
    this.rules = PlatformMatchRules.empty()
        .with(PlatformMatchRule.of(PlatformMatch.withOs(OS.Windows), new WindowsPackageFinder(command)))
        .with(PlatformMatchRule.of(PlatformMatch.withOs(OS.OS_X), new OSXPackageFinder(command)))
        .with(PlatformMatchRule.of(PlatformMatch.withOs(OS.Linux), new LinuxPackageFinder(command)))
        .with(PlatformMatchRule.of(PlatformMatch.withOs(OS.Solaris), new SolarisPackageFinder(command)));
  }

  @Override
  public DistributionPackage packageFor(Distribution distribution) {
    Optional<DistributionPackage> result = rules.packageFor(distribution);
    return result.orElseThrow(() -> {

      String message = "could not resolve package for " + distribution + System.lineSeparator() +
        "--------------" + System.lineSeparator() +
        explain();
      
      return new IllegalArgumentException(message);
    });
  }

  @Override
  public PlatformMatchRules rules() {
    return rules;
  }

  public String explain() {
    return ExplainRules.explain(this.rules);
  }
}
