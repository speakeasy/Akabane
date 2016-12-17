/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bibanon.akabane.command;

import java.util.HashMap;
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

    public void url(String url) {
        this.url = url;
    }

    public void execute(MessageEvent event) {
        if(help) {
            event.respond("Usage: .is url http://example.com");
            help = false;
            return;
        }
        String theUrl = archiveis.submitURL(url);
        url = null;
        event.respond("URL Found: " + theUrl);
    }

}
