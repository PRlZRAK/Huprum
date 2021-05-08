package huprum.main.loginer.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import huprum.main.Huprum;
import huprum.main.loginer.Loginer;

public class LoginActionListener implements ActionListener
{
	private Huprum main;

	public LoginActionListener(Huprum main)
	{
		this.main = main;
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		Loginer loginer = main.getLoginer();
		String  log     = loginer.getJlogin();
		if (log.equals(""))
		{
			loginer.setErrLog("<html><p color=red>заполнить");
			return;
		} else
			loginer.setErrLog("");
		
		// todo
	}
}
