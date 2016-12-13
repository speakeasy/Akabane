/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bibanon.akabane.command.users;

import java.util.ArrayList;
import org.pircbotx.hooks.events.MessageEvent;

public class Users {

    private ArrayList<User> Users = new ArrayList<User>();

    public Users() {
    }

    public void removeUser(String suser) {
        for (User u : Users) {
            if (u.name == suser) {
                Users.remove(u);
                return;
            }
        }
    }

    public void addUser(String suser, Rank rank) {

        for (User u : Users) {
            if (u.name == suser) {
                if (u.rank == rank) {
                    return;
                }
                u.rank = rank;
                return;
            }
        }
        User user = new User(suser, rank);
        Users.add(user);
    }

    public boolean isUser(String user) {
        for (User u : Users) {
            if (u.name == user) {
                return true;
            }
        }
        return false;
    }

    public boolean isBlocked(String user) {
        for (User u : Users) {
            if (u.name == user) {
                if (u.rank == Rank.BLOCKED) {
                    return true;
                }
            }
        }
        return false;
    }

    public Rank getRank(String user) {
        Rank rank = Rank.BLOCKED;
        if (isUser(user)) {
            rank = getUser(user).getRank();
        }
        if (isBlocked(user)) {
            return Rank.BLOCKED;
        }
        return getUser(user).rank;
    }

    public User getUser(String name) {
        for (User u : Users) {
            if (u.name == name) {
                return u;
            }
        }
        return null;
    }

    public boolean hasPermission(String user, String command) {
        Rank rank = getRank(user);
        return Rank.hasPermission(rank, command);
    }
}
