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
import model.pendaftaran;

/**
 *
 * @author atha
 */
public class PendaftaranDAO {
    private final Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    
    public PendaftaranDAO(){
        con = koneksi.getConnection();
    }
    
    public void insert(pendaftaran p, String page) {
        try{
            String query = "INSERT INTO pendaftaran(id_pasien, id_poli, tgl_daftar, keterangan, waktu, id_user, no_antrian) VALUES (?,?,?,?,?,?,?)";
            pst = con.prepareStatement(query);
            pst.setString(1, p.getId_pasien());
            pst.setString(2, p.getId_poli());
            pst.setString(3, p.getTgl_daftar());
            pst.setString(4, p.getKeterangan());
            pst.setString(5, p.getWaktu());
            pst.setString(6, p.getId_user());
            pst.setString(7, p.getNo_antrian());
            pst.executeUpdate();
            System.out.println("insert success");
        }
        catch(SQLException e){
            System.out.println("insert() : " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        PendaftaranDAO ad = new PendaftaranDAO();
        pendaftaran p = new pendaftaran();
        p.setId_pasien("P002");
        p.setId_poli("PU001");
        p.setTgl_daftar("2020-02-21");
        p.setKeterangan("Pasien Jadwal Pagi");
        p.setWaktu("2020-02-21 06:29:19");
        p.setId_user("UP002");
        p.setNo_antrian("001");
        ad.insert(p, "tambah");
//        ad.insert(l, "edit");
//        ad.delete("LP001");
    }
}
