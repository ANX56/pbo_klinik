/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.KaryawanDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.karyawan;

/**
 *
 * @author atha
 */
@WebServlet(name = "KaryawanCTR", urlPatterns = {"/KaryawanCTR"})
public class KaryawanCTR extends HttpServlet {

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
        KaryawanDAO ad = new KaryawanDAO();
        Gson gson = new Gson();
        
        if(page == null){
            List<karyawan> lk = ad.getAllKaryawan();
            String jsonKaryawan = gson.toJson(lk);
            out.println(jsonKaryawan);
        } 
        else if(page.equals("tambah")){
            String id = request.getParameter("id_karyawan");
            if(ad.getRecordById(id).getId_karyawan() != null){
                response.setContentType("text/html;charset=UTF-8");
                out.print("Id Karyawan : " + id + " - " + ad.getRecordById(id).getNama_karyawan() + " sudah terpakai !");
            } else{
                karyawan k = new karyawan();
                k.setId_karyawan(request.getParameter("id_karyawan"));
                k.setNama_karyawan(request.getParameter("nama_karyawan"));
                k.setTgl_lahir(request.getParameter("tgl_lahir"));
                k.setBidang_pekerjaan(request.getParameter("bidang_pekerjaan"));
                k.setJenis_kelamin(request.getParameter("jenis_kelamin"));
                k.setAlamat(request.getParameter("alamat"));
                k.setNo_hp(request.getParameter("no_hp"));
                k.setNo_ktp(request.getParameter("no_ktp"));
                k.setNpwp(request.getParameter("npwp"));
                k.setEmail(request.getParameter("email"));
                k.setPassword(request.getParameter("password"));
                k.setWaktu(request.getParameter("waktu"));
                k.setId_user(request.getParameter("id_user"));
                ad.insert(k, page);
                response.setContentType("text/html;charset=UTF-8");
                out.print("Data berhasil disimpan");
            }
        }
        else if(page.equals("tampil")){
            String jsonKaryawan = gson.toJson(ad.getRecordById(request.getParameter("id_karyawan")));
            response.setContentType("application/json");
            out.println(jsonKaryawan);
        }
        else if(page.equals("edit")){
            karyawan k = new karyawan();
            k.setId_karyawan(request.getParameter("id_karyawan"));
            k.setNama_karyawan(request.getParameter("nama_karyawan"));
            k.setTgl_lahir(request.getParameter("tgl_lahir"));
            k.setBidang_pekerjaan(request.getParameter("bidang_pekerjaan"));
            k.setJenis_kelamin(request.getParameter("jenis_kelamin"));
            k.setAlamat(request.getParameter("alamat"));
            k.setNo_hp(request.getParameter("no_hp"));
            k.setNo_ktp(request.getParameter("no_ktp"));
            k.setNpwp(request.getParameter("npwp"));
            k.setEmail(request.getParameter("email"));
            k.setPassword(request.getParameter("password"));
            k.setWaktu(request.getParameter("waktu"));
            k.setId_user(request.getParameter("id_user"));
            ad.insert(k, page);
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data berhasil disimpan");
        }
        else if(page.equals("hapus")){
            ad.delete(request.getParameter("id_karyawan"));
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
