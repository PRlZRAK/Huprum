package huprum.main.loginer.listener;

import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;

import huprum.main.loginer.Register;

public class RegestrationListener implements MouseListener
{
	private Register register;

	@Override
	public void mouseClicked(MouseEvent e)
	{
		register = new Register(null);
	}

	@Override
	public void mousePressed(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseReleased(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseEntered(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public void mouseExited(MouseEvent e)
	{
		// TODO Auto-generated method stub
	}
}
