/* global $scope */

$(document).ready(function(){
    //deklarasi variabel
    var nomor_rm, id_pasien, tgl_daftar, id_poli, id_dokter, berat, tinggi, tensi, diagnosa, id_resep, waktu, page;
    
    //get data dari database ke table
    $.ajax ({
        url: "/Klinik/RMCTR",
        method: "GET",
        dataType: "json",
        success:
            function(data){
                $("#tabelRM").dataTable({
                    serverside: true,
                    processing: true,
                    data: data,
                    sort: true,
                    searching: true,
                    paging: true,
                    columns: [
                        {'data': 'nomor_rm', 'name': 'nomor_rm', 'type': 'string'},
                        {'data': 'id_pasien'},
                        {'data': 'tgl_daftar'},
                        {'data': 'id_poli'},
                        {'data': 'id_dokter'},
                        {'data': 'berat'},
                        {'data': 'tinggi'},
                        {'data': 'tensi'},
                        {'data': 'diagnosa'},
                        {'data': 'id_resep'},
                        {'data': 'waktu'},
                        {'data': null, 'className': 'dt-right',
                            'mRender' : function(o){
                                return "<a class= 'btn btn-outline-success btn-sm'" 
                                + " id='btnEdit'>Edit</a>"
                                + " <a class='btn btn-outline-danger btn-sm'"
                                + " id='btnDelete'>Hapus</a>";
                            }
                        }
                    ]
                }); 
            }
    });
    
    //dapetin value dari setiap input
    function getInputValue(){
        nomor_rm = $("#nomor_rm").val();
        id_pasien = $("#id_pasien").val();
        tgl_daftar = $("#tgl_daftar").val();
        id_poli = $("#id_poli").val();
        id_dokter = $("#id_dokter").val();
        berat = $("#berat").val();
        tinggi = $("#tinggi").val();
        tensi = $("#tensi").val();
        diagnosa = $("#diagnosa").val();
        id_resep = $("#id_resep").val();
        waktu = $("#waktu").val();
    }
    
    //munculin modal ketika tombol tambah di klik
    $("#btnAdd").click(function(){
        $("#myModal").show();
        $("#title1").show();
        $("#title2").hide();
        page = "tambah";
    });
    
    //validasi jika data kosong dan save data ke database 
    $("#btnSave").on('click', function(){
        getInputValue();
        if(nomor_rm === ""){
            alert("Nomor RM harus diisi");
            $("#nomor_rm").focus();
        } else if(id_pasien === ""){
            alert("Nama Dokter harus diisi");
            $("#id_pasien").focus();
        } else if(id_poli === ""){
            alert("ID Poli harus diisi");
            $("#id_poli").focus();
        } else if(id_dokter === ""){
            alert("ID Dokter harus diisi");
            $("#id_dokter").focus();
        } else if(id_resep === ""){
            alert("ID Resep harus diisi");
            $("#id_resep").focus();
        } else if(diagnosa === ""){
            alert("Diagnosa harus diisi");
            $("#diagnosa").focus();
        } else{
            $.post("/Klinik/RMCTR",
                {
                    page: page,
                    nomor_rm: nomor_rm,
                    id_pasien: id_pasien,
                    tgl_daftar: tgl_daftar,
                    id_poli: id_poli,
                    id_dokter: id_dokter,
                    berat: berat,
                    tinggi: tinggi,
                    tensi: tensi,
                    diagnosa: diagnosa,
                    id_resep: id_resep,
                    waktu: waktu
                },
                function(data, status){
                    alert(data);
                    if(data === "Data berhasil disimpan"){location.reload();}
                }
            );
        }
    });
        
    function formatDate(date) {
        var d = new Date(date),
            month = '' + (d.getMonth() + 1),
            day = '' + d.getDate(),
            year = d.getFullYear();

        if (month.length < 2) 
            month = '0' + month;
        if (day.length < 2) 
            day = '0' + day;

        return [year, month, day].join('-');
    }
    
    //edit data
    $("#tabelRM tbody").on('click', '#btnEdit', function(){
        $("#myModal").show();
        $("#title1").hide();
        $("#title2").show();
        $("#nomor_rm").prop('disabled', true);
        page = "tampil";
        
        //dapetin baris yang di klik
        var baris = $(this).closest('tr');
        var nomor_rm = baris.find("td:eq(0)").text();
        $.post("/Klinik/RMCTR",
            {
                page: page,
                nomor_rm: nomor_rm
            },
            function(data, status){
                //ubah format waktu dari database ke format yang benar (YYYY-MM-ddThh:mm:ss
                let d = new Date(new Date().toString().split('GMT')[0]+' UTC').toISOString().split('.')[0];
                $("#nomor_rm").val(data.nomor_rm);
                $("#id_pasien").val(data.id_pasien);
                $("#tgl_daftar").val(formatDate(data.tgl_daftar));
                $("#id_poli").val(data.id_poli);
                $("#id_dokter").val(data.id_dokter);
                $("#berat").val(data.berat);
                $("#tinggi").val(data.tinggi);
                $("#tensi").val(data.tensi);
                $("#diagnosa").val(data.diagnosa);
                $("#id_resep").val(data.id_resep);
                $("#waktu").val(d);
            }
        );
        page = "edit";
    });
    
    //hapus data
    $("#tabelRM tbody").on('click', '#btnDelete', function(){
        //dapetin baris yang di click
        var baris = $(this).closest('tr');
        var nomor_rm = baris.find("td:eq(0)").text();
        var id_pasien = baris.find("td:eq(1)").text();
        page = "hapus";
        //konfirmasi jika akan dihapus
        if(confirm("Apakah anda yakin akan menghapus data : '" + nomor_rm + " - " + id_pasien +"' ?")){
            $.post("/Klinik/RMCTR",
                {
                    page: page,
                    nomor_rm: nomor_rm
                },
                function(data, status){
                    alert(data);
                    location.reload();
                }
            );
        }
    });
    
    //close modal lewat tombol x
    $("#btnClose").on('click', function(){
       $("#myModal").hide(); 
       clearForm();
    });
    
    //close modal lewat tombol cancel
    $("#btnCancel").on('click', function(){
       $("#myModal").hide(); 
       clearForm();
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
                            {data: 'id_pasien', 'name': 'id_user', 'type': 'string'},
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
    
    //buat function untuk menampilkan data poli ke tabelLookupPoli
    function loadPoli() {
        loadPoli = 1;
        $.ajax({
            url: "/Klinik/PoliCTR",
            method: "GET", 
            dataType: "json",
            success: function(data){
                $("#tabelLookupPoli").dataTable({
                serverside: true,
                processing: true,
                data: data,
                sort: true,
                searching: true,
                paging: true,
                columns: [
                        {'data': 'id_poli', 'name': 'id_poli', 'type': 'string'},
                        {'data': 'nama_poli'},
                        {'data': null, 'className': 'dt-right', 'mRender': function(o){
                                return "<a class='btn btn-warning btn-sm'"
                                + "id = 'btnInsertPoli'>Insert</a>";
                            }
                        }
                    ]
                });
            }
        });
    }
    
    //jika tombol lookup di klik
    $("#btn-lookup-poli").click(function() {
        $("#modalLookupPoli").modal('show');
        if (loadPoli !== 1) {
            loadPoli();
        }
        $("#tabelLookupPoli tbody").on('click', '#btnInsertPoli', function() {
            let baris = $(this).closest('tr');
            let id_poli = baris.find("td:eq(0)").text();
            let nama_poli = baris.find("td:eq(1)").text();
            $("#id_poli").val(id_poli);
            $("#nama_poli").val(nama_poli);
            $("#modalLookupPoli").modal("hide");
        });
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
                            {data: 'id_pasien', 'name': 'id_user', 'type': 'string'},
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
    
    function loadDokter() {
        loadDokter = 1;
        $.ajax({
            url: "/Klinik/DokterCTR",
            method: "GET", 
            dataType: "json",
            success: function(data){
                $("#tabelLookupDokter").dataTable({
                    serverside: true,
                    processing: true,
                    data: data,
                    sort: true,
                    searching: true,
                    paging: true,
                    columns: [
                            {data: 'id_dokter', 'name': 'id_user', 'type': 'string'},
                            {data: 'nama_dokter'},
                            {data: null, orderable:false, 'className': 'dt-right', 'mRender': function(o){
                                    return "<a class='btn btn-warning btn-sm'"
                                    + "id = 'btnInsertDokter'>Insert</a>";
                                }
                            }
                        ]
                });
            }
        });
    }
    
    //jika tombol lookup di klik
    $("#btn-lookup-dokter").click(function() {
        $("#modalLookupDokter").modal('show');
        if (loadDokter !== 1) {
            loadDokter();
        }
        $("#tabelLookupDokter tbody").on('click', '#btnInsertDokter', function() {
            let baris = $(this).closest('tr');
            let id = baris.find("td:eq(0)").text();
            let nama = baris.find("td:eq(1)").text();
            $("#id_dokter").val(id);
            $("#nama_dokter").val(nama);
            $("#modalLookupDokter").modal("hide");
        });
    });
    
    function loadResep() {
        loadResep = 1;
        $.ajax({
            url: "/Klinik/ResepCTR",
            method: "GET", 
            dataType: "json",
            success: function(data){
                $("#tabelLookupResep").dataTable({
                    serverside: true,
                    processing: true,
                    data: data,
                    sort: true,
                    searching: true,
                    paging: true,
                    columns: [
                            {data: 'id_resep', 'name': 'id_user', 'type': 'string'},
                            {data: 'id_obat'},
                            {data: null, orderable:false, 'className': 'dt-right', 'mRender': function(o){
                                    return "<a class='btn btn-warning btn-sm'"
                                    + "id = 'btnInsertResep'>Insert</a>";
                                }
                            }
                        ]
                });
            }
        });
    }
    
    //jika tombol lookup di klik
    $("#btn-lookup-resep").click(function() {
        $("#modalLookupResep").modal('show');
        if (loadResep !== 1) {
            loadResep();
        }
        $("#tabelLookupResep tbody").on('click', '#btnInsertResep', function() {
            let baris = $(this).closest('tr');
            let id = baris.find("td:eq(0)").text();
            let obat = baris.find("td:eq(1)").text();
            $("#id_resep").val(id);
            $("#id_obat").val(obat);
            $("#modalLookupResep").modal("hide");
        });
    });
    
    //clear input 
    function clearForm() {
        $("#nomor_rm").val("");
        $("#nomor_rm").prop('disabled', false);
        $("#id_pasien").val("");
        $("#tgl_daftar").val("");
        $("#berat").val("");
        $("#id_poli").val("");
        $("#id_dokter").val("");
        $("#tinggi").val("");
        $("#tensi").val("");
        $("#diagnosa").val("");
        $("#id_resep").val("");
        $("#waktu").val("");
    }

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