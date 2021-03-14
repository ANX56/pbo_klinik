package dao;


import connection.koneksi;
import dao.ObatDAO;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import model.poli;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author atha
 */
public class PoliDAO {
    private final Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    
    public PoliDAO(){
        con = koneksi.getConnection();
    }
    
    public ArrayList<poli> getAllPoli(){
        ArrayList<poli> lk = new ArrayList<>();
        try{
            String qSelect = "SELECT * FROM poli ORDER BY id_poli";
            pst = con.prepareStatement(qSelect);
            rs = pst.executeQuery();
            while(rs.next()){
                poli p = new poli();
                p.setId_poli(rs.getString("id_poli"));
                p.setNama_poli(rs.getString("nama_poli"));
                p.setId_dokter(rs.getString("id_dokter"));
                lk.add(p);
            }
        }
        catch(SQLException e){
            System.out.println("getAllAnggota() : " + e.getMessage());
        }
        return lk;
    }
    
    public poli getRecordById(String id) {
        poli p = new poli();
        String query = "SELECT * FROM poli WHERE id_poli = ?";
        try{
            pst = con.prepareStatement(query);
            pst.setString(1, id);
            rs = pst.executeQuery();
            if(rs.next()){
                p.setId_poli(rs.getString("id_poli"));
                p.setNama_poli(rs.getString("nama_poli"));  
                p.setId_dokter(rs.getString("id_dokter"));
            }
        }
        catch(SQLException e){
            System.out.println("getRecordByNoanggota() : " + e.getMessage());
        }
        return p;
    }
    
    public void insert(poli p, String page) {
        try{
            String query = "";
            String message="";
            if(page.equals("edit")){
                query = "UPDATE poli SET nama_poli=?, id_dokter=? WHERE id_poli=?";
                message="update";
            }
            else if(page.equals("tambah")){
                query = "INSERT INTO poli(nama_poli, id_dokter, id_poli) VALUES (?,?,?)";
                message="insert";
            }
            pst = con.prepareStatement(query);
            pst.setString(1, p.getNama_poli());
            pst.setString(2, p.getId_dokter());
            pst.setString(3, p.getId_poli());
            pst.executeUpdate();
            System.out.println(message + " success");
        }
        catch(SQLException e){
            System.out.println("insert() : " + e.getMessage());
        }
    }
    
    public void delete(String id) {
        try{
            String qDelete = "DELETE FROM poli WHERE id_poli=?";
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
        PoliDAO pd = new PoliDAO();
        System.out.println(pd.getAllPoli());
        poli p = new poli();
        p.setId_poli("PA001");
        p.setNama_poli("Poli Anak");   
        p.setId_dokter("DA001");
        pd.insert(p, "tambah");
//        pd.insert(d, "edit");
//        pd.delete("PA001");
    }
}
