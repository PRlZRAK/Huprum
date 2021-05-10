package huprum.main.chat;

import java.awt.Button;
import java.awt.Color;

import javax.swing.JButton;

public class UserButtomClass extends JButton implements UserButtom
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 0;
	private boolean           isSel;

	@Override
	public void setSelect(boolean select)
	{
		Button b = new Button();
		if (select == true)
		{
			b.setBackground(new Color(255, 204, 0));
			isSel = true;
		}
	}

	@Override
	public boolean isSelected()
	{
		if (isSel = true)
			return true;
		else
			return false;
	}

	@Override
	public void setId(Integer id)
	{
		// TODO Auto-generated method stub
	}

	@Override
	public Integer getId()
	{
		// TODO Auto-generated method stub
		return null;
	}
}
