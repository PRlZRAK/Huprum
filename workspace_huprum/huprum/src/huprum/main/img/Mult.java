package huprum.main.img;

import java.awt.image.BufferedImage;
import java.io.IOException;

import javax.imageio.ImageIO;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.ImageIcon;
import javax.swing.JLabel;

import huprum.main.media.PlaySound;

public class Mult extends JLabel implements Runnable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 2497522587696762109L;
	private BufferedImage[]   img;
	private int[]             playList;
	private PlaySound         pl;

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

	public void play()
	{
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
				Thread.sleep(50);
			} catch (InterruptedException e)
			{
				e.printStackTrace();
			}
			repaint();
		}
	}
	/*
	 * public static void main(String[] args) { String scenes[] = new String[] {
	 * "dyatel/d1.png", "dyatel/d2.png", "dyatel/d3.png" }; int playList[] = new
	 * int[] { 1, 2, 0, 1, 2, 1, 0, 1, 2, 1, 0 }; Mult m = new Mult(scenes,
	 * playList); JFrame win = new JFrame("test");
	 * win.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE); win.setSize(1000, 800);
	 * win.setLayout(null); m.setBounds(0, 10, 350, 400); win.add(m); JButton b =
	 * new JButton("ok"); b.setBounds(360, 10, 100, 20); b.addActionListener(new
	 * ActionListener() {
	 * 
	 * @Override public void actionPerformed(ActionEvent arg0) { m.play(); } });
	 * win.add(b); win.setVisible(true); }
	 */
}
