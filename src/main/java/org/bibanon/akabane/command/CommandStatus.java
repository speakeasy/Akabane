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

    public CommandStatus(String cs) {
        super(cs);
    }

    @Override
    public void process(MessageEvent event) {
        execute(event);
    }

    private void execute(MessageEvent event) {
        // TODO list job statuses.
        ArrayList<String> buildList = new ArrayList<String>();
        int i = 0;
        if (buildList.isEmpty()) {
            buildList.add("Status: No jobs are currently running.");
        }
        while (i >= 0) {
            event.respond(buildList.get(i));
            i--;
        }
        i = 0;
        buildList = null;

    }

}
