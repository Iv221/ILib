package de.iv.iutils.sqlite;


import org.bukkit.plugin.Plugin;

import java.io.File;
import java.io.IOException;
import java.sql.*;

public class SQLite {


    private static Connection con;
    private static Statement s;
    private static File dbFile;

    public static void setup(Plugin plugin) {
        dbFile = new File(plugin.getDataFolder() + "/data", "database.db");
    }

    public static void connect() {
        con = null;
        if(!dbFile.exists()) {
            try {
                dbFile.createNewFile();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String url = "jdbc:sqlite:" + dbFile.getPath();
            try {
                con = DriverManager.getConnection(url);
                System.out.println("CONNECTED TO DATABASE");
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void disconnect() {
        if(con != null) {
            try {
                con.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }

    public static void update(String sql) {
        try{
            s = con.createStatement();
            s.execute(sql);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static ResultSet query(String sql) {
        try {
            return s.executeQuery(sql);
        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }

}
