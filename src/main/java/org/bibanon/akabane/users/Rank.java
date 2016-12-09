/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bibanon.akabane.users;

/**
 *
 * @author speakeasy
 */
public enum Rank {
    SUPER ("super"),
    USER ("user"),
    VISITOR ("visitor"),
    BLOCKED ("blocked");
    
    private final String rank;
    
    Rank(String arank) {
        rank = arank;
    }
    
    private String getRank() {
        return rank;
    }

    static boolean hasPermission(Rank rank, String command) {
        switch (rank) {
            case SUPER: {
                return true;
            }
            case USER: {
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
                    default: {
                        return false;
                    }
                }
            }
            case VISITOR: {
                switch (command) {
                    case ".time": {
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
