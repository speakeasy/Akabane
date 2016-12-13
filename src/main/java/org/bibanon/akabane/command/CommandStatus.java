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
public class CommandStatus extends Command {

    public CommandStatus(String cs, Class cc, Integer argnum, HashMap<String, Boolean> argsNames) {
        super(cs, cc, argnum, argsNames);
    }

}
