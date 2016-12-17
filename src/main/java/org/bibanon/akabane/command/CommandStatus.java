/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package org.bibanon.akabane.command;

import java.util.ArrayList;
import java.util.HashMap;
import org.bibanon.akabane.command.archival.GrabSite;
import org.pircbotx.hooks.events.MessageEvent;

/**
 *
 * @author speakeasy
 */
public class CommandStatus extends Command {

    public CommandStatus(String cs, Class cc, Integer argnum, HashMap<String, Boolean> argsNames) {
        super(cs, cc, argnum, argsNames);
    }

    @Override
    public void process(String[] message, MessageEvent event) {
        execute(event);
    }

    private void execute(MessageEvent event) {
        // TODO list job statuses.
        HashMap<Integer, String> buildList = new HashMap<Integer, String>();
        int i = 0;
        ArrayList<GrabSite> list;
        list = grabManager.getFinished();
        if (list.size() > 0) {
            for (GrabSite gs : list) {
                buildList.put(i, "Finished: PID: " + gs.getPid() + " URL: " + gs.getURL());
                i++;
            }
        }
        list = grabManager.getRunning();
        if (list.size() > 0) {
            for (GrabSite gs : list) {
                buildList.put(i, "Running: PID: " + gs.getPid() + " URL: " + gs.getURL());
                i++;
            }
        }
        list = grabManager.getWaiting();
        if (list.size() > 0) {
            for (GrabSite gs : list) {
                buildList.put(i, "Waiting: URL: " + gs.getURL());
                i++;
            }
        }
        while (i >= 0) {
            event.respond(buildList.get(i));
            i--;
        }
    }

}
