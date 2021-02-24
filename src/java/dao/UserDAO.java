/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import connection.koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.user;

/**
 *
 * @author hadit
 */
public class UserDAO {
    
    private final Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    
    public UserDAO(){
        con = koneksi.getConnection();
    }
    
    public ArrayList<user> getAllUser(){
        ArrayList<user> lk = new ArrayList<>();
        try{
            String qSelect = "SELECT * FROM user ORDER BY id_user";
            pst = con.prepareStatement(qSelect);
            rs = pst.executeQuery();
            while(rs.next()){
                user p = new user();
                p.setId_user(rs.getString("id_user"));
                p.setNama(rs.getString("nama"));
                p.setLevel(rs.getString("level"));
                if(rs.getString("role") != null){                    
                    if((rs.getString("role")).equals("admin"))
                        p.setLevel("admin");
                    else if ((rs.getString("role")).equals("dokter"))
                        p.setLevel("dokter");
                    else if ((rs.getString("role")).equals("karyawan"))
                        p.setLevel("karyawan");
                    else p.setLevel("pasien");
                } else p.setLevel("");

                if(rs.getString("password") != null){                      
                    p.setPassword(rs.getString("password"));
                } else p.setPassword("");
                if(rs.getString("username") != null){          
                    p.setUsername(rs.getString("username"));
                } else p.setUsername("");
                lk.add(p);
            }
        }
        catch(SQLException e){
            System.out.println("getAllUser() : " + e.getMessage());
        }
        return lk;
    }
    
    public user getRecordByIdUs(String nik) {
        user k = new user();
        String query = "SELECT * FROM user WHERE id_user = ?";
        try{
            pst = con.prepareStatement(query);
            pst.setString(1, nik);
            rs = pst.executeQuery();
            if(rs.next()){
                k.setId_user(rs.getString("id_user"));
                k.setNama(rs.getString("nama"));    
                k.setLevel(rs.getString("role"));  
                k.setUsername(rs.getString("username"));        
                k.setPassword(rs.getString("password"));
                
            }
        }
        catch(SQLException e){
            System.out.println("getRecordByIdUs() : " + e.getMessage());
        }
        return k;
    }
    
    public void insert(user a, String page) {
        try{
            String query = "";
            if(page.equals("edit")){
                query = "UPDATE user SET username=?, password=?, nama=?, role=? WHERE id_user=?";
            }
            else if(page.equals("tambah")){
                query = "INSERT INTO user(username, password, nama, role, id_user) VALUES (?,?,?,?,?)";
            }
            pst = con.prepareStatement(query);
            pst.setString(1, a.getUsername());
            pst.setString(2, a.getPassword());
            pst.setString(3, a.getNama());
            if(a.getLevel().equals("")) pst.setString(4, null);
                else pst.setString(4, a.getLevel());           
            pst.setString(5, a.getId_user());
            pst.executeUpdate();
            System.out.println("insert or update success");
        }
        catch(SQLException e){
            System.out.println("insert() : " + e.getMessage());
        }
    }
    
    public void delete(int rm) {
        try{
            String qDelete = "DELETE FROM user WHERE id_user=?";
            pst = con.prepareStatement(qDelete);
            pst.setInt(1, rm);
            pst.executeUpdate();
            System.out.println("delete success");
        }
        catch(SQLException e){
            System.out.println("delete() : " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        UserDAO ad = new UserDAO();
        System.out.println(ad.getAllUser());
        user a = new user();
//        a.setId_user("UK002");
//        a.setNama("Haditya Fajri Bahri Ramadhan");           
//        a.setUsername("karyawan");   
//        a.setPassword("karyawan1");        
//        a.setLevel("karyawan");
//        ad.insert(a, "tambah");
//        ad.insert(a, "edit");
//        ad.delete(1);
    }
}
