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
import java.util.ArrayList;
import model.kamar;

/**
 *
 * @author hadit
 */
public class KamarDAO {
    private final Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
   
    public KamarDAO(){
        con = koneksi.getConnection();
    }
    
    public ArrayList<kamar> getAllKamar(){
    ArrayList<kamar> ls = new ArrayList<>();
        try{
            String query = "SELECT * FROM kamar ORDER BY id_kamar";
            pst = con.prepareStatement(query);
            rs= pst.executeQuery();
            while(rs.next()){
                kamar r = new kamar();
                r.setId_kamar(rs.getString("id_kamar"));
                r.setNama_kamar(rs.getString("nama_ruang"));
                r.setNo_kamar(rs.getString("no_ruang"));
                r.setKelas(rs.getString("kelas"));
                r.setDeskirpsi(rs.getString("des_kamar"));
                r.setKapasitas(rs.getInt("kapasitas"));
                r.setTerisi(rs.getString("terisi"));
                r.setStatus(rs.getString("status"));
                ls.add(r);
            }
        }
        catch(SQLException e){
            System.out.println("getAllKamar() :" + e.getMessage());
        }
        return ls;
    }
    
    public kamar getRecordById(String no){
        kamar k = new kamar();
        String qusup="SELECT * FROM kamar WHERE id_kamar=?";
        try{
        pst= con.prepareStatement(qusup);
        pst.setString(1, no);
        rs = pst.executeQuery();
            if (rs.next()) {
                k.setId_kamar(rs.getString("id_kamar"));
                k.setNama_kamar(rs.getString("nama_ruang"));
                k.setNo_kamar(rs.getString("no_ruang"));
                k.setKelas(rs.getString("kelas"));
                k.setDeskirpsi(rs.getString("des_kamar"));
                k.setKapasitas(rs.getInt("kapasitas"));
                k.setTerisi(rs.getString("terisi"));
                k.setStatus(rs.getString("status"));
            }
        }
        catch(SQLException e){
            System.out.println("getRecordByIdKamar() :" +e.getMessage());
        }
        return k;
    }
    
    public void insert(kamar a, String page) {
        try{
            String query = "";
            if(page.equals("edit")){
                query = "UPDATE kamar SET nama_ruang=?, no_ruang=?, kelas=?, des_kamar=?, kapasitas=?, terisi=?, status=? WHERE id_kamar=?";
            }
            else if(page.equals("tambah")){
                query = "INSERT INTO kamar(nama_ruang, no_ruang, kelas, des_kamar, kapasitas, terisi, status, id_kamar) VALUES (?,?,?,?,?,?,?,?)";
            }
            pst = con.prepareStatement(query);
            pst.setString(1, a.getNama_kamar());
            pst.setString(2, a.getNo_kamar());
            pst.setString(3, a.getKelas());
            pst.setString(4, a.getDeskirpsi());
            pst.setInt(5, a.getKapasitas());
            pst.setString(6, a.getTerisi());
            pst.setString(7, a.getStatus());
            pst.setString(8, a.getId_kamar());
            pst.executeUpdate();
            System.out.println("insert or update success");
        }
        catch(SQLException e){
            System.out.println("insert() : " + e.getMessage());
        }
    }
    
    public void delete(String id) {
        try{
            String qDelete = "DELETE FROM kamar WHERE id_kamar=?";
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
        KamarDAO ad = new KamarDAO();
        System.out.println(ad.getAllKamar());
        kamar a = new kamar();
//        a.setId_kamar("KM0001");
//        a.setNama_kamar("Mawar");           
//        a.setNo_kamar("01");   
//        a.setDeskirpsi("Korban kecelakaan");        
//        a.setKapasitas(6);
//        a.setTerisi("y");
//        a.setStatus("ok");
//        ad.insert(a, "tambah");
//        ad.insert(a, "edit");
//        ad.delete("KM001");
    }
}
