package huprum.main.connections;

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

import huprum.main.utils.Utilit;

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
			//System.out.println("response = " + response.toString());
			return getJson(response.toString());
		}
	}

	private String getJson(String s)
	{
		int b = s.indexOf("<#");
		if (b > 0)
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
		try
		{
			Client cl = new Client(Utilit.SERVER_URL);
			Map<String, String> pars = new HashMap<String, String>();
			String              otvet;
			JSONObject          jo;
			/*
			pars.put("action", "login");
			pars.put("email", "yaa52@mail.ru");
			otvet = cl.send(pars);
			System.out.println("otvet = " + otvet);
			pars.clear();
			pars.put("action", "get_users");
			
			otvet = cl.send(pars);
			jo = new JSONObject(otvet);
			System.out.println("otvet = " + jo);
			
			// логин
			pars.clear();
			pars.put("action", "registr");
			pars.put("login", "user1");
			pars.put("phone", "898877035351");
			pars.put("email", "yaa521@mail.ru");
			pars.put("password", "qwerty");
			otvet = cl.send(pars);
			jo = new JSONObject(otvet);
			System.out.println("otvet = " + jo);
			//
			pars.clear();
			pars.put("action", "registr");
			pars.put("login", "user");
			pars.put("phone", "89887703535");
			pars.put("email", "yaa52@mail.ru");
			pars.put("password", "qwerty");
			otvet = cl.send(pars);
			jo = new JSONObject(otvet);
			System.out.println("otvet = " + jo);
			pars.clear();
			pars.put("action", "get_users");
			otvet = cl.send(pars);
			System.out.println("otvet = " + otvet);
			jo = new JSONObject(otvet);
			System.out.println("otvet = " + jo);
			// тест отправки сообщения другому юзеру
			
			pars.put("action", "put_msg");
			pars.put("id_from", "3");
			pars.put("id_to", "1");
			pars.put("msg", "Проверка put_msg");
			otvet = cl.send(pars);
			System.out.println("otvet = " + otvet);
			jo = new JSONObject(otvet);
			System.out.println("otvet = " + jo);
			
			// тест получения юзеров для чата
			pars.put("action", "get_my_users");
			pars.put("id", "1");
			pars.put("ksum", "100");
			otvet = cl.send(pars);
			jo = new JSONObject(otvet);
			System.out.println("otvet = " + jo);
			
			// тест удаления юзеров из чата
			pars.put("action", "del_user");
			pars.put("myid", "3");
			pars.put("userid", "5");
			otvet = cl.send(pars);
			jo = new JSONObject(otvet);
			System.out.println("otvet = " + jo);
			
			// тест отправки сообщения другому юзеру
			pars.clear();
			pars.put("action", "put_msg");
			pars.put("id_from", "3");
			pars.put("id_to", "1");
			pars.put("msg", "Проверка put_msg");
			pars.put("dtime","2020-12-12 12:12:12");
			otvet = cl.send(pars);
			System.out.println("otvet = " + otvet);
			//
			pars.clear();
			pars.put("action", "get_chat");
			pars.put("myid", "3");
			pars.put("id", "6");
			pars.put("last_chat_id", "4");
			otvet = cl.send(pars);
			jo = new JSONObject(otvet);
			System.out.println("jo = " + jo);
			//
			pars.clear();
			pars.put("action", "get_img");
			pars.put("id", "62");
			otvet = cl.send(pars);
			jo = new JSONObject(otvet);
			System.out.println("otvet = " + jo);
			*/
			//
			pars.clear();
			pars.put("action", "check_user");
			pars.put("login", "prizrak");
			pars.put("phone", "89886613522");
			pars.put("email", "alesharodygin@gmail.com");
			otvet = cl.send(pars);
			jo = new JSONObject(otvet);
			System.out.println("otvet = " + jo);
			//
			pars.clear();
			pars.put("action", "edit_user");
			pars.put("password", "1234");
			pars.put("id", "6");
			otvet = cl.send(pars);
			jo = new JSONObject(otvet);
			System.out.println("otvet = " + jo);
					
		} catch (IOException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
