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
import model.resep;

/**
 *
 * @author atha
 */
public class ResepDAO {
    private final Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    
    public ResepDAO(){
        con = koneksi.getConnection();
    }
    
    public ArrayList<resep> getAllResep(){
        ArrayList<resep> lk = new ArrayList<>();
        try{
            String qSelect = "SELECT * FROM resep ORDER BY id_resep";
            pst = con.prepareStatement(qSelect);
            rs = pst.executeQuery();
            while(rs.next()){
                resep r = new resep();
                r.setId_resep(rs.getString("id_resep"));
                r.setId_obat(rs.getString("id_obat"));
                r.setJumlah(rs.getInt("jumlah"));
                r.setKeterangan(rs.getString("keterangan"));
                r.setWaktu(rs.getString("waktu"));
                r.setId_user(rs.getString("id_user"));
                lk.add(r);
            }
        }
        catch(SQLException e){
            System.out.println("getAllPasien() : " + e.getMessage());
        }
        return lk;
    }
    
    public resep getRecordById(String id) {
        resep r = new resep();
        String query = "SELECT * FROM resep WHERE id_resep = ?";
        try{
            pst = con.prepareStatement(query);
            pst.setString(1, id);
            rs = pst.executeQuery();
            if(rs.next()){
                r.setId_resep(rs.getString("id_resep"));
                r.setId_obat(rs.getString("id_obat"));
                r.setJumlah(rs.getInt("jumlah"));
                r.setKeterangan(rs.getString("keterangan"));
                r.setWaktu(rs.getString("waktu"));
                r.setId_user(rs.getString("id_user"));
            }
        }
        catch(SQLException e){
            System.out.println("getRecordByNoRM() : " + e.getMessage());
        }
        return r;
    }
    
    public void insert(resep r, String page) {
        try{
            String query = "";
            String message="";
            if(page.equals("edit")){
                query = "UPDATE resep SET id_obat=?, jumlah=?, keterangan=?, waktu=?, id_user=? WHERE id_resep=?";
                message = "update";
            }
            else if(page.equals("tambah")){
                query = "INSERT INTO resep(id_obat, jumlah, keterangan, waktu, id_user, id_resep) VALUES (?,?,?,?,?,?)";
                message = "insert";
            }
            pst = con.prepareStatement(query);
            pst.setString(1, r.getId_obat());
            pst.setInt(2, r.getJumlah());
            pst.setString(3, r.getKeterangan());
            pst.setString(4, r.getWaktu());
            pst.setString(5, r.getId_user());
            pst.setString(6, r.getId_resep());
            pst.executeUpdate();
            System.out.println(message + " success");
        }
        catch(SQLException e){
            System.out.println("insert() : " + e.getMessage());
        }
    }
    
    public void delete(String id) {
        try{
            String qDelete = "DELETE FROM resep WHERE id_resep=?";
            pst = con.prepareStatement(qDelete);
            pst.setString(1, id);
            pst.executeUpdate();
            System.out.println("delete success");
        }
        catch(SQLException e){
            System.out.println("delete() : " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        ResepDAO ad = new ResepDAO();
        System.out.println(ad.getAllResep());
        resep a = new resep();
//        a.setId_resep("RO001");
//        a.setId_obat("OU001");
//        a.setJumlah(12);
//        a.setKeterangan("Obat Umum"); 
//        a.setWaktu("2021-02-21 11:41:48");
//        a.setId_user("UP001");
//        ad.insert(a, "tambah");
//        ad.insert(a, "edit");
//        ad.delete("P001");
    }
}
