package ru.tinkoff.edu.java.parser;

import ru.tinkoff.edu.java.parser.links.LinkParse;
import ru.tinkoff.edu.java.parser.links.GitHubLinkParse;
import ru.tinkoff.edu.java.parser.links.StackOverflowLinkParse;
import ru.tinkoff.edu.java.parser.result.ParseResult;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Objects;

public class LinkParserApplication {
    private static final BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
    private static Parser parser;

    private static void init() {
        parser = new Parser();
        LinkParse supportedLinkParse = LinkParse.link(
                new GitHubLinkParse(),
                new StackOverflowLinkParse());
        parser.setLinks(supportedLinkParse);
    }

    public static void main(String[] args) throws IOException {
        init();
        String input;

        while (true) {
            System.out.print("Enter link: ");
            input = reader.readLine();
            if (Objects.equals(input, "exit")) {
                break;
            }
            ParseResult parseResult = parser.checkLink(input);
            System.out.println(parseResult == null ? null : parseResult.getResult());
        }
    }
}