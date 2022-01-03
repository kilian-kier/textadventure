package com.textadventure.gamemusic;

import com.textadventure.story.World;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

public class MusicPlayer {
    private Clip clip;
    private AudioInputStream audioInputStream;
    private String filePath;

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

    public void play() {
        try {
            stop(true);
            if (World.player.getCurrentRoom().getMusic() != null) {
                filePath = World.tempDir + "/" + World.player.getCurrentRoom().getMusic();
                audioInputStream = AudioSystem.getAudioInputStream(new File(filePath).getAbsoluteFile());
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
                clip.loop(Clip.LOOP_CONTINUOUSLY);
                int frame = World.player.getCurrentRoom().getMusicFrame();
                if (frame != 0)
                    clip.setFramePosition(frame);
                clip.start();
                if (World.player.getCurrentRoom().isEventMusic()) {
                    World.player.getCurrentRoom().setMusic(World.player.getCurrentRoom().getPreviousMusic(), false);
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
