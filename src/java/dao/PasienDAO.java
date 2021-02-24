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
import model.pasien;

/**
 *
 * @author atha
 */
public class PasienDAO {
    private final Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    
    public PasienDAO(){
        con = koneksi.getConnection();
    }
    
    public ArrayList<pasien> getAllPasien(){
        ArrayList<pasien> lk = new ArrayList<>();
        try{
            String qSelect = "SELECT * FROM pasien ORDER BY id_pasien";
            pst = con.prepareStatement(qSelect);
            rs = pst.executeQuery();
            while(rs.next()){
                pasien p = new pasien();
                p.setId_pasien(rs.getString("id_pasien"));
                p.setNama_pasien(rs.getString("nama_pasien"));
                p.setTgl_lahir(rs.getString("tgl_lahir"));
                if(rs.getString("jenis_kelamin") != null){                    
                    if((rs.getString("jenis_kelamin")).equals("L"))
                        p.setJenis_kelamin("Laki-Laki");
                    else p.setJenis_kelamin("Perempuan");
                } else p.setJenis_kelamin("");
                p.setNo_ktp(rs.getString("no_ktp"));
                p.setAlamat(rs.getString("alamat"));
                p.setNo_hp(rs.getString("no_hp"));
                p.setGol_darah(rs.getString("gol_darah"));
                p.setPassword(rs.getString("password"));
                p.setId_user(rs.getString("id_user"));
                lk.add(p);
            }
        }
        catch(SQLException e){
            System.out.println("getAllPasien() : " + e.getMessage());
        }
        return lk;
    }
    
    public pasien getRecordById(String id) {
        pasien p = new pasien();
        String query = "SELECT * FROM pasien WHERE id_pasien = ?";
        try{
            pst = con.prepareStatement(query);
            pst.setString(1, id);
            rs = pst.executeQuery();
            if(rs.next()){
                p.setId_pasien(rs.getString("id_pasien"));
                p.setNama_pasien(rs.getString("nama_pasien"));
                p.setTgl_lahir(rs.getString("tgl_lahir"));
                if(rs.getString("jenis_kelamin") != null){                    
                    if((rs.getString("jenis_kelamin")).equals("L"))
                        p.setJenis_kelamin("Laki-Laki");
                    else p.setJenis_kelamin("Perempuan");
                } else p.setJenis_kelamin("");
                p.setNo_ktp(rs.getString("no_ktp"));
                p.setAlamat(rs.getString("alamat"));
                p.setNo_hp(rs.getString("no_hp"));
                p.setGol_darah(rs.getString("gol_darah"));
                p.setPassword(rs.getString("password"));
                p.setId_user(rs.getString("id_user"));
            }
        }
        catch(SQLException e){
            System.out.println("getRecordByNoRM() : " + e.getMessage());
        }
        return p;
    }
    
    public void insert(pasien p, String page) {
        try{
            String query = "";
            String message="";
            if(page.equals("edit")){
                query = "UPDATE pasien SET nama_pasien=?, tgl_lahir=?, jenis_kelamin=?, no_ktp=?, alamat=?, no_hp=?, gol_darah=?, password=?, id_user=? WHERE id_pasien=?";
                message = "update";
            }
            else if(page.equals("tambah")){
                query = "INSERT INTO pasien(nama_pasien, tgl_lahir, jenis_kelamin, no_ktp, alamat, no_hp, gol_darah, password, id_user, id_pasien) VALUES (?,?,?,?,?,?,?,?,?,?)";
                message = "insert";
            }
            pst = con.prepareStatement(query);
            pst.setString(1, p.getNama_pasien());
            pst.setString(2, p.getTgl_lahir());
            pst.setString(3, p.getJenis_kelamin());
            pst.setString(4, p.getNo_ktp());
            pst.setString(5, p.getAlamat());
            pst.setString(6, p.getNo_hp());
            pst.setString(7, p.getGol_darah());
            pst.setString(8, p.getPassword());
            pst.setString(9, p.getId_user());
            pst.setString(10, p.getId_pasien());
            pst.executeUpdate();
            System.out.println(message + " success");
        }
        catch(SQLException e){
            System.out.println("insert() : " + e.getMessage());
        }
    }
    
    public void delete(String id) {
        try{
            String qDelete = "DELETE FROM pasien WHERE id_pasien=?";
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
        PasienDAO ad = new PasienDAO();
        System.out.println(ad.getAllPasien());
        pasien a = new pasien();
        a.setId_pasien("P001");
        a.setNama_pasien("Athallah Rizaldi");
        a.setTgl_lahir("2003-05-11");   
        a.setJenis_kelamin("L");         
        a.setNo_ktp("3175081105031002");          
        a.setAlamat("Jl. Hias No. 35");
        a.setNo_hp("085691645955");
        a.setGol_darah("O");
        a.setPassword("atha1234");
        a.setId_user("UP001");
//        ad.insert(a, "tambah");
        ad.insert(a, "edit");
//        ad.delete("P001");
    }
}
