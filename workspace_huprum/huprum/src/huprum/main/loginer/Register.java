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


public class Register extends JFrame
{
	

	private JTextField jlogin;
	private JTextField jpass;
	private JTextField jmail;
	private JTextField jphone;
	
		  private static final long serialVersionUID = 1L;
			private GridBagConstraints c;
			private Client cl;
			
			public static void main(String[] args) {
				new Register("Регистрация");
			}
			public Register(String b) 
			{
				super(b);
				Toolkit kit = Toolkit.getDefaultToolkit();
				Dimension screenSize = kit.getScreenSize();
				setLocation((screenSize.width - 1024) / 2, (screenSize.height - 600) / 2);
				setSize(1024,600);
				JPanel panel = new JPanel();
				panel.setLayout(new GridBagLayout());
			    add(panel);

			    try
				{
					cl = new Client("http://130.61.155.146/huprum/server/index.php");
					//cl = new Client("http://localhost/huprum/server/index.php");
				} catch (MalformedURLException e1)
				{
					e1.printStackTrace();
				}
			    
			    
				
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
				panel.add(jlogin, c);
				c.gridx++;
				
				panel.add(new JLabel(""),c);
				
				
				c.gridx = 1;
				c.gridy++;
				
				panel.add(new JLabel("Почта:"), c);
				c.gridy++;
				jmail = new JTextField(20);
				panel.add(jmail, c);
				c.gridx++;
				
				
				
				c.gridx = 1;
				c.gridy++;
				
				panel.add(new JLabel("Телефон:"), c);
				c.gridy++;
				jphone = new JTextField(20);
				panel.add(jphone, c);
				c.gridx++;
			
				
				c.gridx = 1;
				c.gridy++;
				
				panel.add(new JLabel("Пароль:"), c);
				c.gridy++;
				jpass = new JPasswordField(20);
				panel.add(jpass, c);
				c.gridx++;
			
				
				c.gridy++;
				c.gridx = 1;
				
			    
				ActionListener usersListener = new UsersActionListener();
				JButton button = new JButton("Ввод");
				panel.add(button, c);
				button.addActionListener(usersListener);
				setVisible(true);
				
			}
			public class UsersActionListener implements ActionListener {

				@Override
				public void actionPerformed(ActionEvent e)
				{
					Map<String, String> pars = new HashMap<String, String>();
					String otvet = null;
					pars.put("action", "registr");
					pars.put("login",jlogin.getText());
					pars.put("phone",jphone.getText());
					pars.put("email",jmail. getText());
					pars.put("password",jpass.getText());
					try
					{
						otvet = cl.send(pars);
					} catch (IOException e1)
					{
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					JSONObject jo = new JSONObject(otvet);
					System.out.println("otvet = " + jo);
					
				}
		     }

			
}
