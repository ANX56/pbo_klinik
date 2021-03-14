/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import dao.DokterDAO;
import model.dokter;

/**
 *
 * @author atha
 */
@WebServlet(name = "DokterCTR", urlPatterns = {"/DokterCTR"})
public class DokterCTR extends HttpServlet {

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
        DokterDAO ad = new DokterDAO();
        Gson gson = new Gson();
        
        if(page == null){
            List<dokter> lk = ad.getAllDokter();
            String jsonDokter = gson.toJson(lk);
            out.println(jsonDokter);
        } 
        else if(page.equals("tambah")){
            String id = request.getParameter("noanggota");
            if(ad.getRecordById(id).getId_dokter() != null){
                response.setContentType("text/html;charset=UTF-8");
                out.print("Id Dokter : " + id + " - " + ad.getRecordById(id).getNama_dokter() + " sudah terpakai !");
            } else{
                dokter a = new dokter();
                a.setId_dokter(request.getParameter("id_dokter"));
                a.setNama_dokter(request.getParameter("nama_dokter"));
                a.setTgl_lahir(request.getParameter("tgl_lahir"));
                a.setId_poli(request.getParameter("id_poli"));
                a.setJenis_kelamin(request.getParameter("jenis_kelamin"));
                a.setAlamat(request.getParameter("alamat"));
                a.setNo_hp(request.getParameter("no_hp"));
                a.setNpwp(request.getParameter("npwp"));
                a.setNo_ktp(request.getParameter("no_ktp"));
                a.setEmail(request.getParameter("email"));
                a.setPassword(request.getParameter("password"));
                a.setWaktu(request.getParameter("waktu"));
                a.setId_user(request.getParameter("id_user"));
                ad.insert(a, page);
                response.setContentType("text/html;charset=UTF-8");
                out.print("Data berhasil disimpan");
            }
        }
        else if(page.equals("tampil")){
            String jsonDokter = gson.toJson(ad.getRecordById(request.getParameter("id_dokter")));
            response.setContentType("application/json");
            out.println(jsonDokter);
        }
        else if(page.equals("edit")){
            dokter a = new dokter();
            a.setId_dokter(request.getParameter("id_dokter"));
            a.setNama_dokter(request.getParameter("nama_dokter"));
            a.setTgl_lahir(request.getParameter("tgl_lahir"));
            a.setId_poli(request.getParameter("id_poli"));
            a.setJenis_kelamin(request.getParameter("jenis_kelamin"));
            a.setAlamat(request.getParameter("alamat"));
            a.setNo_hp(request.getParameter("no_hp"));
            a.setNpwp(request.getParameter("npwp"));
            a.setNo_ktp(request.getParameter("no_ktp"));
            a.setEmail(request.getParameter("email"));
            a.setPassword(request.getParameter("password"));
            a.setWaktu(request.getParameter("waktu"));
            a.setId_user(request.getParameter("id_user"));
            ad.insert(a, page);
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data berhasil disimpan");
        }
        else if(page.equals("hapus")){
            ad.delete(request.getParameter("id_dokter"));
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
