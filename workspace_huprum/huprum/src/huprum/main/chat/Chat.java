package huprum.main.chat;

import java.awt.BorderLayout;

import javax.swing.JScrollPane;

import huprum.main.Huprum;
import huprum.main.loginer.Loginer;
import huprum.main.panels.CenterPanel;
import huprum.main.panels.SouthPanel;
import huprum.main.panels.WestPanel;

public class Chat
{
	public SouthPanel sp;
	public CenterPanel cp;
	public WestPanel wp;
	
	public Chat(Huprum main)
	{
		Loginer loginer = main.getLoginer();
	 
		
		wp=new WestPanel(this);
		main.add(new JScrollPane(wp),BorderLayout.WEST);
		
		sp = new SouthPanel(this);
		main.add(sp,BorderLayout.SOUTH);
			
		cp=new CenterPanel(this);
		main.add(new JScrollPane(cp),BorderLayout.CENTER);
	
		
		

	}
}
