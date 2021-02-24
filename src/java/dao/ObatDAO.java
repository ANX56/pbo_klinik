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
import model.obat;

/**
 *
 * @author atha
 */
public class ObatDAO {
    private final Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    
    public ObatDAO(){
        con = koneksi.getConnection();
    }
    
    public ArrayList<obat> getAllObat(){
        ArrayList<obat> lk = new ArrayList<>();
        try{
            String qSelect = "SELECT * FROM obat ORDER BY id_obat";
            pst = con.prepareStatement(qSelect);
            rs = pst.executeQuery();
            while(rs.next()){
                obat o = new obat();
                o.setId_obat(rs.getString("id_obat"));
                o.setNama_obat(rs.getString("nama_obat"));
                if(rs.getString("satuan") != null){                    
                    switch (rs.getString("satuan")) {
                        case "botol":
                            o.setSatuan("botol");
                            break;
                        case "kapsul":
                            o.setSatuan("kapsul");
                            break;
                        default:
                            o.setSatuan("butir");
                            break;
                    }
                } else o.setSatuan("");
                o.setStok(rs.getInt("stok"));
                o.setHarga_jual(rs.getInt("harga_jual"));
                o.setNo_faktur(rs.getString("no_faktur"));
                o.setId_user(rs.getString("id_user"));
                lk.add(o);
            }
        }
        catch(SQLException e){
            System.out.println("getAllAnggota() : " + e.getMessage());
        }
        return lk;
    }
    
    public obat getRecordByNoRM(String nik) {
        obat o = new obat();
        String query = "SELECT * FROM obat WHERE id_obat = ?";
        try{
            pst = con.prepareStatement(query);
            pst.setString(1, nik);
            rs = pst.executeQuery();
            if(rs.next()){
                o.setId_obat(rs.getString("id_obat"));
                o.setNama_obat(rs.getString("nama_obat"));  
                o.setSatuan(rs.getString("satuan"));  
                o.setStok(rs.getInt("stok"));                 
                o.setHarga_jual(rs.getInt("harga_jual"));   
                o.setNo_faktur(rs.getString("no_faktur"));
                o.setId_user(rs.getString("id_user"));
            }
        }
        catch(SQLException e){
            System.out.println("getRecordByNoanggota() : " + e.getMessage());
        }
        return o;
    }
    
    public void insert(obat a, String page) {
        try{
            String query = "";
            String message="";
            if(page.equals("edit")){
                query = "UPDATE obat SET nama_obat=?, satuan=?, stok=?, harga_jual=?, no_faktur=?, id_user=? WHERE id_obat=?";
                message="update";
            }
            else if(page.equals("tambah")){
                query = "INSERT INTO obat(nama_obat, satuan, stok, harga_jual, no_faktur, id_user, id_obat) VALUES (?,?,?,?,?,?,?)";
                message="insert";
            }
            pst = con.prepareStatement(query);
            pst.setString(1, a.getNama_obat());
            pst.setString(2, a.getSatuan());
            pst.setInt(3, a.getStok());
            pst.setInt(4, a.getHarga_jual());
            pst.setString(5, a.getNo_faktur());
            pst.setString(6, a.getId_user());
            pst.setString(7, a.getId_obat());
            pst.executeUpdate();
            System.out.println(message + " success");
        }
        catch(SQLException e){
            System.out.println("insert() : " + e.getMessage());
        }
    }
    
    public void delete(String id) {
        try{
            String qDelete = "DELETE FROM obat WHERE id_obat=?";
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
        ObatDAO ad = new ObatDAO();
        System.out.println(ad.getAllObat());
        obat d = new obat();
        d.setId_obat("OU001");
        d.setNama_obat("Paracetamol");   
        d.setSatuan("butir");        
        d.setStok(250);      
        d.setHarga_jual(10000);
        d.setNo_faktur("013001");
        d.setId_user("US001");
        ad.insert(d, "tambah");
//        ad.insert(d, "edit");
//        ad.delete("OU001");
    }
}
