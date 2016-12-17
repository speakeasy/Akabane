/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bibanon.akabane.command;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author speakeasy
 */
public class CommandHelp extends Command {

    public CommandHelp(String cs) {
        super(cs);
    }

    @Override
    public void process(MessageEvent event) {
        execute(event);
    }
    
    private void execute(MessageEvent event) {
        event.respond("Usage: Commands: <.a|.set|.status|.is|.rr|.time> <options> <>=required. []=optional.");
    }
}
