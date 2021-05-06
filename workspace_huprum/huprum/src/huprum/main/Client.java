package huprum.main;

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

public class Client
{
	private URL url;

	public Client(String url) throws MalformedURLException
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
			// System.out.println("yaa response " + response.toString());
			return response.toString();
		}
	}

	public String getStringUrl()
	{
		return url.toString();
	}

	public static void main(String[] args)
	{
		try
		{
			Client              cl   = new Client("http://localhost/huprum/");
			Map<String, String> pars = new HashMap<String, String>();
			pars.put("action", "login");
			pars.put("email", "yaa52@mail.ru");
			String otvet = cl.send(pars);
			System.out.println("otvet = " + otvet);
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}