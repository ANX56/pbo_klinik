/* global $scope */

$(document).ready(function(){
    //deklarasi variabel
    var id_pasien, nama_pasien, tgl_lahir, jenis_kelamin, no_ktp, alamat, no_hp, gol_darah, password, id_user, page;
    
    //get data dari database ke table
    $.ajax ({
        url: "/Klinik/PasienCTR",
        method: "GET",
        dataType: "json",
        success:
            function(data){
                $("#tablePasien").dataTable({
                    serverside: true,
                    processing: true,
                    data: data,
                    sort: true,
                    searching: true,
                    paging: true,
                    columns: [
                        {'data': 'id_pasien', 'name': 'id_pasien', 'type': 'string'},
                        {'data': 'nama_pasien'},
                        {'data': 'tgl_lahir'},
                        {'data': 'jenis_kelamin'},
                        {'data': 'no_ktp'},
                        {'data': 'alamat'},
                        {'data': 'no_hp'},
                        {'data': 'gol_darah'},
                        {'data': 'password'},
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
        id_pasien = $("#id_pasien").val();
        nama_pasien = $("#nama_pasien").val();
        tgl_lahir = $("#tgl_lahir").val();
        jenis_kelamin = $("#jenis_kelamin").val();
        no_ktp = $("#no_ktp").val();
        alamat = $("#alamat").val();
        no_hp = $("#no_hp").val();
        gol_darah = $("#gol_darah").val();
        password = $("#password").val();
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
        if(id_pasien === ""){
            alert("ID Pasien harus diisi");
            $("#id_pasien").focus();
        } else if(nama_pasien === ""){
            alert("Nama Pasien harus diisi");
            $("#nama_pasien").focus();
        } else if(gol_darah === ""){
            alert("Golongan Darah harus diisi");
            $("#gol_darah").focus();
        } else if(password === ""){
            alert("Password harus diisi");
            $("#password").focus();
        } else{
            $.post("/Klinik/PasienCTR",
                {
                    page: page,
                    id_pasien: id_pasien,
                    nama_pasien: nama_pasien,
                    tgl_lahir: tgl_lahir,
                    jenis_kelamin: jenis_kelamin,
                    no_ktp: no_ktp,
                    alamat: alamat,
                    no_hp: no_hp,
                    gol_darah: gol_darah,
                    password: password,
                    id_user: id_user
                },
                function(data, status){
                    alert(data);
                    if(data === "Data berhasil disimpan"){location.reload();}
                }
            );
        }
    });
    
    //edit data
    $("#tablePasien tbody").on('click', '#btnEdit', function(){
        $("#myModal").show();
        $("#title1").hide();
        $("#title2").show();
        $("#id_pasien").prop('disabled', true);
        page = "tampil";
        
        //dapetin baris yang di klik
        var baris = $(this).closest('tr');
        var id_pasien = baris.find("td:eq(0)").text();
        $.post("/Klinik/PasienCTR",
            {
                page: page,
                id_pasien: id_pasien
            },
            function(data, status){
                var jk="";
                if(data.jenis_kelamin === "Laki-Laki"){
                    jk="L";
                } else if(data.jenis_kelamin === "Perempuan"){
                    jk="P";
                }
                $("#id_pasien").val(data.id_pasien);
                $("#nama_pasien").val(data.nama_pasien);
                $("#tgl_lahir").val(data.tgl_lahir);
                $("#jenis_kelamin").val(jk);
                $("#no_ktp").val(data.no_ktp);
                $("#alamat").val(data.alamat);
                $("#no_hp").val(data.no_hp);
                $("#gol_darah").val(data.gol_darah);
                $("#password").val(data.password);
                $("#id_user").val(data.id_user);
            }
        );
        page = "edit";
    });
    
    //hapus data
    $("#tablePasien tbody").on('click', '#btnDelete', function(){
        //dapetin baris yang di click
        var baris = $(this).closest('tr');
        var id_pasien = baris.find("td:eq(0)").text();
        var nama_pasien = baris.find("td:eq(1)").text();
        page = "hapus";
        //konfirmasi jika akan dihapus
        if(confirm("Apakah anda yakin akan menghapus data : '" + id_pasien + " - " + nama_pasien +"' ?")){
            $.post("/Klinik/PasienCTR",
                {
                    page: page,
                    id_pasien: id_pasien
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
        $("#id_pasien").val("");
        $("#id_pasien").prop('disabled', false);
        $("#nama_pasien").val("");
        $("#tgl_lahir").val("");
        $("#jenis_kelamin").val("");
        $("#no_ktp").val("");
        $("#alamat").val("");
        $("#no_hp").val("");
        $("#gol_darah").val("");
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