package huprum.main.chat.panels;

import javax.swing.JWindow;

import huprum.main.Huprum;
import huprum.main.img.Mult1;

public class WaitPanel extends JWindow implements Runnable
{
	/**
	 * 
	 */
	private static final long serialVersionUID = -1897136174933855715L;
	private Mult1             m;

	public WaitPanel(Huprum main)
	{
		m = new Mult1("loading.gif", null);
		setSize(m.getImgWidth(), m.getImgHeight());
		setLocation((main.getWidth() - m.getImgWidth()) / 2 + main.getX(),
				(main.getHeight() - m.getHeight()) / 2 + main.getY());
		
		setAlwaysOnTop(true);
		setOpacity(0.6f);
	
		add(m);
		setVisible(true);
	
	}

	public void play()
	{
		m.play(true);
		
	}

	@Override
	public void run()
	{
		play();
		
	}
}
