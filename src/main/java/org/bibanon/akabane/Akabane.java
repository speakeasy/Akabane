/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bibanon.akabane;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author root
 */
public class Akabane {

    static AkabaneInstance instance;

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        instance = new AkabaneInstance();
        try {
            instance.init(args);
        } catch (Exception ex) {
            Logger.getLogger(Akabane.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}
