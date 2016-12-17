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

    private static GrabSite grabsite;
    private static String url = "";
    private static String igsets = "";
    private static String meta = "";
    private static boolean help;

    public CommandGrab(String cs) {
        super(cs);
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

        for (int i = 0; i < message.length; i++) {
            if (message[i] == "grab") {
                i++;
                url = message[i];
            }
            if (message[i] == "igsets") {
                i++;
                igsets = message[i];
            }
            if (message[i] == "meta") {
                i++;
                meta = message[i];
            }
        }
        if (url.isEmpty()) {
            help = true;
        }
        if (help) {
            event.respond("Usage: .a <grab http://example.com> [igsets list,of,igsets] [meta wararchives;metadat;for;ia]");
            grabsite = null;
            help = false;
        } else {
            if (!igsets.isEmpty()) {
                grabsite.setGrabSite(url, igsets);
            } else {
                grabsite.setGrabSite(url);
            }
            if (!meta.isEmpty()) {
                grabsite.setMetadata(meta);
            }
            grabsite.setMessageEvent(event);
            grabManager.addGrab(grabsite);
            grabsite = null;
            url = "";
            igsets = "";
            meta = "";
            help = false;
        }

    }
}
