package su.tuktuk.utilit;

import java.io.File;
import java.util.HashMap;

public class MStor extends HashMap<String, File>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public void add(String key, File med)
	{
		put(key, med);
	}

	public File get1(String key)
	{
		if (containsKey(key))
			return get(key);
		else
			return null;
	}

	public boolean has(String key)
	{
		if (containsKey(key))
			return true;
		else
			return false;
	}

	public static void main(String[] args)
	{
		MStor s = new MStor();
		s.add("1", null);
		System.out.print(s.get1("fdsa"));
	}
}
