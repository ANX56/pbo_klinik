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
import model.rawatinap;
import model.supplier;

/**
 *
 * @author atha
 */
public class RawatInapDAO {
    private final Connection con;
    private PreparedStatement pst;
   
    public RawatInapDAO(){
        con = koneksi.getConnection();
    }
    
    public void insert(rawatinap pk, String page) {
        try{
            String query = "INSERT INTO rawat_inap(id_pasien, id_kamar, tgl_check_in, tgl_check_out, keterangan, id_rawat) VALUES (?,?,?,?,?,?)";
            pst = con.prepareStatement(query);
            pst.setString(1, pk.getId_pasien());
            pst.setString(2, pk.getId_kamar()); 
            pst.setString(3, pk.getTgl_check_in());  
            pst.setString(4, pk.getTgl_check_out());           
            pst.setString(5, pk.getKeterangan());
            pst.setString(6, pk.getId_rawat());
            pst.executeUpdate();
            System.out.println("insert success");
        }
        catch(SQLException e){
            System.out.println("insert() : " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        RawatInapDAO ad = new RawatInapDAO();
        rawatinap pk = new rawatinap();
        pk.setId_rawat("RA001");   
        pk.setId_pasien("PA001");
        pk.setId_kamar("KM001");           
        pk.setTgl_check_in("2021-02-19");   
        pk.setTgl_check_out("2021-03-05");   
        pk.setKeterangan("Pasien Isolasi Covid-19");              
        ad.insert(pk, "tambah");
    }
}
