package dao;

import connection.koneksi;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.rm;

public class RMDAO {
    private final Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    
    public RMDAO(){
        con = koneksi.getConnection();
    }
    
    public ArrayList<rm> getAllRM(){
        ArrayList<rm> lk = new ArrayList<>();
        try{
            String qSelect = "SELECT * FROM rm ORDER BY nomor_rm";
            pst = con.prepareStatement(qSelect);
            rs = pst.executeQuery();
            while(rs.next()){
                rm d = new rm();
                d.setNomor_rm(rs.getString("nomor_rm"));
                d.setId_pasien(rs.getString("id_pasien"));
                d.setTgl_daftar(rs.getString("tgl_daftar"));
                d.setId_poli(rs.getString("id_poli"));
                d.setId_dokter(rs.getString("id_dokter"));
                d.setBerat(rs.getInt("berat"));
                d.setTinggi(rs.getInt("tinggi"));
                d.setTensi(rs.getString("tensi"));
                d.setDiagnosa(rs.getString("diagnosa"));
                d.setId_resep(rs.getString("id_resep"));
                d.setWaktu(rs.getString("waktu"));
                lk.add(d);
            }
        }
        catch(SQLException e){
            System.out.println("getAllAnggota() : " + e.getMessage());
        }
        return lk;
    }
    
    public rm getRecordById(String id) {
        rm d = new rm();
        String query = "SELECT * FROM rm WHERE nomor_rm = ?";
        try{
            pst = con.prepareStatement(query);
            pst.setString(1, id);
            rs = pst.executeQuery();
            if(rs.next()){
                d.setNomor_rm(rs.getString("nomor_rm"));
                d.setId_pasien(rs.getString("id_pasien"));
                d.setTgl_daftar(rs.getString("tgl_daftar"));
                d.setId_poli(rs.getString("id_poli"));
                d.setId_dokter(rs.getString("id_dokter"));
                d.setBerat(rs.getInt("berat"));
                d.setTinggi(rs.getInt("tinggi"));
                d.setTensi(rs.getString("tensi"));
                d.setDiagnosa(rs.getString("diagnosa"));
                d.setId_resep(rs.getString("id_resep"));
                d.setWaktu(rs.getString("waktu"));
            }
        }
        catch(SQLException e){
            System.out.println("getRecordByNoanggota() : " + e.getMessage());
        }
        return d;
    }
    
    public void insert(rm a, String page) {
        try{
            String query = "";
            String message="";
            if(page.equals("edit")){
                query = "UPDATE rm SET id_pasien=?, tgl_daftar=?, id_poli=?, id_dokter=?, berat=?, tinggi=?, tensi=?, diagnosa=?, id_resep=?, waktu=? WHERE nomor_rm=?";
                message="update";
            }
            else if(page.equals("tambah")){
                query = "INSERT INTO rm(id_pasien, tgl_daftar, id_poli, id_dokter, berat, tinggi, tensi, diagnosa, id_resep, waktu, nomor_rm) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
                message="insert";
            }
            pst = con.prepareStatement(query);
            pst.setString(1, a.getId_pasien());
            pst.setString(2, a.getTgl_daftar());
            pst.setString(3, a.getId_poli());
            pst.setString(4, a.getId_dokter());
            pst.setInt(5, a.getBerat());
            pst.setInt(6, a.getTinggi());
            pst.setString(7, a.getTensi());
            pst.setString(8, a.getDiagnosa());
            pst.setString(9, a.getId_resep());
            pst.setString(10, a.getWaktu());
            pst.setString(11, a.getNomor_rm());
            pst.executeUpdate();
            System.out.println(message + " success");
        }
        catch(SQLException e){
            System.out.println("insert() : " + e.getMessage());
        }
    }
    
    public void delete(String id) {
        try{
            String qDelete = "DELETE FROM rm WHERE nomor_rm=?";
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
        RMDAO ad = new RMDAO();
        System.out.println(ad.getAllRM());
        System.out.println(ad.getRecordById("191417"));
        rm d = new rm();
//        d.setNomor_rm("191417");
//        d.setId_pasien("P001");   
//        d.setTgl_daftar("2021-02-08");        
//        d.setId_poli("PU001");
//        d.setId_dokter("DU001");
//        d.setBerat(100);
//        d.setTinggi(185);
//        d.setTensi("120/70");
//        d.setDiagnosa("kurang istirahat, sakit kepala tension");
//        d.setId_resep("RO001");
//        d.setWaktu("2021-02-08 11:21:19");
//        ad.insert(d, "tambah");
//        ad.insert(d, "edit");
//        ad.delete("191417");
    }
}
