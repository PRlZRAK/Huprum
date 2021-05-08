package huprum.main.loginer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JTextField;

import huprum.main.Huprum;
import huprum.main.loginer.listener.LoginActionListener;

public class Loginer
{
	private int        id;
	private String     login;
	private String     phone;
	private String     email;
	private String     password;
	/*                 */
	private JLabel     er_login;
	private JLabel     er_pass;
	private JTextField jlogin;
	private JTextField jpass;

	public String getJlogin()
	{
		return jlogin.getText();
	}

	public String getJpass()
	{
		return jpass.getText();
	}

	public Loginer(Huprum main)
	{
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		main.add(panel);
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		// c.insets = new Insets(10, 1, 10, 1);
		c.gridx = 0;
		c.gridy = 0;
		panel.add(new JLabel("Логин или Телефон или Почта:"), c);
		c.gridy++;
		jlogin = new JTextField(20);
		panel.add(jlogin, c);
		c.gridx++;
		er_login = new JLabel();
		panel.add(er_login, c);
		c.gridx = 0;
		c.gridy++;
		panel.add(new JLabel("Пароль:"), c);
		c.gridy++;
		jpass = new JTextField(20);
		panel.add(jpass, c);
		c.gridx++;
		er_pass = new JLabel("<html><p color=red>ошибка");
		panel.add(er_pass, c);
		c.gridy++;
		c.gridx = 0;
		JButton button = new JButton("Ввод");
		panel.add(button, c);
		button.addActionListener(new LoginActionListener(main));
	}

	public int getId()
	{
		return id;
	}

	public String getLogin()
	{
		return login;
	}

	public String getPhone()
	{
		return phone;
	}

	public String getEmail()
	{
		return email;
	}

	public String getPassword()
	{
		return password;
	}

	public void setErrLog(String string)
	{
		er_login.setText(string);
		
	}
}
