$(document).ready(function(){
    //deklarasi variabel
    var id_bayar_layanan, id_layanan, id_pasien, tgl_daftar, harga, jenis_pembayaran, status, waktu, page;
    
    //dapetin value dari setiap input
    function getInputValue(){
        id_bayar_layanan = $("#id_bayar_layanan").val();
        id_layanan = $("#id_layanan").val();
        id_pasien = $("#id_pasien").val();
        tgl_daftar = $("#tgl_daftar").val();
        harga = $("#harga").val();
        jenis_pembayaran = $("#jenis_pembayaran").val();
        status = $("#status").val();
        waktu = $("#waktu").val();
    }
    
    function formatRupiah(angka, prefix) {
        var number_string = angka.replace(/[^,\d]/g, "").toString(),
        split = number_string.split(","),
        sisa = split[0].length % 3,
        rupiah = split[0].substr(0, sisa),
        ribuan = split[0].substr(sisa).match(/\d{3}/gi);

        // tambahkan titik jika yang di input sudah menjadi angka ribuan
        if (ribuan) {
            separator = sisa ? "." : "";
            rupiah += separator + ribuan.join(".");
        }

        rupiah = split[1] !== undefined ? rupiah + "," + split[1] : rupiah;
        return prefix === undefined ? rupiah : rupiah ? "Rp. " + rupiah : "";
    }
    
    $.ajax ({
        url: "/Klinik/BayarLayananCTR",
        method: "GET",
        dataType: "json",
        success:
            function(data){
                $("#tabelBayar").dataTable({
                    serverside: true,
                    processing: true,
                    data: data,
                    sort: true,
                    searching: true,
                    paging: true,
                    columns: [
                        {'data': 'id_bayar_layanan', 'name': 'id_bayar_layanan', 'type': 'string'},
                        {'data': 'id_layanan'},
                        {'data': 'id_pasien'},
                        {'data': 'tgl_daftar'},
                        {'data': 'harga'},
                        {'data': 'jenis_pembayaran'},
                        {'data': null, 'className': 'dt-right',
                            'mRender' : function(o){
                                if(o.status === "belum"){
                                    return "<a class= 'btn btn-outline-success btn-sm'" 
                                    + " id='btnDetail'>Detail</a>"
                                    + " <a class='btn btn-outline-danger btn-sm'"
                                    + " id='btnBayar'>Bayar</a>";                                    
                                } else {
                                    return "<a class= 'btn btn-outline-success btn-sm'" 
                                    + " id='btnDetail'>Detail</a>";                                    
                                }
                            }
                        }
                    ]
                }); 
            }
    });
    
    $("#tabelBayar tbody").on('click', '#btnDetail', function(){
        $("#myModal").show();
        page = "tampil";
        
        //dapetin baris yang di klik
        var baris = $(this).closest('tr');
        var id_bayar_layanan = baris.find("td:eq(0)").text();
        $.post("/Klinik/BayarLayananCTR",
            {
                page: page,
                id_bayar_layanan: id_bayar_layanan
            },
            function(data, status){
                console.log(formatRupiah(data.harga.toString(), "Rp. "));
                let d = new Date(new Date().toString().split('GMT')[0]+' UTC').toISOString().split('.')[0];
                $("#id_bayar_layanan").val(data.id_bayar_layanan);
                $("#id_layanan").val(data.id_layanan);
                $("#id_pasien").val(data.id_pasien);
                $("#tgl_daftar").val(data.tgl_daftar);
                $("#harga").val(formatRupiah(data.harga.toString(), "Rp."));
                $("#jenis_pembayaran").val(data.jenis_pembayaran);
            }
        );
        page = "tampil";
    });
    
    $("#tabelBayar tbody").on('click', '#btnDetail', function(){
        $("#myModal").show();
        page = "tampil";
        
        //dapetin baris yang di klik
        var baris = $(this).closest('tr');
        var id_bayar_layanan = baris.find("td:eq(0)").text();
        $.post("/Klinik/BayarLayananCTR",
            {
                page: page,
                id_bayar_layanan: id_bayar_layanan
            },
            function(data, status){
                $("#id_bayar_layanan").val(data.id_bayar_layanan);
                $("#id_layanan").val(data.id_layanan);
                $("#id_pasien").val(data.id_pasien);
                $("#tgl_daftar").val(data.tgl_daftar);
                $("#jenis_pembayaran").val(data.jenis_pembayaran);
            }
        );
        page = "edit";
    });
    
    $("#tabelBayar tbody").on('click', '#btnBayar', function(){
        page = "edit";
        var baris = $(this).closest('tr');
        var id_bayar_layanan1 = baris.find("td:eq(0)").text();
        var id_layanan1 = baris.find("td:eq(1)").text();
        var id_pasien1 = baris.find("td:eq(2)").text();
        var tgl_daftar1 = baris.find("td:eq(3)").text();
        var harga1 = baris.find("td:eq(4)").text();
        var jenis_pembayaran1 = baris.find("td:eq(5)").text();
        $.post("/Klinik/BayarLayananCTR",
            {
                page: page,
                id_bayar_layanan: id_bayar_layanan1,
                id_layanan: id_layanan1,
                id_pasien: id_pasien1,
                tgl_daftar: tgl_daftar1,
                harga: harga1,
                jenis_pembayaran: jenis_pembayaran1,
                status: "lunas"
            },
            function(data, status){
                alert(data);
                if(data === "Pembayaran Berhasil"){location.reload();}
            }
        );
    });
    
    //close modal lewat tombol x
    $("#btnClose").on('click', function(){
       $("#myModal").hide();
    });
    
    //method bawaan untuk buka sidebar
    (function($) {
            "use strict";
            var fullHeight = function() {
                    $('.js-fullheight').css('height', $(window).height());
                    $(window).resize(function(){
                            $('.js-fullheight').css('height', $(window).height());
                    });
            };
            fullHeight();
            $('#sidebarCollapse').on('click', function () {
          $('#sidebar').toggleClass('active');
      });
    })(jQuery);
});