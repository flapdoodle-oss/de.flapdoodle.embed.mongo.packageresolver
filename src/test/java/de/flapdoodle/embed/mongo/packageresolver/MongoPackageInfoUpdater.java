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

import com.google.common.io.Resources;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.net.URL;
import java.nio.charset.StandardCharsets;

public class MongoPackageInfoUpdater {

	public static void main(String[] args) throws IOException {
		System.out.println("--------------------------------------");
		System.out.println("process mongodb download version files");
		System.out.println("--------------------------------------");

		processLinuxVersions(Resources.getResource("versions/linux.html"));
	}
	
	private static void processLinuxVersions(URL resource) throws IOException {
		Document document = Jsoup.parse(Resources.toString(resource, StandardCharsets.UTF_8));
		Elements rows = document.select("table > tbody > tr");

		rows.forEach(row -> {
//			System.out.print(".");
//			System.out.println(row);
			Element link = row.selectFirst("td > a");
			if (link!=null) {
				String url = link.attr("href");
				System.out.println("-> " + url);
			} else {
				System.out.println("no link: "+row);
			}
		});
		System.out.println("done");
	}
}
