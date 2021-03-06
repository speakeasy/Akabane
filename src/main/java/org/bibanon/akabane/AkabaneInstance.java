package org.bibanon.akabane;

import org.bibanon.akabane.command.users.Users;
import java.io.File;
import java.util.Random;
import org.bibanon.akabane.command.ProcessCommands;
import org.bibanon.akabane.command.ircauth.IRCAuth;
import org.bibanon.akabane.command.ircauth.YAMLAuth;
import org.bibanon.akabane.command.users.Rank;
import org.pircbotx.Channel;
import org.pircbotx.Configuration;
import org.pircbotx.PircBotX;
import org.pircbotx.User;
import org.pircbotx.hooks.ListenerAdapter;
import org.pircbotx.hooks.events.JoinEvent;
import org.pircbotx.hooks.events.MessageEvent;
import org.pircbotx.hooks.events.PartEvent;

public class AkabaneInstance extends ListenerAdapter {

    public static PircBotX bot;

    private static Random rand = new Random();
    private static Users users = new Users();
    //    static URLValidator validator = new URLValidator();
    //util
    public static final File cwd = new File(System.getProperty("user.dir", "./"));
    private static ProcessCommands processor = new ProcessCommands();

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
        //System.out.println(event.getMessage()); // DEBUG

        updateUsers(event.getChannel());
        processor.updateUsers(users);

        processor.process(event);
    }

    public void init(String[] args) throws Exception {
        System.out.println("Starting...");

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

        ProcessCommands.processor = processor;
        //Create our bot with the configuration
        bot = new PircBotX(configuration);
        //Connect to the server
        bot.startBot();

        Thread.sleep(5000);
        if (bot.getState() != bot.getState().DISCONNECTED) {
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
