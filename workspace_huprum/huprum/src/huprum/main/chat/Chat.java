package huprum.main.chat;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import huprum.main.Huprum;
import huprum.main.chat.panels.CenterPanel;
import huprum.main.chat.panels.EastPanel;
import huprum.main.chat.panels.SouthPanel;
import huprum.main.chat.panels.UserPanel;
import huprum.main.chat.panels.WestPanel;
import huprum.main.toolbar.ToolBar;

public class Chat
{
	public ToolBar     toolbar;
	public SouthPanel  sp;
	public CenterPanel cp;
	public WestPanel   wp;
	public JScrollPane scroll;
	public EastPanel   ep;
	public UserPanel   up;
	public JScrollPane sep;

	public Chat(Huprum main)
	{
		main.getContentPane().setLayout(new BorderLayout());
		main.getContentPane().removeAll();
		toolbar = new ToolBar(main);
		main.add(toolbar, BorderLayout.PAGE_START);
		JPanel wpanel = new JPanel();
		wpanel.setLayout(new BorderLayout());
		up = new UserPanel(main);
		wpanel.add(up, BorderLayout.NORTH);
		wp = new WestPanel(main);
		wpanel.add(new JScrollPane(wp), BorderLayout.CENTER);
		main.add(wpanel, BorderLayout.WEST);
		sp = new SouthPanel(main);
		main.add(sp, BorderLayout.SOUTH);
		cp = new CenterPanel(main);
		scroll = new JScrollPane(cp);
		main.add(scroll, BorderLayout.CENTER);
		try
		{
			ep = new EastPanel(main);
			sep = new JScrollPane(ep);
			sep.setVisible(false);
			main.add(sep, BorderLayout.EAST);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*
		 * перерисовка окна
		 */
		main.revalidate();
		main.repaint();
		Runnable demon = new Demon(this);
		new Thread(demon).start();
	}
}