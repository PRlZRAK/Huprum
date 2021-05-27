package huprum.main.loginer;

import java.awt.Dimension;
import java.awt.GridBagConstraints;
import java.awt.GridBagLayout;
import java.awt.Insets;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.io.IOException;
import java.net.MalformedURLException;
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
import huprum.main.loginer.listener.LoginActionListener;
import huprum.main.utils.Utilit;


public class Register extends JFrame implements ActionListener
{
	

	private JTextField jlogin;
	private JTextField jpass;
	private JTextField jmail;
	private JTextField jphone;
	
		  private static final long serialVersionUID = 1L;
			private GridBagConstraints c;
			private Huprum main;
			private JLabel er_mail;
			private JLabel er_login;
			private JLabel er_phone;
			private JLabel er_pass;
			private Client cl;
			private String log; 
			private String pass;
			private String mail;
			private String phone;
			
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
			
			
			public Register(Huprum main) 
			{
				this.main=main;
				cl=main.getCl();
				
			} 
			public class UsersActionListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e)
				{

					log     =jlogin.getText();
					if (log.equals(""))
					{
					 setErrLog("<html><p color=red>заполнить");					
					}else
					setErrLog("");
					
					pass     = jpass.getText();
					if (pass.equals(""))
					{
						setErrPas("<html><p color=red>заполнить");
					} else
						setErrPas("");

					mail     = jmail.getText();
					if (mail.equals(""))
					{
						setErrMail("<html><p color=red>заполнить");
					} else
						setErrMail("");

					phone     = jphone.getText();
					if (phone.equals(""))
					{
						setErrPhone("<html><p color=red>заполнить");
						return;
					} else
						setErrPhone("");
					
					phone=Utilit.CleaPhone(phone);
					int i=Utilit.CheckLogin(phone);
					if (i!=1)
						{
							setErrPhone("<html><p color=red>некоректный телефон");
						}else
							setErrPhone("");
					int b=Utilit.CheckLogin(mail);
					if (b!=0)
						{
							setErrMail("<html><p color=red>некоректная почта");
							return;
						}else
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
					JSONObject jo = new JSONObject(otvet);
					
						int status=jo.getInt("status");
						
						if (status==2) setErrLog  ("<html><p color=red>"+jo.getString("msg")); else setErrLog("");
						
						if (status==3) setErrMail ("<html><p color=red>"+jo.getString("msg")); else setErrMail("");
						
						if (status==4) { setErrPhone("<html><p color=red>"+jo.getString("msg"));return;}else setErrPhone("");
					
						
						setVisible(false);
				}
		     }
			@Override
			public void actionPerformed(ActionEvent e)
			{
				Toolkit kit = Toolkit.getDefaultToolkit();
				Dimension screenSize = kit.getScreenSize();
				setLocation((screenSize.width - 1024) / 2, (screenSize.height - 600) / 2);
				setSize(1024,600);
				JPanel panel = new JPanel();
				panel.setLayout(new GridBagLayout());
			    add(panel);

				c = new GridBagConstraints();
				c.weightx = 1;
				c.anchor = GridBagConstraints.CENTER;
				c.insets = new Insets(5,5,5,5);
				c.fill=GridBagConstraints.HORIZONTAL;
				
				c.gridx = 0;
				panel.add(new JLabel(""), c);
				c.gridx = 1;
				c.gridy = 0;
				
				panel.add(new JLabel("Логин:"), c);
				c.gridy++;
				jlogin = new JTextField(20);
				er_login = new JLabel("");
				panel.add(jlogin, c);
				c.gridx++;
				
				panel.add(er_login,c);
								
				c.gridx = 1;
				c.gridy++;
				
				panel.add(new JLabel("Почта:"), c);
				c.gridy++;
				jmail = new JTextField(20);
				er_mail = new JLabel("");
				panel.add(jmail, c);
				c.gridx++;
				panel.add(er_mail,c);
				
				
				c.gridx = 1;
				c.gridy++;
				
				panel.add(new JLabel("Телефон:"), c);
				c.gridy++;
				jphone = new JTextField(20);
				er_phone = new JLabel("");
				panel.add(jphone, c);
				c.gridx++;
				panel.add(er_phone,c);
				
				c.gridx = 1;
				c.gridy++;
				
				panel.add(new JLabel("Пароль:"), c);
				c.gridy++;
				jpass = new JPasswordField(20);
				er_pass = new JLabel("");
				panel.add(jpass, c);
				c.gridx++;
				panel.add(er_pass,c);
				
				c.gridy++;
				c.gridx = 1;
				
			    
				ActionListener usersListener = new UsersActionListener();
				JButton button = new JButton("Ввод");
				panel.add(button, c);
				button.addActionListener(usersListener);
				setVisible(true);			
			}		
				
}