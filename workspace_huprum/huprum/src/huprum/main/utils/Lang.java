package huprum.main.utils;

public class Lang
{
	private static int l = 0;

	public Lang(String lang)
	{
		if ("en".equals(lang))
			l = 0;
		else if ("ru".equals(lang))
			l = 1;
	}

	public static String put(String string)
	{
		String[] ar = string.split("#");
		try
		{
			return ar[l];
		} catch (ArrayIndexOutOfBoundsException e)
		{
			return string;
		}
	}

	public static String put(int i, String string)
	{
		l = i;
		return put(string);
	}

	public static String getLang()
	{
		if (l == 0)
			return "en";
		return "ru";
	}

	public static void setRu()
	{
		l = 1;
	}

	public static void setEn()
	{
		l = 0;
	}
}