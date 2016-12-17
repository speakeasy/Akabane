/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bibanon.akabane.command;

import org.bibanon.akabane.command.archival.ArchiveIsHtmlParser;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author speakeasy
 */
public class CommandIS extends Command {

    private static ArchiveIsHtmlParser archiveis = new ArchiveIsHtmlParser();
    private static String url;
    private boolean help = false;

    public CommandIS(String cs) {
        super(cs);
        archiveis.init();
    }

    @Override
    public void process(MessageEvent event) {
        execute(event);
    }

    private void execute(MessageEvent event) {
        String[] message = event.getMessage().split(" ");
        for (int i = 0; i < message.length; i++) {
            if (message[i] == "help") {
                help = true;
            }
        }
        if(message.length == 2) {
            url = message[1];
        }
        if (help) {
            event.respond("Usage: .is http://example.com");
            help = false;
        } else {
            String theUrl = archiveis.submitURL(url);
            event.respond("URL Found: " + theUrl);
        }
        url = null;
        help = false;

    }

}
