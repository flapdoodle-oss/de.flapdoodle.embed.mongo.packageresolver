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

import de.flapdoodle.embed.process.distribution.Version;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

class DefaultFeatureSetResolverTest {

	private final FeatureSetResolver testee = new DefaultFeatureSetResolver();
	@Test
	public void noFeatureEnabledBefore_2_4_x() {
		FeatureSet features = testee.featuresOf(withVersion("1.99.99"));
		for (Feature feature : Feature.values()) {
			assertThat(features.enabled(feature))
				.describedAs("feature %s", feature).isFalse();
		}
	}

	@ParameterizedTest
	@MethodSource("featureVersion")
	public void featureEnabled(Feature feature, String version) {
		FeatureSet features = testee.featuresOf(withVersion(version));
		assertThat(features.enabled(feature)).isTrue();
	}

	public static Stream<Arguments> featureVersion() {
		return Stream.of(
			Arguments.of(Feature.SYNC_DELAY, "2.4.0"),
			Arguments.of(Feature.TEXT_SEARCH, "2.4.0"),
			Arguments.of(Feature.STORAGE_ENGINE, "3.0.0"),
			Arguments.of(Feature.ONLY_64BIT, "3.4.3"),
			Arguments.of(Feature.NO_CHUNKSIZE_ARG, "3.4.3"),
			Arguments.of(Feature.MONGOS_CONFIGDB_SET_STYLE, "3.4.3"),
			Arguments.of(Feature.NO_SOLARIS_SUPPORT, "3.4.15"),
			Arguments.of(Feature.NO_BIND_IP_TO_LOCALHOST, "3.6.0"),
			Arguments.of(Feature.NO_HTTP_INTERFACE_ARG, "3.6.0"),
			Arguments.of(Feature.DISABLE_USE_PREALLOC, "4.2.13"),
			Arguments.of(Feature.DISABLE_USE_SMALL_FILES, "4.2.13"),
			Arguments.of(Feature.RS_INITIATE, "4.2.0"),
			Arguments.of(Feature.VERBOSITY_LEVEL, "4.2.0"),
			Arguments.of(Feature.JOURNAL_ALWAYS_ON, "6.1.0")
		);
	}

	@Test
	public void mostFeaturesEnabledForAnyFutureVersion() {
		FeatureSet features = testee.featuresOf(withVersion("99.99.99"));
		for (Feature feature : Feature.values()) {
			switch (feature) {
				case TEXT_SEARCH:
					assertThat(features.enabled(feature))
						.describedAs("feature %s", feature).isFalse();
					break;
				default:
					assertThat(features.enabled(feature))
						.describedAs("feature %s", feature).isTrue();
			}
		}
	}

	private static Version withVersion(String version) {
		return Version.of(version);
	}
}