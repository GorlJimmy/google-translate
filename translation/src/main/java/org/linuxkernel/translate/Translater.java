package org.linuxkernel.translate;

public interface Translater {
    public String translate(final String text, final String targetLang);
    public String[] translate(final String[] texts, final String targetLang);
    public String translate(final String text, final String srcLang, final String targetLang);
    public String[] translate(final String[] texts, final String srcLang, final String targetLang);
}