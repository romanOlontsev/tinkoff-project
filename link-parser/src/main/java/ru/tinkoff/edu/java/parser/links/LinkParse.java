package ru.tinkoff.edu.java.parser.links;

import ru.tinkoff.edu.java.parser.result.ParseResult;

public sealed abstract class LinkParse permits GitHubLinkParse, StackOverflowLinkParse {
    private LinkParse next;

    public static LinkParse link(LinkParse first, LinkParse... chain) {
        LinkParse head = first;
        for (LinkParse nextInChain : chain) {
            head.next = nextInChain;
            head = nextInChain;
        }
        return first;
    }

    public abstract ParseResult check(String link);

    protected ParseResult checkNext(String link) {
        if (next == null) {
            return null;
        }
        return next.check(link);
    }
}
