    /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.LayananDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.layanan;

/**
 *
 * @author atha
 */
@WebServlet(name = "LayananCTR", urlPatterns = {"/LayananCTR"})
public class LayananCTR extends HttpServlet {

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
        LayananDAO ad = new LayananDAO();
        Gson gson = new Gson();
        
        if(page == null){
            List<layanan> lk = ad.getAllLayanan();
            String json = gson.toJson(lk);
            out.println(json);
        } 
        else if(page.equals("tambah")){
            String id = request.getParameter("id_layanan");
            if(ad.getRecordById(id).getId_layanan() != null){
                response.setContentType("text/html;charset=UTF-8");
                out.print("ID Layanan : " + id + " - " + ad.getRecordById(id).getDes_layanan() + " sudah terpakai !");
            } else{
                layanan l = new layanan();
                l.setId_layanan(request.getParameter("id_layanan"));
                l.setDes_layanan(request.getParameter("des_layanan"));  
                l.setBiaya_layanan(Integer.parseInt(request.getParameter("biaya_layanan")));  
                l.setKeterangan(request.getParameter("keterangan"));
                ad.insert(l, page);
                response.setContentType("text/html;charset=UTF-8");
                out.print("Data berhasil disimpan");
            }
        }
        else if(page.equals("tampil")){
            String json = gson.toJson(ad.getRecordById(request.getParameter("id_layanan")));
            response.setContentType("application/json");
            out.println(json);
        }
        else if(page.equals("edit")){
            layanan l = new layanan();
            l.setId_layanan(request.getParameter("id_layanan"));
            l.setDes_layanan(request.getParameter("des_layanan"));  
            l.setBiaya_layanan(Integer.parseInt(request.getParameter("biaya_layanan")));  
            l.setKeterangan(request.getParameter("keterangan"));
            ad.insert(l, page);
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data berhasil disimpan");
        }
        else if(page.equals("hapus")){
            ad.delete(request.getParameter("id_layanan"));
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
