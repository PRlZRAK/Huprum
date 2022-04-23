package su.tuktuk.utilit;

import java.io.File;
import java.util.HashMap;
import java.util.Map;

public class MStor extends HashMap<String, File>
{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private UrlComparer urlComparer = new UrlComparer();
	public void add(String key, File med)
	{
		put(key, med);
	}

	public File get1(String key)
	{
		if (containsKey(key))
			return get(key);
		else
			for (Map.Entry e : this.entrySet()) {
				if(urlComparer.urlsMatch((String) e.getKey(),key))return get(e.getKey());
				}
			return null;
	}

	public boolean has(String key)
	{
		if (containsKey(key))
			return true;
		else 
			for (Map.Entry e : this.entrySet()) {
				if(urlComparer.urlsMatch((String) e.getKey(),key))return true;
				}
			
			return false;
	}

	public static void main(String[] args)
	{
		MStor s = new MStor();
		s.add("1", null);
		System.out.print(s.get1("fdsa"));
	}
}
