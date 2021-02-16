/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package connection;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import java.sql.Connection;
import java.sql.SQLException;

/**
 *
 * @author atha
 */
public class koneksi {
    static Connection con;
    
    public static Connection getConnection(){
        if(con == null){
            MysqlDataSource data = new MysqlDataSource();
            data.setDatabaseName("klinik");
            data.setUser("root");
            data.setPassword("");
            try{
                con = (Connection) data.getConnection();
            }
            catch(SQLException e){
                System.out.println("koneksi Gagal : " + e.getMessage());
            }
        }
        return con;
    }
    
    public static void main(String[] args) {
        getConnection();
    }
}
