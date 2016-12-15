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
import org.bibanon.akabane.ArchiveIsHtmlParser;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author speakeasy
 */
public class CommandIS extends Command {

    private static ArchiveIsHtmlParser archiveis = new ArchiveIsHtmlParser();
    private static String url;

    public CommandIS(String cs, Class cc, Integer argnum, HashMap<String, Boolean> argsNames) {
        super(cs, cc, argnum, argsNames);
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

    public void url(String url) {
        this.url = url;
    }

    private void execute(MessageEvent event) {
        archiveis.init();
        archiveis.submitURL(url);
        url = null;
    }

}
