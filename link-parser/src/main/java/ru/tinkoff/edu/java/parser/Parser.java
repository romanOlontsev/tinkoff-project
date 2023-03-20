package ru.tinkoff.edu.java.parser;

import ru.tinkoff.edu.java.parser.links.LinkParse;
import ru.tinkoff.edu.java.parser.result.ParseResult;

import java.net.MalformedURLException;
import java.net.URISyntaxException;
import java.net.URL;

public class Parser {
    private LinkParse linkParse;

    public void setLinks(LinkParse linkParse) {
        this.linkParse = linkParse;
    }

    public ParseResult checkLink(String link) {
        if (!isValidUrl(link)) {
            return null;
        }
        return linkParse.check(link);
    }

    private boolean isValidUrl(String url) {
        try {
            new URL(url).toURI();
            return true;
        } catch (MalformedURLException | URISyntaxException e) {
            return false;
        }
    }
}