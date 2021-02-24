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
    public void insert(bayarlayanan b, String page) {
        try{
            String query = "";
            String message="";
            if(page.equals("edit")){
                query = "UPDATE bayar_layanan SET id_layanan=?, id_pasien=?, tgl_layanan=?, keterangan=? WHERE id_bayar_layanan=?";
                message = "update";
            }
            else if(page.equals("tambah")){
                query = "INSERT INTO bayar_layanan(id_layanan, id_pasien, tgl_layanan, keterangan, id_bayar_layanan) VALUES (?,?,?,?,?)";
                message = "insert";
            }
            pst = con.prepareStatement(query);
            pst.setString(1, b.getId_layanan());
            pst.setString(2, b.getId_pasien());
            pst.setString(3, b.getTgl_layanan());
            pst.setString(4, b.getKeterangan());
            pst.setString(5, b.getId_bayar_layanan());
            pst.executeUpdate();
            System.out.println(message + " success");
        }
        catch(SQLException e){
            System.out.println("insert() : " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        BayarLayananDAO ad = new BayarLayananDAO();
        bayarlayanan a = new bayarlayanan();
        a.setId_bayar_layanan("BL001");
        a.setId_layanan("LP001");
        a.setId_pasien("P001");   
        a.setTgl_layanan("2021-02-20");         
        a.setKeterangan("BPJS Lunas");
        ad.insert(a, "tambah");
    }
}
