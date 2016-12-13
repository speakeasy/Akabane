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
        
        // CMD: .status 
        argsNames.put("list", Boolean.FALSE); // .status list, lists jobs
        commands.add(new Command(".status", CommandStatus.class, argsNames.size(), argsNames));
        
        argsNames = new HashMap<String, Boolean>();
        
        // CMD: .a
        argsNames.put("help", Boolean.FALSE); // help doesn't take args.
        argsNames.put("grab", Boolean.TRUE); // grab takes url to grab.
        argsNames.put("meta", Boolean.TRUE); // meta takes ; separated values. warcarchives;website;...
        commands.add(new CommandGrab(".a", CommandGrab.class, argsNames.size(), argsNames));
        
        argsNames = new HashMap<String, Boolean>();
        
        // CMD: .set
        argsNames.put("job", Boolean.TRUE); // .set <<"job" <id#>> igsets,seperated,values>.
        commands.add(new CommandSet(".set", CommandSet.class, argsNames.size(), argsNames));
        
        argsNames = new HashMap<String, Boolean>();
        
        // CMD: .is
        argsNames.put("url", Boolean.TRUE); // url to archive.
        commands.add(new CommandIS(".is", CommandIS.class, argsNames.size(), argsNames));
        
        argsNames = new HashMap<String, Boolean>();
        
        // CMD: .rr
        commands.add(new CommandRR(".rr", CommandRR.class, argsNames.size(), argsNames));
        
        argsNames = new HashMap<String, Boolean>();
        
        // CMD: .time
        commands.add(new CommandTime(".time", CommandTime.class, argsNames.size(), argsNames));
    }
}
