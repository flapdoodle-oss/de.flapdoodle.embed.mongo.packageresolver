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

import com.google.common.io.Resources;
import de.flapdoodle.types.Pair;
import de.flapdoodle.types.Try;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public class MongoToolsPackageHtmlPageParser extends AbstractPackageHtmlParser {
		public static void main(String[] args) throws IOException {
			List<Pair<String, Boolean>> resources = Arrays.asList(
				Pair.of("versions/react/mongotools-versions-2021-10-28.html", false),
				Pair.of("versions/react/mongotools-versions-2022-09-25.html", false),
				Pair.of("versions/react/mongotools-versions-2022-10-14.html", false),
				Pair.of("versions/react/mongotools-versions-2022-11-27.html", false),
				Pair.of("versions/react/mongotools-versions-2023-02-12.html", false),
				Pair.of("versions/react/mongotools-versions-2023-03-16.html", false),
				Pair.of("versions/react/mongotools-versions-2023-07-25.html", false)
			);

			List<List<ParsedVersion>> allVersions = resources.stream()
				//.map(it -> Try.supplier(() -> parse(Jsoup.parse(Resources.toString(Resources.getResource(it), StandardCharsets.UTF_8)))))
				.map(it -> it.mapFirst(path -> Try.supplier(() -> Resources.toString(Resources.getResource(path), StandardCharsets.UTF_8))
					.mapToUncheckedException(RuntimeException::new)
					.get()))
				.map(it -> it.mapFirst(Jsoup::parse))
				.map(it -> MongoToolsPackageHtmlPageParser.parse(it.first(), it.second()))
				.collect(Collectors.toList());

			ParsedVersions versions = new ParsedVersions(mergeAll(allVersions));

//			URL url = Resources.getResource("versions/react/mongotools-versions-2021-10-28.html");
//				System.out.println("-> " + url);
//				Document document = Jsoup.parse(Resources.toString(url, StandardCharsets.UTF_8));
//
//				ParsedVersions versions = new ParsedVersions(parse(document));
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
		}

		static List<ParsedVersion> parse(Document document, boolean isDevVersion) {
				List<ParsedVersion> versions=new ArrayList<>();
				Elements divs = document.select("div > div");
				for (Element div : divs) {
//      System.out.println("----------------");
						Element versionElement = div.selectFirst("h3");
						if (versionElement != null) {
								String version = versionElement.text();
//        System.out.println("Version: " + version);
//        System.out.println(div);
								List<ParsedDist> parsedDists=new ArrayList<>();
								Elements entries = div.select("div > ul > li");
								for (Element entry : entries) {
//          System.out.println("- - - - - - -");
										String name = entry.selectFirst("li > p").text();
//          System.out.println(" Name: " + name);
//          System.out.println(entry);
										List<ParsedUrl> parsedUrls=new ArrayList<>();
										Elements platforms = entry.select("li > ul > li");
										for (Element platform : platforms) {
//            System.out.println("~~~~~~~~");
//            System.out.println(platform);
												Elements packages = platform.select("li");
												for (Element ppackage : packages) {
														if (ppackage.text().startsWith("Archive:")) {
//                System.out.println("*********");
//                System.out.println(ppackage);
																Element urlElement = ppackage.selectFirst("a");
																String platFormUrl=urlElement.attr("href");
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
