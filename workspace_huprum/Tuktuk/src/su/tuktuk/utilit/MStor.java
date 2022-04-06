package su.tuktuk.utilit;

import java.io.File;

import org.json.JSONObject;

public class MStor extends JSONObject {

	public void add(String key, File med) {
		put(key, med);
	}

	public File get(String key1) {
		if (has(key1))
			return get(key1);
		else
			return null;

	}

	public static void main(String[] args) {
		MStor s = new MStor();
		System.out.print(s.get("fgd"));

	}

}
