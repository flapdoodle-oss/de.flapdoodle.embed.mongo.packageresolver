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

import de.flapdoodle.embed.process.distribution.Version;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class DefaultFeatureSetResolver implements FeatureSetResolver {

	private final List<FeatureSetRule> rules = featureSetRules();
	private static final String MAX_VERSION = "100.0.0";

	public static DefaultFeatureSetResolver INSTANCE=new DefaultFeatureSetResolver();

	@Override
	public FeatureSet featuresOf(Version distribution) {
		Set<Feature> features = rules.stream()
			.filter(rule -> rule.versionRange().match(distribution))
			.flatMap(rule -> rule.features().stream())
			.collect(Collectors.toSet());

		return FeatureSet.of(features);
	}

	private static List<FeatureSetRule> featureSetRules() {
		return Collections.unmodifiableList(Arrays.asList(
			enable(Feature.SYNC_DELAY, VersionRange.of("2.4.0", MAX_VERSION)),
			enable(Feature.TEXT_SEARCH, VersionRange.of("2.4.0", "2.5.4")),
			enable(Feature.STORAGE_ENGINE, VersionRange.of("3.0.0", MAX_VERSION)),
			enable(Feature.ONLY_64BIT, VersionRange.of("3.4.3", MAX_VERSION)),
			enable(Feature.NO_CHUNKSIZE_ARG, VersionRange.of("3.4.3", MAX_VERSION)),
			enable(Feature.MONGOS_CONFIGDB_SET_STYLE, VersionRange.of("3.4.3", MAX_VERSION)),
			enable(Feature.NO_SOLARIS_SUPPORT, VersionRange.of("3.4.15", MAX_VERSION)),
			enable(Feature.NO_HTTP_INTERFACE_ARG, VersionRange.of("3.6.0", MAX_VERSION)),
			enable(Feature.NO_BIND_IP_TO_LOCALHOST, VersionRange.of("3.6.0", MAX_VERSION)),
			enable(Feature.DISABLE_USE_PREALLOC, VersionRange.of("4.2.13", "7.99.99")),
			enable(Feature.DISABLE_USE_SMALL_FILES, VersionRange.of("4.2.13", MAX_VERSION)),
			enable(Feature.RS_INITIATE, VersionRange.of("4.2.0", MAX_VERSION)),
			enable(Feature.VERBOSITY_LEVEL, VersionRange.of("4.2.0", MAX_VERSION)),
			enable(Feature.JOURNAL_ALWAYS_ON, VersionRange.of("6.1.0", MAX_VERSION))
		));
	}

	private static FeatureSetRule enable(Feature feature, VersionRange range) {
		return FeatureSetRule.builder()
			.versionRange(range)
			.addFeatures(feature)
			.build();
	}
}
