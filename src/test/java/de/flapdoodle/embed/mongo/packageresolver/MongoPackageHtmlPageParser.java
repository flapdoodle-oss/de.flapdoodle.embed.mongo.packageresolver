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

import com.google.common.base.Preconditions;
import com.google.common.collect.ImmutableListMultimap;
import com.google.common.collect.ImmutableSet;
import com.google.common.io.Resources;
import de.flapdoodle.embed.process.config.store.FileSet;
import de.flapdoodle.embed.process.config.store.FileType;
import de.flapdoodle.embed.process.config.store.Package;
import de.flapdoodle.embed.process.distribution.ArchiveType;
import de.flapdoodle.embed.process.distribution.Distribution;
import de.flapdoodle.os.*;
import de.flapdoodle.os.linux.*;
import de.flapdoodle.types.Pair;
import de.flapdoodle.types.Try;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class MongoPackageHtmlPageParser extends AbstractPackageHtmlParser {

	public static void main(String[] args) throws IOException {
		List<Pair<String, Boolean>> resources = Arrays.asList(
			Pair.of("versions/react/mongo-db-versions-2021-10-28.html", false),
			Pair.of("versions/react/mongo-db-versions-2022-01-16.html", false),
			Pair.of("versions/react/mongo-db-versions-2022-03-30.html", false),
			Pair.of("versions/react/mongo-db-versions-2022-09-25.html", false),
			Pair.of("versions/react/mongo-db-versions-2022-10-14.html", false),
			Pair.of("versions/react/mongo-db-versions-2022-11-27.html", false),
			Pair.of("versions/react/mongo-db-versions-2023-02-12.html", false),
			Pair.of("versions/react/mongo-db-versions-2023-03-16.html", false),
			Pair.of("versions/react/mongo-db-versions-2023-05-21.html", false),
			Pair.of("versions/react/mongo-db-versions-2023-05-21-dev.html", true),
			Pair.of("versions/react/mongo-db-versions-2023-05-30-dev.html", true),
			Pair.of("versions/react/mongo-db-versions-2023-07-25.html", false),
			Pair.of("versions/react/mongo-db-versions-2023-07-25-dev.html", true)
		);

		List<List<ParsedVersion>> allVersions = resources.stream()
			//.map(it -> Try.supplier(() -> parse(Jsoup.parse(Resources.toString(Resources.getResource(it), StandardCharsets.UTF_8)))))
			.map(it -> it.mapFirst(path -> Try.supplier(() -> Resources.toString(Resources.getResource(path), StandardCharsets.UTF_8))
				.mapToUncheckedException(RuntimeException::new)
				.get()))
			.map(it -> it.mapFirst(Jsoup::parse))
			.map(it -> MongoPackageHtmlPageParser.parse(it.first(), it.second()))
			.collect(Collectors.toList());

		//List<ParsedVersion> versions = mergeAll(allVersions);

		ParsedVersions versions = new ParsedVersions(mergeAll(allVersions));

//    List<ParsedVersion> versions = parse(Jsoup.parse(Resources.toString(Resources.getResource("versions/react/mongo-db-versions-2021-10-28.html"), StandardCharsets.UTF_8)));

//    dump(versions);
		Set<String> names = versions.names();
//    List<ParsedVersion> filtered = filter(versions, it -> it.name.contains("indows"));
		names.forEach(name -> {
			System.out.println("-----------------------------------");
			System.out.println(name);
			ParsedVersions filtered = versions.filterByName(name);
			versionAndUrl(filtered);
		});

		System.out.println();
		System.out.println("-----------------------------------");
		System.out.println("- ");
		System.out.println("-----------------------------------");
		System.out.println();

		names.forEach(name -> {
			System.out.println("-----------------------------------");
			System.out.println(name);
			ParsedVersions filtered = versions.filterByName(name);
			compressedVersionAndUrl(filtered);
		});

		asPlatformRules(versions);
	}

	private static void asPlatformRules(ParsedVersions versions) {
		System.out.println();
		System.out.println();
		System.out.println("- - - 8<- - - - - - - - ");
		List<PlatformVersions> byPlatform = versions.groupedByPlatform();

		ImmutableListMultimap<PlatformMatch, List<UrlAndVersions>> asPlatformMatchMap = byPlatform.stream()
			.map(entry -> new Tuple<>(asPlatformMatch(entry.name()), entry.urlAndVersions()))
			.filter(tuple -> tuple.a().isPresent())
			.collect(ImmutableListMultimap.toImmutableListMultimap(tuple -> tuple.a().get(), tuple -> tuple.b()));

		String explained = ExplainRules.explain(mapAsPlatformRules(asPlatformMatchMap));
		System.out.println(explained);
	}

	private static PackageFinderRules mapAsPlatformRules(ImmutableListMultimap<PlatformMatch, List<UrlAndVersions>> map) {
    List<Tuple<PlatformMatch, List<UrlAndVersions>>> platformAndVersions = map.asMap()
      .entrySet()
      .stream().map(entry -> new Tuple<>(entry.getKey(), entry.getValue()
        .stream()
        .flatMap(Collection::stream)
        .collect(Collectors.toList())))
      .collect(Collectors.toList());

		Set<OS> osSet = ImmutableSet.of(CommonOS.Windows, CommonOS.OS_X, CommonOS.Linux, CommonOS.Solaris, CommonOS.FreeBSD);
		Preconditions.checkArgument(osSet.size()==CommonOS.list().size(),"entries missing");

		List<PackageFinderRule> rules = osSet.stream()
			.map(os -> asPlatformRules(os, platformAndVersions))
			.collect(Collectors.toList());

		return PackageFinderRules.builder()
			.addAllRules(rules)
			.build();
	}

	private static PackageFinderRule asPlatformRules(OS os, List<Tuple<PlatformMatch, List<UrlAndVersions>>> platformAndVersions) {
    Optional<OS> osMatch = Optional.of(os);

    List<Tuple<PlatformMatch, List<UrlAndVersions>>> matchingOs = platformAndVersions.stream()
      .filter(it -> it.a().os().equals(osMatch))
      .collect(Collectors.toList());
//    if (matchingOs.size()==1) {
//      // got it
//      return PlatformMatchRule.of(PlatformMatch.withOs(os), new JustRulesPackageFinder(
//        asVersionDetectionRules(PlatformMatch.withOs(os), matchingOs.get(0).b())));
//
//    } else {
      // more than one
			if (os.distributions().isEmpty()) {
				// no dist
				return PackageFinderRule.of(PlatformMatch.withOs(os), new JustRulesPackageFinder(
					PackageFinderRules.empty().withRules(matchingOs.stream()
						.map(entry -> PackageFinderRule.of(entry.a(), new JustRulesPackageFinder(asVersionDetectionRules(entry.a(), entry.b()))))
						.collect(Collectors.toList()))));

			} else {
				return groupedByDist(os, matchingOs);
			}
//    }
	}

	private static PackageFinderRule groupedByDist(OS os, List<Tuple<PlatformMatch, List<UrlAndVersions>>> matchingOs) {

		List<PackageFinderRule> list = os.distributions().stream()
			.map(dist -> PackageFinderRule.of(PlatformMatch.withOs(os).withVersion(dist.versions()), new JustRulesPackageFinder(
				groupedByVersion(os, dist, matchingOs.stream()
					.filter(entry -> entry.a().version().stream().anyMatch(v -> dist.versions().contains(v)))
					.collect(Collectors.toList()))
			)))
			.collect(Collectors.toList());

		return PackageFinderRule.of(PlatformMatch.withOs(os), new JustRulesPackageFinder(PackageFinderRules.empty().withRules(list)));
	}

	private static PackageFinderRules groupedByVersion(OS os, de.flapdoodle.os.Distribution dist, List<Tuple<PlatformMatch, List<UrlAndVersions>>> matching) {
		List<PackageFinderRule> rules = dist.versions().stream()
			.map(version -> tuple(version, asVersionDetectionRules(PlatformMatch.withOs(os).withVersion(version), matching.stream()
				.filter(entry -> entry.a().version().contains(version))
				.flatMap(tuple -> tuple.b().stream())
				.collect(Collectors.toList()))))
			.map(tuple -> PackageFinderRule.of(PlatformMatch.withOs(os).withVersion(tuple.a()), new JustRulesPackageFinder(tuple.b())))
			.collect(Collectors.toList());

		return PackageFinderRules.empty().withRules(rules);
	}

	private static PackageFinderRules asVersionDetectionRules(PlatformMatch parent, List<UrlAndVersions> urlAndVersions) {
    List<PackageFinderRule> list = urlAndVersions.stream()
			.flatMap(entry -> {
				List<String> releaseVersions = entry.versions().stream()
					.filter(it -> !it.second())
					.map(Pair::first)
					.collect(Collectors.toList());

        DistributionMatch match = DistributionMatch.any(compressedVersionsList(releaseVersions));

				String url = entry.url();
				url=url.replace("https://fastdl.mongodb.org","");

				PackageFinderRule releaseRule = PackageFinderRule.of(parent.andThen(match), UrlTemplatePackageResolver.builder()
					.urlTemplate(url)
					.fileSet(FileSet.builder()
						.addEntry(FileType.Executable, "mongod")
						.build())
					.archiveType(archiveTypeFromUrl(url))
					.build());

				List<String> devVersions = entry.versions().stream()
					.filter(it -> it.second())
					.map(Pair::first)
					.collect(Collectors.toList());

				if (!devVersions.isEmpty()) {
					DistributionMatch devMatch = DistributionMatch.any(compressedVersionsList(devVersions));

					PackageFinderRule devRule = PackageFinderRule.of(parent.andThen(devMatch), UrlTemplatePackageResolver.builder()
						.urlTemplate(url)
						.fileSet(FileSet.builder()
							.addEntry(FileType.Executable, "mongod")
							.build())
						.archiveType(archiveTypeFromUrl(url))
						.isDevVersion(true)
						.build());

					return Stream.of(releaseRule, devRule);
				}
				return Stream.of(releaseRule);
				
      })
      .collect(Collectors.toList());

    return PackageFinderRules.empty()
      .withRules(list);
  }

	private static ArchiveType archiveTypeFromUrl(String url) {
		if (url.endsWith(".zip")) {
			return ArchiveType.ZIP;
		}
		if (url.endsWith(".tgz")) {
			return ArchiveType.TGZ;
		}
		throw new IllegalArgumentException("not supported: "+url);
	}

	static class JustRulesPackageFinder implements PackageFinder, HasPlatformMatchRules {

		private final PackageFinderRules rules;

		public JustRulesPackageFinder(PackageFinderRules rules) {
			this.rules = rules;
		}

		@Override
    public PackageFinderRules rules() {
			return rules;
		}

		@Override
    public Optional<Package> packageFor(Distribution distribution) {
			return rules.packageFor(distribution);
		}
	}

	private static Optional<PlatformMatch> asPlatformMatch(String name) {
		Optional<OS> os = Optional.empty();
		Optional<BitSize> bitsize = Optional.empty();
		Optional<CPUType> cpuType = Optional.empty();
		Optional<Version> versions = Optional.empty();

		if (name.contains("ARM")) {
			cpuType = Optional.of(CPUType.ARM);
		}
		if (name.contains("64")) {
			bitsize = Optional.of(BitSize.B64);
		}
		if (name.contains("x64")) {
			cpuType = Optional.of(CPUType.X86);
			bitsize = Optional.of(BitSize.B64);
		}
		if (name.contains("s390x")) {
			return Optional.empty();
		}

		if (name.contains("indows")) {
			os = Optional.of(CommonOS.Windows);
			if (!bitsize.isPresent()) {
				bitsize=Optional.of(BitSize.B32);
			}
		}
		if (name.contains("Amazon Linux")) {
			os = Optional.of(CommonOS.Linux);
			versions = Optional.of(AmazonVersion.AmazonLinux);
		}
		if (name.contains("Amazon Linux 2")) {
			os = Optional.of(CommonOS.Linux);
			versions = Optional.of(AmazonVersion.AmazonLinux2);
		}
		if ((name.contains("mazon") && name.contains("2023")) || name.contains("amazon2023")) {
			os = Optional.of(CommonOS.Linux);
			versions = Optional.of(AmazonVersion.AmazonLinux2023);
		}
		if (name.contains("Debian 7.1") || name.contains("Debian 8.1") || name.contains("CentOS 5.5")) {
			return Optional.empty();
		}
		if (name.contains("Debian 11.0")) {
			os = Optional.of(CommonOS.Linux);
			versions = Optional.of(DebianVersion.DEBIAN_11);
		}
		if (name.contains("Debian 10.0")) {
			os = Optional.of(CommonOS.Linux);
			versions = Optional.of(DebianVersion.DEBIAN_10);
		}
		if (name.contains("Debian 9.2")) {
			os = Optional.of(CommonOS.Linux);
			versions = Optional.of(DebianVersion.DEBIAN_9);
		}

		if (name.contains("CentOS 6.2") || name.contains("CentOS 6.7")) {
			os = Optional.of(CommonOS.Linux);
			versions = Optional.of(CentosVersion.CentOS_6);
		}
		if (name.contains("CentOS 7.0")) {
			os = Optional.of(CommonOS.Linux);
			versions = Optional.of(CentosVersion.CentOS_7);
		}
		if (name.contains("CentOS 8.0") || name.contains("CentOS 8.2")) {
			os = Optional.of(CommonOS.Linux);
			versions = Optional.of(CentosVersion.CentOS_8);
		}
		if (name.contains("RedHat / CentOS 9.0")) {
			os = Optional.of(CommonOS.Linux);
			versions = Optional.of(CentosVersion.CentOS_9);
		}

		if (name.contains("rhel90")) {
			os = Optional.of(CommonOS.Linux);
			versions = Optional.of(CentosVersion.CentOS_9);
		}

		if (name.contains("SUSE")) {
			return Optional.empty();
		}

		if (name.contains("Ubuntu 12.04") || name.contains("Ubuntu 14.04") || name.contains("ubuntu1410-clang")) {
			return Optional.empty();
		}

		if (name.contains("Ubuntu 16.04")) {
			os = Optional.of(CommonOS.Linux);
			versions = Optional.of(UbuntuVersion.Ubuntu_16_04);
		}
		if (name.contains("Ubuntu 18.04")) {
			os = Optional.of(CommonOS.Linux);
			versions = Optional.of(UbuntuVersion.Ubuntu_18_04);
		}
		if (name.contains("Ubuntu 20.04")) {
			os = Optional.of(CommonOS.Linux);
			versions = Optional.of(UbuntuVersion.Ubuntu_20_04);
		}
		if (name.contains("Ubuntu 22.04")) {
			os = Optional.of(CommonOS.Linux);
			versions = Optional.of(UbuntuVersion.Ubuntu_22_04);
		}

		if (name.contains("Linux (legacy)")) {
			os = Optional.of(CommonOS.Linux);
		}
		if (name.contains("Linux (legacy) undefined")) {
			os = Optional.of(CommonOS.Linux);
			cpuType = Optional.of(CPUType.X86);
			bitsize = Optional.of(BitSize.B32);
		}

		if (name.contains("macOS")) {
			os = Optional.of(CommonOS.OS_X);
		}

		if (name.contains("sunos5")) {
			os = Optional.of(CommonOS.Solaris);
		}

		if (!bitsize.isPresent()) {
			bitsize=Optional.of(BitSize.B64);
		}

		ImmutablePlatformMatch ret = PlatformMatch.builder()
			.os(os)
			.bitSize(bitsize)
			.cpuType(cpuType)
			.version(versions.map(Arrays::asList).orElse(Collections.emptyList()))
			.build();

		Preconditions.checkArgument(os.isPresent(), "no os for %s (%s)", name, ret);
		Preconditions.checkArgument(!ret.equals(PlatformMatch.any()), "could not detect %s", name);

		return Optional.of(ret);
	}

	static class Tuple<A, B> {
		private final A a;
		private final B b;
		public Tuple(A a, B b) {
			this.a = a;
			this.b = b;
		}
		public A a() {
			return a;
		}

		public B b() {
			return b;
		}
	}

	static <A,B> Tuple<A, B> tuple(A a, B b) {
		return new Tuple<>(a,b);
	}

	static List<ParsedVersion> parse(Document document, boolean isDevVersion) {
		List<ParsedVersion> versions = new ArrayList<>();
		Elements divs = document.select("div > div");
		for (Element div : divs) {
//      System.out.println("----------------");
			Element versionElement = div.selectFirst("h3");
			if (versionElement != null) {
				String version = versionElement.text();
//        System.out.println("Version: " + version);
//        System.out.println(div);
				List<ParsedDist> parsedDists = new ArrayList<>();
				Elements entries = div.select("div > ul > li");
				for (Element entry : entries) {
//          System.out.println("- - - - - - -");
					String name = entry.selectFirst("li > p").text();
//          System.out.println(" Name: " + name);
//          System.out.println(entry);
					List<ParsedUrl> parsedUrls = new ArrayList<>();
					Elements platforms = entry.select("li > ul > li");
					for (Element platform : platforms) {
//            System.out.println("~~~~~~~~");
//            System.out.println(platform);
						Elements packages = platform.select("li > p");
						for (Element ppackage : packages) {
							if (ppackage.text().startsWith("Archive:")) {
//                System.out.println("*********");
//                System.out.println(ppackage);
								Element urlElement = ppackage.selectFirst("a");
								String platFormUrl = urlElement.attr("href");
//                System.out.println("  Url: "+platFormUrl);
								parsedUrls.add(new ParsedUrl(platFormUrl));
							}
						}
					}
					parsedDists.add(new ParsedDist(name, parsedUrls));
				}
				versions.add(new ParsedVersion(version, isDevVersion, parsedDists));
			} else {
//        System.out.println("##############");
//        System.out.println(div);
			}
		}
		return versions;
	}
}
