package huprum.main.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
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

	public String dayToString()
	{
		Calendar cal     = Calendar.getInstance();
		Calendar cal_now = Calendar.getInstance();
		cal.setTime(date);
		cal_now.setTime(new Date());
		if (cal.get(Calendar.DAY_OF_YEAR) == cal_now.get(Calendar.DAY_OF_YEAR))
			return "сегодня";
		else if (cal_now.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR) == 1)
			return "вчера";
		else if (cal_now.get(Calendar.DAY_OF_YEAR) - cal.get(Calendar.DAY_OF_YEAR) == 2)
			return "позавчера";
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("EEE, d MMM yyyy");
		return simpleDateFormat.format(date);
	}

	public int day()
	{
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		return cal.get(Calendar.DAY_OF_YEAR);
	}

	public static String now()
	{
		SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		return simpleDateFormat.format(new Date());
	}
}
