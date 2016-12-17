/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bibanon.akabane.command.archival;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author speakeasy
 */
public class ProcessManagerGrabSite implements Runnable {

    private static ArrayList<GrabSite> GrabSites = new ArrayList<GrabSite>();
    private static ArrayList<GrabSite> runningGS = new ArrayList<GrabSite>();
    private static ArrayList<GrabSite> finishedGS = new ArrayList<GrabSite>();
    private static HashMap<GrabSite, Thread> threads = new HashMap<GrabSite, Thread>();
    public static ProcessManagerGrabSite processManager;
    private boolean running;

    public ProcessManagerGrabSite() {
        init();
    }

    private void init() {
        processManager = this;
    }

    @Override
    public void run() {
        running = true;
        while (running) {
            try {
                Thread.sleep(11);
            } catch (InterruptedException ex) {
                Logger.getLogger(ProcessManagerGrabSite.class.getName()).log(Level.SEVERE, null, ex);
            }
            synchronized (runningGS) {
                for (GrabSite g : runningGS) {
                    if (!threads.get(g).isAlive()) {
                        threads.remove(g);
                        runningGS.remove(g);
                        synchronized (finishedGS) {
                            finishedGS.add(g);
                        }
                    }
                }
                if (threads.size() < 4) {
                    synchronized (GrabSites) {
                        for (GrabSite gs : GrabSites) {
                            if (threads.size() < 4) {
                                GrabSites.remove(gs);
                                runningGS.add(gs);
                                Thread thread = new Thread(gs);
                                threads.put(gs, thread);
                                thread.start();
                            }
                        }
                    }
                }
            }
            // TODO: finishedGS to IA upload.
        }
    }

    public void addGrab(GrabSite addgs) {
        synchronized (GrabSites) {
            GrabSites.add(addgs);
        }
    }

    public ArrayList<GrabSite> getFinished() {
        synchronized (finishedGS) {
            return (ArrayList<GrabSite>) finishedGS.clone();
        }
    }

    public ArrayList<GrabSite> getRunning() {
        synchronized (runningGS) {
            return (ArrayList<GrabSite>) runningGS.clone();
        }
    }

    public ArrayList<GrabSite> getWaiting() {
        synchronized (GrabSites) {
            return (ArrayList<GrabSite>) GrabSites.clone();
        }
    }

    public void die() {
        running = false;
    }
}
