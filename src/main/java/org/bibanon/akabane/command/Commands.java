/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bibanon.akabane.command;

import java.util.ArrayList;

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
        // Register commands.
        commands.add(new CommandStatus(".status"));
        commands.add(new CommandGrab(".a"));
        commands.add(new CommandSet(".set"));
        commands.add(new CommandIS(".is"));
        commands.add(new CommandRR(".rr"));
        commands.add(new CommandTime(".time"));
        commands.add(new CommandHelp(".help"));
    }
}
