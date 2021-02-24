/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author Administrator
 */
public class layanan {
    private String id_layanan, des_layanan, keterangan;
    private int biaya_layanan;

    public String getId_layanan() {
        return id_layanan;
    }

    public void setId_layanan(String id_layanan) {
        this.id_layanan = id_layanan;
    }

    public String getDes_layanan() {
        return des_layanan;
    }

    public void setDes_layanan(String des_layanan) {
        this.des_layanan = des_layanan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }

    public int getBiaya_layanan() {
        return biaya_layanan;
    }

    public void setBiaya_layanan(int biaya_layanan) {
        this.biaya_layanan = biaya_layanan;
    }
}
