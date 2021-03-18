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
import model.bayarlayanan;

/**
 *
 * @author atha
 */
public class BayarLayananDAO {
    private final Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    
    public BayarLayananDAO(){
        con = koneksi.getConnection();
    }
    
    public ArrayList<bayarlayanan> getAllBayarLayanan(){
        ArrayList<bayarlayanan> lk = new ArrayList<>();
        try{
            String qSelect = "SELECT * FROM bayar_layanan ORDER BY id_bayar_layanan";
            pst = con.prepareStatement(qSelect);
            rs = pst.executeQuery();
            while(rs.next()){
                bayarlayanan d = new bayarlayanan();
                d.setId_bayar_layanan(rs.getString("id_bayar_layanan"));
                d.setId_layanan(rs.getString("id_layanan"));
                d.setId_pasien(rs.getString("id_pasien"));
                d.setTgl_daftar(rs.getString("tgl_daftar"));
                d.setHarga(Double.parseDouble(rs.getString("harga")));
                switch (rs.getString("jenis_pembayaran")) {
                    case "bpjs":
                        d.setJenis_pembayaran("BPJS");
                        break;
                    case "cash":
                        d.setJenis_pembayaran("Cash");
                        break;
                    default:
                        d.setJenis_pembayaran("Bank");
                        break;
                }
                d.setStatus(rs.getString("status"));
                d.setWaktu(rs.getString("waktu"));
                lk.add(d);
            }
        }
        catch(SQLException e){
            System.out.println("getAllBayar() : " + e.getMessage());
        }
        return lk;
    }
    
    public bayarlayanan getRecordById(String id) {
        bayarlayanan d = new bayarlayanan();
        String query = "SELECT * FROM bayar_layanan WHERE id_bayar_layanan = ?";
        try{
            pst = con.prepareStatement(query);
            pst.setString(1, id);
            rs = pst.executeQuery();
            if(rs.next()){
                d.setId_bayar_layanan(rs.getString("id_bayar_layanan"));
                d.setId_layanan(rs.getString("id_layanan"));
                d.setId_pasien(rs.getString("id_pasien"));
                d.setTgl_daftar(rs.getString("tgl_daftar"));
                d.setHarga(Double.parseDouble(rs.getString("harga")));
                switch (rs.getString("jenis_pembayaran")) {
                    case "bpjs":
                        d.setJenis_pembayaran("BPJS");
                        break;
                    case "cash":
                        d.setJenis_pembayaran("Cash");
                        break;
                    default:
                        d.setJenis_pembayaran("Bank");
                        break;
                }
                d.setStatus(rs.getString("status"));
                d.setWaktu(rs.getString("waktu"));
            }
        }
        catch(SQLException e){
            System.out.println("getRecordById() : " + e.getMessage());
        }
        return d;
    }
    
    public void insert(bayarlayanan b, String page) {
        try{
            String query = "";
            String message="";
            if(page.equals("edit")){
                query = "UPDATE bayar_layanan SET id_layanan=?, id_pasien=?, tgl_daftar=?, harga=?, jenis_pembayaran=?, status=?, waktu=? WHERE id_bayar_layanan=?";
                message = "update";
            }
            else if(page.equals("tambah")){
                query = "INSERT INTO bayar_layanan(id_layanan, id_pasien, tgl_daftar, harga, jenis_pembayaran, status, waktu, id_bayar_layanan) VALUES (?,?,?,?,?,?,?,?)";
                message = "insert";
            }
            pst = con.prepareStatement(query);
            pst.setString(1, b.getId_layanan());
            pst.setString(2, b.getId_pasien());
            pst.setString(3, b.getTgl_daftar());
            pst.setDouble(4, b.getHarga());
            pst.setString(5, b.getJenis_pembayaran());
            pst.setString(6, b.getStatus());
            pst.setString(7, b.getWaktu());
            pst.setString(8, b.getId_bayar_layanan());
            pst.executeUpdate();
            System.out.println(message + " success");
        }
        catch(SQLException e){
            System.out.println("insert() : " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        BayarLayananDAO ad = new BayarLayananDAO();
        System.out.println(ad.getAllBayarLayanan());
        System.out.println(ad.getRecordById("BL001"));
        bayarlayanan a = new bayarlayanan();
//        a.setId_bayar_layanan("BL001");
//        a.setId_layanan("LP001");
//        a.setId_pasien("P001");   
//        a.setTgl_daftar("2021-02-20");         
//        a.setStatus("lunas");
//        ad.insert(a, "tambah");
    }
}
