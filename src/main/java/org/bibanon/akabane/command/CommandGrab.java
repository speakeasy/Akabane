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

    public CommandGrab(String cs, Class cc, Integer argnum, HashMap<String, Boolean> argsNames) {
        super(cs, cc, argnum, argsNames);
    }

    @Override
    public void process(String[] message, MessageEvent event) {
        if (message.length > 1) {
            Method method;
            grabsite = new GrabSite();
            for (int i = 0; i < message.length; i++) {
                for (String arg : commandArgsNames.keySet()) {
                    if (message[i] == arg) {
                        if (commandArgsNames.get(arg)) {
                            try {
                                // command takes arg
                                method = commandClass.getDeclaredMethod(arg, String.class);
                                i++;
                                method.invoke(commandClass, message[i]);
                            } catch (NoSuchMethodException ex) {
                                Logger.getLogger(CommandGrab.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (SecurityException ex) {
                                Logger.getLogger(CommandGrab.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IllegalAccessException ex) {
                                Logger.getLogger(CommandGrab.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IllegalArgumentException ex) {
                                Logger.getLogger(CommandGrab.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (InvocationTargetException ex) {
                                Logger.getLogger(CommandGrab.class.getName()).log(Level.SEVERE, null, ex);
                            }
                        }
                    }
                }
                execute(event);
            }
        } else {
            event.respond("HELP: Usage: [not implemented]");
            return;
        }
    }

    private void execute(MessageEvent event) {

        if (help) {
            event.respond("Usage: .a <grab http://example.com> [igsets list,of,igsets] [meta wararchives;metadat;for;ia]");
            grabsite = null;
            help = false;
            return;
        }

        synchronized (grabManager) {
            grabManager.addGrab(grabsite);
        }
        grabsite = null;
    }

    public void grab(String url) {
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

    private boolean contains(String[] message, String contain) {
        for (String s : message) {
            if (s == contain) {
                return true;
            }
        }
        return false;
    }
}
