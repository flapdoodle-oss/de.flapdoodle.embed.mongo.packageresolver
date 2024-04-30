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

import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.distribution.Version;
import org.immutables.value.Value;

@Value.Immutable
public abstract class VersionRange implements DistributionMatch {
  @Value.Parameter
  public abstract NumericVersion min();
  @Value.Parameter
  public abstract NumericVersion max();

  @Value.Check
  protected void check() {
    if (min().compareTo(max())>0) throw new IllegalArgumentException(min()+" > "+max());
  }

  @Value.Auxiliary
  public boolean isExact() {
    return min().isEqual(max());
  }

  @Override
  @Value.Auxiliary
  public boolean match(Distribution distribution) {
    return match(distribution.version());
  }

  @Value.Auxiliary
  public boolean match(Version version) {
    NumericVersion asNumeric = NumericVersion.of(version.asInDownloadPath());
    if (asNumeric.build().isPresent()) {
      return min().isOlderOrEqual(asNumeric) && max().isEqual(asNumeric) || nextPatchWithoutBuild(asNumeric).isOlderOrEqual(max());
    }
    return min().isOlderOrEqual(asNumeric) && asNumeric.isOlderOrEqual(max());
  }

  private static NumericVersion nextPatchWithoutBuild(NumericVersion src) {
    return NumericVersion.of(src.major(), src.minor(), src.patch() + 1);
  }

  public static VersionRange of(NumericVersion min, NumericVersion max) {
    return ImmutableVersionRange.of(min, max);
  }

  public static VersionRange of(String min, String max) {
    return of(NumericVersion.of(min), NumericVersion.of(max));
  }

  public static VersionRange of(String minMax) {
    return of(NumericVersion.of(minMax), NumericVersion.of(minMax));
  }
}
