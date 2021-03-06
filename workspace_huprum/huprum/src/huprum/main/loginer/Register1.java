package huprum.main.loginer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONObject;

import huprum.main.Huprum;
import huprum.main.connections.Client;
import huprum.main.img.TukPanel;
import huprum.main.utils.Lang;
import huprum.main.utils.Utilit;

public class Register1 extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 7798770920700174385L;

	public class UsersActionListener1 implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent arg0)
		{
			String log = jlogin.getText();
			if (log.equals(""))
			{
				setErrLog(Lang.put("<html><p color=red>Fill in the field#<html><p color=red>заполнить"));
			} else
				setErrLog("");
			if (log.length() > 10)
			{
				setErrLog(Lang.put("<html><p color=red>" + Lang.put("more than 10 characters!#больше 10 знаков!")));
			} else
				setErrLog("");
			@SuppressWarnings("deprecation")
			String pass = jpass.getText();
			if (pass.equals(""))
			{
				setErrPas(Lang.put("<html><p color=red>Fill in the field#<html><p color=red>заполнить"));
			} else
				setErrPas("");
			String mail = jmail.getText();
			if (mail.trim().equals(""))
			{
				setErrMail(Lang.put("<html><p color=red>Fill in the field#<html><p color=red>заполнить"));
			} else
				setErrMail("");
			String phone = jphone.getText();
			if (phone.equals(""))
			{
				setErrPhone(Lang.put("<html><p color=red>Fill in the field#<html><p color=red>заполнить"));
				return;
			} else
				setErrPhone("");
			phone = Utilit.CleaPhone(phone);
			int i = Utilit.CheckLogin(phone);
			if (i != 1)
			{
				setErrPhone("<html><p color=red>Phone is invalid#<html><p color=red>некоректный телефон");
			} else
				setErrPhone("");
			int b = Utilit.CheckLogin(mail);
			if (b != 0)
			{
				setErrMail("<html><p color=red>Email is invalid#<html><p color=red>некоректная почта");
				return;
			} else
				setErrMail("");
			Map<String, String> pars = new HashMap<String, String>();
			pars.put("action", "registr");
			pars.put("login", log);
			pars.put("phone", phone);
			pars.put("email", mail);
			pars.put("password", pass);
			String otvet = null;
			try
			{
				otvet = cl.send(pars);
			} catch (IOException g)
			{
				g.printStackTrace();
			}
			JSONObject jo     = new JSONObject(otvet);
			int        status = jo.getInt("status");
			if (status == 2)
			{
				setErrLog("<html><p color=red>" + Lang.put(jo.getString("msg")));
				return;
			} else
				setErrLog("");
			if (status == 3)
				setErrPhone("<html><p color=red>" + Lang.put(jo.getString("msg")));
			else
				setErrPhone("");
			if (status == 4)
			{
				setErrMail("<html><p color=red>" + Lang.put(jo.getString("msg")));
				return;
			} else
				setErrMail("");
			if (status == 0)
				setVisible(false);
		}
	}

	private JTextField     jlogin;
	private JLabel         er_login;
	private JTextField     jmail;
	private JLabel         er_mail;
	private JTextField     jphone;
	private JLabel         er_phone;
	private JPasswordField jpass;
	private JLabel         er_pass;
	private Client         cl;

	public Register1(Huprum main)
	{
		super(Lang.put("Registration#Регистрация"));
		cl = main.getCl();
		setLocation(main.getLocation().x + 50, main.getLocation().y + 30);
		setSize(1024, 600);
		String pic_name;
		if (Lang.getLang().equals("ru"))
			pic_name = "brand_ru1.png";
		else
			pic_name = "brand_en1.png";
		TukPanel panel;
		try
		{
			panel = new TukPanel(pic_name, 250, 250, 40, 20, Utilit.COLOR_1068);
		} catch (IOException e1)
		{
			panel = (TukPanel) new JPanel();
		}
		panel.setLayout(new GridBagLayout());
		add(panel);
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(5, 5, 5, 5);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		panel.add(new JLabel(""), c);
		c.gridx = 1;
		c.gridy = 0;
		panel.add(new JLabel(Lang.put("Login:#Логин:")), c);
		c.gridy++;
		jlogin = new JTextField(20);
		er_login = new JLabel("");
		panel.add(jlogin, c);
		c.gridx++;
		panel.add(er_login, c);
		c.gridx = 1;
		c.gridy++;
		panel.add(new JLabel(Lang.put("Email address#Почта:")), c);
		c.gridy++;
		jmail = new JTextField(20);
		er_mail = new JLabel("");
		panel.add(jmail, c);
		c.gridx++;
		panel.add(er_mail, c);
		c.gridx = 1;
		c.gridy++;
		panel.add(new JLabel(Lang.put("Phone number:#Телефон:")), c);
		c.gridy++;
		jphone = new JTextField(20);
		er_phone = new JLabel("");
		panel.add(jphone, c);
		c.gridx++;
		panel.add(er_phone, c);
		c.gridx = 1;
		c.gridy++;
		panel.add(new JLabel(Lang.put("Password:#Пароль:")), c);
		c.gridy++;
		jpass = new JPasswordField(20);
		er_pass = new JLabel("");
		panel.add(jpass, c);
		c.gridx++;
		panel.add(er_pass, c);
		c.gridy++;
		c.gridx = 1;
		ActionListener usersListener = new UsersActionListener1();
		JButton        button        = new JButton(Lang.put("Register#Зарегестрироваться"));
		panel.add(button, c);
		button.addActionListener(usersListener);
	}

	@Override
	public void actionPerformed(ActionEvent arg0)
	{
		setVisible(true);
	}

	public void setErrPas(String string)
	{
		er_pass.setText(string);
	}

	public void setErrPhone(String string)
	{
		er_phone.setText(string);
	}

	public void setErrMail(String string)
	{
		er_mail.setText(string);
	}

	public void setErrLog(String string)
	{
		er_login.setText(string);
	}
}
