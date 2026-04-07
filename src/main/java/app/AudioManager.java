package app;

import java.io.IOException;
import java.io.InputStream;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public final class AudioManager {

    public static final String MENU_MUSIC = "/audio/jaunty_gumption.mp3";
    public static final String GAME_MUSIC = "/audio/jaunty_gumption.mp3";

    private static Thread playbackThread;
    private static AdvancedPlayer currentPlayer;
    private static String currentTrack;
    private static volatile boolean looping;

    private AudioManager() {}

    public static synchronized void playLoop(String resourcePath) {
        if (resourcePath == null || resourcePath.equals(currentTrack)) {
            return;
        }

        stop();

        currentTrack = resourcePath;
        looping = true;
        playbackThread = new Thread(() -> loopPlayback(resourcePath), "audio-playback");
        playbackThread.setDaemon(true);
        playbackThread.start();
    }

    public static synchronized void stop() {
        looping = false;

        if (currentPlayer != null) {
            currentPlayer.close();
            currentPlayer = null;
        }

        currentTrack = null;
        playbackThread = null;
    }

    private static void loopPlayback(String resourcePath) {
        while (looping && resourcePath.equals(currentTrack)) {
            try (InputStream resourceStream = AudioManager.class.getResourceAsStream(resourcePath)) {
                if (resourceStream == null) {
                    System.err.println("Audio file not found: " + resourcePath);
                    stop();
                    return;
                }

                AdvancedPlayer player = new AdvancedPlayer(resourceStream);

                synchronized (AudioManager.class) {
                    if (!looping || !resourcePath.equals(currentTrack)) {
                        player.close();
                        return;
                    }
                    currentPlayer = player;
                }

                player.play();
            } catch (JavaLayerException | IOException e) {
                System.err.println("Could not play audio: " + resourcePath);
                e.printStackTrace();
                stop();
                return;
            } finally {
                synchronized (AudioManager.class) {
                    if (currentPlayer != null) {
                        currentPlayer.close();
                        currentPlayer = null;
                    }
                }
            }
        }
    }
}
