package huprum.main.loginer;

import huprum.main.Huprum;

import java.awt.BorderLayout;
import huprum.main.loginer.panels.Panel;

public class Loginer
{
	private int    id;
	private String login;
	private String phone;
	private String email;
	private String password;
	public Panel p;

	public Loginer(Huprum main)
	{


		p=new Panel(main);
		main.add(p,BorderLayout.CENTER);
		
		       
		main.setSize(500, 200);     
		        

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
}
