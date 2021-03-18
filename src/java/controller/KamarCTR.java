/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.KamarDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.kamar;

/**
 *
 * @author atha
 */
@WebServlet(name = "KamarCTR", urlPatterns = {"/KamarCTR"})
public class KamarCTR extends HttpServlet {

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
        KamarDAO ad = new KamarDAO();
        Gson gson = new Gson();
        
        if(page == null){
            List<kamar> lk = ad.getAllKamar();
            String jsonDokter = gson.toJson(lk);
            out.println(jsonDokter);
        } 
        else if(page.equals("tambah")){
            String id = request.getParameter("id_kamar");
            if(ad.getRecordById(id).getId_kamar() != null){
                response.setContentType("text/html;charset=UTF-8");
                out.print("Id Dokter : " + id + " - " + ad.getRecordById(id).getNama_kamar() + " sudah terpakai !");
            } else{
                kamar r = new kamar();
                r.setId_kamar(request.getParameter("id_kamar"));
                r.setNama_kamar(request.getParameter("nama_ruang"));
                r.setNo_kamar(request.getParameter("no_ruang"));
                r.setKelas(request.getParameter("kelas"));
                r.setDeskirpsi(request.getParameter("des_kamar"));
                r.setKapasitas(Integer.parseInt(request.getParameter("kapasitas")));
                r.setTerisi(request.getParameter("terisi"));
                r.setStatus(request.getParameter("status"));
                ad.insert(r, page);
                response.setContentType("text/html;charset=UTF-8");
                out.print("Data berhasil disimpan");
            }
        }
        else if(page.equals("tampil")){
            String jsonDokter = gson.toJson(ad.getRecordById(request.getParameter("id_kamar")));
            response.setContentType("application/json");
            out.println(jsonDokter);
        }
        else if(page.equals("edit")){
            kamar r = new kamar();
            r.setId_kamar(request.getParameter("id_kamar"));
            r.setNama_kamar(request.getParameter("nama_ruang"));
            r.setNo_kamar(request.getParameter("no_ruang"));
            r.setKelas(request.getParameter("kelas"));
            r.setDeskirpsi(request.getParameter("des_kamar"));
            r.setKapasitas(Integer.parseInt(request.getParameter("kapasitas")));
            r.setTerisi(request.getParameter("terisi"));
            r.setStatus(request.getParameter("status"));
            ad.insert(r, page);
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data berhasil disimpan");
        }
        else if(page.equals("hapus")){
            ad.delete(request.getParameter("id_kamar"));
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
