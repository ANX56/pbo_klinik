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
import model.pembelianobat;

/**
 *
 * @author atha
 */
public class PembelianObatDAO {
    private final Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    
    public PembelianObatDAO(){
        con = koneksi.getConnection();
    }
    
    public void insert(pembelianobat po, String page) {
        try{
            String query = "INSERT INTO supply_obat(id_pembelian, id_supplier, id_obat, no_faktur, tgl_faktur, harga_beli, jumlah, keterangan, tgl_expired, waktu, id_user) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            pst = con.prepareStatement(query);
            pst.setString(1, po.getId_pembelian());
            pst.setString(2, po.getId_supplier());
            pst.setString(3, po.getId_obat());
            pst.setString(4, po.getNo_faktur());
            pst.setString(5, po.getTgl_faktur());
            pst.setInt(6, po.getHarga_beli());
            pst.setInt(7, po.getJumlah());
            pst.setString(8, po.getKeterangan());
            pst.setString(9, po.getTgl_expired());
            pst.setString(10, po.getWaktu());
            pst.setString(11, po.getId_user());
            pst.executeUpdate();
            System.out.println("insert success");
        }
        catch(SQLException e){
            System.out.println("insert() : " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        PembelianObatDAO ad = new PembelianObatDAO();
        pembelianobat l = new pembelianobat();
        l.setId_pembelian("PO001");
        l.setId_supplier("SP001");
        l.setId_obat("OU001");
        l.setNo_faktur("11001100");
        l.setTgl_faktur("2021-02-19");
        l.setHarga_beli(2500000);
        l.setJumlah(250);
        l.setKeterangan("Stok bulanan");
        l.setTgl_expired("2022-01-20");
        l.setWaktu("2021-02-19 09:25:21");
        l.setId_user("UK001");
        ad.insert(l, "tambah");
    }
}
