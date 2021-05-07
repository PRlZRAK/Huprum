package huprum.main;

import java.awt.BorderLayout;
import java.awt.Container;
import java.awt.Dimension;
import java.awt.Toolkit;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JFrame;

import huprum.main.chat.Chat;
import huprum.main.loginer.Loginer;

public class Huprum extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = -3256294715807967862L;
	private static final int DEFAULT_HEIGHT = 600;
	private static final int DEFAULT_WIDTH = 1024;
	private Loginer loginer;

	public Huprum(String title) {
		super(title);
		addWindowListener(new WindowAdapter() {
			public void windowClosing(WindowEvent e) {
				System.exit(0);
			}
		});
		setSize(DEFAULT_WIDTH, DEFAULT_HEIGHT);
		// setExtendedState(JFrame.MAXIMIZED_BOTH);
		Toolkit kit = Toolkit.getDefaultToolkit();
		Dimension screenSize = kit.getScreenSize();
		setLocation((screenSize.width - DEFAULT_WIDTH) / 2, (screenSize.height - DEFAULT_HEIGHT) / 2);
		loginer = new Loginer(this);
		//new Chat(this);
		setVisible(true);
		//test
		//Michail bober
	}

	public Loginer getLoginer() {
		return loginer;
	}

	public static void main(String[] args) {
		new Huprum("Messenger Huprum");
	}
}
