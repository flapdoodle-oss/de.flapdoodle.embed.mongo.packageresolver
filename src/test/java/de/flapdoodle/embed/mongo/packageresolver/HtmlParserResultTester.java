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

import com.google.common.base.Preconditions;
import de.flapdoodle.embed.process.config.store.Package;
import de.flapdoodle.embed.process.distribution.Distribution;

import java.util.List;
import java.util.Optional;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;

public class HtmlParserResultTester {

  private final PackageFinder testee;
  private final Function<String, Distribution> distributionWithVersion;
  private final List<VersionGenerator> versions;

  public HtmlParserResultTester(PackageFinder testee, Function<String, Distribution> distributionWithVersion, List<VersionGenerator> versions) {
    this.testee = testee;
    this.distributionWithVersion = distributionWithVersion;
    this.versions = versions;
  }

  /*
    4.0.26 - 4.0.0, 3.6.22 - 3.6.0, 3.4.23 - 3.4.9, 3.4.7 - 3.4.0, 3.2.21 - 3.2.0, 3.0.14 - 3.0.0
    */
  public static HtmlParserResultTester with(PackageFinder testee, Function<String, Distribution> distributionWithVersion, String versionList) {
    String[] ranges = versionList.split(",");
    List<VersionGenerator> versions = Stream.of(ranges)
            .map(VersionGenerator::of)
            .collect(Collectors.toList());
    return new HtmlParserResultTester(testee, distributionWithVersion, versions);
  }

  public void resolvesTo(String url) {
    versions.forEach(versionGenerator -> {
      versionGenerator.forEach(version -> {
        Optional<Package> result = testee.packageFor(distributionWithVersion.apply(version.asString()));
        assertThat(result).describedAs("package for "+version).isPresent();
        String expectedUrl = url.replace("{}", version.asString());
        assertThat(result.get().url()).isEqualTo(expectedUrl);
      });
    });
  }

  public void resolveDevPackageTo(String url) {
    versions.forEach(versionGenerator -> {
      versionGenerator.forEach(version -> {
        Optional<Package> optionalPackage = testee.packageFor(distributionWithVersion.apply(version.asString()));
        assertThat(optionalPackage).describedAs("package for "+version).isPresent();
        String expectedUrl = url.replace("{}", version.asString());
        Package aPackage = optionalPackage.get();
        assertThat(aPackage.url()).isEqualTo(expectedUrl);
        assertThat(aPackage.hint()).isPresent().hasValue("Development Version / Release Candidate");
      });
    });
  }

  private static class VersionGenerator {

    private final NumericVersion min;
    private final NumericVersion max;

    public VersionGenerator(NumericVersion min, NumericVersion max) {
      this.min = min;
      this.max = max;
      if (max!=null) {
        Preconditions.checkArgument(min.isOlderOrEqual(max),"%s > %s",min, max);
      }
    }

    static VersionGenerator of(String versionRange) {
      final NumericVersion max;
      final NumericVersion min;

      if (versionRange.contains("->")) {
        String[] minMax = versionRange.split("->");
        Preconditions.checkArgument(minMax.length==2,"invalid version range: %s",versionRange);
        min = NumericVersion.of(minMax[0].trim());
        max = NumericVersion.of(minMax[1].trim());
      } else {
//        if (versionRange.contains("-")) {
//          String[] maxMin = versionRange.split("-");
//          Preconditions.checkArgument(maxMin.length==2,"invalid version range: %s",versionRange);
//          min = NumericVersion.of(maxMin[1].trim());
//          max = NumericVersion.of(maxMin[0].trim());
//        } else {
          min = NumericVersion.of(versionRange.trim());
          max = null;
//        }
      }
      return new VersionGenerator(min, max);
    }

    public void forEach(Consumer<NumericVersion> withVersion) {
      if (max!=null) {
        withVersion.accept(min);
        between(min, max).forEach(withVersion);
        withVersion.accept(max);
      } else {
        withVersion.accept(min);
      }
    }

    private static Stream<NumericVersion> between(NumericVersion start, NumericVersion limit) {
      if (start.major()==limit.major()) {
        if (start.minor()==limit.minor()) {
          Stream.Builder<NumericVersion> streamBuilder = Stream.builder();
          ImmutableNumericVersion current = ImmutableNumericVersion.copyOf(start);
          while ((current.patch() + 1)<limit.patch()) {
            current=current.withPatch(current.patch() + 1);
            streamBuilder.add(current);
          }
          return streamBuilder.build();
        } else {
          throw new IllegalArgumentException("not supported");
        }
      }
      return Stream.of();
    }
  }
}
