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
import model.pasien;

/**
 *
 * @author atha
 */
public class PasienDAO {
    private final Connection con;
    private PreparedStatement pst;
    private ResultSet rs;
    private SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy");
    
    public PasienDAO(){
        con = koneksi.getConnection();
    }
    
    public ArrayList<pasien> getAllPasien(){
        ArrayList<pasien> lk = new ArrayList<>();
        try{
            String qSelect = "SELECT * FROM pasien ORDER BY rm";
            pst = con.prepareStatement(qSelect);
            rs = pst.executeQuery();
            while(rs.next()){
                pasien p = new pasien();
                p.setRm(rs.getString("rm"));
                p.setNoidentitas(rs.getString("noidentitas"));
                p.setNama(rs.getString("nama"));
                if(rs.getString("jk") != null){                    
                    if((rs.getString("jk")).equals("L"))
                        p.setJk("Laki-Laki");
                    else p.setJk("Perempuan");
                } else p.setJk("");
                if(rs.getString("tmplahir") != null){                      
                    p.setTmplahir(rs.getString("tmplahir"));
                } else p.setTmplahir("");
                if(rs.getString("tgllahir") != null){                      
                    String tgl = sdf.format(rs.getDate("tgllahir"));
                    p.setTgllahir(tgl);
                } else p.setTgllahir("");
                if(rs.getString("alamat") != null){                      
                    p.setAlamat(rs.getString("alamat"));
                } else p.setAlamat("");
                if(rs.getString("nohp") != null){          
                    p.setNohp(rs.getString("nohp"));
                } else p.setNohp("");
                p.setStatus(rs.getString("status"));
                p.setPekerjaan(rs.getString("pekerjaan"));
                lk.add(p);
            }
        }
        catch(SQLException e){
            System.out.println("getAllPasien() : " + e.getMessage());
        }
        return lk;
    }
    
    public pasien getRecordByNoRM(String rm) {
        pasien p = new pasien();
        String query = "SELECT * FROM pasien WHERE rm = ?";
        try{
            pst = con.prepareStatement(query);
            pst.setString(1, rm);
            rs = pst.executeQuery();
            if(rs.next()){
                p.setNoidentitas(rs.getString("noidentitas"));
                p.setNama(rs.getString("nama"));
                p.setJk(rs.getString("jk"));            
                p.setTmplahir(rs.getString("tmplahir"));          
                p.setTgllahir(rs.getString("tgllahir"));
                p.setAlamat(rs.getString("alamat"));
                p.setNohp(rs.getString("nohp"));
                p.setStatus(rs.getString("status")); 
                p.setPekerjaan(rs.getString("pekerjaan")); 
            }
        }
        catch(SQLException e){
            System.out.println("getRecordByNoRM() : " + e.getMessage());
        }
        return p;
    }
    
    public void insert(pasien a, String page) {
        try{
            String query = "";
            if(page.equals("edit")){
                query = "UPDATE pasien SET noidentitas=?, nama=?, jk=?, tmplahir=?, tgllahir=?, alamat=?, nohp=?, status=?, pekerjaan=? WHERE rm=?";
            }
            else if(page.equals("tambah")){
                query = "INSERT INTO pasien(noidentitas, nama, jk, tmplahir, tgllahir, alamat, nohp, status, pekerjaan, rm) VALUES (?,?,?,?,?,?,?,?,?,?)";
            }
            pst = con.prepareStatement(query);
            pst.setString(1, a.getNoidentitas());
            pst.setString(2, a.getNama());
            if(a.getJk().equals("")) pst.setString(3, null);
            else pst.setString(3, a.getJk());
            pst.setString(4, a.getTmplahir());
            if(a.getTgllahir().equals("")) pst.setString(5, null);
            else pst.setString(5, a.getTgllahir());
            pst.setString(6, a.getAlamat());
            pst.setString(7, a.getNohp());
            pst.setString(8, a.getStatus());
            pst.setString(9, a.getPekerjaan());
            pst.setString(10, a.getRm());
            pst.executeUpdate();
            System.out.println("insert or update success");
        }
        catch(SQLException e){
            System.out.println("insert() : " + e.getMessage());
        }
    }
    
    public void delete(String rm) {
        try{
            String qDelete = "DELETE FROM pasien WHERE rm=?";
            pst = con.prepareStatement(qDelete);
            pst.setString(1, rm);
            pst.executeUpdate();
            System.out.println("delete success");
        }
        catch(SQLException e){
            System.out.println("delete() : " + e.getMessage());
        }
    }
    
    public static void main(String[] args) {
        PasienDAO ad = new PasienDAO();
        System.out.println(ad.getAllPasien());
        pasien a = new pasien();
//        a.setRm("191417");
//        a.setNoidentitas("3175081105031002");
//        a.setNama("Athallah Rizaldi");   
//        a.setJk("L");         
//        a.setTmplahir("Jakarta");          
//        a.setTgllahir("2003-05-11");
//        a.setAlamat("Jl. Hias No. 35");
//        a.setNohp("085691645955");
//        a.setStatus("belum");
//        a.setPekerjaan("Pelajar");
//        ad.insert(a, "tambah");
//        ad.insert(a, "edit");
//        ad.delete("191417");
    }
}
