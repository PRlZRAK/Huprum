package huprum.main.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

import javax.swing.JOptionPane;

public class Store
{
	public static void main(String[] args)
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
			String img = st.getAvaImg(2);
			String img1 = st.getChatImg(45);
			System.out.println(img);
			System.out.println(img1);
		} catch (SQLException e)
		{
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		st.close();
	}

	private void close()
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

	private HashMap<String, String> pars;
	private Connection              conn;

	public Store()
	{
		pars = new HashMap<String, String>();
		//cl = main.getCl();
		
		try
		{
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:SQL.s3db");
			Statement statmt = conn.createStatement();
			statmt.execute("CREATE TABLE if not exists AvaImg (" + 
					"	id	INTEGER NOT NULL," + 
					"	img	TEXT," + 
					"	PRIMARY KEY(id)" + 
					");");
			
			Statement statmt1 = conn.createStatement();
			statmt1.execute("CREATE TABLE if not exists ChatImg (" + 
					"	id	INTEGER NOT NULL," +  
					"	img	TEXT," + 
					"	PRIMARY KEY(id)" + 
					");");
			
			Statement statmt2 = conn.createStatement();
			statmt2.execute("CREATE TABLE if not exists set1 (" + 
					"	id	INTEGER NOT NULL," + 
					"	key	TEXT," + 
					"	param	TEXT," + 
					"	PRIMARY KEY(id AUTOINCREMENT)" + 
					");");		
		} catch (ClassNotFoundException | SQLException e)
		{
			JOptionPane.showOptionDialog(null, "Ошибка", null, JOptionPane.YES_OPTION,
					JOptionPane.WARNING_MESSAGE, null, new String[]
					{ "Назад" }, "default");
			e.printStackTrace();
		}
	}

	private void putAva(int id, String img) throws SQLException
	{
		PreparedStatement statmt = conn.prepareStatement("SELECT * FROM AvaImg WHERE id=?");
		statmt.setInt(1, id);
		ResultSet rs = statmt.executeQuery();
		if (rs.next())
		{
			PreparedStatement statmt1 = conn.prepareStatement("UPDATE AvaImg SET img=? WHERE id=?");
			statmt1.setString(1, img);
			statmt1.setInt(2, id);
			statmt1.executeUpdate();
		} else
		{
			PreparedStatement statmt2 = conn.prepareStatement("INSERT INTO AvaImg(id,img) VALUES (?,?)");
			statmt2.setInt(1, id);
			statmt2.setString(2, img);
			statmt2.execute();
		}
	}
	private void putChatImg(int id, String img) throws SQLException
	{
			PreparedStatement statmt = conn.prepareStatement("INSERT INTO ChatImg(id,img) VALUES (?,?)");
			statmt.setInt(1, id);
			statmt.setString(2, img);
			statmt.execute();
	}
	private void setParam(String key, String param) throws SQLException
	{
		PreparedStatement statmt = conn.prepareStatement("SELECT id FROM set1 WHERE key=?");
		statmt.setString(1, key);	
		 ResultSet rs = statmt.executeQuery();
		 if (rs.next())
		 {
			int id = rs.getInt("id");
			 PreparedStatement statmt1 = conn.prepareStatement("UPDATE 'set1' SET param=? WHERE id=?");
			 statmt1.setString(1, param);
			 statmt1.setInt(2, id);
			 statmt1.executeUpdate();
		 }
		 else
		 {
			 PreparedStatement statmt2 = conn.prepareStatement("INSERT INTO 'set1'('key',param) VALUES (?,?)");
			
			 statmt2.setString(1, key);
			 statmt2.setString(2, param);
			 statmt2.execute(); 
		 }
		
	}
	
	

	private String getChatImg(int id) throws SQLException
	{
		PreparedStatement statmt = conn.prepareStatement("SELECT img FROM ChatImg WHERE id=?");
		statmt.setInt(1, id);
		ResultSet rs = statmt.executeQuery();
		if (rs.next())
			return rs.getString("img");
		return null;
	}
	private String getAvaImg(int id) throws SQLException
	{
		PreparedStatement statmt = conn.prepareStatement("SELECT img FROM AvaImg WHERE id=?");
		statmt.setInt(1, id);
		ResultSet rs = statmt.executeQuery();
		if (rs.next())
			return rs.getString("img");
		return null;
	}
	private String getParam(String key) throws SQLException
	{
		PreparedStatement statmt = conn.prepareStatement("SELECT param FROM set1 WHERE key=?");
		statmt.setString(1, key);	
		 ResultSet rs = statmt.executeQuery();
		 if (rs.next())
				return rs.getString("param");
		 return null;
	}
}
