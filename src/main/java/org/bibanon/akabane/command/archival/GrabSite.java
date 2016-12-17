package org.bibanon.akabane.command.archival;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Field;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bibanon.akabane.AkabaneInstance;
import org.pircbotx.hooks.events.MessageEvent;

public class GrabSite implements Runnable {

    private String igsets = "--igsets=global,";
    private Process process;
    private int pid;
    private URL url;
    private IAMetadata metadata;
    private boolean running;
    private MessageEvent event;
    public Thread thread = new Thread(this);
    public GrabSiteState state = GrabSiteState.STOPPED;

    private File directory;

    public void setGrabSite(URL url) {
        setGrabSite(url, "");
    }

    public void setMessageEvent(MessageEvent mev) {
        event = mev;
    }

    public void setGrabSite(String theurl) {
        state = GrabSiteState.INIT;
        try {
            url = new URL(theurl);
            setGrabSite(url, "");
        } catch (MalformedURLException ex) {
            Logger.getLogger(GrabSite.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void setGrabSite(String theurl, String igsets) {
        state = GrabSiteState.INIT;
        try {
            if (theurl != "") {
                url = new URL(theurl);
            }
            setGrabSite(url, igsets);
            System.out.println("URL: " + theurl);
        } catch (MalformedURLException ex) {
            Logger.getLogger(GrabSite.class.getName()).log(Level.SEVERE, null, ex);
            Logger.getLogger(GrabSite.class.getName()).log(Level.SEVERE, null, "\n" + theurl + "\n");
        }
    }

    public void setGrabSite(URL theurl, String igsets) {
        state = GrabSiteState.INIT;
        try {
            url = new URL(theurl.toExternalForm().replaceAll("[&]", "\\&").replaceAll("[\"]", "\\\"").replaceAll("[;]", "\\;").replaceAll("[|]", "").replaceAll("[.][.][/]", "./").replaceAll("[$]", "\\$").replaceAll(" ", ""));
        } catch (MalformedURLException ex) {
            Logger.getLogger(GrabSite.class.getName()).log(Level.SEVERE, null, ex);
        }

        if (igsets.isEmpty()) {
            this.igsets = "";
        } else {
            if (igsets.contains("noglobal")) {
                this.igsets = "--igsets=";
                igsets = igsets.replaceAll("noglobal,", "");
            }
            this.igsets += igsets.replaceAll("[&]", "\\&").replaceAll("[\"]", "\\\"").replaceAll("[;]", "\\;").replaceAll("[|]", "").replaceAll("[.][.][/]", "./").replaceAll("[$]", "\\$").replaceAll(" ", "") + " ";
        }
        state = GrabSiteState.INIT_DONE;
    }

    public void setMetadata(String data) {
        if (state != GrabSiteState.INIT_DONE) {
            state = GrabSiteState.INIT;
        }
        if (url != null && data != null) {
            metadata = new IAMetadata(url, data);
        }
    }

    public IAMetadata getMetadata() {
        return metadata;
    }

    @Override
    public void run() {
        state = GrabSiteState.RUNNING;
        try {
            directory = new File(AkabaneInstance.cwd.getAbsoluteFile() + "/" + url.getHost());
            process = Runtime.getRuntime().exec("grab-site " + url.toExternalForm() + " " + this.igsets + " --dir=" + directory);
            pid = getPid(process);
            running = true;
            event.respond("Grab-Site PID: " + pid);
            while (process.isAlive() && running) {
                Thread.sleep(200);
            }

            if (running == false) {
                process.destroy();
            }
            running = false;
            state = GrabSiteState.FINISHED_GRAB;
            event.respond("Grab-Site " + url.toExternalForm() + " finished!");
            uploadToIA();
        } catch (IOException ex) {
            Logger.getLogger(GrabSite.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InterruptedException ex) {
            Logger.getLogger(GrabSite.class.getName()).log(Level.SEVERE, null, ex);
        }

    }

    private void uploadToIA() throws IOException, InterruptedException {
        event.respond("Starting IA Upload: " + directory.getName());
        state = GrabSiteState.INIT_IA;
        ArrayList<File> warcs = new ArrayList<File>();
        File[] files = directory.listFiles();
        for (File fi : files) {
            if (fi.canRead() && fi.isFile() && fi.getName().endsWith(".warc.gz")) {
                warcs.add(fi);
            }
        }
        state = GrabSiteState.UPLOADING;
        for (File fi : warcs) {
            process = Runtime.getRuntime().exec("ia upload warc-" + directory.getName() + " " + metadata.iaMetadata() + fi.getAbsolutePath());
            pid = getPid(process);
            running = true;
            event.respond("IA Upload PID: " + pid);
            event.respond("File: " + fi.getName());
            while (process.isAlive() && running) {
                Thread.sleep(200);
            }

            if (running == false) {
                process.destroy();
            }
        }
        state = GrabSiteState.FINISHED_UPLOADING;
        event.respond("IA upload finished: " + directory.getName());
    }

    public int getPid(Process process) {
        try {
            Class<?> cProcessImpl = process.getClass();
            Field fPid = cProcessImpl.getDeclaredField("pid");
            if (!fPid.isAccessible()) {
                fPid.setAccessible(true);
            }
            return fPid.getInt(process);
        } catch (Exception e) {
            return -1;
        }
    }

    public int getPid() {
        return pid;
    }

    public Process getProcess() {
        return process;
    }

    public boolean isRunning() {
        return running;
    }

    public void stopRunning() {
        running = false;
    }

    public String getURL() {
        return url.toExternalForm();
    }
}
