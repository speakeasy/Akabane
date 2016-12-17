/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bibanon.akabane.command;

import java.util.HashMap;
import java.util.Random;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author speakeasy
 */
public class CommandRR extends Command {

    Random random = new Random();

    public CommandRR(String cs, Class cc, Integer argnum, HashMap<String, Boolean> argsNames) {
        super(cs, cc, argnum, argsNames);
    }

    @Override
    public void process(String[] message, MessageEvent event) {
        execute(event);
    }

    private void execute(MessageEvent event) {
        if (random.nextInt() % 6 == 0) {
            event.respond("*bang*");
        } else {
            event.respond("*click*");
        }
    }

}
