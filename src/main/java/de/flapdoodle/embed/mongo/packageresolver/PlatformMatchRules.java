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

import de.flapdoodle.embed.process.config.store.DistributionPackage;
import de.flapdoodle.embed.process.distribution.Distribution;
import org.immutables.value.Value;

import java.util.List;
import java.util.Optional;

@Value.Immutable
public abstract class PlatformMatchRules {
  abstract List<PlatformMatchRule> rules();

  public ImmutablePlatformMatchRules with(PlatformMatchRule rule) {
    return builder()
      .rules(rules())
      .addRules(rule)
      .build();
  }

  @Value.Auxiliary
  public Optional<DistributionPackage> packageFor(Distribution distribution) {
    for (PlatformMatchRule rule : rules()) {
      if (rule.match().match(distribution)) {
        Optional<DistributionPackage> result = rule.finder().packageFor(distribution);
        if (result.isPresent()) {
          return result;
        }
      }
    }
    return Optional.empty();
  }


  public static ImmutablePlatformMatchRules empty() {
    return builder().build();
  }

  public static ImmutablePlatformMatchRules.Builder builder() {
    return ImmutablePlatformMatchRules.builder();
  }
}
