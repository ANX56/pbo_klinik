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
public class kamar {
    private int kapasitas;
    private String nama_kamar, deskirpsi, terisi, status, id_kamar, no_kamar;

    public int getKapasitas() {
        return kapasitas;
    }

    public void setKapasitas(int kapasitas) {
        this.kapasitas = kapasitas;
    }

    public String getDeskirpsi() {
        return deskirpsi;
    }

    public void setDeskirpsi(String deskirpsi) {
        this.deskirpsi = deskirpsi;
    }

    public String getTerisi() {
        return terisi;
    }

    public void setTerisi(String terisi) {
        this.terisi = terisi;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getId_kamar() {
        return id_kamar;
    }

    public void setId_kamar(String id_kamar) {
        this.id_kamar = id_kamar;
    }

    public String getNo_kamar() {
        return no_kamar;
    }

    public void setNo_kamar(String no_kamar) {
        this.no_kamar = no_kamar;
    }

    public String getNama_kamar() {
        return nama_kamar;
    }

    public void setNama_kamar(String nama_kamar) {
        this.nama_kamar = nama_kamar;
    }
}
