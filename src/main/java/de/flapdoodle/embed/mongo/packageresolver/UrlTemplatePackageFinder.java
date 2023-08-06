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

import de.flapdoodle.embed.process.config.store.FileSet;
import de.flapdoodle.embed.process.config.store.Package;
import de.flapdoodle.embed.process.distribution.ArchiveType;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.embed.process.distribution.Version;
import org.immutables.value.Value;

import java.util.Optional;

@Value.Immutable
public abstract class UrlTemplatePackageFinder implements PackageFinder, HasExplanation {

  protected abstract ArchiveType archiveType();
  protected abstract FileSet fileSet();
  abstract String urlTemplate();

  @Value.Default
  protected boolean isDevVersion() {
    return false;
  }

  @Override
  public Optional<Package> packageFor(Distribution distribution) {
    String path=render(urlTemplate(), distribution);
    return Optional.of(isDevVersion()
      ? Package.of(archiveType(), fileSet(), path, "Development Version / Release Candidate")
      : Package.of(archiveType(), fileSet(), path));
  }

  @Value.Auxiliary
  @Override
  public String explain() {
    return "url=" + urlTemplate() + " (" + archiveType().name() + (isDevVersion() ? "(DEV)" : "") + ")";
  }

  private static String render(String urlTemplate, Distribution distribution) {
    String version=distribution.version().asInDownloadPath();
    String withVersion = urlTemplate.replace("{version}", version);

    Optional<String> toolsVersion = Optional.of(distribution.version())
        .flatMap(it -> it instanceof HasMongotoolsPackage ? Optional.of((HasMongotoolsPackage) it) : Optional.empty())
        .flatMap(HasMongotoolsPackage::mongotoolsVersion)
        .map(Version::asInDownloadPath);

    String withOrWithoutToolsVersion=toolsVersion.isPresent()
        ? withVersion.replace("{tools.version}", toolsVersion.get())
        : withVersion;

    return withOrWithoutToolsVersion;
  }

  public static ImmutableUrlTemplatePackageFinder.Builder builder() {
    return ImmutableUrlTemplatePackageFinder.builder();
  }
}
