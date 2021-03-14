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
                karyawan k = new karyawan();
                k.setId_karyawan(rs.getString("id_karyawan"));
                k.setNama_karyawan(rs.getString("nama_karyawan"));
                k.setTgl_lahir(rs.getString("tgl_lahir"));
                k.setBidang_pekerjaan(rs.getString("bidang_pekerjaan"));                
                if((rs.getString("bidang_pekerjaan")).equals("administrasi"))
                    k.setBidang_pekerjaan("Administrasi");
                else if((rs.getString("bidang_pekerjaan")).equals("suster"))
                    k.setBidang_pekerjaan("Suster");
                else if((rs.getString("bidang_pekerjaan")).equals("rm"))
                    k.setBidang_pekerjaan("Rekam Medis");
                else k.setBidang_pekerjaan("Apoteker");     
                if((rs.getString("jenis_kelamin")).equals("L"))
                    k.setJenis_kelamin("Laki-Laki");
                else k.setJenis_kelamin("Perempuan");
                k.setAlamat(rs.getString("alamat"));
                k.setNo_hp(rs.getString("no_hp"));
                k.setNo_ktp(rs.getString("no_ktp"));
                k.setNpwp(rs.getString("npwp"));
                k.setEmail(rs.getString("email"));
                k.setPassword(rs.getString("password"));
                k.setWaktu(rs.getString("waktu"));
                k.setId_user(rs.getString("id_user"));
                lk.add(k);
            }
        }
        catch(SQLException e){
            System.out.println("getAllAnggota() : " + e.getMessage());
        }
        return lk;
    }
    
    public karyawan getRecordById(String id) {
        karyawan k = new karyawan();
        String query = "SELECT * FROM karyawan WHERE id_karyawan = ?";
        try{
            pst = con.prepareStatement(query);
            pst.setString(1, id);
            rs = pst.executeQuery();
            if(rs.next()){
                k.setId_karyawan(rs.getString("id_karyawan"));
                k.setNama_karyawan(rs.getString("nama_karyawan"));
                k.setTgl_lahir(rs.getString("tgl_lahir"));
                if((rs.getString("bidang_pekerjaan")).equals("administrasi"))
                    k.setBidang_pekerjaan("Administrasi");
                else if((rs.getString("bidang_pekerjaan")).equals("suster"))
                    k.setBidang_pekerjaan("Suster");
                else if((rs.getString("bidang_pekerjaan")).equals("rm"))
                    k.setBidang_pekerjaan("Rekam Medis");
                else k.setBidang_pekerjaan("Apoteker");    
                if(rs.getString("jenis_kelamin") != null){                    
                    if((rs.getString("jenis_kelamin")).equals("L"))
                        k.setJenis_kelamin("Laki-Laki");
                    else k.setJenis_kelamin("Perempuan");
                } else k.setJenis_kelamin("");
                k.setAlamat(rs.getString("alamat"));
                k.setNo_hp(rs.getString("no_hp"));
                k.setNo_ktp(rs.getString("no_ktp"));
                k.setNpwp(rs.getString("npwp"));
                k.setEmail(rs.getString("email"));
                k.setPassword(rs.getString("password"));
                k.setWaktu(rs.getString("waktu"));
                k.setId_user(rs.getString("id_user"));
            }
        }
        catch(SQLException e){
            System.out.println("getRecordById() : " + e.getMessage());
        }
        return k;
    }
    
    public void insert(karyawan k, String page) {
        try{
            String query = "";
            String message="";
            if(page.equals("edit")){
                query = "UPDATE karyawan SET nama_karyawan=?, tgl_lahir=?, bidang_pekerjaan=?, jenis_kelamin=?, alamat=?, no_hp=?, no_ktp=?, npwp=?, email=?, password=?, waktu=?, id_user=? WHERE id_karyawan=?";
                message="update";
            }
            else if(page.equals("tambah")){
                query = "INSERT INTO karyawan(nama_karyawan, tgl_lahir, bidang_pekerjaan, jenis_kelamin, alamat, no_hp, no_ktp, npwp, email, password, waktu, id_user, id_karyawan) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                message="insert";
            }
            pst = con.prepareStatement(query);
            pst.setString(1, k.getNama_karyawan());
            pst.setString(2, k.getTgl_lahir());
            pst.setString(3, k.getBidang_pekerjaan());
            pst.setString(4, k.getJenis_kelamin());
            pst.setString(5, k.getAlamat());
            pst.setString(6, k.getNo_hp());
            pst.setString(7, k.getNo_ktp());
            pst.setString(8, k.getNpwp());
            pst.setString(9, k.getEmail());
            pst.setString(10, k.getPassword());
            pst.setString(11, k.getWaktu());
            pst.setString(12, k.getId_user());
            pst.setString(13, k.getId_karyawan());
            pst.executeUpdate();
            System.out.println(message + " success");
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
//        a.setId_karyawan("KR001");
//        a.setNama_karyawan("Tegar Rizki Harnandi");
//        a.setTgl_lahir("1993-04-24");
//        a.setBidang_pekerjaan("rm");
//        a.setJenis_kelamin("L");
//        a.setAlamat("Jl. Hias No. 35");
//        a.setNo_hp("083455767783");
//        a.setNo_ktp("3175872837467623");
//        a.setNpwp("0012348958263");
//        a.setEmail("tegarrizki@gmail.com");
//        a.setPassword("tegar123");
//        a.setWaktu("2021-03-14 22:04:14");
//        a.setId_user("UK002");
//        ad.insert(a, "tambah");
//        ad.insert(a, "edit");
//        ad.delete("K0002");
    }
}
