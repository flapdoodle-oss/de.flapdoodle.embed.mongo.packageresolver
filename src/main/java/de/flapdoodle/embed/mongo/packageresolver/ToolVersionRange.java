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

import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.distribution.Version;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
public abstract class ToolVersionRange implements DistributionMatch {
  @Value.Parameter
  abstract NumericVersion min();
  @Value.Parameter
  abstract NumericVersion max();

  @Value.Check
  protected void check() {
    if (min().compareTo(max())>0) throw new IllegalArgumentException(min()+" > "+max());
  }

  @Override
  @Value.Auxiliary
  public boolean match(Distribution distribution) {
    return match(distribution.version());
  }

  @Value.Auxiliary
  public boolean match(Version version) {
    if (version instanceof HasMongotoolsPackage) {
      Optional<MongotoolsVersion.Main> toolsVersion = ((HasMongotoolsPackage) version).mongotoolsVersion();
      if (toolsVersion.isPresent()) {
        NumericVersion asNumeric = NumericVersion.of(toolsVersion.get().asInDownloadPath());
        return min().isOlderOrEqual(asNumeric) && asNumeric.isOlderOrEqual(max());
      }
    }
    return false;
  }

  public static ToolVersionRange of(NumericVersion min, NumericVersion max) {
    return ImmutableToolVersionRange.of(min, max);
  }

  public static ToolVersionRange of(String min, String max) {
    return of(NumericVersion.of(min), NumericVersion.of(max));
  }

  public static ToolVersionRange of(String minMax) {
    return of(NumericVersion.of(minMax), NumericVersion.of(minMax));
  }
}
