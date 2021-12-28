package com.textadventure.gamemusic;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MusicPlayer implements Runnable {
    Thread thread = null;
    private Player player = null;

    /**
     * Für jedes abzuspielende Audio muss ein MusicPlayer erstellt werden
     *
     * @param filename Dateipath des Audios
     */
    public MusicPlayer(String filename) {
        if (filename != null) {
            try {
                FileInputStream inputStream = new FileInputStream(filename);
                player = new Player(inputStream);
            } catch (FileNotFoundException | JavaLayerException e) {
                e.printStackTrace();
            }
        }
    }

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
