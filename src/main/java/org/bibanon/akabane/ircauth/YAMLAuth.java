/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bibanon.akabane.ircauth;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author speakeasy
 */
public class YAMLAuth {

    public static final File PWD = new File(System.getProperty("user.dir"));
    public static final File Permdir = new File(PWD.getAbsolutePath() + "/permissions");
    private final File authfile = new File(Permdir.getAbsolutePath() + "/auth.yaml");
    private IRCAuth authpw;

    public YAMLAuth() {
        init();
    }

    private void init() {
        if (authfile.exists()) {
            try {
                authpw = readAuth(authfile);
                Logger.getLogger(YAMLAuth.class.getName()).log(Level.ALL, null, "Auth loaded");
            } catch (FileNotFoundException ex) {
                Logger.getLogger(YAMLAuth.class.getName()).log(Level.SEVERE, null, ex);
            }
        } else {
            authpw = new IRCAuth();
            authpw.password = "password";
            writeAuth(authfile, authpw);
            System.out.println("Written");
        }
    }

    private void writeAuth(File fyaml, IRCAuth auth) {
        Yaml yaml = new Yaml();
        byte[] out = yaml.dump(authpw).getBytes();
        try (OutputStream output = new FileOutputStream(fyaml)) {
            output.write(out, 0, out.length);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(IRCAuth.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(IRCAuth.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private IRCAuth readAuth(File fyaml) throws FileNotFoundException {
        InputStream input = new FileInputStream(fyaml);
        Yaml yaml = new Yaml();
        return (IRCAuth) yaml.load(input);
    }
}
