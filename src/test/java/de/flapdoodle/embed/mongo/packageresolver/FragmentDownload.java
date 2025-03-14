package de.flapdoodle.embed.mongo.packageresolver;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class FragmentDownload {

	private static void download(String url, Path destination) throws IOException, InterruptedException {
		Path tempFile = Files.createTempFile("fragment-download", ".html");

		Process process = new ProcessBuilder()
			.command("google-chrome",
				"--incognito",
				"--proxy-auto-detect",
				"--temp-profile",
				"--headless",
				"--virtual-time-budget=5000",
				"--dump-dom",
				"https://www.mongodb.com/try/download/community-edition/releases")
			.inheritIO()
			.redirectOutput(tempFile.toFile())
			.start();

		int result = process.waitFor();
		process.destroy();
		if (result!=0) {
			throw new RuntimeException("chrome exited with: " + result);
		}

		System.out.println("parse: "+tempFile);
		Document parsed = Jsoup.parse(tempFile.toFile(), "UTF-8");
		// <main class="w-max-700 p-h-15 m-auto">
		Elements block = parsed.select("body main.w-max-700.p-h-15.m-auto");

		Files.write(destination, block.toString().getBytes(StandardCharsets.UTF_8), StandardOpenOption.CREATE, StandardOpenOption.TRUNCATE_EXISTING);
		System.out.println("done: "+destination);
	}
	
	public static void main(String[] args) throws IOException, InterruptedException {
		LocalDate now = LocalDate.now();
		String datePart = DateTimeFormatter.ofPattern("yyyy-MM-dd").format(now);
		Path basePath = Paths.get("src/test/resources/versions/react/");

		String downloadCenter = "https://www.mongodb.com/download-center/";
		String[][] downloads = new String[][] {
			{ "community/releases", "mongo-db-versions-" + datePart + ".html"},
			{ "community/releases/development", "mongo-db-versions-" + datePart + "-dev.html"},
			{ "community/releases/archive", "mongo-db-versions-" + datePart + "-archive.html"},
			{ "database-tools/releases/archive", "mongotools-versions-" + datePart + ".html"},
		};

		for (String[] line : downloads) {
			String url = downloadCenter + line[0];
			Path destination = basePath.resolve(line[1]);
			download(url, destination);
		}

//		Path htmlDump = Paths.get("releases-dump.html");
//
//		Process process = new ProcessBuilder()
//			.command("google-chrome",
//				"--incognito",
//				"--proxy-auto-detect",
//				"--temp-profile",
//				"--headless",
//				"--virtual-time-budget=5000",
//				"--dump-dom",
//				"https://www.mongodb.com/try/download/community-edition/releases")
//			.inheritIO()
//			.redirectOutput(htmlDump.toFile())
//			.start();
//
////		BufferedReader reader = new BufferedReader(new InputStreamReader(process.getInputStream()));
////
////		StringJoiner sj = new StringJoiner(System.getProperty("line.separator"));
////		reader.lines().iterator().forEachRemaining(sj::add);
////		result = sj.toString();
////
////		p.waitFor();
////		p.destroy();
//
////		Thread readInput = Executors.defaultThreadFactory().newThread(() -> {
////			try {
////				StringBuilder sb = new StringBuilder();
////
////				BufferedInputStream is = new BufferedInputStream(process.getInputStream());
////				int read;
////				byte[] buf = new byte[1024];
////				while ((read = is.read(buf)) != -1) {
////					sb.append(new String(buf, 0, read));
////				}
////			} catch (IOException e) {
////				throw new RuntimeException(e);
////			}
////		});
////
////		readInput.start();
//
//		int result = process.waitFor();
//		process.destroy();
//		System.out.println("chrome exited with: " + result);
//
//		System.out.println("parse "+htmlDump);
//		Document parsed = Jsoup.parse(htmlDump.toFile(), "UTF-8");
//		// <main class="w-max-700 p-h-15 m-auto">
//		Elements block = parsed.select("body main.w-max-700.p-h-15.m-auto");
//		System.out.println(block);
	}

}
