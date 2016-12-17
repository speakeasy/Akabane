/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bibanon.akabane.command;

import org.bibanon.akabane.command.users.Users;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author speakeasy
 */
public class ProcessCommands {

    public static ProcessCommands processor;
    public static Users users;
    public static Commands commands = new Commands();

    public ProcessCommands() {
    }

    public void updateUsers(Users users) {
        ProcessCommands.users = users;
    }

    public void process(MessageEvent event) {
        String[] message = event.getMessage().split(" ");
        // CMD: Check if is user.
        if (!users.isUser(event.getUser().getNick())) {
            return;
        }
        for (Command c : Commands.commands) {
            if (message[0].equals(c.commandString)) {
                // CMD: Permissions.
                switch (c.commandString) {
                    case ".status": {
                        CommandStatus cc = (CommandStatus) c;
                        if (hasPermissions(event, cc)) {
                            cc.process(event);
                            return;
                        }
                    }
                    case ".a": {
                        CommandGrab cc = (CommandGrab) c;
                        if (hasPermissions(event, cc)) {
                            cc.process(event);
                        }
                        return;
                    }
                    case ".set": {
                        CommandSet cc = (CommandSet) c;
                        if (hasPermissions(event, cc)) {
                            cc.process(event);
                        }
                        return;
                    }
                    case ".is": {
                        CommandIS cc = (CommandIS) c;
                        if (hasPermissions(event, cc)) {
                            cc.process(event);
                        }
                        return;
                    }
                    case ".rr": {
                        CommandRR cc = (CommandRR) c;
                        if (hasPermissions(event, cc)) {
                            cc.process(event);
                        }
                        return;
                    }
                    case ".time": {
                        CommandTime cc = (CommandTime) c;
                        if (hasPermissions(event, cc)) {
                            cc.process(event);
                        }
                        return;
                    }
                    case ".help": {
                        CommandHelp cc = (CommandHelp) c;
                        if (hasPermissions(event, cc)) {
                            cc.process(event);
                        }
                        return;
                    }
                    default: {
                        return;
                    }

                }
            }
        }
    }

    public boolean hasPermissions(MessageEvent event, Command command) {
        if (users.hasPermission(event.getUser().getNick(), command.commandString)) {
            return true;
        }
        return false;
    }
}
