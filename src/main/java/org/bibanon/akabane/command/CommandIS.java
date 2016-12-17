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

    public CommandIS(String cs, Integer argnum, HashMap<String, Boolean> argsNames) {
        super(cs, argnum, argsNames);
        archiveis.init();
    }

    @Override
    public void process(String[] message, MessageEvent event) {
        if (message.length > 1) {
            Method method;
            for (int i = 0; i < message.length; i++) {
                for (String arg : commandArgsNames.keySet()) {
                    if (message[i] == arg) {
                        if (commandArgsNames.get(arg)) {
                            try {
                                // command takes arg
                                method = this.getClass().getDeclaredMethod(arg, String.class);
                                i++;
                                method.invoke(this.getClass(), message[i]);
                            } catch (NoSuchMethodException ex) {
                                Logger.getLogger(CommandIS.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IllegalAccessException ex) {
                                Logger.getLogger(CommandIS.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (IllegalArgumentException ex) {
                                Logger.getLogger(CommandIS.class.getName()).log(Level.SEVERE, null, ex);
                            } catch (InvocationTargetException ex) {
                                Logger.getLogger(CommandIS.class.getName()).log(Level.SEVERE, null, ex);
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

    public void url(String url) {
        this.url = url;
    }

    public void execute(MessageEvent event) {
        String theUrl = archiveis.submitURL(url);
        url = null;
        event.respond("URL Found: " + theUrl);
    }

}
