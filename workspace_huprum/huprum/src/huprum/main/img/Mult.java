package huprum.main.img;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import huprum.main.media.PlaySound;
import huprum.main.utils.Utilit;

public class Mult extends JLabel implements Runnable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2497522587696762109L;
	private BufferedImage[]   img;
	private int[]             playList;
	private PlaySound         pl;
	private long              pause;

	public Mult(String[] scenes, int[] playList)
	{
		this.playList = playList;
		img = new BufferedImage[scenes.length];
		for (int i = 0; i < img.length; i++)
			try
			{
				img[i] = ImageIO.read(Mult.class.getResource(scenes[i]));
			} catch (IOException e)
			{
				e.printStackTrace();
			}
		setIcon(new ImageIcon(img[0]));
		try
		{
			pl = new PlaySound("dyatel_stuk.wav");
		} catch (UnsupportedAudioFileException | IOException | LineUnavailableException e)
		{
			e.printStackTrace();
		}
	}

	public void play(long pause)
	{
		this.pause = pause;
		if (Utilit.SET_STUK_SOUND)
			pl.play();
		new Thread(this).start();;
	}

	@Override
	public void run()
	{
		for (int i : playList)
		{
			setIcon(new ImageIcon(img[i]));
			try
			{
				Thread.sleep(pause);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			repaint();
		}
	}
}
