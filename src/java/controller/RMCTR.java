/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.RMDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.rm;

/**
 *
 * @author atha
 */
@WebServlet(name = "RMCTR", urlPatterns = {"/RMCTR"})
public class RMCTR extends HttpServlet {

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
        RMDAO rm = new RMDAO();
        Gson gson = new Gson();
        
        if(page == null){
            List<rm> lk = rm.getAllRM();
            String jsonRM = gson.toJson(lk);
            out.println(jsonRM);
        } 
        else if(page.equals("tambah")){
            String id = request.getParameter("nomor_rm");
            if(rm.getRecordById(id).getNomor_rm() != null){
                response.setContentType("text/html;charset=UTF-8");
                out.print("Nomor RM : " + id + " - " + rm.getRecordById(id).getId_pasien() + " sudah terpakai !");
            } else{
                rm r = new rm();
                r.setNomor_rm(request.getParameter("nomor_rm"));
                r.setId_pasien(request.getParameter("id_pasien"));
                r.setTgl_daftar(request.getParameter("tgl_daftar"));
                r.setId_poli(request.getParameter("id_poli"));
                r.setId_dokter(request.getParameter("id_dokter"));
                r.setBerat(Integer.parseInt(request.getParameter("berat")));
                r.setTinggi(Integer.parseInt(request.getParameter("tinggi")));
                r.setTensi(request.getParameter("tensi"));
                r.setDiagnosa(request.getParameter("diagnosa"));
                r.setId_resep(request.getParameter("id_resep"));
                r.setWaktu(request.getParameter("waktu"));
                rm.insert(r, page);
                response.setContentType("text/html;charset=UTF-8");
                out.print("Data berhasil disimpan");
            }
        }
        else if(page.equals("tampil")){
            String jsonRM = gson.toJson(rm.getRecordById(request.getParameter("nomor_rm")));
            response.setContentType("application/json");
            out.println(jsonRM);
        }
        else if(page.equals("edit")){
            rm r = new rm();
            r.setNomor_rm(request.getParameter("nomor_rm"));
            r.setId_pasien(request.getParameter("id_pasien"));
            r.setTgl_daftar(request.getParameter("tgl_daftar"));
            r.setId_poli(request.getParameter("id_poli"));
            r.setId_dokter(request.getParameter("id_dokter"));
            r.setBerat(Integer.parseInt(request.getParameter("berat")));
            r.setTinggi(Integer.parseInt(request.getParameter("tinggi")));
            r.setTensi(request.getParameter("tensi"));
            r.setDiagnosa(request.getParameter("diagnosa"));
            r.setId_resep(request.getParameter("id_resep"));
            r.setWaktu(request.getParameter("waktu"));
            rm.insert(r, page);
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data berhasil disimpan");
        }
        else if(page.equals("hapus")){
            rm.delete(request.getParameter("nomor_rm"));
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data berhasil dihapus");
        }
    }

    // <editor-fold defaultstate="collapsed" desc="HttpServlet methods. Click on the + sign on the left to edit the code.">
    /**
     * Handles the HTTP <code>GET</code> methor.
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
     * Handles the HTTP <code>POST</code> methor.
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
