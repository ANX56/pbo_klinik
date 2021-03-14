/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.PoliDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.poli;

/**
 *
 * @author atha
 */
@WebServlet(name = "PoliCTR", urlPatterns = {"/PoliCTR"})
public class PoliCTR extends HttpServlet {

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
        PoliDAO ad = new PoliDAO();
        Gson gson = new Gson();
        
        if(page == null){
            List<poli> lk = ad.getAllPoli();
            String jsonPoli = gson.toJson(lk);
            out.println(jsonPoli);
        } 
        else if(page.equals("tambah")){
            String id = request.getParameter("id_poli");
            if(ad.getRecordById(id).getId_dokter() != null){
                response.setContentType("text/html;charset=UTF-8");
                out.print("Id Poli : " + id + " - " + ad.getRecordById(id).getNama_poli() + " sudah terpakai !");
            } else{
                poli p = new poli();
                p.setId_poli(request.getParameter("id_poli"));
                p.setNama_poli(request.getParameter("nama_poli"));
                p.setId_dokter(request.getParameter("id_dokter"));
                ad.insert(p, page);
                response.setContentType("text/html;charset=UTF-8");
                out.print("Data berhasil disimpan");
            }
        }
        else if(page.equals("tampil")){
            String jsonPoli = gson.toJson(ad.getRecordById(request.getParameter("id_poli")));
            response.setContentType("application/json");
            out.println(jsonPoli);
        }
        else if(page.equals("edit")){
            poli p = new poli();
            p.setId_poli(request.getParameter("id_poli"));
            p.setNama_poli(request.getParameter("nama_poli"));
            p.setId_dokter(request.getParameter("id_dokter"));
            ad.insert(p, page);
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data berhasil disimpan");
        }
        else if(page.equals("hapus")){
            ad.delete(request.getParameter("id_poli"));
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
