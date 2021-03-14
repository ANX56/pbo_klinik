/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.PasienDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.pasien;

/**
 *
 * @author atha
 */
@WebServlet(name = "PasienCTR", urlPatterns = {"/PasienCTR"})
public class PasienCTR extends HttpServlet {

    /**
     * Processes requests for both HTTP <code>GET</code> and <code>POST</code>
     * methods.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    protected void processRequest(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        response.setContentType("text/html;charset=UTF-8");
        
        String page = request.getParameter("page");
        PrintWriter out = response.getWriter();
        PasienDAO ad = new PasienDAO();
        Gson gson = new Gson();
        
        if(page == null){
            List<pasien> lk = ad.getAllPasien();
            String jsonPasien = gson.toJson(lk);
            out.println(jsonPasien);
        } 
        else if(page.equals("tambah")){
            String id = request.getParameter("noanggota");
            if(ad.getRecordById(id).getId_pasien() != null){
                response.setContentType("text/html;charset=UTF-8");
                out.print("Id Pasien : " + id + " - " + ad.getRecordById(id).getNama_pasien() + " sudah terpakai !");
            } else{
                pasien p = new pasien();
                p.setId_pasien(request.getParameter("id_pasien"));
                p.setNama_pasien(request.getParameter("nama_pasien"));
                p.setTgl_lahir(request.getParameter("tgl_lahir"));
                p.setJenis_kelamin(request.getParameter("jenis_kelamin"));
                p.setNo_ktp(request.getParameter("no_ktp"));
                p.setAlamat(request.getParameter("alamat"));
                p.setNo_hp(request.getParameter("no_hp"));
                p.setGol_darah(request.getParameter("gol_darah"));
                p.setPassword(request.getParameter("password"));
                p.setId_user(request.getParameter("id_user"));
                ad.insert(p, page);
                response.setContentType("text/html;charset=UTF-8");
                out.print("Data berhasil disimpan");
            }
        }
        else if(page.equals("tampil")){
            String jsonPasien = gson.toJson(ad.getRecordById(request.getParameter("id_pasien")));
            response.setContentType("application/json");
            out.println(jsonPasien);
        }
        else if(page.equals("edit")){
            pasien p = new pasien();
            p.setId_pasien(request.getParameter("id_pasien"));
            p.setNama_pasien(request.getParameter("nama_pasien"));
            p.setTgl_lahir(request.getParameter("tgl_lahir"));
            p.setJenis_kelamin(request.getParameter("jenis_kelamin"));
            p.setNo_ktp(request.getParameter("no_ktp"));
            p.setAlamat(request.getParameter("alamat"));
            p.setNo_hp(request.getParameter("no_hp"));
            p.setGol_darah(request.getParameter("gol_darah"));
            p.setPassword(request.getParameter("password"));
            p.setId_user(request.getParameter("id_user"));
            ad.insert(p, page);
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data berhasil disimpan");
        }
        else if(page.equals("hapus")){
            ad.delete(request.getParameter("id_pasien"));
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data berhasil dihapus");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doGet(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Handles the HTTP <code>POST</code> method.
     *
     * @param request servlet request
     * @param response servlet response
     * @throws ServletException if a servlet-specific error occurs
     * @throws IOException if an I/O error occurs
     */
    @Override
    protected void doPost(HttpServletRequest request, HttpServletResponse response)
            throws ServletException, IOException {
        processRequest(request, response);
    }

    /**
     * Returns a short description of the servlet.
     *
     * @return a String containing servlet description
     */
    @Override
    public String getServletInfo() {
        return "Short description";
    }// </editor-fold>

}
