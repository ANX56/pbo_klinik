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
public class rawatinap {
    private String id_rawat, id_pasien, id_kamar, tgl_check_in, tgl_check_out, keterangan;

    public String getId_rawat() {
        return id_rawat;
    }

    public void setId_rawat(String id_rawat) {
        this.id_rawat = id_rawat;
    }

    public String getId_pasien() {
        return id_pasien;
    }

    public void setId_pasien(String id_pasien) {
        this.id_pasien = id_pasien;
    }

    public String getId_kamar() {
        return id_kamar;
    }

    public void setId_kamar(String id_kamar) {
        this.id_kamar = id_kamar;
    }

    public String getTgl_check_in() {
        return tgl_check_in;
    }

    public void setTgl_check_in(String tgl_check_in) {
        this.tgl_check_in = tgl_check_in;
    }

    public String getTgl_check_out() {
        return tgl_check_out;
    }

    public void setTgl_check_out(String tgl_check_out) {
        this.tgl_check_out = tgl_check_out;
    }

    public String getKeterangan() {
        return keterangan;
    }

    public void setKeterangan(String keterangan) {
        this.keterangan = keterangan;
    }
}
