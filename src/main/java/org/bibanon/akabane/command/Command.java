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
public abstract class Command {

    public String commandString;

    public Command(String cs) {
        commandString = cs;
    }

    public void process(MessageEvent event) {

    }

}
