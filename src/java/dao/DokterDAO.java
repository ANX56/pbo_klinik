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
                d.setId(rs.getString("id_dokter"));
                d.setNama(rs.getString("nama"));
                d.setPoli(rs.getString("poli"));
                if(rs.getString("jk") != null){                    
                    if((rs.getString("jk")).equals("L"))
                        d.setJk("Laki-Laki");
                    else d.setJk("Perempuan");
                } else d.setJk("");
                if(rs.getString("tgllahir") != null){                      
                    String tgl = sdf.format(rs.getDate("tgllahir"));
                    d.setTgllahir(tgl);
                } else d.setTgllahir("");
                if(rs.getString("alamat") != null){                      
                    d.setAlamat(rs.getString("alamat"));
                } else d.setAlamat("");
                if(rs.getString("nohp") != null){          
                    d.setNohp(rs.getString("nohp"));
                } else d.setNohp("");
                d.setJadwal(rs.getString("jadwal"));
                lk.add(d);
            }
        }
        catch(SQLException e){
            System.out.println("getAllAnggota() : " + e.getMessage());
        }
        return lk;
    }
    
    public dokter getRecordByNoRM(String nik) {
        dokter k = new dokter();
        String query = "SELECT * FROM dokter WHERE id_dokter = ?";
        try{
            pst = con.prepareStatement(query);
            pst.setString(1, nik);
            rs = pst.executeQuery();
            if(rs.next()){
                k.setId(rs.getString("id_dokter"));
                k.setNama(rs.getString("nama"));  
                k.setPoli(rs.getString("poli"));  
                k.setJk(rs.getString("jk"));                 
                k.setTgllahir(rs.getString("tgllahir"));   
                k.setAlamat(rs.getString("alamat"));
                k.setNohp(rs.getString("nohp"));
                k.setJadwal(rs.getString("jadwal"));
            }
        }
        catch(SQLException e){
            System.out.println("getRecordByNoanggota() : " + e.getMessage());
        }
        return k;
    }
    
    public void insert(dokter a, String page) {
        try{
            String query = "";
            if(page.equals("edit")){
                query = "UPDATE dokter SET nama=?, poli=?, jk=?, tgllahir=?, alamat=?, nohp=?, jadwal=? WHERE id_dokter=?";
            }
            else if(page.equals("tambah")){
                query = "INSERT INTO dokter(nama, poli, jk, tgllahir, alamat, nohp, jadwal, id_dokter) VALUES (?,?,?,?,?,?,?,?)";
            }
            pst = con.prepareStatement(query);
            pst.setString(1, a.getNama());
            pst.setString(2, a.getPoli());
            if(a.getJk().equals("")) pst.setString(3, null);
            else pst.setString(3, a.getJk());
            if(a.getTgllahir().equals("")) pst.setString(4, null);
            else pst.setString(4, a.getTgllahir());
            pst.setString(5, a.getAlamat());
            pst.setString(6, a.getNohp());
            pst.setString(7, a.getJadwal());
            pst.setString(8, a.getId());
            pst.executeUpdate();
            System.out.println("insert or update success");
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
//        d.setId("DU001");
//        d.setNama("dr. Ishak Husein");   
//        d.setPoli("Umum");        
//        d.setJk("L");      
//        d.setTgllahir("1997-07-18");
//        d.setAlamat("Komplek Timah Blok DD.5 No.3");
//        d.setNohp("083455767783");
//        d.setJadwal("Senin - Kamis | 08.00 - 15.00");
//        ad.insert(d, "tambah");
//        ad.insert(d, "edit");
//        ad.delete("DU001");
    }
}
