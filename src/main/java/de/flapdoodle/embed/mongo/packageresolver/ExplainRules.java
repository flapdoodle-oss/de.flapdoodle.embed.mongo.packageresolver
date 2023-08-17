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

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

public final class ExplainRules {
	private ExplainRules() {
		// no instance
	}

	private static class Output {
		private final StringBuilder sb=new StringBuilder();
		private final String NEW_LINE=System.lineSeparator();

		public String asString() {
			return sb.toString();
		}

		public Context root() {
			return new Context(0);
		}

		class Context {

			private final int level;

			public Context(int level) {
				this.level = level;
			}

			public Context oneDeeper() {
				return new Context(level+1);
			}

			public void matching(String explainMatch) {
				sb.append(indent(level)).append(explainMatch).append(NEW_LINE);
			}

			public void finder(String finderExplained) {
				sb.append(indent(level+1)).append(finderExplained).append(NEW_LINE);
			}
		}
	}

	static String explain(PackageFinderRules rules) {
		Output output = new Output();
		explain(output.root(), rules);
		return output.asString();
	}

	static void explain(Output.Context context, PackageFinderRules rules) {
		rules.rules().forEach(rule -> {
			context.matching(explainMatch(rule.match()));
			PackageFinder finder = rule.finder();

			if (finder instanceof HasPlatformMatchRules) {
				explain(context.oneDeeper(), ((HasPlatformMatchRules) finder).rules());
			} else {
				context.finder(packageFinderName(finder));
			}
		});
	}

	private static String packageFinderName(PackageFinder packageFinder) {
		return packageFinder instanceof HasExplanation
			? ((HasExplanation) packageFinder).explain()
			: packageFinder.getClass().getSimpleName();
	}

	static String explainMatch(DistributionMatch match) {
		return forType(DistributionMatch.class)
			.mapIfInstance(PlatformMatch.class, ExplainRules::explainPlatformMatch)
			.orMapIfInstance(DistributionMatch.AndThen.class, andThen -> "" + explainMatch(andThen.first()) + " and " + explainMatch(andThen.second()))
			.orMapIfInstance(DistributionMatch.Any.class, any -> any.matcher().stream().map(ExplainRules::explainMatch).collect(Collectors.joining(" or ","(",")")))
			.orMapIfInstance(VersionRange.class, ExplainRules::explainVersionRange)
			.orMapIfInstance(ToolVersionRange.class, ExplainRules::explainToolsVersionRange)
			.orMapIfInstance(DistributionMatch.class, it -> it.getClass().getSimpleName())
			.apply(match)
			.get();
	}

	static String explainPlatformMatch(PlatformMatch match) {
		List<String> parts=new ArrayList<>();
		match.os().ifPresent(os -> parts.add("os="+os));
		match.bitSize().ifPresent(bitSize -> parts.add("bitSize="+bitSize));
		match.cpuType().ifPresent(cpuType -> parts.add("cpuType="+cpuType));

		if (!match.version().isEmpty()) {
			parts.add(match.version().stream().map(version -> ""+version)
				.collect(Collectors.joining(", ", "(version is any of ", ")")));
		}

		return !parts.isEmpty()
			? parts.stream().collect(Collectors.joining(" and ", "(", ")"))
			: "(any)";
	}

	private static String explainVersionRange(VersionRange versionRange) {
		if (versionRange.min().isEqual(versionRange.max())) {
			return asHumanReadable(versionRange.min());
		}
		return asHumanReadable(versionRange.min())+"-"+asHumanReadable(versionRange.max());
	}

	private static String explainToolsVersionRange(ToolVersionRange versionRange) {
		if (versionRange.min().isEqual(versionRange.max())) {
			return "tools.version "+asHumanReadable(versionRange.min());
		}
		return "tools.version "+asHumanReadable(versionRange.min())+"-"+asHumanReadable(versionRange.max());
	}

	private static String asHumanReadable(NumericVersion version) {
		return version.asString();
	}

	private static String indent(int level) {
		return repeat(' ', level * 2);
	}

	private static String repeat(char c, int level) {
		char[] s = new char[level];
		for (int i = 0; i < level; i++) {
			s[i] = c;
		}
		return String.valueOf(s);
	}

	public static <S> HasOptionalBuilder<S> forType(Class<S> sourceType) {
		return new HasOptionalBuilder<>();
	}

	static class HasOptionalBuilder<S> {
		<T extends S,V> HasOptionalResult<S, V> mapIfInstance(Class<T> type, Function<T, V> mapIfTypeMatches) {
			return ExplainRules.mapIfInstance(type, mapIfTypeMatches);
		}
	}

	interface HasOptionalResult<S, V> extends Function<S, Optional<V>> {

		default HasOptionalResult<S, V> or(HasOptionalResult<S, V> other) {
			HasOptionalResult<S, V> that = this;
			return s -> {
				Optional<V> first = that.apply(s);
				return first.isPresent() ? first : other.apply(s);
			};
		}

		default <T extends S> HasOptionalResult<S, V> orMapIfInstance(Class<T> type, Function<T, V> mapIfTypeMatches) {
			return or(ExplainRules.mapIfInstance(type, mapIfTypeMatches));
		}
	}

	static <S,T extends S,V> HasOptionalResult<S, V> mapIfInstance(Class<T> type, Function<T, V> mapIfTypeMatches) {
		return s -> type.isInstance(s)
			? Optional.of(mapIfTypeMatches.apply(type.cast(s)))
			: Optional.empty();
	}
}
