package org.bibanon.akabane;

import org.bibanon.akabane.users.Users;
import java.io.File;
import java.util.Date;
import java.util.Random;
import org.bibanon.akabane.ircauth.IRCAuth;
import org.bibanon.akabane.ircauth.YAMLAuth;
import org.bibanon.akabane.users.Rank;
import org.pircbotx.Channel;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
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
    private static String url;
    private static String[] cmdutil;

    private IRCAuth authpw = new IRCAuth();
    private static String igsets, meta = "";
    private static int i;

    @Override
    public void onJoin(JoinEvent join) {
        for (User u : join.getChannel().getUsers()) {
            for (User us : join.getChannel().getOwners()) {
                users.addUser(join.getUser().getNick(), Rank.OP);
            }
            for (User us : join.getChannel().getSuperOps()) {
                users.addUser(join.getUser().getNick(), Rank.OP);
            }
            for (User us : join.getChannel().getOps()) {
                users.addUser(join.getUser().getNick(), Rank.OP);
            }
            for (User us : join.getChannel().getHalfOps()) {
                users.addUser(join.getUser().getNick(), Rank.HOP);
            }
            for (User us : join.getChannel().getVoices()) {
                users.addUser(join.getUser().getNick(), Rank.VOICE);
            }

        }
    }

    @Override
    public void onPart(PartEvent part) {
        users.removeUser(part.getUser().getNick());
    }

    @Override
    public void onMessage(MessageEvent event) {
        String[] message = event.getMessage().split(" ");
        if (!event.getUser().isVerified()) {
            return;
        }

        if (!users.hasPermission(event.getUser().getNick(), message[0])) {
            return;
        }
        switch (message[0]) {
            case ".a": {
                cmdutil = new String[message.length - 1];
                for (i = 1; i < message.length; i++) {
                    cmdutil[i - 1] = message[i];
                }
                grab(cmdutil, event);
                cmdutil = null;
                return;
            }
            case ".is": {
                String response = "No dice.";
                if (message.length > 1) {
                    archiveis.init();
                    response = archiveis.submitURL(message[1]);

                    event.respond(response);
                    url = null;
                    return;
                }
                event.respond("Usage: \".is <url to archive>\"");
                url = null;
                return;
            }
            case ".rr": {
                int r = rand.nextInt() % 6;
                if (r == 0) {
                    event.respond("*bang*");
                } else {
                    event.respond("*click*");
                }
                break;
            }
            case ".time": {
                event.respond("The current time is: " + new Date() + "UTC");
                break;
            }
        }
    }

    public void grab(String[] cmd, MessageEvent event) {
        System.out.println("Grab...");
        igsets = "";
        meta = "";
        if (cmd.length > 1) {
            if (cmd.length % 3 == 0 || cmd.length % 5 == 0) {
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
                            event.respond("Usage: \".grab [<set> <igsetsoptions>] [<meta> <metadata[;tags[;separated[;...]]]>] <url>\"");
                            return;
                        }
                        default: {
                            break;
                        }
                    }
                }

            } else {
                event.respond("Usage: \".grab [<set> <igsetsoptions>] [<meta> <metadata[;tags[;separated[;...]]]>] <url>\"");
                return;
            }
        } else if (cmd[0] != null) {
            url = cmd[0];
        } else {
            event.respond("Usage: \".grab [<set> <igsetsoptions>] [<meta> <metadata[;tags[;separated[;...]]]>] <url>\"");
            url = "";
            return;
        }
        event.respond("Grab-Site started: PID: " + iagrabsite.addGrab(url, igsets, meta));
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
        //users.tmpImit();
        if (bot.isConnected()) {
            System.out.println("Connected.");
        }
    }
}
