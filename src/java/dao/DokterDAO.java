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
import model.dokter;

/**
 *
 * @author atha
 */
public class DokterDAO {
    private final Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    
    public DokterDAO(){
        con = koneksi.getConnection();
    }
    
    public ArrayList<dokter> getAllDokter(){
        ArrayList<dokter> lk = new ArrayList<>();
        try{
            String qSelect = "SELECT * FROM dokter ORDER BY id_dokter";
            pst = con.prepareStatement(qSelect);
            rs = pst.executeQuery();
            while(rs.next()){
                dokter d = new dokter();
                d.setId_dokter(rs.getString("id_dokter"));
                d.setNama_dokter(rs.getString("nama_dokter"));
                d.setTgl_lahir(rs.getString("tgl_lahir"));
                d.setId_poli(rs.getString("id_poli"));
                if(rs.getString("jenis_kelamin") != null){                    
                    if((rs.getString("jenis_kelamin")).equals("L"))
                        d.setJenis_kelamin("Laki-Laki");
                    else d.setJenis_kelamin("Perempuan");
                }
                d.setAlamat(rs.getString("alamat"));
                d.setNo_hp(rs.getString("no_hp"));
                d.setNpwp(rs.getString("npwp"));
                d.setNo_ktp(rs.getString("no_ktp"));
                d.setEmail(rs.getString("email"));
                d.setPassword(rs.getString("password"));
                d.setWaktu(rs.getString("waktu"));
                d.setId_user(rs.getString("id_user"));
                lk.add(d);
            }
        }
        catch(SQLException e){
            System.out.println("getAllAnggota() : " + e.getMessage());
        }
        return lk;
    }
    
    public dokter getRecordById(String id) {
        dokter d = new dokter();
        String query = "SELECT * FROM dokter WHERE id_dokter = ?";
        try{
            pst = con.prepareStatement(query);
            pst.setString(1, id);
            rs = pst.executeQuery();
            if(rs.next()){
                d.setId_dokter(rs.getString("id_dokter"));
                d.setNama_dokter(rs.getString("nama_dokter"));
                d.setTgl_lahir(rs.getString("tgl_lahir"));
                d.setId_poli(rs.getString("id_poli"));
                if(rs.getString("jenis_kelamin") != null){                    
                    if((rs.getString("jenis_kelamin")).equals("L"))
                        d.setJenis_kelamin("Laki-Laki");
                    else d.setJenis_kelamin("Perempuan");
                }
                d.setAlamat(rs.getString("alamat"));
                d.setNo_hp(rs.getString("no_hp"));
                d.setNpwp(rs.getString("npwp"));
                d.setNo_ktp(rs.getString("no_ktp"));
                d.setEmail(rs.getString("email"));
                d.setPassword(rs.getString("password"));
                d.setWaktu(rs.getString("waktu"));
                d.setId_user(rs.getString("id_user"));
            }
        }
        catch(SQLException e){
            System.out.println("getRecordByNoanggota() : " + e.getMessage());
        }
        return d;
    }
    
    public void insert(dokter a, String page) {
        try{
            String query = "";
            String message="";
            if(page.equals("edit")){
                query = "UPDATE dokter SET nama_dokter=?, tgl_lahir=?, id_poli=?, jenis_kelamin=?, alamat=?, no_hp=?, npwp=?, no_ktp=?, email=?, password=?, waktu=?, id_user=? WHERE id_dokter=?";
                message="update";
            }
            else if(page.equals("tambah")){
                query = "INSERT INTO dokter(nama_dokter, tgl_lahir, id_poli, jenis_kelamin, alamat, no_hp, npwp, no_ktp, email, password, waktu, id_user, id_dokter) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?)";
                message="insert";
            }
            pst = con.prepareStatement(query);
            pst.setString(1, a.getNama_dokter());
            pst.setString(2, a.getTgl_lahir());
            pst.setString(3, a.getId_poli());
            pst.setString(4, a.getJenis_kelamin());
            pst.setString(5, a.getAlamat());
            pst.setString(6, a.getNo_hp());
            pst.setString(7, a.getNpwp());
            pst.setString(8, a.getNo_ktp());
            pst.setString(9, a.getEmail());
            pst.setString(10, a.getPassword());
            pst.setString(11, a.getWaktu());
            pst.setString(12, a.getId_user());
            pst.setString(13, a.getId_dokter());
            pst.executeUpdate();
            System.out.println(message + " success");
        }
        catch(SQLException e){
            System.out.println("insert() : " + e.getMessage());
        }
    }
    
    public void delete(String id) {
        try{
            String qDelete = "DELETE FROM dokter WHERE id_dokter=?";
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
        DokterDAO ad = new DokterDAO();
        System.out.println(ad.getAllDokter());
        dokter d = new dokter();
        d.setId_dokter("DU001");
        d.setNama_dokter("dr. Ishak Husein");   
        d.setTgl_lahir("1973-05-21");        
        d.setId_poli("PU001");      
        d.setJenis_kelamin("L");
        d.setAlamat("Komplek Timah Blok DD.5 No.3");
        d.setNo_hp("083455767783");
        d.setNpwp("092452943407000");
        d.setNo_ktp("3128082839384484");
        d.setEmail("contact.ishakhusein@gmail.com");
        d.setPassword("ishakhusein");
        d.setWaktu("2021-02-13 14:21:19");
        d.setId_user("UD001");
//        ad.insert(d, "tambah");
        ad.insert(d, "edit");
//        ad.delete("DU001");
    }
}
