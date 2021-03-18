$(document).ready(function(){
    //deklarasi variabel
    var id_rawat, id_pasien, id_kamar, tgl_check_in, tgl_check_out, keterangan, page;
    
    //dapetin value dari setiap input
    function getInputValue(){
        id_rawat = $("#id_rawat").val();
        id_pasien = $("#id_pasien").val();
        id_kamar = $("#id_kamar").val();
        tgl_check_in = $("#tgl_check_in").val();
        tgl_check_out = $("#tgl_check_out").val();
        keterangan = $("#keterangan").val();
    }    
    
    //validasi jika data kosong dan save data ke database 
    $("#btnSave").on('click', function(){
        getInputValue();
        if(id_rawat === ""){
            alert("ID Pasien harus diisi");
            $("#id_rawat").focus();
        } else if(id_pasien === ""){
            alert("ID Pasien harus diisi");
            $("#id_pasien").focus();
        } else if(id_kamar === ""){
            alert("ID Kamar harus diisi");
            $("#id_kamar").focus();
        } else if(tgl_check_in === ""){
            alert("Tanggal Check In harus diisi");
            $("#tgl_check_in").focus();
        } else if(tgl_check_in === ""){
            alert("Tanggal Check In harus diisi");
            $("#tgl_check_in").focus();
        } else{
            $.post("/Klinik/RawatCTR",
                {
                    page: page,
                    id_rawat: id_rawat,
                    id_pasien: id_pasien,
                    id_kamar: id_kamar,
                    tgl_check_in: tgl_check_in,
                    tgl_check_out: tgl_check_out,
                    keterangan: keterangan
                },
                function(data, status){
                    alert(data);
                    if(data === "Data berhasil disimpan"){location.reload();}
                }
            );
        }
    });
    
    function loadPasien() {
        loadPasien = 1;
        $.ajax({
            url: "/Klinik/PasienCTR",
            method: "GET", 
            dataType: "json",
            success: function(data){
                $("#tabelLookupPasien").dataTable({
                    serverside: true,
                    processing: true,
                    data: data,
                    sort: true,
                    searching: true,
                    paging: true,
                    columns: [
                            {data: 'id_pasien', 'name': 'id_pasien', 'type': 'string'},
                            {data: 'nama_pasien'},
                            {data: null, orderable:false, 'className': 'dt-right', 'mRender': function(o){
                                    return "<a class='btn btn-warning btn-sm'"
                                    + "id = 'btnInsertPasien'>Insert</a>";
                                }
                            }
                        ]
                });
            }
        });
    }
    
    //jika tombol lookup di klik
    $("#btn-lookup-pasien").click(function() {
        $("#modalLookupPasien").modal('show');
        if (loadPasien !== 1) {
            loadPasien();
        }
        $("#tabelLookupPasien tbody").on('click', '#btnInsertPasien', function() {
            let baris = $(this).closest('tr');
            let id = baris.find("td:eq(0)").text();
            let nama = baris.find("td:eq(1)").text();
            $("#id_pasien").val(id);
            $("#nama_pasien").val(nama);
            $("#modalLookupPasien").modal("hide");
        });
    });
    
    function loadKamar() {
        loadKamar = 1;
        $.ajax({
            url: "/Klinik/KamarCTR",
            method: "GET", 
            dataType: "json",
            success: function(data){
                $("#tabelLookupKamar").dataTable({
                    serverside: true,
                    processing: true,
                    data: data,
                    sort: true,
                    searching: true,
                    paging: true,
                    columns: [
                            {data: 'id_kamar', 'name': 'id_user', 'type': 'string'},
                            {data: 'nama_kamar'},
                            {data: null, orderable:false, 'className': 'dt-right', 'mRender': function(o){
                                    return "<a class='btn btn-warning btn-sm'"
                                    + "id = 'btnInsertKamar'>Insert</a>";
                                }
                            }
                        ]
                });
            }
        });
    }
    
    //jika tombol lookup di klik
    $("#btn-lookup-kamar").click(function() {
        $("#modalLookupKamar").modal('show');
        if (loadKamar !== 1) {
            loadKamar();
        }
        $("#tabelLookupKamar tbody").on('click', '#btnInsertKamar', function() {
            let baris = $(this).closest('tr');
            let id = baris.find("td:eq(0)").text();
            let nama = baris.find("td:eq(1)").text();
            $("#id_kamar").val(id);
            $("#nama_kamar").val(nama);
            $("#modalLookupKamar").modal("hide");
        });
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