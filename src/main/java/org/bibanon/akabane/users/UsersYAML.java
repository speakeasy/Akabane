/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bibanon.akabane.users;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Spliterator;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.yaml.snakeyaml.Yaml;

/**
 *
 * @author speakeasy
 */
public class UsersYAML {

    public static final File PWD = new File(System.getProperty("user.dir"));
    public static final File Permdir = new File(PWD.getAbsolutePath() + "/permissions");
    public static final File UsersYAML = new File(Permdir.getAbsolutePath() + "/users.yaml");
    public static final File BlockedYAML = new File(Permdir.getAbsolutePath() + "/blocked.yaml");

    //public static UsersYAML Uyaml;
    private static List<User> users;
    private static List<User> blocked;

    public UsersYAML() {
        users = new ArrayList<User>();
        blocked = new ArrayList<User>();
    }

    public boolean isUser(String user) {
        int len = users.size();
        for (int i = 0; i < len; i++) {
            if (users.get(i).getName() == user) {
                if (blocked.contains(users.get(i))) {
                    users.remove(i);
                    return false;
                }
                return true;
            }
        }
        return false;
    }
    
    public boolean isBlocked(String user) {
        int len = blocked.size();
        for (int i = 0; i<len; i++) {
            if(blocked.get(i).getName() == user) {
                return true;
            }
        }
        return false;
    }

    public User getUser(String user) {
        if (isUser(user)) {
            int len = users.size();
            for (int i = 0; i < len; i++) {
                if(users.get(i).getName() == user) {
                    return users.get(i);
                }
            }
        }
        return null;
    }

    public void addUser(User auser) {
        users.add(auser);
    }

    public void removeUser(User auser) {
        users.remove(auser);
    }

    public void addBlocked(User buser) {
        blocked.add(buser);
        if (users.contains(buser)) {
            removeUser(buser);
        }
    }

    public void loadUsers() {
        try {
            List<User> tmpusers = loadYaml(UsersYAML);
            List<User> blocked = loadYaml(BlockedYAML);
            int bli = blocked.size();
            for (int i = 0; i < bli; i++) {
                if (tmpusers.contains(blocked.get(i))) {
                    tmpusers.remove(blocked.get(i));
                }
            }
            users = tmpusers;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UsersYAML.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    public void writeOut() {
        writeYaml(UsersYAML, users);
        writeYaml(BlockedYAML, blocked);
    }

    private List<User> loadYaml(File fyaml) throws FileNotFoundException {
        InputStream input = new FileInputStream(fyaml);
        Yaml yaml = new Yaml();
        return (List<User>) yaml.load(input);
    }

    private void writeYaml(File fyaml, List<User> list) {
        Yaml yaml = new Yaml();
        byte[] out = yaml.dump(list).getBytes();
        try (OutputStream output = new FileOutputStream(fyaml)) {
            output.write(out, 0, out.length);
        } catch (FileNotFoundException ex) {
            Logger.getLogger(UsersYAML.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(UsersYAML.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    public boolean firstRun() {
        if (UsersYAML.exists() && BlockedYAML.exists()) {
            if((!Permdir.exists()) && PWD.canWrite()) {
                Permdir.mkdir();
            }
            return false;
        }
        return true;
    }

}
