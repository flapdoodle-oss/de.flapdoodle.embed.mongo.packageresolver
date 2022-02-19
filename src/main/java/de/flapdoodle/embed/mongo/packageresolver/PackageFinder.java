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

import java.util.Optional;
import java.util.function.Function;

public interface PackageFinder {
  Optional<DistributionPackage> packageFor(Distribution distribution);

  class FailWithMessage implements PackageFinder, HasExplanation {

    private final Function<Distribution, String> messageFactory;

    public FailWithMessage(Function<Distribution,String> messageFactory) {
      this.messageFactory = messageFactory;
    }

    @Override public Optional<DistributionPackage> packageFor(Distribution distribution) {
      throw new IllegalArgumentException(messageFactory.apply(distribution));
    }

    @Override
    public String explain() {
      return "fail";
    }
  }

  static PackageFinder failWithMessage(Function<Distribution, String> messageFactory) {
    return new FailWithMessage(messageFactory);
  }
}
