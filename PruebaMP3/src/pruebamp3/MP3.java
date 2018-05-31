/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package pruebamp3;

/**
 *
 * @author user
 */
import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

import javazoom.jl.player.Player;


public class MP3 {
    private String filename;
    private Player player; 
    FileInputStream fis;
    BufferedInputStream bis;
    int locPausa;
    int durTotal;
    boolean pausado;

    public int getDurTotal() {
        return durTotal;
    }

    public void setLocPausa(int locPausa) {
        this.locPausa = locPausa;
    }

    public int getLocPausa() {
        return locPausa;
    }

    public boolean isPausado() {
        return pausado;
    }

    public void setPausado(boolean pausado) {
        this.pausado = pausado;
    }
            
    
    
    // constructor that takes the name of an MP3 file
    public MP3(String filename) {
            this.filename = filename;
//            this.pausado = false;
    }

    public void stop(){
        this.filename = null;
        player.close();
   
    }
    
    public void close() { if (player != null) player.close(); }

    // play the MP3 file to the sound card
    public void play() {
        try {
            fis     = new FileInputStream(filename);
            bis = new BufferedInputStream(fis);
            player = new Player(bis);
            //Almacenamos la duracion total en una variable
            durTotal = fis.available();
        }
        catch (Exception e) {
            System.out.println("Problem playing file " + filename);
            System.out.println(e);
        }

        // run in new thread to play in background
        new Thread() {
            public void run() {
                try { player.play(); }
                catch (Exception e) { System.out.println(e); }
            }
        }.start();
    }
    
    public void pause(){
        try {
            this.locPausa = fis.available();
            player.close();
//            this.pausado = true;
            
        } catch (IOException ex) {
            Logger.getLogger(MP3.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void resume() {
        try {
            fis     = new FileInputStream(filename);
            bis = new BufferedInputStream(fis);
            player = new Player(bis);
            fis.skip(durTotal - locPausa);
        }
        catch (Exception e) {
            System.out.println("Problem playing file " + filename);
            System.out.println(e);
        }

        // run in new thread to play in background
        new Thread() {
            public void run() {
                try { player.play(); }
                catch (Exception e) { System.out.println(e); }
            }
        }.start();
    }
}
