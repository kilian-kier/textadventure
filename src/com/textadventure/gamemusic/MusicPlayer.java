package com.textadventure.gamemusic;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.Player;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import java.io.*;
import java.nio.file.Files;
import java.nio.file.Path;

public class MusicPlayer implements Runnable {
    private Player player = null;
    private final String filename;

    public MusicPlayer(String filename) {
        this.filename = filename;
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

    public void play() {
        if (player != null)
            new Thread(this).start();
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

    public void stop() {
        if (player != null)
            player.close();
    }

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
