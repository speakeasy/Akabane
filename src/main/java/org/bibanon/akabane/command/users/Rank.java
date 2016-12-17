/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bibanon.akabane.command.users;

/**
 *
 * @author speakeasy
 */
public enum Rank {
    OP("op"),
    HOP("hop"),
    VOICE("voice"),
    BLOCKED("blocked");

    private final String rank;

    Rank(String arank) {
        rank = arank;
    }

    private String getRank() {
        return rank;
    }

    static boolean hasPermission(Rank rank, String command) {
        switch (rank) {
            case OP: {
                return true;
            }
            case HOP: {
                switch (command) {
                    case ".is": {
                        return true;
                    }
                    case ".a": {
                        return true;
                    }
                    case ".rr": {
                        return true;
                    }
                    case ".time": {
                        return true;
                    }
                    case ".status": {
                        return true;
                    }
                    case ".help": { 
                        return true;
                    }
                    default: {
                        return true;
                    }
                }
            }
            case VOICE: {
                switch (command) {
                    case ".is": {
                        return true;
                    }
                    case ".a": {
                        return true;
                    }
                    case ".rr": {
                        return false;
                    }
                    case ".time": {
                        return true;
                    }
                    case ".status": {
                        return true;
                    }
                    case ".help": { 
                        return true;
                    }
                    default: {
                        return false;
                    }
                }
            }
            case BLOCKED: {
                return false;
            }
            default: {
                return false;
            }
        }
    }
}
