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
import org.bibanon.akabane.command.archival.GrabSite;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author speakeasy
 */
public class CommandGrab extends Command {

    GrabSite grabsite;
    boolean help = false;

    public CommandGrab(String cs, Integer argnum, HashMap<String, Boolean> argsNames) {
        super(cs, argnum, argsNames);
    }

    @Override
    public void process(String[] message, MessageEvent event) {
        if (message.length > 1) {
            grabsite = new GrabSite();
            for (int i = 0; i < message.length; i++) {
                for (String arg : commandArgsNames.keySet()) {
                    if (message[i] == arg && i < message.length + 1 &! commandArgsNames.containsKey(message[i+i])) {
                        switch(arg) {
                            case "grab" : {
                                i++;
                                grab(message[i]);
                            }
                            case "igsets" : {
                                i++;
                                igsets(message[i]);
                            }
                            case "meta": {
                                i++;
                                meta(message[i]);
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

    private void execute(MessageEvent event) {

        if (help) {
            event.respond("Usage: .a <grab http://example.com> [igsets list,of,igsets] [meta wararchives;metadat;for;ia]");
            grabsite = null;
            help = false;
            return;
        }
        grabManager.addGrab(grabsite);
        grabsite = null;
    }

    public void grab(String url) {
        help = false;
        grabsite = new GrabSite();
        grabsite.setGrabSite(url);
    }

    public void help() {
        help = true;
    }

    public void igsets(String igsets) {
        grabsite.setGrabSite("", igsets);
    }

    public void meta(String meta) {
        grabsite.setMetadata(meta);
    }
}
