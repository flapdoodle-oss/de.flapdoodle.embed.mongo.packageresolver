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

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public interface DistributionMatch {
	boolean match(Distribution distribution);

	default DistributionMatch andThen(DistributionMatch other) {
		return new AndThen(this, other);
	}

	static DistributionMatch all() {
		return new All();
	}

	static DistributionMatch any(DistributionMatch... matcher) {
		return new Any(matcher);
	}

	static DistributionMatch any(List<? extends DistributionMatch> matcher) {
		return new Any(matcher);
	}

	class AndThen implements DistributionMatch {
		private final DistributionMatch first;
		private final DistributionMatch second;

		public AndThen(DistributionMatch first, DistributionMatch second) {
			this.first = first;
			this.second = second;
		}

		public DistributionMatch first() {
			return first;
		}

		public DistributionMatch second() {
			return second;
		}

		@Override
		public boolean match(Distribution distribution) {
			return first.match(distribution) && second.match(distribution);
		}
	}

	class All implements DistributionMatch {
		@Override
		public boolean match(Distribution distribution) {
			return true;
		}
	}

	class Any implements DistributionMatch {
		private final List<DistributionMatch> matcher;

		public Any(DistributionMatch... matcher) {
			this(Arrays.asList(matcher));
		}

		public Any(List<? extends DistributionMatch> matcher) {
			this.matcher = Collections.unmodifiableList(new ArrayList<>(matcher));
		}

		public List<DistributionMatch> matcher() {
			return matcher;
		}
		@Override
		public boolean match(Distribution distribution) {
			return matcher.stream().anyMatch(m -> m.match(distribution));
		}
	}
}
