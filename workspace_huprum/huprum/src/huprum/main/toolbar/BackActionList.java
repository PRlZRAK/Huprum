package huprum.main.toolbar;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.json.JSONObject;

import huprum.main.Huprum;
import huprum.main.connections.Client;
import huprum.main.loginer.Loginer;
import huprum.main.utils.Utilit;

public class BackActionList implements ActionListener {
	private Huprum main;
	private Loginer loginer;
	public BackActionList(Huprum main) {
		this.main = main;	
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		int confirm = JOptionPane.showConfirmDialog(main, "Вы уверены, что хотите выйти?", "Выход из аккаунта",
				JOptionPane.YES_OPTION, JOptionPane.QUESTION_MESSAGE);
		if (confirm == JOptionPane.YES_OPTION) {
		main.getContentPane().removeAll();		
		loginer = new Loginer(main);
		main.setLoginer(loginer);
		main.revalidate();
		main.repaint();
		}
	}
}
