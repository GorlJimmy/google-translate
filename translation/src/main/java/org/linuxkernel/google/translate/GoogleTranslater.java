package org.linuxkernel.google.translate;

import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.linuxkernel.google.translate.config.Config;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GoogleTranslater implements Translater {

	private static final Logger LOG = LoggerFactory
			.getLogger(GoogleTranslater.class);
	private static final String ID_RESULTBOX = "result_box";

	@Override
	public String translate(String text, String targetLang) {
		return translate(text, "", targetLang);
	}

	@Override
	public String[] translate(String[] texts, String targetLang) {
		return translate(texts, "", targetLang);
	}

	@Override
	public String translate(String text, String srcLang, String targetLang) {
		return execute(text, srcLang, targetLang);
	}

	@Override
	public String[] translate(String[] texts, String srcLang, String targetLang) {
		StringBuilder content = new StringBuilder();
		int size = texts.length;
		for (int i = 0; i < size; i++) {
			if (i < size - 1) {
				content.append(texts[i]).append("{*}");
			} else {
				content.append(texts[i]);
			}
		}
		String[] temps = execute(content.toString(), srcLang, targetLang)
				.split("\\{\\*\\}");
		size = temps.length;
		for (int i = 0; i < size; i++) {
			temps[i] = temps[i].trim();
		}
		return temps;
	}

	private String execute(final String text, final String srcLang,
			final String targetLang) {
		if (GoogleLanguage.fromString(srcLang) == null
				|| GoogleLanguage.fromString(targetLang) == null) {
			throw new RuntimeException("Google not support language");
		}
		try {
			Document document = Jsoup.connect("https://translate.google.com")
					.data("sl", srcLang).data("ie", "UTF-8")
					.data("oe", "UTF-8").data("text", text)
					.data("tl", targetLang).userAgent(Config.USER_AGENT)
					.cookie("Cookie", Config.COOKIE).timeout(2000000).post();
			Element element = document.getElementById(ID_RESULTBOX);
			return element.text();
		} catch (IOException e) {
			LOG.error("translate error: ", e);
		}
		return null;
	}
}