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
    SUPER,
    USER,
    VISITOR,
    BLOCKED;

    private static boolean hasPermission(Rank rank, String command) {
        switch (rank) {
            case SUPER: {
                return true;
            }
            case USER: {
                switch (command) {
                    case ".is": {
                        return true;
                    }
                    case ".grab": {
                        return true;
                    }
                    case ".rr": {
                        return true;
                    }
                    case ".time": {
                        return true;
                    }
                    default: {
                        return true;
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
