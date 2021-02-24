/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author hadit
 */
public class supplier {
    private String id_sup,nama_sup, email, alamat, notelp, id_user;

    public String getId_user() {
        return id_user;
    }

    public void setId_user(String id_user) {
        this.id_user = id_user;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getNama_sup() {
        return nama_sup;
    }

    public void setNama_sup(String nama_sup) {
        this.nama_sup = nama_sup;
    }

    public String getAlamat() {
        return alamat;
    }

    public void setAlamat(String alamat) {
        this.alamat = alamat;
    }

    public String getId_sup() {
        return id_sup;
    }

    public void setId_sup(String id_sup) {
        this.id_sup = id_sup;
    }

    public String getNotelp() {
        return notelp;
    }

    public void setNotelp(String notelp) {
        this.notelp = notelp;
    }
    
}
