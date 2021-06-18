package huprum.main.loginer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import org.json.JSONObject;

import huprum.main.Huprum;
import huprum.main.chat.Chat;
import huprum.main.connections.Client;
import huprum.main.loginer.listener.LoginActionListener;
import huprum.main.utils.DTime;
import huprum.main.utils.Utilit;

public class Loginer
{
	private int    id;
	private String login;
	private String phone;
	private String email;
	private String password;
	/*                 */
	private JLabel     er_login;
	private JLabel     er_pass;
	private JTextField jlogin;
	private JTextField jpass;
	private Chat       chat;
	private JLabel     er_сonnection;
	private JCheckBox  jremember;
	private Client     cl;

	public Chat getChat()
	{
		return chat;
	}

	public void setChat(Chat chat)
	{
		this.chat = chat;
	}

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
		cl = main.getCl();
		JSONObject jo     = null;
		int        status = 0;
		try
		{
			String config = new String(Files.readAllBytes(Paths.get(Utilit.CONFIG)), StandardCharsets.UTF_8);
			if (config.equals(""))
				status = 2;
			else
			{
				jo = new JSONObject(config);
				status = jo.getInt("status");
			}
		} catch (IOException e)
		{
			//e.printStackTrace();
			status =2;
		}
		if (!(status == 2))
		{
			main.setLocation(jo.getInt("locatx"), jo.getInt("locaty"));
			main.setSize(jo.getInt("width"), jo.getInt("height"));
		}
		if (status == 1)
		{
			String              log  = (String) jo.get("login");
			int                 i    = Utilit.CheckLogin(log);
			String[]            par  = new String[]
			{ "mail", "phone", "login" };
			Map<String, String> pars = new HashMap<String, String>();
			pars.put("action", "login");
			pars.put(par[i], log);
			pars.put("dtime", DTime.now());
			String     otvet = null;
			JSONObject jo1;
			try
			{
				otvet = cl.send(pars);
			} catch (IOException e)
			{
				e.printStackTrace();
			}
			jo1 = new JSONObject(otvet);
			main.setRemember(true);
			id = jo1.getInt("id");
			login = (String) jo1.get("login");
			phone = (String) jo1.get("phone");
			password = (String) jo1.get("password");
			main.setLoginer(this);
			Chat chat = new Chat(main);
			setChat(chat);
		} else
		{
			JPanel panel = new JPanel();
			panel.setLayout(new GridBagLayout());
			main.add(panel);
			GridBagConstraints c = new GridBagConstraints();
			c.weightx = 1;
			c.anchor = GridBagConstraints.CENTER;
			c.insets = new Insets(5, 5, 5, 5);
			c.fill = GridBagConstraints.HORIZONTAL;
			c.gridx = 0;
			panel.add(new JLabel(""), c);
			c.gridx = 1;
			c.gridy = 0;
			er_сonnection = new JLabel("");
			panel.add(er_сonnection, c);
			c.gridy++;
			panel.add(new JLabel("Логин или Телефон или Почта:"), c);
			c.gridy++;
			jlogin = new JTextField(20);
			panel.add(jlogin, c);
			c.gridx++;
			er_login = new JLabel();
			panel.add(er_login, c);
			c.gridx = 1;
			c.gridy++;
			panel.add(new JLabel("Пароль:"), c);
			c.gridy++;
			jpass = new JPasswordField(20);
			panel.add(jpass, c);
			c.gridx++;
			er_pass = new JLabel();
			panel.add(er_pass, c);
			c.gridy++;
			c.gridx = 1;
			jremember = new JCheckBox("Запомнить на этом устройстве");
			panel.add(jremember, c);
			c.gridy++;
			JButton button = new JButton("Ввод");
			panel.add(button, c);
			button.addActionListener(new LoginActionListener(main));
			c.gridy++;
			JButton button_new_user = new JButton("Регистрация");
			panel.add(button_new_user, c);
			button_new_user.addActionListener(new Register1(main));
		}
	}

	public void setId(int id)
	{
		this.id = id;
	}

	public void setLogin(String login)
	{
		this.login = login;
	}

	public void setPhone(String phone)
	{
		this.phone = phone;
	}

	public void setPassword(String password)
	{
		this.password = password;
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

	public void setErrPas(String string)
	{
		er_pass.setText(string);
	}

	public void setEr_сonnection(String string)
	{
		er_сonnection.setText(string);
	}

	public JCheckBox getJremember()
	{
		return jremember;
	}
}
