/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bibanon.akabane.command;

import java.util.ArrayList;
import java.util.HashMap;

/**
 *
 * @author speakeasy
 */
public class Commands {
    public static ArrayList<Command> commands = new ArrayList<Command>();
    public Commands() {
        init();

    }
    
    private void init() {
        HashMap<String, Boolean> argsNames = new HashMap<String, Boolean>();
        argsNames.put(".help", Boolean.FALSE); // help doesn't take args.
        commands.add(new Command(".a", CommandGrab.class, argsNames.size(), argsNames));
        argsNames = new HashMap<String, Boolean>();
        //commands.add(new Command(".is", CommandIS.class));
        //commands.add(new Command(".rr", CommandRR.class));
        //commands.add(new Command(".time", CommandTime.class));
    }
}
