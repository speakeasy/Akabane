/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bibanon.akabane.command;

import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bibanon.akabane.command.users.Users;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author speakeasy
 */
public class RunnableCommandProcessor implements Runnable {

    public static RunnableCommandProcessor processor;
    public static Users users;
    public static final Commands commands = new Commands();
    private static ArrayList<MessageEvent> messages = new ArrayList<MessageEvent>();
    public static ArrayList<MessageEvent> addMessages = new ArrayList<MessageEvent>();
    private static boolean running = true;
    private static boolean mlock = false;

    public RunnableCommandProcessor() {
        processor = new RunnableCommandProcessor();
    }

    @Override
    public void run() {
        while (running) {
            try {
                Thread.sleep(11);
                while (mlock) {
                    Thread.sleep(2);
                }
                if (!mlock) {
                    for (MessageEvent ev : addMessages) {
                        messages.add(addMessages.remove(addMessages.indexOf(ev)));
                    }
                }
                if (messages.size() > 0) {
                    for (MessageEvent event : messages) {
                        process(event);
                    }
                }
            } catch (InterruptedException ex) {
                Logger.getLogger(RunnableCommandProcessor.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    public void updateUsers(Users users) {
        this.users = users;
    }

    public void process(MessageEvent event) {
        this.users = users;
        String[] message = event.getMessage().split(" ");
        // CMD: Check if is user.
        if (!users.isUser(event.getUser().getNick())) {
            return;
        }
        for (Command c : commands.commands) {
            if (message[0] == c.commandString) {
                // CMD: Permissions.
                if (hasPermissions(event, c)) {
                    switch (c.commandString) {
                        case ".status": {
                            CommandStatus cc = (CommandStatus) c;
                            cc.process(message, event);
                            return;
                        }
                        case ".a": {
                            CommandGrab cc = (CommandGrab) c;
                            cc.process(message, event);
                            return;
                        }
                        case ".set": {
                            CommandSet cc = (CommandSet) c;
                            cc.process(message, event);
                            return;
                        }
                        case ".is": {
                            CommandIS cc = (CommandIS) c;
                            cc.process(message, event);
                            return;
                        }
                        case ".rr": {
                            CommandRR cc = (CommandRR) c;
                            cc.process(message, event);
                            return;
                        }
                        case ".time": {
                            CommandTime cc = (CommandTime) c;
                            cc.process(message, event);
                            return;
                        }
                        default: {
                            CommandHelp cc = (CommandHelp) c;
                            cc.process(message, event);
                            return;
                        }
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

    public void addEvent(MessageEvent event) throws InterruptedException {
        mlock = true;
        addMessages.add(event);
        mlock = false;
    }

    public void die() {
        running = false;
    }
}
