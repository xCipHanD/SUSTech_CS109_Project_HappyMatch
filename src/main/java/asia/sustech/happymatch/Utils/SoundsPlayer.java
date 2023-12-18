package asia.sustech.happymatch.Utils;

import javax.sound.sampled.*;
import java.io.File;
import java.io.IOException;
import java.util.Objects;

public class SoundsPlayer {

    public static void playSound_btnClick() {
        play("/Sounds/btnClick.wav");
    }

    public static void playSound_btnClick1() {
        play("/Sounds/btnClick1.wav");
    }

    public void playSound3() {
        play("path/to/your/sound3.wav");
    }

    private static void play(String audioFilePath) {
        Thread thread = new Thread(() -> {
            File audioFile =
                    new File(Objects.requireNonNull(SoundsPlayer.class.getResource(audioFilePath)).getFile());
            AudioInputStream audioInputStream = null;
            try {
                audioInputStream = AudioSystem.getAudioInputStream(audioFile);
            } catch (UnsupportedAudioFileException | IOException e) {
                throw new RuntimeException(e);
            }

            Clip clip = null;
            try {
                clip = AudioSystem.getClip();
                clip.open(audioInputStream);
            } catch (LineUnavailableException | IOException e) {
                throw new RuntimeException(e);
            }
            //设置音量
            FloatControl gainControl = (FloatControl) clip.getControl(FloatControl.Type.MASTER_GAIN);
            gainControl.setValue(-10.0f);
            clip.start();
        });
        thread.start();

    }
}
