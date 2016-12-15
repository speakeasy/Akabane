/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bibanon.akabane.command;

import java.util.HashMap;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author speakeasy
 */
public class CommandHelp extends Command {
    
    public CommandHelp(String cs, Class cc, Integer argnum, HashMap<String, Boolean> argsNames) {
        super(cs, cc, argnum, argsNames);
    }
    
    @Override
    public void process(String[] message, MessageEvent event) {
        this.event = event;
        this.event.respond("Usage: Commands: <.a|.set|.status|.is|.rr|.time> <options> <>=required. []=optional.");
        this.event = null;
        return;
    }
}
