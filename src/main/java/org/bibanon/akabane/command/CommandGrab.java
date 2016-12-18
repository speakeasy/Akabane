/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bibanon.akabane.command;

import java.util.HashMap;
import org.bibanon.akabane.command.archival.GrabSite;
import org.bibanon.akabane.command.archival.GrabSiteState;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author speakeasy
 */
public class CommandGrab extends Command {
    
    //private GrabSite grabsite;
    private String url = "";
    private String igsets = "";
    private String meta = "";
    private boolean furl = false;
    private boolean figsets = false;
    private boolean fmeta = false;
    private boolean help = false;
    
    public static HashMap<GrabSite, Thread> grabsites = new HashMap<GrabSite, Thread>();
    
    public CommandGrab(String cs) {
        super(cs);
    }
    
    @Override
    public void process(MessageEvent event) {
        execute(event);
    }
    
    private void execute(MessageEvent event) {
        String[] message = event.getMessage().split(" ");
        help = false;
        for (int i = 0; i < message.length; i++) {
            if (message[i] == "help") {
                help = true;
                //System.out.println("help a");
            }
        }
        
        for (String str : message) {
            //System.out.println("Message str: \"" + str + "\"");
            if (furl) {
                furl = false;
                url = str;
                System.out.println("URL: " + url);
            }
            if (figsets) {
                figsets = false;
                igsets = str;
                System.out.println("igsets: " + igsets);
            }
            if (fmeta) {
                fmeta = false;
                meta = str;
                System.out.println("meta: " + meta);
            }
            if (str.equals("grab")) {
                furl = true;
            }
            if (str.equals("igsets")) {
                figsets = true;
            }
            if (str.equals("meta")) {
                fmeta = true;
            }
        }
        if (url.equals("")) {
            help = true;
        }
        if (help) {
            event.respond("Usage: .a <grab http://example.com> [igsets list,of,igsets] [meta wararchives;metadat;for;ia]");
        } else {
            GrabSite grabsite = new GrabSite();
            if (!igsets.equals("")) {
                grabsite.setGrabSite(url, igsets);
            } else {
                grabsite.setGrabSite(url);
            }
            if (!meta.equals("")) {
                grabsite.setMetadata(meta);
            }
            grabsite.setMessageEvent(event);
            grabsites.put(grabsite, grabsite.thread);
            grabsite.thread.start();
            event.respond("Grab started.");
            for(GrabSite gs : grabsites.keySet()) {
                if(gs.state.equals(GrabSiteState.CANCALLED) || gs.state.equals(GrabSiteState.FINISHED_UPLOADING)) {
                    grabsites.remove(gs);
                }
            }
        }
        url = "";
        igsets = "";
        meta = "";
        help = false;
    }
}
