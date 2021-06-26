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
			st.put("name","алексей");
			st.put("fio","**************");
			String name=st.get("name");
			System.out.println(name);
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
	private Connection conn;

	public Store()
	{

		pars = new HashMap<String, String>();
		//cl = main.getCl();
		
		try
		{
			Class.forName("org.sqlite.JDBC");
			conn = DriverManager.getConnection("jdbc:sqlite:SQL.s3db");
			Statement statmt = conn.createStatement();
			//statmt.execute("CREATE TABLE if not exists 'Images' ('id' INT, 'base64Img' TEXT, 'status' INT);");
			statmt.execute("CREATE TABLE if not exists \"set1\" (\n" + 
					"	\"id\"	INTEGER NOT NULL,\n" + 
					"	\"key\"	TEXT,\n" + 
					"	\"value\"	TEXT,\n" + 
					"	PRIMARY KEY(\"id\" AUTOINCREMENT)\n" + 
					");");
		} catch (ClassNotFoundException | SQLException e)
		{
			// TODO  joptionpane
			e.printStackTrace();
		}
		
		
	}
	private void put(String key, String value) throws SQLException
	{
		PreparedStatement statmt = conn.prepareStatement("SELECT id FROM \"set1\" WHERE key=?");
		statmt.setString(1, key);	
		 ResultSet rs = statmt.executeQuery();
		 if (rs.next())
		 {
			int id = rs.getInt("id");
			 PreparedStatement statmt1 = conn.prepareStatement("UPDATE 'set1' SET value=? WHERE id=?");
			 statmt1.setString(1, value);
			 statmt1.setInt(2, id);
			 statmt1.executeUpdate();
		 }
		 else
		 {
			 PreparedStatement statmt2 = conn.prepareStatement("INSERT INTO 'set1'('key',value) VALUES (?,?)");
			
			 statmt2.setString(1, key);
			 statmt2.setString(2, value);
			 statmt2.execute(); 
		 }
		
	}

	private String get(String key) throws SQLException
	{
		PreparedStatement statmt = conn.prepareStatement("SELECT value FROM \"set1\" WHERE key=?");
		statmt.setString(1, key);	
		 ResultSet rs = statmt.executeQuery();
		 if (rs.next())
				return rs.getString("value");
		 return null;
	}
}
