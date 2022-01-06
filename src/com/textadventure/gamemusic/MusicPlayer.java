package com.textadventure.gamemusic;

import com.textadventure.input.Input;
import com.textadventure.story.World;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MusicPlayer extends Thread {
    private Clip clip;
    private AudioInputStream audioInputStream;
    private String filePath;
    private Thread thread;

    /**
     * Liest eine Audiodatei ein und gibt die Bytes der Datei zurück
     *
     * @param filepath Dateipfad der Audiodatei
     * @return Bytes der Audiodatei
     */
    public static byte[] readFile(String filepath) {
        byte[] ret = null;
        try {
            ret = Files.readAllBytes(Path.of(World.tempDir+"/"+filepath));
        } catch (IOException e) {
            e.printStackTrace();
        }
        return ret;
    }

    /**
     * Wandelt die Endung einer Date in .wav um und gibt den neuen Namen zurück
     *
     * @param path Dateiname
     * @return Neuer Dateiname mit .wav
     */
    private static String getWavPath(String path){
        File file = new File(path);
        path= Input.getFileType(file.getName(),false);
        return path+".wav";
    }

    @Override
    public void run() {
        try {
            while (clip == null)
                Thread.sleep(100);
            while (!clip.isRunning())
                Thread.sleep(100);
            while (clip.isRunning())
                Thread.sleep(100);
            World.musicPlayer.play();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    public void play() {
        try {
            stop(true);
            if (World.player.getCurrentRoom().getMusic() != null) {
                String filename=getWavPath(World.player.getCurrentRoom().getMusic());
                filePath = World.tempDir + "/" + filename;
                audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                if (!World.player.getCurrentRoom().isEventMusic())
                    clip.loop(Clip.LOOP_CONTINUOUSLY);
                int frame = World.player.getCurrentRoom().getMusicFrame();
                if (frame != 0)
                    clip.setFramePosition(frame);
                clip.start();
                if (World.player.getCurrentRoom().isEventMusic()) {
                    World.player.getCurrentRoom().setMusic(World.player.getCurrentRoom().getPreviousMusic(), false);
                    try {
                        if (thread != null)
                            if (thread.isAlive())
                                thread.interrupt();
                        thread = new Thread(new MusicPlayer());
                        thread.start();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }
        } catch (UnsupportedAudioFileException | IOException | LineUnavailableException e) {
            e.printStackTrace();
        }
    }

    public void stop(boolean changedRoom) {
        if (this.clip != null) {
            if (changedRoom) {
                if (World.player.getPreviousRoom() != null)
                    World.player.getPreviousRoom().setMusicFrame(clip.getFramePosition());
            } else
                World.player.getCurrentRoom().setMusicFrame(clip.getFramePosition());
            clip.stop();
            clip.close();
        }
    }
}
