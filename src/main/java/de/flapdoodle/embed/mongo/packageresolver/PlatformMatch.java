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

import de.flapdoodle.os.BitSize;
import de.flapdoodle.os.CPUType;
import de.flapdoodle.os.OS;
import de.flapdoodle.os.Version;
import org.immutables.value.Value;

import java.util.Optional;
import java.util.Set;

@Value.Immutable
public interface PlatformMatch extends DistributionMatch {
  Set<Version> version();
  Optional<CPUType> cpuType();
  Optional<BitSize> bitSize();
  Optional<OS> os();

  static ImmutablePlatformMatch.Builder builder() {
    return ImmutablePlatformMatch.builder();
  }

  static ImmutablePlatformMatch any() {
    return builder().build();
  }

  static ImmutablePlatformMatch withOs(OS os) {
    return any().withOs(os);
  }

  @Override
  @Value.Auxiliary
  default boolean match(de.flapdoodle.embed.process.distribution.Distribution distribution) {
    return match(this, distribution);
  }

  static boolean match(PlatformMatch match, de.flapdoodle.embed.process.distribution.Distribution distribution) {
    if (dontMatch(match.os(), distribution.platform().operatingSystem())) return false;
    if (dontMatch(match.cpuType(), distribution.platform().architecture().cpuType())) return false;
    if (dontMatch(match.bitSize(), distribution.platform().architecture().bitSize())) return false;
    if (dontMatch(match.version(), distribution.platform().version())) return false;
    return true;
  }

  static <T extends Enum<T>> boolean dontMatch(Optional<T> match, T value) {
    return match.isPresent() && match.get()!=value;
  }

  static <T> boolean dontMatch(Optional<T> match, Optional<T> value) {
    return match.isPresent() && value.isPresent() && !match.get().equals(value.get());
  }

  static <T> boolean dontMatch(Set<T> match, Optional<T> value) {
    return !match.isEmpty() && (!value.isPresent() || !match.contains(value.get()));
  }
}
