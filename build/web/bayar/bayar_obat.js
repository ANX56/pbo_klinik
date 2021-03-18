$(document).ready(function(){
    //deklarasi variabel
    var id_bayar_obat, id_obat, id_pasien, id_resep, jumlah, harga, jenis_pembayaran, status, waktu, page;
    
    //dapetin value dari setiap input
    function getInputValue(){
        id_bayar_obat = $("#id_bayar_obat").val();
        id_obat = $("#id_obat").val();
        id_pasien = $("#id_pasien").val();
        id_resep = $("#id_resep").val();
        jumlah = $("#jumlah").val();
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
        url: "/Klinik/BayarObatCTR",
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
                        {'data': 'id_bayar_obat', 'name': 'id_bayar_obat', 'type': 'string'},
                        {'data': 'id_obat'},
                        {'data': 'id_pasien'},
                        {'data': 'id_resep'},
                        {'data': 'jumlah'},
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
        var id_bayar_obat = baris.find("td:eq(0)").text();
        $.post("/Klinik/BayarObatCTR",
            {
                page: page,
                id_bayar_obat: id_bayar_obat
            },
            function(data, status){
                $("#id_bayar_obat").val(data.id_bayar_obat);
                $("#id_obat").val(data.id_obat);
                $("#id_pasien").val(data.id_pasien);
                $("#id_resep").val(data.id_resep);
                $("#jumlah").val(data.jumlah);
                $("#harga").val(formatRupiah(data.harga.toString(), "Rp."));
                $("#jenis_pembayaran").val(data.jenis_pembayaran);
            }
        );
        page = "tampil";
    });
    
    $("#tabelBayar tbody").on('click', '#btnBayar', function(){
        page = "edit";
        var baris = $(this).closest('tr');
        var id_bayar_obat1 = baris.find("td:eq(0)").text();
        var id_obat1 = baris.find("td:eq(1)").text();
        var id_pasien1 = baris.find("td:eq(2)").text();
        var id_resep1 = baris.find("td:eq(3)").text();
        var jumlah1 = baris.find("td:eq(4)").text();
        var harga1 = baris.find("td:eq(5)").text();
        var jenis_pembayaran1 = baris.find("td:eq(6)").text();
        $.post("/Klinik/BayarObatCTR",
            {
                page: page,
                id_bayar_obat: id_bayar_obat1,
                id_obat: id_obat1,
                id_pasien: id_pasien1,
                id_resep: id_resep1,
                jumlah: jumlah1,
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