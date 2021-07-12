package huprum.main.chat.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import javax.swing.JPanel;


import huprum.main.img.Mult1;

public class DyatelPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -3314533031189267270L;
	private Mult1              m;

	public DyatelPanel()
	{
		GridBagLayout gridBagLayout = new GridBagLayout();
		gridBagLayout.columnWeights = new double[]
		{ 0.0, 0.0, 0.0, 0.0, 1.0, 0.0, Double.MIN_VALUE };
		gridBagLayout.rowWeights = new double[]
		{ 0.1, 1.0, 0.0, Double.MIN_VALUE };
		setLayout(gridBagLayout);
		GridBagConstraints c = new GridBagConstraints();
		c.anchor = GridBagConstraints.NORTH;
		c.insets = new Insets(0, 0, 0, 0);
		c.gridx = 0;
		c.gridy = 1;
		/*
		String scenes[]   = new String[]
				{ "dyatel/d10.png", "dyatel/d11.png", "dyatel/d12.png" , "dyatel/d13.png" };
	
		int    playList[] = new int[]
				{ 0, 1,2, 3, 2, 1, 2, 3, 2, 1, 0 };
		m = new Mult(scenes, playList);
		*/
		m=new Mult1("stuk3.gif", "dyatel_stuk.wav");
		m.addMouseListener(new MouseListener()
		{
			@Override
			public void mouseReleased(MouseEvent arg0)
			{
			}

			@Override
			public void mousePressed(MouseEvent arg0)
			{
			}

			@Override
			public void mouseExited(MouseEvent arg0)
			{
			}

			@Override
			public void mouseEntered(MouseEvent arg0)
			{
				m.play();
			}

			@Override
			public void mouseClicked(MouseEvent arg0)
			{
			}
		});
		add(m, c);
	}

	public void play()
	{
		m.play();
	}
}
