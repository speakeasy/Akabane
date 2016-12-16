/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bibanon.akabane.command;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author speakeasy
 */
public class CommandTime extends Command {

    DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");

    public CommandTime(String cs, Class cc, Integer argnum, HashMap<String, Boolean> argsNames) {
        super(cs, cc, argnum, argsNames);
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
