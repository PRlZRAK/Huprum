package huprum.main.sqlite;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import org.json.JSONObject;

import huprum.main.Huprum;
import huprum.main.connections.Client;
import huprum.main.img.ImageManipulation;

/**
 * @author PRIZRAK Класс для манипулирования изображениями с помощью базы данных
 *         SQLite
 */
public class ImageStor
{
	
	public  Connection        conn;
	public  Statement         statmt;
	public  ResultSet         resSet;
	private Client                  cl;
	private HashMap<String, String> pars;

	/**
	 * Метод для соединения с БД
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLExceptio
	 */
	public void conn(Huprum main) throws ClassNotFoundException, SQLException
	{
		pars = new HashMap<String, String>();
		cl = main.getCl();
		conn = null;
		Class.forName("org.sqlite.JDBC");
		conn = DriverManager.getConnection("jdbc:sqlite:SQL.s3db");
		System.out.println("База Подключена!");
	}

	/**
	 * Метод создания таблицы для изображений
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLExceptio
	 */
	public void create() throws ClassNotFoundException, SQLException
	{
		statmt = conn.createStatement();
		statmt.execute("CREATE TABLE if not exists 'Images' ('id' INT, 'base64Img' TEXT, 'status' INT);");
		System.out.println("Таблица создана или уже существует.");
	}

	/**
	 * Метод для добавления изображения в БД
	 * 
	 * @param id        - id картинки
	 * @param status    - 1 (если это картинка чата), 2 (если это картина автарки)..
	 * @param base64Img - строка base64 добавляемой картинки
	 * @throws SQLException
	 */
	public void add(int id, String base64Img, int status) throws SQLException
	{
		PreparedStatement statmt = conn.prepareStatement("INSERT INTO Images (id, base64Img, status) VALUES (?, ?, ?)");
		statmt.setInt(1, id);
		statmt.setString(2, base64Img);
		statmt.setInt(3, status);
		statmt.execute();
		System.out.println("Таблица заполнена");
	}

	/**
	 * Метод для получения изображения из БД
	 * 
	 * @param id     - id картинки
	 * @param status - 1 (если это изображение чата), 2 (если это аватарка)..
	 * @return ImageManipulation
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 * @throws IOException 
	 */
	public ImageManipulation getImage(int id, int status) throws ClassNotFoundException, SQLException, IOException
	{
		ImageManipulation im;
		resSet = statmt.executeQuery("SELECT * FROM Images");
		while (resSet.next())
		{
			System.out.println("работает цикл");
			int status1 = resSet.getInt("status");
			int id1     = resSet.getInt("id");
			if(status1==status&&id==id1) {
				System.out.println("работает условие");
			im=new ImageManipulation(resSet.getString("base64Img"));
			im.setId(id1);
			return im;
			}					
		}		
			pars.clear();
			pars.put("action", "get_img");
			pars.put("id", Integer.toString(id));
			String     ot     = cl.send(pars);
			JSONObject jo1    = new JSONObject(ot);
			int        clStatus = jo1.getInt("status");
			if (clStatus == 0)
			{
				int newId = jo1.getInt("id");
				String newIm =jo1.getString("img");
				im = new ImageManipulation(newIm);
				im.setId(newId);
				add(newId, newIm , status);
				return im;
			}		
		return null;
	}

	/**
	 * Метод для завершения работы с БД
	 * 
	 * @throws ClassNotFoundException
	 * @throws SQLException
	 */
	public void close()
	{
		
		try
		{
			conn.close();
			statmt.close();
			resSet.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}		
		System.out.println("Соединения закрыты");
	}
}
