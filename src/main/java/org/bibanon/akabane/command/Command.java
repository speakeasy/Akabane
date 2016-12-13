/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bibanon.akabane.command;

import java.util.HashMap;

/**
 *
 * @author speakeasy
 */
public abstract class Command {

    public String commandString;
    public Class commandClass;
    public int commandArgs;
    public HashMap<String, Boolean> commandArgsNames;

    public Command(String cs, Class cc, Integer argnum, HashMap<String, Boolean> argsNames) {
        commandString = cs;
        commandClass = cc;
        commandArgs = argnum;
        commandArgsNames = argsNames;
    }

    public void process(String[] message) {
    }

}
