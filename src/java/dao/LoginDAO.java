/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import connection.koneksi;
import model.login;

/**
 *
 * @author asus
 */
public class LoginDAO {
    private final Connection conn;
    private PreparedStatement pStmt;
    private ResultSet rS;
    
    public LoginDAO() {
        conn = koneksi.getConnection();
    }
    
    public ArrayList<login> aut() {
        ArrayList<login> listLogin = new ArrayList<>();
        login log = new login();
        String query = "SELECT * FROM user WHERE username = ? AND password = ?";
        try {
            pStmt = conn.prepareStatement(query);
            pStmt.setString(1, log.getUsername());
            pStmt.setString(2, log.getPassword());
            rS = pStmt.executeQuery();
            if (rS.next()) {
                System.out.println("User authenticated successfully");
            }
            else {
                System.out.println("Invalid username or password!");
            }
            listLogin.add(log);
        }
        catch (SQLException e) {
            System.out.println("login error");
        }
        return listLogin;
    }
    
    public static void main(String[] args) {
        LoginDAO ad = new LoginDAO();
        login log = new login();        
        log.setUsername("admin");
        log.setPassword("admin123");
        System.out.println(ad.aut());
    }
}