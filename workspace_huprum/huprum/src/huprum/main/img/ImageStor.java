package huprum.main.img;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Vector;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import huprum.main.Huprum;
import huprum.main.connections.Client;
import huprum.main.utils.Utilit;

public class ImageStor extends Vector<ImageManipulation>
{
	/**
	 * 
	 */
	private static final long       serialVersionUID = 1L;
	private Client                  cl;
	private HashMap<String, String> pars;

	public ImageStor(String fileName, Huprum main)
	{
		super();
		pars = new HashMap<String, String>();
		cl = main.getCl();
		String tmp;
		try
		{
			tmp = new String(Files.readAllBytes(Paths.get(Utilit.CHAT_IMAGES)), StandardCharsets.UTF_8);
		} catch (IOException e)
		{
			return;
		}
		JSONArray arr = new JSONArray(tmp);
		for (int i = 0; i < arr.length(); i++)
		{
			JSONObject row = (JSONObject) arr.get(i);
			try
			{
				ImageManipulation im = new ImageManipulation(row.getString("image"));
				int               id = row.getInt("id");
				im.setId(id);
				add(im);
			} catch (JSONException | IOException e)
			{
				//e.printStackTrace();
			}
		}
	}

	public void save()
	{
		JSONArray arr = new JSONArray();
		for (int i = 0; i < this.size(); i++)
		{
			ImageManipulation row = this.get(i);
			JSONObject        jo  = new JSONObject();
			jo.put("id", row.getId());
			jo.put("image", row.getBase64());
			arr.put(jo);
		}
		File       f = new File(Utilit.CHAT_IMAGES);
		FileWriter filewr;
		try
		{
			filewr = new FileWriter(f);
			filewr.write(arr.toString());
			filewr.flush();
			filewr.close();
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public ImageManipulation getImage(String id)
	{
		ImageManipulation im = null;
		for (int i = 0; i < this.size(); i++)
		{
			im = this.get(i);
			if (id.equals(Integer.toString(im.getId())))
				return im;
			im = null;
		}
		if (im == null)
			try
			{
				pars.clear();
				pars.put("action", "get_img");
				pars.put("id", id);
				String     ot     = cl.send(pars);
				JSONObject jo1    = new JSONObject(ot);
				int        status = jo1.getInt("status");
				if (status == 0)
				{
					im = new ImageManipulation(jo1.getString("img"));
					im.setId(jo1.getInt("id"));
					add(im);
					return im;
				}
			} catch (JSONException | IOException e)
			{
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		return null;
	}
}
