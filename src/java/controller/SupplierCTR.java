/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package controller;

import com.google.gson.Gson;
import dao.SupplierDAO;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import model.supplier;

/**
 *
 * @author atha
 */
@WebServlet(name = "SupplierCTR", urlPatterns = {"/SupplierCTR"})
public class SupplierCTR extends HttpServlet {

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
        SupplierDAO ad = new SupplierDAO();
        Gson gson = new Gson();
        
        if(page == null){
            List<supplier> lk = ad.getAllSupplier();
            String json = gson.toJson(lk);
            out.println(json);
        } 
        else if(page.equals("tambah")){
            String id = request.getParameter("id_supplier");
            if(ad.getRecordById(id).getId_supplier() != null){
                response.setContentType("text/html;charset=UTF-8");
                out.print("Id Supplier : " + id + " - " + ad.getRecordById(id).getNama_supplier() + " sudah terpakai !");
            } else{
                supplier s = new supplier();
                s.setId_supplier(request.getParameter("id_supplier"));
                s.setNama_supplier(request.getParameter("nama_supplier"));
                s.setAlamat(request.getParameter("alamat"));
                s.setNo_hp(request.getParameter("no_hp"));
                s.setEmail(request.getParameter("email"));
                s.setPassword(request.getParameter("password"));
                s.setWaktu(request.getParameter("waktu"));
                s.setId_user(request.getParameter("id_user"));
                ad.insert(s, page);
                response.setContentType("text/html;charset=UTF-8");
                out.print("Data berhasil disimpan");
            }
        }
        else if(page.equals("tampil")){
            String json = gson.toJson(ad.getRecordById(request.getParameter("id_supplier")));
            response.setContentType("application/json");
            out.println(json);
        }
        else if(page.equals("edit")){
            supplier s = new supplier();
            s.setId_supplier(request.getParameter("id_supplier"));
            s.setNama_supplier(request.getParameter("nama_supplier"));
            s.setAlamat(request.getParameter("alamat"));
            s.setNo_hp(request.getParameter("no_hp"));
            s.setEmail(request.getParameter("email"));
            s.setPassword(request.getParameter("password"));
            s.setWaktu(request.getParameter("waktu"));
            s.setId_user(request.getParameter("id_user"));
            ad.insert(s, page);
            response.setContentType("text/html;charset=UTF-8");
            out.print("Data berhasil disimpan");
        }
        else if(page.equals("hapus")){
            ad.delete(request.getParameter("id_supplier"));
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
