/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package dao;

import java.sql.Connection;
import connection.koneksi;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import model.supplier;

/**
 *
 * @author hadit
 */
public class SupplierDAO {
    
    private final Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
   
    public SupplierDAO(){
        con = koneksi.getConnection();
    }
    
    public ArrayList<supplier> getAllSupplier(){
    ArrayList<supplier> ls = new ArrayList<>();
        try{
            String query = "SELECT * FROM supplier ORDER BY id_supplier";
            pst = con.prepareStatement(query);
            rs= pst.executeQuery();
            while(rs.next()){
                supplier s = new supplier();
                s.setId_sup(rs.getString("id_supplier"));
                s.setNama_sup(rs.getString("nama_supplier"));
                s.setAlamat(rs.getString("alamat"));
                s.setNotelp(rs.getString("no_hp"));
                s.setEmail(rs.getString("email"));
                s.setId_user(rs.getString("id_user"));
            
            }
        }
        catch(SQLException e){
            System.out.println("getAllSuplier() :" + e.getMessage());
        }
        return ls;
    }
    
    public supplier getRecordByIdSup(String no){
        supplier u = new supplier();
        String qusup="SELECT * FROM supplier WHERE id_suplier=?";
        try{
        pst= con.prepareStatement(qusup);
        pst.setString(1, no);
        rs = pst.executeQuery();
            if (rs.next()) {
                u.setId_sup(rs.getString("id_suplier"));
                u.setNama_sup(rs.getString("nama_supplier"));
                u.setAlamat(rs.getString("alamat"));
                u.setNotelp(rs.getString("no_hp"));
                u.setEmail(rs.getString("email"));
                u.setId_user(rs.getString("id_user"));
            }
        }
        catch(SQLException e){
            System.out.println("getRecordByIdSup() :" +e.getMessage());
        }
        return u;
    }
    
    public void insert(supplier a, String page) {
        try{
            String query = "";
            if(page.equals("edit")){
                query = "UPDATE supplier SET nama_supplier=?, alamat=?, no_hp=?, email=?, id_user=? WHERE id_supplier=?";
            }
            else if(page.equals("tambah")){
                query = "INSERT INTO supplier(nama_supplier, alamat, no_hp, email, id_user, id_supplier) VALUES (?,?,?,?,?,?)";
            }
            pst = con.prepareStatement(query);
            pst.setString(1, a.getNama_sup());
            pst.setString(2, a.getAlamat());
            pst.setString(3, a.getNotelp()); 
            pst.setString(4, a.getEmail());  
            pst.setString(5, a.getId_user());           
            pst.setString(6, a.getId_sup());
            pst.executeUpdate();
            System.out.println("insert or update success");
        }
        catch(SQLException e){
            System.out.println("insert() : " + e.getMessage());
        }
    }
    
    public void delete(String rm) {
        try{
            String qDelete = "DELETE FROM supplier WHERE id_supplier=?";
            pst = con.prepareStatement(qDelete);
            pst.setString(1, rm);
            pst.executeUpdate();
            System.out.println("delete success");
        }
        catch(SQLException e){
            System.out.println("delete() : " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        SupplierDAO ad = new SupplierDAO();
        System.out.println(ad.getAllSupplier());
        supplier a = new supplier();
//        a.setId_sup("SP001");
//        a.setNama_sup("Kimia Farma");           
//        a.setAlamat("Jl. Veteran No. 9");   
//        a.setNotelp("1500255");   
//        a.setEmail("webmaster@kimiafarma.co.id");  
//        a.setId_user("UA001");               
//        ad.insert(a, "tambah");
//        ad.insert(a, "edit");
//        ad.delete("SP001");
    }
    
}
