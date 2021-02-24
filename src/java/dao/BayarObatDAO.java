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
    public void insert(bayarobat b, String page) {
        try{
            String query = "";
            String message="";
            if(page.equals("edit")){
                query = "UPDATE bayar_obat SET id_obat=?, id_pasien=?, id_resep=?, jumlah=?, jenis_pembayaran=?, waktu=?, id_user=? WHERE id_bayar_obat=?";
                message = "update";
            }
            else if(page.equals("tambah")){
                query = "INSERT INTO bayar_obat(id_obat, id_pasien, id_resep, jumlah, jenis_pembayaran, waktu, id_user, id_bayar_obat) VALUES (?,?,?,?,?,?,?,?)";
                message = "insert";
            }
            pst = con.prepareStatement(query);
            pst.setString(1, b.getId_obat());
            pst.setString(2, b.getId_pasien());
            pst.setString(3, b.getId_resep());
            pst.setInt(4, b.getJumlah());
            pst.setString(5, b.getJenis_pembayaran());
            pst.setString(6, b.getWaktu());
            pst.setString(7, b.getId_user());
            pst.setString(8, b.getId_bayar_obat());
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
        a.setId_bayar_obat("BO001");
        a.setId_obat("OU001");
        a.setId_pasien("P001");   
        a.setId_resep("RO001");         
        a.setJumlah(12);    
        a.setJenis_pembayaran("BPJS");    
        a.setWaktu("2021-02-20 10:02:11");
        a.setId_user("UP001");
        ad.insert(a, "tambah");
    }
}
