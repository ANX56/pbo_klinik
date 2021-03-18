/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.BayarObatDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.bayarobat;

/**
 *
 * @author atha
 */
@WebServlet(name = "BayarObatCTR", urlPatterns = {"/BayarObatCTR"})
public class BayarObatCTR extends HttpServlet {

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
        BayarObatDAO ad = new BayarObatDAO();
        Gson gson = new Gson();
        
        if(page == null){
            List<bayarobat> lk = ad.getAllBayarObat();
            String jsonDokter = gson.toJson(lk);
            out.println(jsonDokter);
        }
        else if(page.equals("tambah")){
            String id = request.getParameter("id_bayar_layanan");
            if(ad.getRecordById(id).getId_bayar_obat() != null){
                response.setContentType("text/html;charset=UTF-8");
                out.print("ID Bayar Obat : " + id + " sudah terpakai !");
            } else{
                bayarobat d = new bayarobat();
                d.setId_bayar_obat(request.getParameter("id_bayar_obat"));
                d.setId_obat(request.getParameter("id_obat"));
                d.setId_pasien(request.getParameter("id_pasien"));
                d.setId_resep(request.getParameter("id_resep"));
                d.setJumlah(Integer.parseInt(request.getParameter("jumlah")));
                d.setHarga(Double.parseDouble(request.getParameter("harga")));
                d.setJenis_pembayaran(request.getParameter("jenis_pembayaran"));
                d.setStatus(request.getParameter("status"));
                d.setWaktu(request.getParameter("waktu"));
                ad.insert(d, page);
                response.setContentType("text/html;charset=UTF-8");
                out.print("Data berhasil disimpan");
            }
        }
        else if(page.equals("tampil")){
            String jsonDokter = gson.toJson(ad.getRecordById(request.getParameter("id_bayar_obat")));
            response.setContentType("application/json");
            out.println(jsonDokter);
        }
        else if(page.equals("edit")){
            bayarobat d = new bayarobat();
            d.setId_bayar_obat(request.getParameter("id_bayar_obat"));
            d.setId_obat(request.getParameter("id_obat"));
            d.setId_pasien(request.getParameter("id_pasien"));
            d.setId_resep(request.getParameter("id_resep"));
            d.setJumlah(Integer.parseInt(request.getParameter("jumlah")));
            d.setHarga(Double.parseDouble(request.getParameter("harga")));
            d.setJenis_pembayaran(request.getParameter("jenis_pembayaran"));
            d.setStatus(request.getParameter("status"));
            d.setWaktu(request.getParameter("waktu"));
            ad.insert(d, page);
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
