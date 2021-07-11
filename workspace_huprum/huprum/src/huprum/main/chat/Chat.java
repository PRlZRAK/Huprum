package huprum.main.chat;

import java.awt.BorderLayout;
import java.io.IOException;

import javax.swing.JPanel;
import javax.swing.JScrollPane;

import huprum.main.Huprum;
import huprum.main.chat.panels.CenterPanel;
import huprum.main.chat.panels.DyatelPanel;
import huprum.main.chat.panels.EastPanel;
import huprum.main.chat.panels.SettingsPanel;
import huprum.main.chat.panels.SouthPanel;
import huprum.main.chat.panels.UserPanel;
import huprum.main.chat.panels.WestPanel;
import huprum.main.img.TukPanel;
import huprum.main.toolbar.ToolBar;
import huprum.main.utils.Utilit;

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
	public DyatelPanel dp;
	private TukPanel setp;
	private JPanel rightPanel;
	public JScrollPane scsetp;
	public JScrollPane lastrp;

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
		JPanel lp = new JPanel();
		lp.setLayout(new BorderLayout());
		dp = new DyatelPanel();
		dp.setBackground(Utilit.COLOR_1068);
		dp.setVisible(Utilit.SET_DYATEL_SHOW);
		cp = new CenterPanel(main);
		scroll = new JScrollPane(cp);
		scroll.setBorder(null);
		lp.add(scroll, BorderLayout.CENTER);
		lp.add(dp, BorderLayout.WEST);
		main.add(lp, BorderLayout.CENTER);
		try
		{
			lastrp = new JScrollPane();
			ep = new EastPanel(main);
			rightPanel = new JPanel();
			rightPanel.setLayout(new BorderLayout());
			sep = new JScrollPane(ep);
			sep.setVisible(false);
			rightPanel.add(sep, BorderLayout.CENTER);
		} catch (IOException e)
		{
			e.printStackTrace();
		}
		try
		{
			setp=new SettingsPanel(main);
		} catch (IOException e)
		{
			
			e.printStackTrace();
		}
		
		scsetp = new JScrollPane(setp);
		scsetp.setVisible(false);
		rightPanel.add(scsetp, BorderLayout.EAST);
        main.add(rightPanel, BorderLayout.EAST);
		
		
		/*
		 * перерисовка окна
		 */
		main.revalidate();
		main.repaint();
		Runnable demon = new Demon(this);
		new Thread(demon).start();
	}
}