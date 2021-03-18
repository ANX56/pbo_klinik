/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.BayarLayananDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bayarlayanan;

/**
 *
 * @author atha
 */
@WebServlet(name = "BayarLayananCTR", urlPatterns = {"/BayarLayananCTR"})
public class BayarLayananCTR extends HttpServlet {

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
        BayarLayananDAO ad = new BayarLayananDAO();
        Gson gson = new Gson();
        
        if(page == null){
            List<bayarlayanan> lk = ad.getAllBayarLayanan();
            String jsonDokter = gson.toJson(lk);
            out.println(jsonDokter);
        }
        else if(page.equals("tambah")){
            String id = request.getParameter("id_bayar_layanan");
            if(ad.getRecordById(id).getId_bayar_layanan() != null){
                response.setContentType("text/html;charset=UTF-8");
                out.print("ID Bayar Layanan : " + id + " sudah terpakai !");
            } else{
                bayarlayanan a = new bayarlayanan();
                a.setId_bayar_layanan(request.getParameter("id_bayar_layanan"));
                a.setId_layanan(request.getParameter("id_layanan"));
                a.setId_pasien(request.getParameter("id_pasien"));
                a.setTgl_daftar(request.getParameter("tgl_daftar"));
                a.setHarga(Double.parseDouble(request.getParameter("harga")));
                a.setJenis_pembayaran(request.getParameter("jenis_pembayaran"));
                a.setStatus(request.getParameter("status"));
                a.setWaktu(request.getParameter("waktu"));
                ad.insert(a, page);
                response.setContentType("text/html;charset=UTF-8");
                out.print("Data berhasil disimpan");
            }
        }
        else if(page.equals("tampil")){
            String jsonDokter = gson.toJson(ad.getRecordById(request.getParameter("id_bayar_layanan")));
            response.setContentType("application/json");
            out.println(jsonDokter);
        }
        else if(page.equals("edit")){
            bayarlayanan a = new bayarlayanan();
            a.setId_bayar_layanan(request.getParameter("id_bayar_layanan"));
            a.setId_layanan(request.getParameter("id_layanan"));
            a.setId_pasien(request.getParameter("id_pasien"));
            a.setTgl_daftar(request.getParameter("tgl_daftar"));
            a.setHarga(Double.parseDouble(request.getParameter("harga")));
            a.setJenis_pembayaran(request.getParameter("jenis_pembayaran"));
            a.setStatus(request.getParameter("status"));
            a.setWaktu(request.getParameter("waktu"));
            ad.insert(a, page);
            response.setContentType("text/html;charset=UTF-8");
            out.print("Pembayaran Berhasil");
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
