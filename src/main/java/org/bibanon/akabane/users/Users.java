/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bibanon.akabane.users;

import org.pircbotx.hooks.events.MessageEvent;

public class Users {

    UsersYAML Users = new UsersYAML();

    public void tmpImit() {
        if (Users.firstRun()) {
            addUser("x404102", Rank.SUPER);
            addUser("antonizoon", Rank.SUPER);
            addUser("Akaibu", Rank.USER);
            addUser("Gazlene", Rank.USER);
            addUser("r3c0d3x", Rank.USER);
            addUser("jondon", Rank.USER);
            Users.writeOut();
        }
        Users.loadUsers();
    }

    public void addUser(String suser, Rank rank) {
        User user = new User(suser, rank);
        Users.addUser(user);
    }

    public boolean isUser(String user) {
        if (Users.isUser(user)) {
            return true;
        }
        return false;
    }
    
    public boolean isBlocked(String user) {
        if (Users.isBlocked(user)) {
            return true;
        }
        return false;
    }

    public Rank getRank(String user) {
        Rank rank = Rank.VISITOR;
        if (Users.isUser(user)) {
            rank = Users.getUser(user).getRank();
        }
        if(Users.isBlocked(user)) {
                return Rank.BLOCKED;
        }
        return rank;
    }

    public boolean hasPermission(String user, String command) {
        Rank rank = getRank(user);
        return Rank.hasPermission(rank, command);
    }

    public void addUser(String[] cmdutil, MessageEvent event) {
        if(hasPermission(event.getUser().getNick(), ".add")) {
            if(cmdutil.length == 2) {
                for(Rank r : Rank.values()) {
                    if(r.toString().equals(cmdutil[1].toUpperCase())) {
                        if(cmdutil[0].length() >= 3 &! isUser(cmdutil[0]) &! isBlocked(cmdutil[0])) {
                            addUser(cmdutil[0], r);
                            Users.writeOut();
                            return;
                        }
                    }
                }
            }
        }
    }

    public void blockUser(String[] cmdutil, MessageEvent event) {
        if(hasPermission(event.getUser().getNick(), ".block")) {
            if(cmdutil.length == 1 && cmdutil[0].length() >= 3) {
                if(Users.isUser(cmdutil[0])) {
                    Users.addBlocked(Users.getUser(cmdutil[0]));
                } else {
                    User blocked = new User(cmdutil[0], Rank.BLOCKED);
                    Users.addBlocked(blocked);
                    Users.writeOut();
                }
            }
        }
    }

}
