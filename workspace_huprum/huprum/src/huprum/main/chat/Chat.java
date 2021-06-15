package huprum.main.chat;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;

import huprum.main.Huprum;
import huprum.main.chat.panels.CenterPanel;
import huprum.main.chat.panels.SouthPanel;
import huprum.main.chat.panels.WestPanel;
import huprum.main.toolbar.ToolBar;

public class Chat {
	
	public ToolBar toolbar;
	public SouthPanel sp;
	public CenterPanel cp;
	public WestPanel wp;
	
	public JScrollPane scroll;

	public Chat(Huprum main) {
		main.getContentPane().setLayout(new BorderLayout());
		main.getContentPane().removeAll();
		toolbar=new ToolBar(main);
		main.add(toolbar, BorderLayout.PAGE_START);
		wp = new WestPanel(main);
		main.add(new JScrollPane(wp), BorderLayout.WEST);
		sp = new SouthPanel(main);
		main.add(sp, BorderLayout.SOUTH);
		cp = new CenterPanel(main);
		scroll = new JScrollPane(cp);
		main.add(scroll, BorderLayout.CENTER);
		/*
		 * перерисовка окна
		 */
		main.revalidate();
		main.repaint();
		Runnable demon = new Demon(this);
		new Thread(demon).start();
	}
}