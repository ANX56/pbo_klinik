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
import model.bayarobat;

/**
 *
 * @author atha
 */
public class BayarObatDAO {
    private final Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    
    public BayarObatDAO(){
        con = koneksi.getConnection();
    }
    
    public ArrayList<bayarobat> getAllBayarObat(){
        ArrayList<bayarobat> lk = new ArrayList<>();
        try{
            String qSelect = "SELECT * FROM bayar_obat ORDER BY id_bayar_obat";
            pst = con.prepareStatement(qSelect);
            rs = pst.executeQuery();
            while(rs.next()){
                bayarobat d = new bayarobat();
                d.setId_bayar_obat(rs.getString("id_bayar_obat"));
                d.setId_obat(rs.getString("id_obat"));
                d.setId_pasien(rs.getString("id_pasien"));
                d.setId_resep(rs.getString("id_resep"));
                d.setJumlah(rs.getInt("jumlah"));
                d.setHarga(rs.getDouble("harga"));
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
    
    public bayarobat getRecordById(String id) {
        bayarobat d = new bayarobat();
        String query = "SELECT * FROM bayar_obat WHERE id_bayar_obat = ?";
        try{
            pst = con.prepareStatement(query);
            pst.setString(1, id);
            rs = pst.executeQuery();
            if(rs.next()){
                d.setId_bayar_obat(rs.getString("id_bayar_obat"));
                d.setId_obat(rs.getString("id_obat"));
                d.setId_pasien(rs.getString("id_pasien"));
                d.setId_resep(rs.getString("id_resep"));
                d.setJumlah(rs.getInt("jumlah"));
                d.setHarga(rs.getDouble("harga"));
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
    
    public void insert(bayarobat b, String page) {
        try{
            String query = "";
            String message="";
            if(page.equals("edit")){
                query = "UPDATE bayar_obat SET id_obat=?, id_pasien=?, id_resep=?, jumlah=?, harga=?, jenis_pembayaran=?, status=?, waktu=? WHERE id_bayar_obat=?";
                message = "update";
            }
            else if(page.equals("tambah")){
                query = "INSERT INTO bayar_obat(id_obat, id_pasien, id_resep, jumlah, harga, jenis_pembayaran, status, waktu, id_bayar_obat) VALUES (?,?,?,?,?,?,?,?)";
                message = "insert";
            }
            pst = con.prepareStatement(query);
            pst.setString(1, b.getId_obat());
            pst.setString(2, b.getId_pasien());
            pst.setString(3, b.getId_resep());
            pst.setInt(4, b.getJumlah());
            pst.setDouble(5, b.getHarga());
            pst.setString(6, b.getJenis_pembayaran());
            pst.setString(7, b.getStatus());
            pst.setString(8, b.getWaktu());
            pst.setString(9, b.getId_bayar_obat());
            pst.executeUpdate();
            System.out.println(message + " success");
        }
        catch(SQLException e){
            System.out.println("insert() : " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        BayarObatDAO ad = new BayarObatDAO();
        bayarobat a = new bayarobat();
        System.out.println(ad.getAllBayarObat());
//        a.setId_bayar_obat("BO001");
//        a.setId_obat("OU001");
//        a.setId_pasien("P001");   
//        a.setId_resep("RO001");         
//        a.setJumlah(12);    
//        a.setJenis_pembayaran("BPJS");    
//        a.setWaktu("2021-02-20 10:02:11");
//        ad.insert(a, "tambah");
    }
}
