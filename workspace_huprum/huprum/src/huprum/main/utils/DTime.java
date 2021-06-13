package huprum.main.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class DTime
{
	private Date date;

	public DTime(String string) throws ParseException
	{
		 SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		  date = simpleDateFormat.parse(string);
	}
	public String time()
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("HH:mm");
		return simpleDateFormat.format(date);
	}
	public static void main(String[] args)
	{
		try
		{
			DTime dt = new DTime("2015-12-03 17:00:08");
			System.out.println(dt.time());
		} catch (ParseException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

	
}
