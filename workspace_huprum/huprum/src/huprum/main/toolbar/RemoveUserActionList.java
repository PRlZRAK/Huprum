package huprum.main.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import huprum.main.Huprum;
import huprum.main.chat.UserButtomClass;
import huprum.main.connections.Client;
import huprum.main.loginer.Loginer;

public class RemoveUserActionList implements ActionListener
{
	private Client                  cl;
	private Loginer                 loginer;
	private String                  myId;
	private HashMap<String, String> pars;
	private Huprum                  main;

	public RemoveUserActionList(Huprum main)
	{
		this.main = main;
		cl = main.getCl();
		loginer = main.getLoginer();
		myId = Integer.toString(loginer.getId());
		pars = new HashMap<String, String>();
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		UserButtomClass button = loginer.getChat().wp.getLastButton();
		String          user   = button.getText();
		if (user.equals(""))
			return;
		int confirm = JOptionPane.showConfirmDialog(main, "Удалить " + user + " из списка чатов?", "Подтверждение",
				JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (confirm == JOptionPane.YES_OPTION)
		{
			String id = Integer.toString(button.getId());
			try
			{
				pars.put("action", "del_user");
				pars.put("myid", myId);
				pars.put("userid", id);
				cl.send(pars);
			} catch (IOException e1)
			{
				JOptionPane.showConfirmDialog(main, "Нет соединения с интернетом", "Ошибка", JOptionPane.CLOSED_OPTION,
						JOptionPane.ERROR_MESSAGE);
				e1.printStackTrace();
			}
		}
	}
}
