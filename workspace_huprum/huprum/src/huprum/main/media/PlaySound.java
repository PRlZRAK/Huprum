package huprum.main.media;

import java.io.BufferedInputStream;
import java.io.IOException;
import java.io.InputStream;

import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.sound.sampled.DataLine;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;

public class PlaySound implements Runnable
{
	private String file_name;

	public PlaySound(String file_name) throws UnsupportedAudioFileException, IOException, LineUnavailableException
	{
		this.file_name = file_name;
	}

	public void play()
	{
		new Thread(this).start();
	}

	@Override
	public void run()
	{
		try
		{
			InputStream      bufferedIn1 = new BufferedInputStream(PlaySound.class.getResourceAsStream(file_name));
			AudioInputStream ais         = AudioSystem.getAudioInputStream(bufferedIn1);
			if (!AudioSystem.isLineSupported(new DataLine.Info(Clip.class, ais.getFormat())))
				return;
			Clip clip = AudioSystem.getClip();
			clip.open(ais);
			clip.start();
		} catch (LineUnavailableException | IOException | UnsupportedAudioFileException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
