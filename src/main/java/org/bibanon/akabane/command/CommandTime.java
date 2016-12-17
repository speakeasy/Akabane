/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bibanon.akabane.command;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author speakeasy
 */
public class CommandTime extends Command {

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public CommandTime(String cs, Integer argnum, HashMap<String, Boolean> argsNames) {
        super(cs, argnum, argsNames);
    }

    @Override
    public void process(String[] message, MessageEvent event) {
        execute(event);
    }

    private void execute(MessageEvent event) {
        Date date = new Date();
        event.respond("The current time is: " + dateFormat.format(date));
    }

}
