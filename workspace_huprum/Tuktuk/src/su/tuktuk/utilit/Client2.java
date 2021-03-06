package su.tuktuk.utilit;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.util.HashMap;
import java.util.Map;

import org.json.JSONObject;

public class Client2
{
	private URL url;

	public Client2(String url) throws MalformedURLException
	{
		this.url = new URL(url);
	}

	public String send(Map<String, String> params) throws IOException
	{
		HttpURLConnection con = (HttpURLConnection) url.openConnection();
		con.setRequestMethod("POST");
		con.setRequestProperty("Content-Type", "application/json; utf-8");
		con.setDoOutput(true);
		con.setReadTimeout(10 * 1000);
		JSONObject jo = new JSONObject(params);
		try (OutputStream os = con.getOutputStream())
		{
			byte[] input = jo.toString().getBytes("utf-8");
			os.write(input);// , 0, input.length);
			os.flush();
			os.close();
			int responseCode = con.getResponseCode();
			if (responseCode != 200)
				System.out.println("POST Response Code :: " + responseCode);
		} catch (ConnectException e)
		{
			// return "{\"status\":1,\"msg\":\"Нет связи с сервером\"}";
			return null;
		}
		try (BufferedReader br = new BufferedReader(new InputStreamReader(con.getInputStream(), "utf-8")))
		{
			StringBuilder response     = new StringBuilder();
			String        responseLine = null;
			while ((responseLine = br.readLine()) != null)
			{
				response.append(responseLine.trim());
			}
			System.out.println("response = " + response.toString());
			return getJson(response.toString());
		}
	}

	private String getJson(String s)
	{
		int b = s.indexOf("<#");
		if (b > -1)
		{
			int e = s.indexOf("#>");
			if (e > 0 && e > b)
				return s.substring(b + 2, e);
		}
		return s;
	}

	public String getStringUrl()
	{
		return url.toString();
	}

	public static void main(String[] args)
	{
		String HUPRUM_URL = "http://tuktuk.su/huprum/tuktuk/index.php";
		try
		{
			Client2             cl   = new Client2(HUPRUM_URL);
			Map<String, String> pars = new HashMap<String, String>();
			String              otvet;
			JSONObject          jo;
			pars.put("action", "get users");
			otvet = cl.send(pars);
			jo = new JSONObject(otvet);
			System.out.println("otvet = " + jo);
			/*
			pars.clear();
			
			pars.put("action", "sum");
			pars.put("x", "7а8");
			pars.put("y", "9");
			otvet = cl.send(pars);
			jo = new JSONObject(otvet);
			System.out.println("otvet = " + jo);
			pars.clear();
			
			pars.put("action", "step");
			pars.put("x", "2");
			pars.put("y", "3");
			otvet = cl.send(pars);
			jo = new JSONObject(otvet);
			System.out.println("otvet = " + jo);
			pars.clear();*/
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
