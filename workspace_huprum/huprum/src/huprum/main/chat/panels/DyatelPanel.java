package huprum.main.chat.panels;

import java.awt.FlowLayout;

import javax.swing.JPanel;

import huprum.main.img.Mult;

public class DyatelPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3314533031189267270L;
	private Mult              m;

	public DyatelPanel()
	{
		super(new FlowLayout(FlowLayout.LEFT));
		String scenes[]   = new String[]
		{ "dyatel/d1.png", "dyatel/d2.png", "dyatel/d3.png" };
		int    playList[] = new int[]
		{ 1, 2, 0, 1, 2, 1, 0, 1, 2, 1, 0 };
		m = new Mult(scenes, playList);
		add(m);
	}

	public void play()
	{
		m.play();
	}
}
