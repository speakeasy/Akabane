/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bibanon.akabane.users;

import java.util.HashMap;

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

}
