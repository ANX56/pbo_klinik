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
import model.karyawan;

/**
 *
 * @author atha
 */
public class KaryawanDAO {
    private final Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    
    public KaryawanDAO(){
        con = koneksi.getConnection();
    }
    
    public ArrayList<karyawan> getAllKaryawan(){
        ArrayList<karyawan> lk = new ArrayList<>();
        try{
            String qSelect = "SELECT * FROM karyawan ORDER BY id_karyawan";
            pst = con.prepareStatement(qSelect);
            rs = pst.executeQuery();
            while(rs.next()){
                karyawan p = new karyawan();
                p.setId(rs.getString("id_karyawan"));
                p.setNama(rs.getString("nama"));
                p.setBagian(rs.getString("bagian"));
                if(rs.getString("jenis_kelamin") != null){                    
                    if((rs.getString("jenis_kelamin")).equals("L"))
                        p.setJk("Laki-Laki");
                    else p.setJk("Perempuan");
                } else p.setJk("");
                if(rs.getString("tgl_lahir") != null){                      
                    String tgl = sdf.format(rs.getDate("tgl_lahir"));
                    p.setTgllahir(tgl);
                } else p.setTgllahir("");
                if(rs.getString("alamat") != null){                      
                    p.setAlamat(rs.getString("alamat"));
                } else p.setAlamat("");
                if(rs.getString("nohp") != null){          
                    p.setNohp(rs.getString("nohp"));
                } else p.setNohp("");
                lk.add(p);
            }
        }
        catch(SQLException e){
            System.out.println("getAllAnggota() : " + e.getMessage());
        }
        return lk;
    }
    
    public karyawan getRecordByNoRM(String nik) {
        karyawan k = new karyawan();
        String query = "SELECT * FROM karyawan WHERE id_karyawan = ?";
        try{
            pst = con.prepareStatement(query);
            pst.setString(1, nik);
            rs = pst.executeQuery();
            if(rs.next()){
                k.setId(rs.getString("id_karyawan"));
                k.setNama(rs.getString("nama"));    
                k.setBagian(rs.getString("bagian"));  
                k.setJk(rs.getString("jenis_kelamin"));        
                k.setTgllahir(rs.getString("tgllahir"));
                k.setAlamat(rs.getString("alamat"));
                k.setNohp(rs.getString("nohp"));
            }
        }
        catch(SQLException e){
            System.out.println("getRecordByNoanggota() : " + e.getMessage());
        }
        return k;
    }
    
    public void insert(karyawan a, String page) {
        try{
            String query = "";
            if(page.equals("edit")){
                query = "UPDATE karyawan SET nama=?, bagian=?, jenis_kelamin=?, tgl_lahir=?, alamat=?, nohp=? WHERE id_karyawan=?";
            }
            else if(page.equals("tambah")){
                query = "INSERT INTO karyawan(nama, bagian, jenis_kelamin, tgl_lahir, alamat, nohp, id_karyawan) VALUES (?,?,?,?,?,?,?)";
            }
            pst = con.prepareStatement(query);
            pst.setString(1, a.getNama());
            pst.setString(2, a.getBagian());
            if(a.getJk().equals("")) pst.setString(4, null);
                else pst.setString(3, a.getJk());
            if(a.getTgllahir().equals("")) pst.setString(4, null);
                else pst.setString(4, a.getTgllahir());
            pst.setString(5, a.getAlamat());
            pst.setString(6, a.getNohp());
            pst.setString(7, a.getId());
            pst.executeUpdate();
            System.out.println("insert or update success");
        }
        catch(SQLException e){
            System.out.println("insert() : " + e.getMessage());
        }
    }
    
    public void delete(String rm) {
        try{
            String qDelete = "DELETE FROM karyawan WHERE id_karyawan=?";
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
        KaryawanDAO ad = new KaryawanDAO();
        System.out.println(ad.getAllKaryawan());
        karyawan a = new karyawan();
//        a.setId("K0002");
//        a.setNama("M. Alvif Arfin");           
//        a.setBagian("Apoteker");   
//        a.setJk("L");        
//        a.setTgllahir("2003-07-18");
//        a.setAlamat("Warung Pak Jusuf");
//        a.setNohp("083455767783");
//        ad.insert(a, "tambah");
//        ad.insert(a, "edit");
//        ad.delete("K0002");
    }
}
