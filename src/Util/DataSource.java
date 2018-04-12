/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package Util;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.logging.Logger;
/**
 *
 * @author AmineAmri
 */
public class DataSource {
    private static DataSource data;
	private Connection con;
	public String user = "root";
	public String password = "";
	public String url = "jdbc:mysql://127.0.0.1:3306/bonplan";
	private DataSource() {

		try {
                    if (System.getProperty("os.name").equals("Linux") )
                        Class.forName("org.mariadb.jdbc.Driver");
                    else
                        Class.forName("com.mysql.jdbc.Driver");
                    
                   con = DriverManager.getConnection(url, user, password);
                   System.out.println("Connexion établie!");
		}
             
		catch (SQLException e)  {
			Logger.getLogger("DataSource: "+ e.getMessage());
			
		}
                
                catch (ClassNotFoundException e) {
                    Logger.getLogger("DataSource: " + e.getLocalizedMessage());
                }
	}

	public static DataSource getInstance() {
		
		if (data == null)
			data = new DataSource();
		return data;

	}

	public Connection getCon() {
		return con;
	}
}