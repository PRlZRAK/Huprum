package huprum.main.chat;

import java.awt.Color;

import javax.swing.JButton;

public class UserButtomClass extends JButton implements UserButtom
{
	@Override
	public String toString()
	{
		return "UserButtomClass [isSel=" + isSel + ", recId=" + recId + "]";
	}

	/**
	 * 
	 */
	private static final long serialVersionUID = 0;
	private boolean           isSel;
	private Integer           recId;
	private Color             defaultColor;
	private Color             defaultColorText;
	private Color             selectBackgColor;
	private Color             selectTextColor;

	public UserButtomClass(String b)
	{
		super(b);
		defaultColor = getBackground();
		defaultColorText = getForeground();
		selectBackgColor = Color.gray;
		selectTextColor = Color.white;
	}

	public UserButtomClass(String b, Color backGround, Color textColor)
	{
		super(b);
		defaultColor = getBackground();
		defaultColorText = getForeground();
		selectBackgColor = backGround;
		selectTextColor = textColor;
	}

	@Override
	public void setSelect(boolean select)
	{
		isSel = select;
		if (select)
		{
			this.setBackground(selectBackgColor);
			setForeground(selectTextColor);
		} else
		{
			this.setBackground(defaultColor);
			setForeground(defaultColorText);
		}
	}

	@Override
	public boolean isSelected()
	{
		return isSel;
	}

	@Override
	public void setId(Integer id)
	{
		recId = id;
	}

	@Override
	public Integer getId()
	{
		return recId;
	}
}
