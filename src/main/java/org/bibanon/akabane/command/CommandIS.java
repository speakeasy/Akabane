/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bibanon.akabane.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
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
    
    public CommandIS(String cs, Integer argnum, HashMap<String, Boolean> argsNames) {
        super(cs, argnum, argsNames);
        archiveis.init();
    }

    @Override
    public void process(String[] message, MessageEvent event) {
        if (message.length > 1) {
            for (int i = 0; i < message.length; i++) {
                for (String arg : commandArgsNames.keySet()) {
                    if (message[i] == arg && i < message.length + 1 &! commandArgsNames.containsKey(message[i+i])) {
                        switch(arg) {
                            case "url" : {
                                i++;
                                url(message[i]);
                            }
                            default : {
                                ;
                            }
                        }
                    } else {
                        if (message[i] == "help") {
                            help = true;
                        }
                    }
                }
            }
        } else {
            help = true;
        }
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
