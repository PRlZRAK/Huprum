package huprum.main.chat.panels;

import java.awt.Cursor;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.IOException;
import java.net.URL;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;

import javax.imageio.ImageIO;
import javax.swing.ImageIcon;
import javax.swing.JCheckBox;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;

import org.json.JSONObject;

import huprum.main.Huprum;
import huprum.main.connections.Client;
import huprum.main.img.ImageManipulation;
import huprum.main.img.TukPanel;
import huprum.main.utils.Lang;
import huprum.main.utils.Utilit;

public class EastPanel extends TukPanel
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
	private Huprum                  main;

	public EastPanel(Huprum main) throws IOException
	{
		super("logo_en.png", 100, 100, 10, 12, Utilit.COLOR_1085);
		this.main = main;
		setBackground(Utilit.COLOR_1085);
		personal_data = main.getPersonalData();
		id = personal_data.getString("id");
		cl = main.getCl();
		pars = new HashMap<String, String>();
		ImageIcon icon;
		if (personal_data.getInt("avatar") == 0)
		{
			try
			{
				icon = new ImageIcon(ImageIO.read(new URL(Utilit.IMG_URL + "user.png")));
			} catch (IOException e1)
			{
				icon = null;
			}
			show = false;
		} else
		{
			try
			{
				im = main.store.getAvaImg(Integer.parseInt(id));
				icon = im.getImageIcon(200, 200);
				show = true;
			} catch (NumberFormatException | SQLException | IOException | NullPointerException e1)
			{
				try
				{
					icon = new ImageIcon(ImageIO.read(new URL(Utilit.IMG_URL + "mask.jpg")));
				} catch (IOException e2)
				{
					icon = null;
				}
				show = false;
			}
		}
		ImageIcon pensil_image;
		try
		{
			pensil_image = new ImageIcon(ImageIO.read(new URL(Utilit.IMG_URL + "pensil.png")));
		} catch (IOException e2)
		{
			pensil_image = null;
		}
		setLayout(new GridBagLayout());
		GridBagConstraints c = new GridBagConstraints();
		c.insets = new Insets(5, 10, 5, 10);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy = 0;
		c.gridwidth = 2;
		String logo_name;
		if (Lang.getLang().equals("ru"))
			logo_name = "logo_ru.png";
		else
			logo_name = "logo_en.png";
		super.setImg(logo_name);
		c.gridwidth = 1;
		c.gridx = 3;
		ImageIcon close_image;
		JLabel    label_close = new JLabel();
		label_close.setToolTipText(Lang.put("Close a window#Закрыть окно"));
		try
		{
			close_image = new ImageIcon(ImageIO.read(new URL(Utilit.IMG_URL + "close.png")));
			label_close.setIcon(close_image);
		} catch (IOException e)
		{
			label_close.setText("X");
		}
		label_close.addMouseListener(new CloseWidow());
		add(label_close, c);
		c.gridwidth = 3;
		c.gridy++;
		c.gridx = 0;
		add(new JLabel("<html><h2 >" + Lang.put("Profile#Профиль")), c);
		//
		c.gridwidth = 2;
		c.gridy++;
		c.gridx = 0;
		label_icon = new JLabel();
		if (icon != null)
			label_icon.setIcon(icon);
		else
			label_icon.setText("Portrait");
		label_icon.addMouseListener(new iconShow(this));
		add(label_icon, c);
		c.gridx = 2;
		c.gridwidth = 1;
		JLabel pensil1 = new JLabel();
		pensil1.setToolTipText(Lang.put("Edit#Редактировать"));
		if (pensil_image == null)
			pensil1.setText("pensil");
		else
			pensil1.setIcon(pensil_image);
		pensil1.addMouseListener(new FaceEditList(this));
		add(pensil1, c);
		//
		c.gridy++;
		c.gridx = 0;
		add(new JLabel(Lang.put("Login:#Логин:")), c);
		c.gridx = 1;
		login_label = new JLabel(personal_data.getString("login"));
		add(login_label, c);
		c.gridx = 2;
		JLabel pensil2 = new JLabel();
		pensil2.setToolTipText(Lang.put("Edit#Редактировать"));
		if (pensil_image == null)
			pensil2.setText("pensil");
		else
			pensil2.setIcon(pensil_image);
		pensil2.addMouseListener(new editLogin(this));
		add(pensil2, c);
		//
		c.gridy++;
		c.gridx = 0;
		add(new JLabel(Lang.put("Phone number:#Tелефон:")), c);
		c.gridx = 1;
		phone_label = new JLabel(personal_data.getString("phone"));
		add(phone_label, c);
		c.gridx = 2;
		JLabel pensil3 = new JLabel();
		pensil3.setToolTipText(Lang.put("Edit#Редактировать"));
		if (pensil_image == null)
			pensil3.setText("pensil");
		else
			pensil3.setIcon(pensil_image);
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
		add(new JLabel(Lang.put("Email address:#Почта:")), c);
		c.gridx = 1;
		email_label = new JLabel(personal_data.getString("email"));
		add(email_label, c);
		c.gridx = 2;
		JLabel pensil4 = new JLabel();
		pensil4.setToolTipText(Lang.put("Edit#Редактировать"));
		if (pensil_image == null)
			pensil4.setText("pensil");
		else
			pensil4.setIcon(pensil_image);
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
		add(new JLabel(Lang.put("Full name:#Ф.И.О:")), c);
		c.gridx = 1;
		String fio = "";
		if (!personal_data.isNull("fio"))
			fio = personal_data.getString("fio");
		fio_label = new JLabel("<html><p>" + Utilit.insertWordWrap(fio, text_whide, "<br>"));
		add(fio_label, c);
		c.gridx = 2;
		JLabel pensil5 = new JLabel();
		pensil5.setToolTipText(Lang.put("Edit#Редактировать"));
		if (pensil_image == null)
			pensil5.setText("pensil");
		else
			pensil5.setIcon(pensil_image);
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
		add(new JLabel(Lang.put("Password:#Пароль:")), c);
		c.gridx = 1;
		pass_label = new JLabel(stars(personal_data.getString("password")));
		add(pass_label, c);
		c.gridx = 2;
		JLabel pensil6 = new JLabel();
		pensil6.setToolTipText(Lang.put("Edit#Редактировать"));
		if (pensil_image == null)
			pensil6.setText("pensil");
		else
			pensil6.setIcon(pensil_image);
		pensil6.addMouseListener(new editPass(this));
		add(pensil6, c);
		c.gridx = 0;
		c.gridy++;
		c.gridwidth = 4;
		add(new JLabel("<html><i>" + Utilit.insertWordWrap(
				Lang.put("The checkboxes mark the data that you are ready to show to your interlocutors."
						+ "#Галочками отмечены те данные, которые вы готовы показывать своим собеседникам."),
				32, "<br>")), c);
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
					return;
			} catch (IOException e)
			{
				JOptionPane.showMessageDialog(eastPanel, "Пожалуйста выберите другую картинку",
						"Очень испорченное изображение", JOptionPane.ERROR_MESSAGE);
				return;
			}
			try
			{
				// Если картинка большая то уменьшить
				im.checkMaxSize(400, 400);
			} catch (IOException e1)
			{
				e1.printStackTrace();
				return;
			}
			label_icon.setIcon(im.getImageIcon(200, 200));
			Map<String, String> pars = new HashMap<String, String>();
			pars.put("action", "edit_user");
			pars.put("id", personal_data.getString("id"));
			pars.put("img", im.getBase64());
			pars.put("width", im.getWidth() + "");
			pars.put("height", im.getHeight() + "");
			try
			{
				cl.send(pars);
			} catch (IOException e)
			{
				JOptionPane.showMessageDialog(eastPanel, Lang.put("Connection is lost#Нет соединения с сервером"),
						Lang.put("Error#Ошибка"), JOptionPane.ERROR_MESSAGE);
				return;
			}
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
			((JLabel) arg0.getSource()).setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
			((JLabel) arg0.getSource()).setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
			((JLabel) arg0.getSource()).setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
			((JLabel) arg0.getSource()).setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
			String n = (String) JOptionPane.showInputDialog(main,
					Lang.put("Enter your full name#Введите Фамилию Имя Отчество:"),
					Lang.put("Editing your personal data#Редактируем личные данные"), JOptionPane.NO_OPTION, null, null,
					data);
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
				JOptionPane.showMessageDialog(main, Lang.put("Connection is lost#Нет соединения с сервером"),
						Lang.put("Error#Ошибка"), JOptionPane.ERROR_MESSAGE);
				return;
			}
			JSONObject jo = new JSONObject(answer);
			if (jo.getInt("status") != 0)
				System.err.println("FaceEditList " + jo.getString("msg"));
			else
				fio_label.setText("<html><p>" + Utilit.insertWordWrap(n, text_whide, "<br>"));
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
			((JLabel) arg0.getSource()).setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
			((JLabel) arg0.getSource()).setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
			String n = (String) JOptionPane.showInputDialog(main, Lang.put("Enter a new login:#Введите новый логин:"),
					Lang.put("Editing your personal data#Редактируем личные данные"), JOptionPane.NO_OPTION, null, null,
					data);
			if (n == null)
				return;
			if (n.length() > 10)
			{
				JOptionPane.showMessageDialog(main,
						Lang.put("Login is more than 10 characters!#Логин больше 10 знаков!"), Lang.put("Error#Ошибка"),
						JOptionPane.ERROR_MESSAGE);
				return;
			}
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
				JOptionPane.showMessageDialog(main, Lang.put("Connection is lost#Нет соединения с сервером"),
						Lang.put("Error#Ошибка"), JOptionPane.ERROR_MESSAGE);
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
			((JLabel) arg0.getSource()).setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
			((JLabel) arg0.getSource()).setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
			String n = (String) JOptionPane.showInputDialog(main,
					Lang.put("Enter a new phone number:#Введите новый телефонный номер:"),
					Lang.put("Editing your personal data#Редактируем личные данные"), JOptionPane.NO_OPTION, null, null,
					data);
			if (n == null)
				return;
			if (!Utilit.isPhone(n))
			{
				JOptionPane.showMessageDialog(main, Lang.put("Phone is invalid #Неправильный телефон ") + n,
						Lang.put("Error!#Ошибка!"), JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(main, Lang.put("Connection is lost#Нет соединения с сервером"),
						Lang.put("Error#Ошибка"), JOptionPane.ERROR_MESSAGE);
				return;
			}
			JSONObject jo = new JSONObject(answer);
			if (jo.getInt("status") != 0)
				JOptionPane.showMessageDialog(main, jo.getString("msg"), Lang.put("Error#Ошибка"),
						JOptionPane.ERROR_MESSAGE);
			else
				phone_label.setText(n);
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
			((JLabel) arg0.getSource()).setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
			((JLabel) arg0.getSource()).setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
			String n = (String) JOptionPane.showInputDialog(main, Lang.put("Enter a new email:#Введите новую почту:"),
					Lang.put("Editing your personal data#Редактируем личные данные"), JOptionPane.NO_OPTION, null, null,
					data);
			if (n == null)
				return;
			if (!Utilit.isMail(n))
			{
				JOptionPane.showMessageDialog(main, Lang.put("Email is invalid #Неправильная почта ") + n,
						Lang.put("Error!#Ошибка!"), JOptionPane.ERROR_MESSAGE);
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
				JOptionPane.showMessageDialog(main, Lang.put("Connection is lost#Нет соединения с сервером"),
						Lang.put("Error#Ошибка"), JOptionPane.ERROR_MESSAGE);
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
			((JLabel) arg0.getSource()).setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
			((JLabel) arg0.getSource()).setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
			String n = (String) JOptionPane.showInputDialog(main,
					Lang.put("Enter a new password#Введите новый пароль:"),
					Lang.put("Editing your personal data#Редактируем личные данные"), JOptionPane.NO_OPTION, null, null,
					data);
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
				JOptionPane.showMessageDialog(main, Lang.put("Connection is lost#Нет соединения с сервером"),
						Lang.put("Error#Ошибка"), JOptionPane.ERROR_MESSAGE);
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
			((JLabel) arg0.getSource()).setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
			((JLabel) arg0.getSource()).setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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

	public class CloseWidow implements MouseListener
	{
		@Override
		public void mouseClicked(MouseEvent arg0)
		{
			JScrollPane sep = main.getLoginer().getChat().sep;
			if (sep.isVisible())
				sep.setVisible(false);
			else
				sep.setVisible(true);
			main.revalidate();
			main.repaint();
		}

		@Override
		public void mouseEntered(MouseEvent arg0)
		{
			((JLabel) arg0.getSource()).setCursor(new Cursor(Cursor.HAND_CURSOR));
		}

		@Override
		public void mouseExited(MouseEvent arg0)
		{
			((JLabel) arg0.getSource()).setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
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
