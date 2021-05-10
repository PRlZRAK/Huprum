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
		
		String  pass     = loginer.getJpass();
		if (pass.equals(""))
		{
			loginer.setErrPas("<html><p color=red>заполнить");
			return;
		} else
			loginer.setErrPas("");
		// todo
		/* дальше:
		 * Определить что у нас в loginer
		 * отправить запрос на сервер (Client)
		 * Eсли satus 0 
		 * сверить пароль
		 * заполнить свойства в объекте Loginer
		 * и передать управление в Chat
		 * 
		 */
	}
}
