/* global $scope */

$(document).ready(function(){
    //deklarasi variabel
    var id_dokter, nama_dokter, tgl_lahir, id_poli, jenis_kelamin, alamat, no_hp, npwp, no_ktp, email, password, waktu, id_user, page;
    
    //get data dari database ke table
    $.ajax ({
        url: "/Klinik/DokterCTR",
        method: "GET",
        dataType: "json",
        success:
            function(data){
                $("#tabelDokter").dataTable({
                    serverside: true,
                    processing: true,
                    data: data,
                    sort: true,
                    searching: true,
                    paging: true,
                    columns: [
                        {'data': 'id_dokter', 'name': 'id_dokter', 'type': 'string'},
                        {'data': 'nama_dokter'},
                        {'data': 'tgl_lahir'},
                        {'data': 'id_poli'},
                        {'data': 'jenis_kelamin'},
                        {'data': 'alamat'},
                        {'data': 'no_hp'},
                        {'data': 'npwp'},
                        {'data': 'no_ktp'},
                        {'data': 'email'},
                        {'data': 'password'},
                        {'data': 'waktu'},
                        {'data': 'id_user'},
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
        id_dokter = $("#id_dokter").val();
        nama_dokter = $("#nama_dokter").val();
        tgl_lahir = $("#tgl_lahir").val();
        id_poli = $("#id_poli").val();
        jenis_kelamin = $("#jenis_kelamin").val();
        alamat = $("#alamat").val();
        no_hp = $("#no_hp").val();
        npwp = $("#npwp").val();
        no_ktp = $("#no_ktp").val();
        email = $("#email").val();
        password = $("#password").val();
        waktu = $("#waktu").val();
        id_user = $("#id_user").val();
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
        if(id_dokter === ""){
            alert("ID Dokter harus diisi");
            $("#id_dokter").focus();
        } else if(nama_dokter === ""){
            alert("Nama Dokter harus diisi");
            $("#nama_dokter").focus();
        } else if(id_poli === ""){
            alert("ID Poli harus diisi");
            $("#id_poli").focus();
        } else if(email === ""){
            alert("Email harus diisi");
            $("#email").focus();
        } else if(password === ""){
            alert("Password harus diisi");
            $("#password").focus();
        } else{
            $.post("/Klinik/DokterCTR",
                {
                    page: page,
                    id_dokter: id_dokter,
                    nama_dokter: nama_dokter,
                    tgl_lahir: tgl_lahir,
                    id_poli: id_poli,
                    jenis_kelamin: jenis_kelamin,
                    alamat: alamat,
                    no_hp: no_hp,
                    npwp: npwp,
                    no_ktp: no_ktp,
                    email: email,
                    password: password,
                    waktu: waktu,
                    id_user: id_user
                },
                function(data, status){
                    alert(data+" "+status);
                    if(data === "Data berhasil disimpan"){location.reload();}
                }
            );
        }
    });
    
    //edit data
    $("#tabelDokter tbody").on('click', '#btnEdit', function(){
        $("#myModal").show();
        $("#title1").hide();
        $("#title2").show();
        $("#id_dokter").prop('disabled', true);
        page = "tampil";
        
        //dapetin baris yang di klik
        var baris = $(this).closest('tr');
        var id_dokter = baris.find("td:eq(0)").text();
        $.post("/Klinik/DokterCTR",
            {
                page: page,
                id_dokter: id_dokter
            },
            function(data, status){
                //karena data dari database itu sudah diubah di DAO, 
                //dimana jika L menjadi Laki-Laki, dan P menjadi Perempuan,
                //karena value di dropdown itu pakai L dan P, 
                //supaya ketika di tampilkan ke database gak perlu ubah lagi
                var jk="";
                if(data.jenis_kelamin === "Laki-Laki"){
                    jk="L";
                } else if(data.jenis_kelamin === "Perempuan"){
                    jk="P";
                }
                //ubah format waktu dari database ke format yang benar (YYYY-MM-ddThh:mm:ss
                let d = new Date(new Date().toString().split('GMT')[0]+' UTC').toISOString().split('.')[0];
                $("#id_dokter").val(data.id_dokter);
                $("#nama_dokter").val(data.nama_dokter);
                $("#tgl_lahir").val(data.tgl_lahir);
                $("#id_poli").val(data.id_poli);
                $("#jenis_kelamin").val(jk);
                $("#alamat").val(data.alamat);
                $("#no_hp").val(data.no_hp);
                $("#npwp").val(data.npwp);
                $("#no_ktp").val(data.no_ktp);
                $("#email").val(data.email);
                $("#password").val(data.password);
                $("#waktu").val(d);
                $("#id_user").val(data.id_user);
            }
        );
        page = "edit";
    });
    
    //hapus data
    $("#tabelDokter tbody").on('click', '#btnDelete', function(){
        //dapetin baris yang di click
        var baris = $(this).closest('tr');
        var id_dokter = baris.find("td:eq(0)").text();
        var nama_dokter = baris.find("td:eq(1)").text();
        page = "hapus";
        //konfirmasi jika akan dihapus
        if(confirm("Apakah anda yakin akan menghapus data : '" + id_dokter + " - " + nama_dokter +"' ?")){
            $.post("/Klinik/DokterCTR",
                {
                    page: page,
                    id_dokter: id_dokter
                },
                function(data, status){
                    alert(data+" "+status);
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
    
    //buat function untuk menampilkan data poli ke tabelLookupUser
    function loadUser() {
        loadUser = 1;
        $.ajax({
            url: "/Klinik/UserCTR",
            method: "GET", 
            dataType: "json",
            success: function(data){
                $("#tabelLookupUser").dataTable({
                    serverside: true,
                    processing: true,
                    data: data,
                    sort: true,
                    searching: true,
                    paging: true,
                    columns: [
                            {data: 'id_user', 'name': 'id_user', 'type': 'string'},
                            {data: 'nama'},
                            {data: 'level'},
                            {data: null, orderable:false, 'className': 'dt-right', 'mRender': function(o){
                                    return "<a class='btn btn-warning btn-sm'"
                                    + "id = 'btnInsertUser'>Insert</a>";
                                }
                            }
                        ]
                });
            }
        });
    }
    
    //jika tombol lookup di klik
    $("#btn-lookup-user").click(function() {
        $("#modalLookupUser").modal('show');
        if (loadUser !== 1) {
            loadUser();
        }
        $("#tabelLookupUser tbody").on('click', '#btnInsertUser', function() {
            let baris = $(this).closest('tr');
            let id = baris.find("td:eq(0)").text();
            let nama = baris.find("td:eq(1)").text();
            $("#id_user").val(id);
            $("#nama_user").val(nama);
            $("#modalLookupUser").modal("hide");
        });
    });
    
    //clear input 
    function clearForm() {
        $("#id_dokter").val("");
        $("#id_dokter").prop('disabled', false);
        $("#nama_dokter").val("");
        $("#tgl_lahir").val("");
        $("#alamat").val("");
        $("#id_poli").val("");
        $("#jenis_kelamin").val("");
        $("#no_hp").val("");
        $("#npwp").val("");
        $("#no_ktp").val("");
        $("#email").val("");
        $("#password").val("");
        $("#waktu").val("");
        $("#id_user").val("");
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