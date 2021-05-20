package huprum.main.chat;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;

import huprum.main.Huprum;
import huprum.main.chat.panels.CenterPanel;
import huprum.main.chat.panels.SouthPanel;
import huprum.main.chat.panels.WestPanel;
import huprum.main.loginer.Loginer;

public class Chat
{
	public SouthPanel sp;
	public CenterPanel cp;
	public WestPanel wp;
	
	public Chat(Huprum main)
	{
		
		main.getContentPane().removeAll();
		
	
		Loginer loginer = main.getLoginer();
	 		
		wp=new WestPanel(main);
		main.add(new JScrollPane(wp),BorderLayout.WEST);
		
		sp = new SouthPanel(main);
		main.add(sp,BorderLayout.SOUTH);
			
		cp=new CenterPanel(main);
		main.add(new JScrollPane(cp),BorderLayout.CENTER);
		main.repaint();
	
		
		

	}
}
