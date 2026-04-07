package app;

import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;

import javazoom.jl.decoder.JavaLayerException;
import javazoom.jl.player.advanced.AdvancedPlayer;

public final class AudioManager {

  // public static final String MENU_MUSIC = "/audio/jaunty_gumption.mp3";
  // public static final String GAME_MUSIC = "/audio/jaunty_gumption.mp3";
  public static final String MENU_MUSIC = "/audio/relax.mp3";
  public static final String GAME_MUSIC = "/audio/relax.mp3";
  public static final String SNAKE_UP_VOICE = "/audio/snake_up_voice.wav";
  private static final long DEFAULT_MUSIC_DELAY_MS = 1000;

  private static Thread playbackThread;
  private static AdvancedPlayer currentPlayer;
  private static String currentTrack;
  private static volatile boolean looping;
  private static boolean musicEnabled;
  private static String desiredMusicTrack;
  private static long pendingMusicRequestId;

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

  public static void enableMusicAfterDelay() {
    enableMusicAfterDelay(DEFAULT_MUSIC_DELAY_MS);
  }

  public static void enableMusicAfterDelay(long delayMs) {
    final long requestId;
    synchronized (AudioManager.class) {
      pendingMusicRequestId++;
      requestId = pendingMusicRequestId;
      musicEnabled = false;
      stop();
    }

    Thread delayedMusicThread = new Thread(() -> {
      try {
        Thread.sleep(Math.max(0, delayMs));
      } catch (InterruptedException e) {
        Thread.currentThread().interrupt();
        return;
      }

      synchronized (AudioManager.class) {
        if (requestId != pendingMusicRequestId) {
          return;
        }

        musicEnabled = true;
        if (desiredMusicTrack != null) {
          playLoop(desiredMusicTrack);
        }
      }
    }, "audio-enable-music");
    delayedMusicThread.setDaemon(true);
    delayedMusicThread.start();
  }

  public static synchronized void disableMusic() {
    pendingMusicRequestId++;
    musicEnabled = false;
    desiredMusicTrack = null;
    stop();
  }

  public static synchronized void syncMusic(String resourcePath) {
    desiredMusicTrack = resourcePath;
    if (!musicEnabled) {
      stop();
      return;
    }

    playLoop(resourcePath);
  }

  public static void playOnce(String resourcePath) {
    if (resourcePath == null) {
      return;
    }

    Thread effectThread = new Thread(() -> playSingle(resourcePath), "audio-effect");
    effectThread.setDaemon(true);
    effectThread.start();
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

  private static void playSingle(String resourcePath) {
    try (InputStream resourceStream = AudioManager.class.getResourceAsStream(resourcePath)) {
      if (resourceStream == null) {
        System.err.println("Audio file not found: " + resourcePath);
        return;
      }

      // BufferedInputStream adds mark/reset support needed by AudioSystem inside a jar
      try (InputStream buffered = new java.io.BufferedInputStream(resourceStream);
          AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(buffered)) {
        Clip clip = AudioSystem.getClip();
        clip.open(audioInputStream);
        clip.addLineListener(event -> {
          if (event.getType() == javax.sound.sampled.LineEvent.Type.STOP
              || event.getType() == javax.sound.sampled.LineEvent.Type.CLOSE) {
            clip.close();
              }
        });
        clip.start();

        // Need to block or the clip closes before it finishes playing
        Thread.sleep(clip.getMicrosecondLength() / 1000 + 200);
          }
    } catch (Exception e) {
      System.err.println("Could not play audio: " + resourcePath);
      e.printStackTrace();
    }
  }
}
