/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package model;

/**
 *
 * @author atha
 */
public class bayarlayanan {
    private String id_bayar_layanan, id_layanan, id_pasien, tgl_layanan, keterangan;

    public String getId_bayar_layanan() {
        return id_bayar_layanan;
    }

    public void setId_bayar_layanan(String id_bayar_layanan) {
        this.id_bayar_layanan = id_bayar_layanan;
    }

    public String getId_layanan() {
        return id_layanan;
    }

    public void setId_layanan(String id_layanan) {
        this.id_layanan = id_layanan;
    }

    public String getId_pasien() {
        return id_pasien;
    }

    public void setId_pasien(String id_pasien) {
        this.id_pasien = id_pasien;
    }

    public String getTgl_layanan() {
        return tgl_layanan;
    }

    public void setTgl_layanan(String tgl_layanan) {
        this.tgl_layanan = tgl_layanan;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
