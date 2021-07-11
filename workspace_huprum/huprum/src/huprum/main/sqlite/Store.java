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
import huprum.main.utils.Utilit;

public class Store
{
	public void close()
	{
		try
		{
			conn.close();
		} catch (SQLException e)
		{
			e.printStackTrace();
		}
	}

	private Connection              conn;
	private PreparedStatement       statmt_3;
	private PreparedStatement       statmt_4;
	private HashMap<String, String> pars;
	private Client                  cl;

	public Store(Huprum main)
	{
		super();
		pars = new HashMap<String, String>();
		cl = main.getCl();
		try
		{
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:" + Utilit.DBNAME);
			Statement statmt1 = conn.createStatement();
			statmt1.execute("CREATE TABLE if not exists ChatImg (" + "	id	INTEGER NOT NULL," + "	img	TEXT,"
					+ "	PRIMARY KEY(id)" + ");");
			Statement statmt2 = conn.createStatement();
			statmt2.execute("CREATE TABLE if not exists set1 (" + "	id	INTEGER NOT NULL," + "	key	TEXT,"
					+ "	param	TEXT," + "	PRIMARY KEY(id AUTOINCREMENT)" + ");");
			statmt_3 = conn.prepareStatement("INSERT OR IGNORE INTO ChatImg(id,img) VALUES (?,?)");
			statmt_4 = conn.prepareStatement("SELECT img FROM ChatImg WHERE id=?");
		} catch (ClassNotFoundException | SQLException e)
		{
			JOptionPane.showOptionDialog(main, "Ошибка", null, JOptionPane.YES_OPTION, JOptionPane.WARNING_MESSAGE,
					null, new String[]
					{ "Назад" }, "default");
			e.printStackTrace();
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
		if (rs.next())
		{
			im = new ImageManipulation(rs.getString("img"));
			im.setId(id);
			return im;
		} else
		{
			pars.clear();
			pars.put("action", "get_img");
			pars.put("id", Integer.toString(id));
			String     ot     = cl.send(pars);
			JSONObject jo1    = new JSONObject(ot);
			int        status = jo1.getInt("status");
			if (status == 0)
			{
				String base64 = jo1.getString("img");
				im = new ImageManipulation(base64);
				im.setId(id);
				im.setId(jo1.getInt("id"));
				putChatImg(id, base64);
				return im;
			}
		}
		return null;
	}

	public ImageManipulation getAvaImg(int id) throws SQLException, IOException
	{
		ImageManipulation im;
		pars.clear();
		pars.put("action", "get_user_img");
		pars.put("id", id + "");
		String     otvet1 = cl.send(pars);
		JSONObject jo1    = new JSONObject(otvet1);
		int        status = jo1.getInt("status");
		if (status == 0)
		{
			String avatar = jo1.getString("avatar");
			try
			{
				im = new ImageManipulation(avatar);
			} catch (ArrayIndexOutOfBoundsException e)
			{
				return null;
			}
			return im;
		}
		return null;
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
