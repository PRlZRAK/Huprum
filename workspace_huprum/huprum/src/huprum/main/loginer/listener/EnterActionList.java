package huprum.main.loginer.listener;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import huprum.main.Huprum;
import huprum.main.Utilit;

public class EnterActionList implements ActionListener
{	
	private String Pas;
	private	String L;
	private	String P;
	private	String M;
	private String LMP;
	private Huprum main;

	public EnterActionList(Huprum main)
		{
			this.main= main;
		}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		Pas = main.loginer.p.GetPas();
		LMP = main.loginer.p.GetLMP();
		int znach = Utilit.CheckLogin(LMP);
	    if (znach==0)
	    {
	    	M=LMP;
	    }
	    else if (znach==1)
	    {	    	
	    	P=Utilit.CleaPhone(LMP);
	    }
	    else 
	    {
	    	L=LMP;
	    }
	    
	}
	
}
