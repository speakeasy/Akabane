package org.bibanon.akabane;

import org.bibanon.akabane.command.users.Users;
import java.io.File;
import java.util.Date;
import java.util.Random;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.bibanon.akabane.command.RunnableCommandProcessor;
import org.bibanon.akabane.command.ircauth.IRCAuth;
import org.bibanon.akabane.command.ircauth.YAMLAuth;
import org.bibanon.akabane.command.users.Rank;
import org.pircbotx.Channel;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.Event;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PartEvent;

public class AkabaneInstance extends ListenerAdapter {

    static PircBotX bot;
    static ArchiveIsHtmlParser archiveis = new ArchiveIsHtmlParser();
    static IAGrabSiteProcessManager iagrabsite = new IAGrabSiteProcessManager();

    private static Random rand = new Random();
    private static Users users = new Users();
    //    static URLValidator validator = new URLValidator();
    //util
    private static File cwd = new File(System.getProperty("user.dir", "./"));
    private static RunnableCommandProcessor processor = new RunnableCommandProcessor();
    private static Thread pThread = new Thread(processor);

    private IRCAuth authpw = new IRCAuth();
    private static String igsets, meta = "";
    private static int i;

    @Override
    public void onJoin(JoinEvent join) {
        updateUsers(join.getChannel());
    }

    @Override
    public void onPart(PartEvent part) {
        users.removeUser(part.getUser().getNick());
        updateUsers(part.getChannel());
    }

    @Override
    public void onMessage(MessageEvent event) {
        updateUsers(event.getChannel());
        processor.updateUsers(users);
        processor.addEvent(event);
    }

    public void grab(String[] cmd, MessageEvent event) {
        System.out.println("Grab...");
        igsets = "";
        meta = "";
        if (cmd.length > 1) {
            if (cmd.length == 3 || cmd.length == 5 || cmd[0] == "help") {
                for (i = 0; i < cmd.length; i++) {
                    switch (cmd[i]) {
                        case "set": {
                            i++;
                            igsets = cmd[i];
                            break;
                        }
                        case "meta": {
                            i++;
                            meta = cmd[i];
                            break;
                        }
                        case "help": {
                            event.respond("Usage: \".a [<set> <igsetsoptions>] [<meta> <metadata[;tags[;separated[;...]]]>] <url>\"");
                            return;
                        }
                        default: {
                            break;
                        }
                    }
                }

            } else {
                event.respond("Usage: \".a [<set> <igsetsoptions>] [<meta> <metadata[;tags[;separated[;...]]]>] <url>\"");
                return;
            }
        } else if (cmd[0] != null) {
            if (cmd[0] == "help") {
                event.respond("Usage: \".grab [<set> <igsetsoptions>] [<meta> <metadata[;tags[;separated[;...]]]>] <url>\"");
                return;
            } else {
                url = cmd[0];
            }
        } else {
            event.respond("Usage: \".grab [<set> <igsetsoptions>] [<meta> <metadata[;tags[;separated[;...]]]>] <url>\"");
            url = "";
            return;
        }
        if (url != "" && url != "help") {
            event.respond("Grab-Site started: PID: " + iagrabsite.addGrab(url, igsets, meta));
        } else {
            event.respond("Usage: \".grab [<set> <igsetsoptions>] [<meta> <metadata[;tags[;separated[;...]]]>] <url>\"");
        }
        igsets = "";
        meta = "";
        url = "";
    }

    public void init(String[] args) throws Exception {
        System.out.println("Starting...");

        iagrabsite = new IAGrabSiteProcessManager();
        iagrabsite.start();
        System.out.println("Loading Configuration...");
        //Loading YAML NickServ password
        YAMLAuth yauth = new YAMLAuth();
        //Configure what we want our bot to do
        Configuration configuration = new Configuration.Builder()
                .setName("Akabane")
                .addServer("irc.rizon.net")
                .addAutoJoinChannel("#bibanon-ab")
                .addListener(new AkabaneInstance())
                .setLogin("Akabane")
                .setRealName("Akabane Archive Bot (Alpha)")
                .setNickservNick("NickServ")
                .setNickservPassword(authpw.password)
                .buildConfiguration();

        //Create our bot with the configuration
        bot = new PircBotX(configuration);
        //Connect to the server
        bot.startBot();
        pThread.start();
        if (bot.isConnected()) {
            System.out.println("Connected.");
        }
    }

    private void updateUsers(Channel ch) {
        for (User u : ch.getUsers()) {
            for (User us : ch.getOwners()) {
                users.addUser(us.getNick(), Rank.OP);
            }
            for (User us : ch.getSuperOps()) {
                users.addUser(us.getNick(), Rank.OP);
            }
            for (User us : ch.getOps()) {
                users.addUser(us.getNick(), Rank.OP);
            }
            for (User us : ch.getHalfOps()) {
                users.addUser(us.getNick(), Rank.HOP);
            }
            for (User us : ch.getVoices()) {
                users.addUser(us.getNick(), Rank.VOICE);
            }
        }

    }
}
