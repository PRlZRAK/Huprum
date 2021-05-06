package huprum.main.loginer.panels;


import java.awt.FlowLayout;
import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import huprum.main.loginer.Loginer;

public class Panel extends JPanel 
{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private TextField LogMailPhone;
	private TextField Pas;
	private JButton Enter;
	private JLabel LMP;
	private JLabel P;
	public Panel(Loginer main) { 
		
		setLayout (new FlowLayout(FlowLayout.CENTER));
		
		Pas=new TextField(60);
		LogMailPhone=new TextField(60);
		Enter=new JButton("Enter");
		LMP = new JLabel("Почта/Телефон/Логин");
		P = new JLabel("Пароль");
		add(LMP);
		add(LogMailPhone);
		add(P);
		add(Pas);
		add(Enter);

	
		
		
		
	}

}
