/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bibanon.akabane.command;

/**
 *
 * @author speakeasy
 */
public class Command {
    public String commandString;
    public Class commandClass;
    public Command(String cs, Class cc) {
        commandString = cs;
        commandClass = cc;
    }
}
