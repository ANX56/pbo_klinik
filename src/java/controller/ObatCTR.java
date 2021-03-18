/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.ObatDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.obat;

/**
 *
 * @author atha
 */
@WebServlet(name = "ObatCTR", urlPatterns = {"/ObatCTR"})
public class ObatCTR extends HttpServlet {

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
        ObatDAO ad = new ObatDAO();
        Gson gson = new Gson();
        
        if(page == null){
            List<obat> lk = ad.getAllObat();
            String jsonDokter = gson.toJson(lk);
            out.println(jsonDokter);
        } 
        else if(page.equals("tambah")){
            String id = request.getParameter("id_obat");
            if(ad.getRecordById(id).getId_obat() != null){
                response.setContentType("text/html;charset=UTF-8");
                out.print("Id Obat : " + id + " - " + ad.getRecordById(id).getNama_obat() + " sudah terpakai !");
            } else{
                obat o = new obat();
                o.setId_obat(request.getParameter("id_obat"));
                o.setNama_obat(request.getParameter("nama_obat"));  
                o.setSatuan(request.getParameter("satuan"));  
                o.setStok(Integer.parseInt(request.getParameter("stok")));                 
                o.setHarga_jual(Integer.parseInt(request.getParameter("harga_jual")));   
                o.setNo_faktur(request.getParameter("no_faktur"));
                o.setId_user(request.getParameter("id_user"));
                ad.insert(o, page);
                response.setContentType("text/html;charset=UTF-8");
                out.print("Data berhasil disimpan");
            }
        }
        else if(page.equals("tampil")){
            String jsonDokter = gson.toJson(ad.getRecordById(request.getParameter("id_obat")));
            response.setContentType("application/json");
            out.println(jsonDokter);
        }
        else if(page.equals("edit")){
            obat o = new obat();
            o.setId_obat(request.getParameter("id_obat"));
            o.setNama_obat(request.getParameter("nama_obat"));  
            o.setSatuan(request.getParameter("satuan"));  
            o.setStok(Integer.parseInt(request.getParameter("stok")));                 
            o.setHarga_jual(Integer.parseInt(request.getParameter("harga_jual")));   
            o.setNo_faktur(request.getParameter("no_faktur"));
            o.setId_user(request.getParameter("id_user"));
            ad.insert(o, page);
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data berhasil disimpan");
        }
        else if(page.equals("hapus")){
            ad.delete(request.getParameter("id_obat"));
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
