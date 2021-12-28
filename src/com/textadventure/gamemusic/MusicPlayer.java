package com.textadventure.gamemusic;

import com.textadventure.story.World;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Path;
import java.security.CodeSource;
import java.util.zip.ZipInputStream;

public class MusicPlayer implements Runnable {
    private Thread thread = null;
    private Player player = null;

    @Override
    public void run() {
        try {
            player.play();
            restart();
        } catch (JavaLayerException e) {
            e.printStackTrace();
        }
    }

    /**
     * Startet einen neuen Thread mit dem MusicPlayer
     */
    public void play() {
        InputStream input;
        if (World.player != null) {
            if (World.player.getCurrentRoom() != null) {
                try {
                    if (World.isJar())
                        input = getClass().getResourceAsStream("/music/" + World.player.getCurrentRoom().getName() + ".mp3");
                    else
                        input = new FileInputStream("music/" + World.player.getCurrentRoom().getName() + ".mp3");

                    if (input != null)
                        player = new Player(input);
                } catch (IOException | JavaLayerException e) {
                    //DO NOTHING
                }
            }
        }
        if (player != null) {
            thread = new Thread(this);
            thread.start();
        }
    }

    private void restart() {
        if (player != null) {
            player.close();
            try {
                player.play(0);
            } catch (JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * Stoppt den Thread und löscht den Player
     */
    public void stop() {
        if (player != null) {
            player.close();
            if (thread != null)
                thread.stop();
        }
    }

    /**
     * Liest eine Audiodatei ein und gibt die Bytes der Datei zurück
     *
     * @param filepath Dateipfad der Audiodatei
     * @return Bytes der Audiodatei
     */
    public byte[] readFile(String filepath) {
        byte[] ret = null;
        String temp = "";
        try {
            ret = Files.readAllBytes(Path.of(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }
}
