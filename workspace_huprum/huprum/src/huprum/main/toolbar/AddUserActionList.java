package huprum.main.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JOptionPane;

import org.json.JSONObject;

import huprum.main.Huprum;
import huprum.main.chat.Chat;
import huprum.main.connections.Client;
import huprum.main.loginer.Loginer;
import huprum.main.utils.Utilit;

public class AddUserActionList implements ActionListener {
	private Client cl;
	private Loginer loginer;
	private Huprum main;
	private String myId;
	private HashMap<String, String> pars;

	public AddUserActionList(Huprum main) {
		this.main = main;
		cl = main.getCl();
		loginer = main.getLoginer();
		myId = Integer.toString(loginer.getId());
		pars = new HashMap<String, String>();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		AddUsOptonPane();
	}

	public void AddUsOptonPane() {

		String user = JOptionPane.showInputDialog(null, "Введите логин/почту/телефон того, кого хотите добавить",
				"Добавление переписки", JOptionPane.PLAIN_MESSAGE);
		if (user == null) {
			System.out.println("Отменено");
			return;
		}
		System.out.println("Подтверждено");
		int i = Utilit.CheckLogin(user);
		if (i == 1) {
			user = Utilit.CleaPhone(user);
		}
		String[] par = new String[] { "mail", "phone", "login" };
		pars.clear();
		pars.put("action", "login");
		pars.put(par[i], user);
		String otvet;
		try {
			otvet = cl.send(pars);
			System.out.println(otvet);
			JSONObject jo = new JSONObject(otvet);
			int status = jo.getInt("status");
			if (status != 0) {
				int repeat = JOptionPane.showConfirmDialog(null, "Пользователь не найден, попробовать ещё раз?",
						"Ошибка", JOptionPane.YES_OPTION, JOptionPane.PLAIN_MESSAGE);
				if (repeat == JOptionPane.YES_OPTION)
					AddUsOptonPane();
				return;
			}

			String id = Integer.toString(jo.getInt("id"));
			pars.put("action", "put_msg");
			pars.put("id_from", myId);
			pars.put("id_to", id);
			pars.put("msg", "Добавлена новая переписка");
			try {
				cl.send(pars);
			} catch (IOException b) {
				b.printStackTrace();
			}
		} catch (IOException d) {
			d.printStackTrace();
			return;
		}
	}

}
