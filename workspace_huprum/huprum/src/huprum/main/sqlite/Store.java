package huprum.main.sqlite;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;

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
			
			String img = st.get(2,"AvaImg");
			String img1 = st.get(45,"ChatImg");
			
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
					"	img	TEXT" + 
					");");
		} catch (ClassNotFoundException | SQLException e)
		{
			// TODO  joptionpane
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
	

	private String get(int id, String table) throws SQLException
	{
		PreparedStatement statmt = conn.prepareStatement("SELECT img FROM "+ table +" WHERE id=?");
		statmt.setInt(1, id);
		ResultSet rs = statmt.executeQuery();
		if (rs.next())
			return rs.getString("img");
		return null;
	}
}
