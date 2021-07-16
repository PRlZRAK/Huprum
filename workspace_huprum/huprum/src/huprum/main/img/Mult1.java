package huprum.main.img;

import java.io.IOException;
import java.net.URL;

import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import huprum.main.media.PlaySound;
import huprum.main.utils.Utilit;

public class Mult1 extends JLabel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2497522587696762109L;
	private PlaySound         pl;
	private ImageIcon         icon;

	public Mult1(String gif_name, String wav_name)
	{
		try
		{
			URL url = new URL(Utilit.IMG_URL + gif_name);
			icon = new ImageIcon(url);
			if (wav_name == null)
				pl = null;
			else
				pl = new PlaySound(wav_name);
		} catch (IOException | UnsupportedAudioFileException | LineUnavailableException e1)
		{
			icon = null;
		}
	}

	public void play(boolean sound)
	{
		if (sound && pl != null)
			pl.play();
		setIcon(null);
		icon.getImage().flush();
		setIcon(icon);
	}

	public int getImgWidth()
	{
		return icon.getIconWidth();
	}

	public int getImgHeight()
	{
		return icon.getIconHeight();
	}
}
