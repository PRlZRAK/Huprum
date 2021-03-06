package huprum.main.loginer;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.io.IOException;
import java.sql.SQLException;

import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import huprum.main.Huprum;
import huprum.main.chat.Chat;
import huprum.main.img.TukPanel;
import huprum.main.utils.Lang;
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
	private JButton    forgotBut;

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
		try
		{
			int width  = Integer.parseInt(main.store.getParam("width"));
			int height = Integer.parseInt(main.store.getParam("height"));
			int locatx = Integer.parseInt(main.store.getParam("locatx"));
			int locaty = Integer.parseInt(main.store.getParam("locaty"));
			main.setLocation(locatx, locaty);
			main.setSize(width, height);
			String crys  = main.store.getParam("cry_sound");
			String stucs = main.store.getParam("stuc_sound");
			String sends = main.store.getParam("send_sound");
			String dyats = main.store.getParam("dyatel_show");
			if (!(crys == null) && Integer.parseInt(crys) == 0)
				Utilit.SET_CRY_SOUND = false;
			if (!(stucs == null) && Integer.parseInt(stucs) == 0)
				Utilit.SET_STUK_SOUND = false;
			if (!(sends == null) && Integer.parseInt(sends) == 0)
				Utilit.SET_SEND_SOUND = false;
			if (!(dyats == null) && Integer.parseInt(dyats) == 0)
				Utilit.SET_DYATEL_SHOW = false;
		} catch (NumberFormatException | SQLException e)
		{
		}
		{
			String pic_name;
			if (Lang.getLang().equals("ru"))
				pic_name = "brand_ru1.png";
			else
				pic_name = "brand_en1.png";
			TukPanel panel;
			try
			{
				panel = new TukPanel(pic_name, 350, 350, 50, 30, Utilit.COLOR_1068);
			} catch (IOException e1)
			{
				panel = (TukPanel) new JPanel();
			}
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
			panel.add(new JLabel(Lang.put("Username, email address or phone number#Логин, Телефон или Почта:")), c);
			c.gridy++;
			jlogin = new JTextField(20);
			panel.add(jlogin, c);
			c.gridx++;
			er_login = new JLabel();
			panel.add(er_login, c);
			c.gridx = 1;
			c.gridy++;
			panel.add(new JLabel(Lang.put("Password:#Пароль:")), c);
			c.gridy++;
			jpass = new JPasswordField(20);
			panel.add(jpass, c);
			c.gridx++;
			er_pass = new JLabel();
			panel.add(er_pass, c);
			c.gridy++;
			c.gridx = 1;
			jremember = new JCheckBox(Lang.put("Remember on this device#Запомнить на этом устройстве"));
			panel.add(jremember, c);
			c.gridy++;
			JButton button = new JButton(Lang.put("Sign in#Вход"));
			panel.add(button, c);
			button.addActionListener(new LoginActionListener(main));
			c.gridy++;
			JButton button_new_user = new JButton(Lang.put("Register now#Зарегистрироваться"));
			panel.add(button_new_user, c);
			button_new_user.addActionListener(new Register1(main));
			c.gridy++;
			forgotBut = new JButton(Lang.put("Forgot Password?#Забыл пароль?"));
			panel.add(forgotBut, c);
			forgotBut.addActionListener(new RestorePas(main));
			forgotBut.setVisible(false);
			try
			{
				String rem = main.store.getParam("remember");
				if (!(rem == null) && Integer.parseInt(rem) == 1)
				{
					jlogin.setText(main.store.getParam("login"));
					jpass.setText(main.store.getParam("password"));
					jremember.setSelected(true);
				}
			} catch (SQLException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
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

	public JButton getForgotBut()
	{
		return forgotBut;
	}

	public void setNewPass(String newPas)
	{
		jpass.setText(newPas);
	}
}
