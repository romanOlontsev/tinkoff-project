package ru.tinkoff.edu.java.parser.links;

import ru.tinkoff.edu.java.parser.links.LinkParse;
import ru.tinkoff.edu.java.parser.result.GitHubResultRecord;
import ru.tinkoff.edu.java.parser.result.ParseResult;

import java.util.Objects;

public final class GitHubLinkParse extends LinkParse {
    @Override
    public ParseResult check(String link) {
        String[] splitLink = link.split("/");
        if (splitLink[2].equals("github.com") && splitLink.length > 4) {
            return new GitHubResultRecord(splitLink[3], splitLink[4]);
        }
        return checkNext(link);
    }
}
