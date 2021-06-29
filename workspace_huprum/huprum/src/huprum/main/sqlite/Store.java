package huprum.main.sqlite;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.JOptionPane;

import org.json.JSONObject;

import huprum.main.Huprum;
import huprum.main.connections.Client;
import huprum.main.img.ImageManipulation;

public class Store
{
	/*public static void main(String[] args)
	{
	  	Store st = new Store();
		try
		{
			st.putAva(1, "image1");
			st.putAva(2, "image223");
			st.putChatImg(1, "image1");
			st.putChatImg(45, "image123");
			st.setParam("width", "123");
			System.out.println(st.getParam("width"));
			String img  = st.getAvaImg(2);
			String img1 = st.getChatImg(45);
			System.out.println(img);
			System.out.println(img1);
			st.setParam("Lang", "ru");
			System.out.println(st.getParam("Lang"));
			st.setParam("Lang", "en");
			System.out.println(st.getParam("Lang"));
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		st.close();
	}*/

	public void close()
	{
		try
		{
			conn.close();
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private Connection              conn;
	private PreparedStatement       statmt_0;
	private PreparedStatement       statmt_1;
	private PreparedStatement       statmt_2;
	private PreparedStatement       statmt_3;
	private PreparedStatement       statmt_4;
	private PreparedStatement       statmt_5;
	private HashMap<String, String> pars;
	private Client                  cl;
	//private Huprum                  main;

	public Store(Huprum main)
	{
		super();
		//this.main= main;
		pars = new HashMap<String, String>();
		cl = main.getCl();
		try
		{
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:SQL.s3db");
			Statement statmt = conn.createStatement();
			statmt.execute("CREATE TABLE if not exists AvaImg (" + "	id	INTEGER NOT NULL," + "	img	TEXT,"
					+ "	PRIMARY KEY(id)" + ");");
			Statement statmt1 = conn.createStatement();
			statmt1.execute("CREATE TABLE if not exists ChatImg (" + "	id	INTEGER NOT NULL," + "	img	TEXT,"
					+ "	PRIMARY KEY(id)" + ");");
			Statement statmt2 = conn.createStatement();
			statmt2.execute("CREATE TABLE if not exists set1 (" + "	id	INTEGER NOT NULL," + "	key	TEXT,"
					+ "	param	TEXT," + "	PRIMARY KEY(id AUTOINCREMENT)" + ");");
			statmt_0 = conn.prepareStatement("SELECT id FROM AvaImg WHERE id=?");
			statmt_1 = conn.prepareStatement("UPDATE AvaImg SET img=? WHERE id=?");
			statmt_2 = conn.prepareStatement("INSERT OR IGNORE INTO AvaImg(id,img) VALUES (?,?)");
			statmt_3 = conn.prepareStatement("INSERT OR IGNORE INTO ChatImg(id,img) VALUES (?,?)");
			statmt_4 = conn.prepareStatement("SELECT img FROM ChatImg WHERE id=?");
			statmt_5 = conn.prepareStatement("SELECT img FROM AvaImg WHERE id=?");
		} catch (ClassNotFoundException | SQLException e)
		{
			JOptionPane.showOptionDialog(main, "Ошибка", null, JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE,
					null, new String[]
					{ "Назад" }, "default");
			e.printStackTrace();
		}
	}

	public void putAva(int id, String img) throws SQLException
	{
		statmt_0.setInt(1, id);
		ResultSet rs = statmt_0.executeQuery();
		if (rs.next())
		{
			statmt_1.setString(1, img);
			statmt_1.setInt(2, id);
			statmt_1.executeUpdate();
		} else
		{
			statmt_2.setInt(1, id);
			statmt_2.setString(2, img);
			statmt_2.execute();
		}
	}

	public void putChatImg(int id, String img) throws SQLException
	{
		statmt_3.setInt(1, id);
		statmt_3.setString(2, img);
		statmt_3.execute();
	}

	public void setParam(String key, String param) throws SQLException
	{
		PreparedStatement statmt = conn.prepareStatement("SELECT id FROM set1 WHERE key=?");
		statmt.setString(1, key);
		ResultSet rs = statmt.executeQuery();
		if (rs.next())
		{
			int               id      = rs.getInt("id");
			PreparedStatement statmt1 = conn.prepareStatement("UPDATE 'set1' SET param=? WHERE id=?");
			statmt1.setString(1, param);
			statmt1.setInt(2, id);
			statmt1.executeUpdate();
		} else
		{
			PreparedStatement statmt2 = conn.prepareStatement("INSERT INTO 'set1'('key',param) VALUES (?,?)");
			statmt2.setString(1, key);
			statmt2.setString(2, param);
			statmt2.execute();
		}
	}

	public ImageManipulation getChatImg(int id) throws SQLException, IOException
	{
		statmt_4.setInt(1, id);
		ResultSet         rs = statmt_4.executeQuery();
		ImageManipulation im = null;
		if (rs.next()) {
			im = new ImageManipulation(rs.getString("img"));
			im.setId(id);
			return im;
			}
			else
			{
				pars.clear();
				pars.put("action", "get_img");
				pars.put("id", Integer.toString(id));
				String     ot     = cl.send(pars);
				JSONObject jo1    = new JSONObject(ot);
				int        status = jo1.getInt("status");
				if (status == 0)
				{
					String base64 =jo1.getString("img");
					im = new ImageManipulation(base64);
					im.setId(id);
					im.setId(jo1.getInt("id"));
					putChatImg(id, base64);
				return im;
				}
			}
			return im;
	}

	public ImageManipulation getAvaImg(int id) throws SQLException, IOException
	{
		statmt_5.setInt(1, id);
		ResultSet         rs = statmt_5.executeQuery();
		ImageManipulation im;
		if (rs.next())
		{
			im = new ImageManipulation(rs.getString("img"));
			im.setId(id);
			return im;
		}
		pars.clear();
		pars.put("action", "get_user_img");
		pars.put("id", id + "");	
			String     otvet1 = cl.send(pars);
			JSONObject jo1    = new JSONObject(otvet1);
			String     avatar = jo1.getString("avatar");
				im = new ImageManipulation(avatar);
				putAva(id, avatar);
				return im;
	}

	public String getParam(String key) throws SQLException
	{
		PreparedStatement statmt = conn.prepareStatement("SELECT param FROM set1 WHERE key=?");
		statmt.setString(1, key);
		ResultSet rs = statmt.executeQuery();
		if (rs.next())
			return rs.getString("param");
		return null;
	}
}
