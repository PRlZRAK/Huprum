package huprum.main.loginer.panels;


import java.awt.FlowLayout;
import java.awt.TextField;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;

import huprum.main.Huprum;
import huprum.main.loginer.Loginer;
import huprum.main.loginer.listener.EnterActionList;

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
	public Panel(Huprum main) { 
		
		setLayout (new FlowLayout(FlowLayout.CENTER));
		
		Pas=new TextField(60);
		LogMailPhone=new TextField(60);
		Enter=new JButton("Enter");
		LMP = new JLabel("Телефон/Почта/Логин");
		P = new JLabel("Пароль");
		add(LMP);
		add(LogMailPhone);
		add(P);
		add(Pas);
		add(Enter);
        Enter.addActionListener(new EnterActionList(main));
      
	
		
		
	}
	public String GetLMP() 
	{
		String s = LogMailPhone.getText();
		return s;
	}
	public String GetPas() 
	{
		String s = Pas.getText();
		return s;
	}

}
