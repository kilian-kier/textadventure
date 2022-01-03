package com.textadventure.gamemusic;

import com.textadventure.story.World;
import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;
import javazoom.jl.converter.Converter;

import java.io.FileInputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MusicPlayer implements Runnable {
    private Thread thread = null;
    private Player player = null;

    /**
     * Liest eine Audiodatei ein und gibt die Bytes der Datei zurück
     *
     * @param filepath Dateipfad der Audiodatei
     * @return Bytes der Audiodatei
     */
    public static byte[] readFile(String filepath) {
        byte[] ret = null;
        try {
            ret = Files.readAllBytes(Path.of(filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    @Override
    public void run() {
        try {
            player.play();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Startet einen neuen Thread mit dem MusicPlayer
     */
    public void play() {
        this.stop();
        if (World.player != null) {
            if (World.player.getCurrentRoom() != null) {
                try {
                    if (World.player.getCurrentRoom().getMusic() != null) {
                        String musicpath = World.tempDir + "/" + World.player.getCurrentRoom().getMusic();
                        player = new Player(new FileInputStream(musicpath));
                    } else
                        player = null;

                    if (World.player.getCurrentRoom().isEventMusic()) {
                        World.player.getCurrentRoom().setMusic(World.player.getCurrentRoom().getPreviousMusic(), false);
                    }
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
            try {
                player.close();
                if (World.player.getCurrentRoom().getMusic() != null) {
                    String musicpath = World.tempDir + "/" + World.player.getCurrentRoom().getMusic();
                    player = new Player(new FileInputStream(musicpath));
                } else
                    player = null;
                thread = new Thread(this);
                thread.start();
            } catch (IOException | JavaLayerException e) {
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
}
