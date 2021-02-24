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
import model.layanan;

/**
 *
 * @author atha
 */
public class LayananDAO {
    private final Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    
    public LayananDAO(){
        con = koneksi.getConnection();
    }
    
    public ArrayList<layanan> getAllLayanan(){
        ArrayList<layanan> lk = new ArrayList<>();
        try{
            String qSelect = "SELECT * FROM layanan ORDER BY id_layanan";
            pst = con.prepareStatement(qSelect);
            rs = pst.executeQuery();
            while(rs.next()){
                layanan l = new layanan();
                l.setId_layanan(rs.getString("id_layanan"));
                l.setDes_layanan(rs.getString("des_layanan"));
                l.setBiaya_layanan(rs.getInt("biaya_layanan"));
                l.setKeterangan(rs.getString("keterangan"));
                lk.add(l);
            }
        }
        catch(SQLException e){
            System.out.println("getAllAnggota() : " + e.getMessage());
        }
        return lk;
    }
    
    public layanan getRecordById(String nik) {
        layanan l = new layanan();
        String query = "SELECT * FROM layanan WHERE id_layanan = ?";
        try{
            pst = con.prepareStatement(query);
            pst.setString(1, nik);
            rs = pst.executeQuery();
            if(rs.next()){
                l.setId_layanan(rs.getString("id_layanan"));
                l.setDes_layanan(rs.getString("des_layanan"));  
                l.setBiaya_layanan(rs.getInt("biaya_layanan"));  
                l.setKeterangan(rs.getString("keterangan"));
            }
        }
        catch(SQLException e){
            System.out.println("getRecordById() : " + e.getMessage());
        }
        return l;
    }
    
    public void insert(layanan l, String page) {
        try{
            String query = "";
            String message="";
            if(page.equals("edit")){
                query = "UPDATE layanan SET des_layanan=?, biaya_layanan=?, keterangan=? WHERE id_layanan=?";
                message="update";
            }
            else if(page.equals("tambah")){
                query = "INSERT INTO layanan(des_layanan, biaya_layanan, keterangan, id_layanan) VALUES (?,?,?,?)";
                message="insert";
            }
            pst = con.prepareStatement(query);
            pst.setString(1, l.getDes_layanan());
            pst.setInt(2, l.getBiaya_layanan());
            pst.setString(3, l.getKeterangan());
            pst.setString(4, l.getId_layanan());
            pst.executeUpdate();
            System.out.println(message + " success");
        }
        catch(SQLException e){
            System.out.println("insert() : " + e.getMessage());
        }
    }
    
    public void delete(String id) {
        try{
            String qDelete = "DELETE FROM layanan WHERE id_layanan=?";
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
        LayananDAO ad = new LayananDAO();
        System.out.println(ad.getAllLayanan());
        layanan l = new layanan();
        l.setId_layanan("LP001");
        l.setDes_layanan("Poliklinik Umum");   
        l.setBiaya_layanan(50000);        
        l.setKeterangan("dr. Ishak Husein");
        ad.insert(l, "tambah");
//        ad.insert(l, "edit");
//        ad.delete("LP001");
    }
}
