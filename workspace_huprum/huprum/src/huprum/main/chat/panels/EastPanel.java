package huprum.main.chat.panels;

import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import org.json.JSONObject;

import huprum.main.Huprum;
import huprum.main.connections.Client;
import huprum.main.img.ImageManipulation;
import huprum.main.utils.Utilit;

public class EastPanel extends JPanel
{
	/**
	 * 
	 */
	private static final long       serialVersionUID = -2864670554727813946L;
	private int                     text_whide       = 20;
	private JSONObject              personal_data;
	private JLabel                  label_icon;
	private Client                  cl;
	private boolean                 show;
	private ImageManipulation       im;
	private JLabel                  login_label;
	private JLabel                  phone_label;
	private JLabel                  email_label;
	private JLabel                  fio_label;
	private JLabel                  pass_label;
	private HashMap<String, String> pars;
	private String                  id;
	private JCheckBox               check_phone;
	private JCheckBox               check_email;
	private JCheckBox               check_fio;

	public EastPanel(Huprum main) throws IOException
	{
		setBackground(Utilit.COLOR_389);
		personal_data = main.getPersonalData();
		id = personal_data.getString("id");
		cl = main.getCl();
		pars = new HashMap<String, String>();
		ImageIcon icon;
		if (personal_data.getInt("avatar") == 0)
		{
			icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(Huprum.class.getResource("img/user.png")));
			show = false;
		} else
		{
			try
			{
				im = main.store.getAvaImg(Integer.parseInt(id));
				icon = im.getImageIcon(200, 200);
				show = true;
			} catch (NumberFormatException | SQLException | IOException e1)
			{
				icon = new ImageIcon(Toolkit.getDefaultToolkit().createImage(Huprum.class.getResource("img/mask.jpg")));
				show = false;
				e1.printStackTrace();
			}
		}
		ImageIcon pensil_image = new ImageIcon(
				Toolkit.getDefaultToolkit().createImage(Huprum.class.getResource("img/pensil.png")));
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(10, 5, 15, 5);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 3;
		add(new JLabel("<html><h2 >Профиль"), c);
		c.gridwidth = 2;
		c.gridy++;
		c.gridx = 0;
		label_icon = new JLabel(icon);
		label_icon.addMouseListener(new iconShow(this));
		add(label_icon, c);
		c.gridx = 2;
		c.gridwidth = 1;
		JLabel pensil1 = new JLabel(pensil_image);
		pensil1.addMouseListener(new FaceEditList(this));
		add(pensil1, c);
		//
		c.gridy++;
		c.gridx = 0;
		add(new JLabel("Login:"), c);
		c.gridx = 1;
		login_label = new JLabel(personal_data.getString("login"));
		add(login_label, c);
		c.gridx = 2;
		JLabel pensil2 = new JLabel(pensil_image);
		pensil2.addMouseListener(new editLogin(this));
		add(pensil2, c);
		//
		c.gridy++;
		c.gridx = 0;
		add(new JLabel("Tелефон:"), c);
		c.gridx = 1;
		phone_label = new JLabel(personal_data.getString("phone"));
		add(phone_label, c);
		c.gridx = 2;
		JLabel pensil3 = new JLabel(pensil_image);
		pensil3.addMouseListener(new editPhone(this));
		add(pensil3, c);
		c.gridx = 3;
		check_phone = new JCheckBox();
		if (personal_data.getInt("show_phone") == 0)
			check_phone.setSelected(false);
		else
			check_phone.setSelected(true);
		check_phone.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				String check = e.getStateChange() == 1 ? "1" : "0";
				pars.clear();
				pars.put("action", "check_show");
				pars.put("id", id);
				pars.put("param", "phone");
				pars.put("val", check);
				try
				{
					cl.send(pars);
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		add(check_phone, c);
		//
		c.gridy++;
		c.gridx = 0;
		add(new JLabel("Почта:"), c);
		c.gridx = 1;
		email_label = new JLabel(personal_data.getString("email"));
		add(email_label, c);
		c.gridx = 2;
		JLabel pensil4 = new JLabel(pensil_image);
		pensil4.addMouseListener(new editEmail(this));
		add(pensil4, c);
		c.gridx = 3;
		check_email = new JCheckBox();
		if (personal_data.getInt("show_email") == 0)
			check_email.setSelected(false);
		else
			check_email.setSelected(true);
		check_email.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				String check = e.getStateChange() == 1 ? "1" : "0";
				pars.clear();
				pars.put("action", "check_show");
				pars.put("id", id);
				pars.put("param", "email");
				pars.put("val", check);
				try
				{
					cl.send(pars);
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		add(check_email, c);
		//
		c.gridy++;
		c.gridx = 0;
		add(new JLabel("Ф.И.О:"), c);
		c.gridx = 1;
		String fio = "";
		if (!personal_data.isNull("fio"))
			fio = personal_data.getString("fio");
		fio_label = new JLabel("<html><p>" + Utilit.InsertPerenos(fio, text_whide, "<br>"));
		add(fio_label, c);
		c.gridx = 2;
		JLabel pensil5 = new JLabel(pensil_image);
		pensil5.addMouseListener(new editFio(this));
		add(pensil5, c);
		c.gridx = 3;
		check_fio = new JCheckBox();
		if (personal_data.getInt("show_fio") == 0)
			check_fio.setSelected(false);
		else
			check_fio.setSelected(true);
		check_fio.addItemListener(new ItemListener()
		{
			@Override
			public void itemStateChanged(ItemEvent e)
			{
				String check = e.getStateChange() == 1 ? "1" : "0";
				pars.clear();
				pars.put("action", "check_show");
				pars.put("id", id);
				pars.put("param", "fio");
				pars.put("val", check);
				try
				{
					cl.send(pars);
				} catch (IOException e1)
				{
					e1.printStackTrace();
				}
			}
		});
		add(check_fio, c);
		//
		c.gridy++;
		c.gridx = 0;
		add(new JLabel("Пароль:"), c);
		c.gridx = 1;
		pass_label = new JLabel(stars(personal_data.getString("password")));
		add(pass_label, c);
		c.gridx = 2;
		JLabel pensil6 = new JLabel(pensil_image);
		pensil6.addMouseListener(new editPass(this));
		add(pensil6, c);
		c.gridx = 0;
		c.gridy++;
		c.gridwidth = 4;
		add(new JLabel("<html><i>" + Utilit.InsertPerenos(
				"Галочками отмечены те данные, которые вы готовы показывать своим собеседникам.", 32, "<br>")), c);
	}

	private String stars(String string)
	{
		String tmp = "";
		for (int i = 0; i < string.length(); i++)
			tmp += "*";
		return tmp;
	}

	public class FaceEditList implements MouseListener
	{
		private EastPanel eastPanel;

		public FaceEditList(EastPanel eastPanel)
		{
			this.eastPanel = eastPanel;
		}

		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			ImageManipulation im = null;
			try
			{
				im = new ImageManipulation(eastPanel);
				if (im.getImage() == null)
				{
					JOptionPane.showMessageDialog(eastPanel, "Пожалуйста выберите другую картинку",
							"Испорченное изображение", JOptionPane.ERROR_MESSAGE);
					return;
				}
			} catch (IOException e)
			{
				JOptionPane.showMessageDialog(eastPanel, "Пожалуйста выберите другую картинку",
						"Очень испорченное изображение", JOptionPane.ERROR_MESSAGE);
				return;
			}
			label_icon.setIcon(im.getImageIcon(200, 200));
			Map<String, String> pars = new HashMap<String, String>();
			pars.put("action", "edit_user");
			pars.put("id", personal_data.getString("id"));
			pars.put("img", im.getBase64());
			String answer = null;
			try
			{
				answer = cl.send(pars);
			} catch (IOException e)
			{
				JOptionPane.showMessageDialog(eastPanel, "Нет соединения с сервером", "Потеряна связь",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JSONObject jo = new JSONObject(answer);
			if (jo.getInt("status") != 0)
				System.err.println("FaceEditList " + jo.getString("msg"));
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
		}

		@Override
		public void mousePressed(MouseEvent arg0)
		{
		}

		@Override
		public void mouseReleased(MouseEvent arg0)
		{
		}
	}

	public class iconShow implements MouseListener
	{
		private EastPanel eastPanel;

		public iconShow(EastPanel eastPanel)
		{
			this.eastPanel = eastPanel;
		}

		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			if (show)
				try
				{
					im.show(eastPanel, "");
				} catch (IOException e)
				{
					e.printStackTrace();
				}
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
		}

		@Override
		public void mousePressed(MouseEvent arg0)
		{
		}

		@Override
		public void mouseReleased(MouseEvent arg0)
		{
		}
	}

	public class editFio implements MouseListener
	{
		private EastPanel main;

		public editFio(EastPanel eastPanel)
		{
			main = eastPanel;
		}

		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			String data     = "";
			String par_name = "fio";
			if (!personal_data.isNull(par_name))
				data = personal_data.getString(par_name);
			String n = (String) JOptionPane.showInputDialog(main, "Введите Фамилию Имя Отчество:",
					"Редактируем личные данные", JOptionPane.NO_OPTION, null, null, data);
			if (n == null)
				return;
			Map<String, String> pars = new HashMap<String, String>();
			pars.put("action", "edit_user");
			pars.put("id", personal_data.getString("id"));
			pars.put("fio", n);
			String answer = null;
			try
			{
				answer = cl.send(pars);
			} catch (IOException e)
			{
				JOptionPane.showMessageDialog(main, "Нет соединения с сервером", "Потеряна связь",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JSONObject jo = new JSONObject(answer);
			if (jo.getInt("status") != 0)
				System.err.println("FaceEditList " + jo.getString("msg"));
			else
				fio_label.setText("<html><p>" + Utilit.InsertPerenos(n, text_whide, "<br>"));
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
		}

		@Override
		public void mousePressed(MouseEvent arg0)
		{
		}

		@Override
		public void mouseReleased(MouseEvent arg0)
		{
		}
	}

	public class editLogin implements MouseListener
	{
		private EastPanel main;

		public editLogin(EastPanel eastPanel)
		{
			main = eastPanel;
		}

		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			String data     = "";
			String par_name = "login";
			if (!personal_data.isNull(par_name))
				data = personal_data.getString(par_name);
			String n = (String) JOptionPane.showInputDialog(main, "Логин:", "Редактируем личные данные",
					JOptionPane.NO_OPTION, null, null, data);
			if (n == null)
				return;
			Map<String, String> pars = new HashMap<String, String>();
			pars.put("action", "edit_user");
			pars.put("id", personal_data.getString("id"));
			pars.put("login", n);
			String answer = null;
			try
			{
				answer = cl.send(pars);
			} catch (IOException e)
			{
				JOptionPane.showMessageDialog(main, "Нет соединения с сервером", "Потеряна связь",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JSONObject jo = new JSONObject(answer);
			if (jo.getInt("status") != 0)
				// System.err.println("FaceEditList " + jo.getString("msg"));
				JOptionPane.showMessageDialog(main, jo.getString("msg"), "Ошибка", JOptionPane.ERROR_MESSAGE);
			else
				login_label.setText(n);
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
		}

		@Override
		public void mousePressed(MouseEvent arg0)
		{
		}

		@Override
		public void mouseReleased(MouseEvent arg0)
		{
		}
	}

	public class editPhone implements MouseListener
	{
		private EastPanel main;

		public editPhone(EastPanel eastPanel)
		{
			main = eastPanel;
		}

		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			String data     = "";
			String par_name = "phone";
			if (!personal_data.isNull(par_name))
				data = personal_data.getString(par_name);
			String n = (String) JOptionPane.showInputDialog(main, "Телефон:", "Редактируем личные данные",
					JOptionPane.NO_OPTION, null, null, data);
			if (n == null)
				return;
			if (!Utilit.isPhone(n))
			{
				JOptionPane.showMessageDialog(main, "Неправильный телефон " + n, "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			n = Utilit.CleaPhone(n);
			Map<String, String> pars = new HashMap<String, String>();
			pars.put("action", "edit_user");
			pars.put("id", personal_data.getString("id"));
			pars.put("phone", n);
			String answer = null;
			try
			{
				answer = cl.send(pars);
			} catch (IOException e)
			{
				JOptionPane.showMessageDialog(main, "Нет соединения с сервером", "Потеряна связь",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JSONObject jo = new JSONObject(answer);
			if (jo.getInt("status") != 0)
				JOptionPane.showMessageDialog(main, jo.getString("msg"), "Ошибка", JOptionPane.ERROR_MESSAGE);
			else
				phone_label.setText(n);
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
		}

		@Override
		public void mousePressed(MouseEvent arg0)
		{
		}

		@Override
		public void mouseReleased(MouseEvent arg0)
		{
		}
	}

	public class editEmail implements MouseListener
	{
		private EastPanel main;

		public editEmail(EastPanel eastPanel)
		{
			main = eastPanel;
		}

		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			String data     = "";
			String par_name = "email";
			if (!personal_data.isNull(par_name))
				data = personal_data.getString(par_name);
			String n = (String) JOptionPane.showInputDialog(main, "Email:", "Редактируем личные данные",
					JOptionPane.NO_OPTION, null, null, data);
			if (n == null)
				return;
			if (!Utilit.isMail(n))
			{
				JOptionPane.showMessageDialog(main, "Неправильная почта " + n, "Ошибка!", JOptionPane.ERROR_MESSAGE);
				return;
			}
			Map<String, String> pars = new HashMap<String, String>();
			pars.put("action", "edit_user");
			pars.put("id", personal_data.getString("id"));
			pars.put("email", n);
			String answer = null;
			try
			{
				answer = cl.send(pars);
			} catch (IOException e)
			{
				JOptionPane.showMessageDialog(main, "Нет соединения с сервером", "Потеряна связь",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JSONObject jo = new JSONObject(answer);
			if (jo.getInt("status") != 0)
				// System.err.println("FaceEditList " + jo.getString("msg"));
				JOptionPane.showMessageDialog(main, jo.getString("msg"), "Ошибка", JOptionPane.ERROR_MESSAGE);
			else
				email_label.setText(n);
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
		}

		@Override
		public void mousePressed(MouseEvent arg0)
		{
		}

		@Override
		public void mouseReleased(MouseEvent arg0)
		{
		}
	}

	public class editPass implements MouseListener
	{
		private EastPanel main;

		public editPass(EastPanel eastPanel)
		{
			main = eastPanel;
		}

		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			String data     = "";
			String par_name = "password";
			if (!personal_data.isNull(par_name))
				data = personal_data.getString(par_name);
			String n = (String) JOptionPane.showInputDialog(main, "Введите новый пароль:", "Редактируем личные данные",
					JOptionPane.NO_OPTION, null, null, data);
			if (n == null)
				return;
			Map<String, String> pars = new HashMap<String, String>();
			pars.put("action", "edit_user");
			pars.put("id", personal_data.getString("id"));
			pars.put("password", n);
			String answer = null;
			try
			{
				answer = cl.send(pars);
			} catch (IOException e)
			{
				JOptionPane.showMessageDialog(main, "Нет соединения с сервером", "Потеряна связь",
						JOptionPane.ERROR_MESSAGE);
				return;
			}
			JSONObject jo = new JSONObject(answer);
			if (jo.getInt("status") != 0)
				System.err.println("FaceEditList " + jo.getString("msg"));
			else
				pass_label.setText(stars(n));
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
		}

		@Override
		public void mousePressed(MouseEvent arg0)
		{
		}

		@Override
		public void mouseReleased(MouseEvent arg0)
		{
		}
	}
}
