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
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;

import org.json.JSONObject;

import huprum.main.Huprum;
import huprum.main.connections.Client;
import huprum.main.utils.Lang;
import huprum.main.utils.Utilit;

public class RestorePas extends JFrame implements ActionListener
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private JTextField        jlogin;
	private JTextField        jmail;
	private JTextField        jphone;
	private JLabel            erLab;
	private Client            cl;
	private Huprum main;

	public RestorePas(Huprum main)
	{
		
		super(Lang.put("Password recovery#Восстановление пароля"));
		cl = main.getCl();
		this.main = main;
		setLocation(main.getLocation().x + 50, main.getLocation().y + 30);
		setSize(500, 400);
		JPanel panel = new JPanel();
		panel.setLayout(new GridBagLayout());
		add(panel);
		GridBagConstraints c = new GridBagConstraints();
		c.weightx = 1;
		c.anchor = GridBagConstraints.CENTER;
		c.insets = new Insets(5, 5, 5, 5);
		c.fill = GridBagConstraints.HORIZONTAL;
		c.gridx = 0;
		c.gridy=0;
		erLab= new JLabel("");
		panel.add(erLab , c);
		c.gridy++;
		panel.add(new JLabel("Логин:"), c);
		c.gridy++;
		jlogin = new JTextField(20);
		panel.add(jlogin, c);
		c.gridy++;
		panel.add(new JLabel("Почта:"), c);
		c.gridy++;
		jmail = new JTextField(20);
		panel.add(jmail, c);
		c.gridy++;
		panel.add(new JLabel("Телефон:"), c);
		c.gridy++;
		jphone = new JTextField(20);
		panel.add(jphone, c);
		c.gridy++;
		ActionListener restoreList = new RestoreActionListener();
		JButton        button      = new JButton("Ввод");
		panel.add(button, c);
		button.addActionListener(restoreList);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		setVisible(true);
	}

	public class RestoreActionListener implements ActionListener
	{
		@Override
		public void actionPerformed(ActionEvent e)
		{
			String log = jlogin.getText();
			if (log.equals(""))
			{
				setErrLab("<html><p color=red>Заполните все поля");
			} else
				setErrLab("");
			
			String mail = jmail.getText();
			if (mail.equals(""))
			{
				setErrLab("<html><p color=red>Заполните все поля");
			} else
				setErrLab("");
			String phone = jphone.getText();
			if (phone.equals(""))
			{
				setErrLab("<html><p color=red>Заполните все поля");
				return;
			}
			setErrLab("");
			Map<String, String> pars = new HashMap<String, String>();
			pars.put("action", "check_user");
			pars.put("login", log);
			pars.put("phone", Utilit.CleaPhone(phone));
			pars.put("email", mail);
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
			if (!(status == 0)) {
				setErrLab("<html><p color=red>"+jo.getString("msg"));
			    return;
			}else 
			{

				try
				{
					String pas = JOptionPane.showInputDialog(main, "Введите новый пароль, ваш старый пароль - "+jo.getString("password"),
							"Восстановление пароля", JOptionPane.PLAIN_MESSAGE);
					if (pas == null) {
						return;
					}
					pars.clear();
					pars.put("action", "edit_user");
					pars.put("password", pas);
					pars.put("id", jo.getString("id"));
					otvet = cl.send(pars);
					jo = new JSONObject(otvet);
				} catch (IOException e1)
				{
					// TODO Auto-generated catch block
					e1.printStackTrace();
				}

			}
		} 
		

	}
	public void setErrLab(String string)
	{
		erLab.setText(string);
	}
}
