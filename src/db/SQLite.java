package db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import org.sqlite.SQLiteConfig;

public class SQLite {
	public static Statement stmt;
	public static Connection con;

	static {
		try {
			Class.forName("org.sqlite.JDBC");
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		try {
			SQLiteConfig config = new SQLiteConfig();
			config.enableLoadExtension(true);

			con = DriverManager.getConnection("jdbc:sqlite:db\\schedule.db", config.toProperties());
			stmt = con.createStatement();

			ResultSet rs = getResultSet("select count(*) from sqlite_master where name = 'schedule' or name = 'note'");

			if (rs.getInt("count(*)") != 2) {
				stmt.execute("CREATE TABLE if not exists \"schedule\" (\r\n" + "	\"s_no\"	INTEGER NOT NULL,\r\n"
						+ "	\"s_name\"	TEXT,\r\n" + "	\"s_date\"	TEXT,\r\n" + "	\"s_time\"	TEXT,\r\n"
						+ "	\"s_color\"	TEXT,\r\n" + "	PRIMARY KEY(\"s_no\" AUTOINCREMENT)\r\n" + ");");
				stmt.execute("CREATE TABLE if not exists \"note\" (\r\n" + 
						"	\"n_no\"	INTEGER NOT NULL,\r\n" + 
						"	\"n_text\"	TEXT,\r\n" + 
						"	PRIMARY KEY(\"n_no\" AUTOINCREMENT)\r\n" + 
						");");
			}

		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static void insertSchedule(String text, String date, String time, String color) {
		try {
			PreparedStatement pst = con.prepareStatement("insert into schedule values(NULL, ?, ?, ?, ?)");
			pst.setString(1, text);
			pst.setString(2, date);
			pst.setString(3, time);
			pst.setString(4, color);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}
	
	public static void insertNote(String text) {
		try {
			PreparedStatement pst = con.prepareStatement("insert into note values(NULL, ?)");
			pst.setString(1, text);
			pst.executeUpdate();
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	public static ResultSet getResultSet(String sql) throws SQLException {
		return stmt.executeQuery(sql);
	}

	public static void main(String[] args) {

	}
}
