/* global $scope */

$(document).ready(function(){
    //deklarasi variabel
    var id_karyawan, nama_karyawan, tgl_lahir, bidang_pekerjaan, jenis_kelamin, alamat, no_hp, no_ktp, npwp, email, password, waktu, id_user, page;
    
    //get data dari database ke table
    $.ajax ({
        url: "/Klinik/KaryawanCTR",
        method: "GET",
        dataType: "json",
        success:
            function(data){
                $("#tableKaryawan").dataTable({
                    serverside: true,
                    processing: true,
                    data: data,
                    sort: true,
                    searching: true,
                    paging: true,
                    columns: [
                        {'data': 'id_karyawan', 'name': 'id_karyawan', 'type': 'string'},
                        {'data': 'nama_karyawan'},
                        {'data': 'tgl_lahir'},
                        {'data': 'bidang_pekerjaan'},
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
        id_karyawan = $("#id_karyawan").val();
        nama_karyawan = $("#nama_karyawan").val();
        tgl_lahir = $("#tgl_lahir").val();
        bidang_pekerjaan = $("#bidang_pekerjaan").val();
        jenis_kelamin = $("#jenis_kelamin").val();
        alamat = $("#alamat").val();
        no_hp = $("#no_hp").val();
        no_ktp = $("#no_ktp").val();
        npwp = $("#npwp").val();
        email = $("#email").val();
        password = $("#password").val();
        waktu = $("#waktu").val();
        id_user = $("#id_user").val();
        console.log(id_karyawan);
        console.log(nama_karyawan);
        console.log(tgl_lahir);
        console.log(bidang_pekerjaan);
        console.log(jenis_kelamin);
        console.log(alamat);
        console.log(no_hp);
        console.log(no_ktp);
        console.log(npwp);
        console.log(email);
        console.log(password);
        console.log(waktu);
        console.log(id_user);
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
        if(id_karyawan === ""){
            alert("ID Karyawan harus diisi");
            $("#id_karyawan").focus();
        } else if(nama_karyawan === ""){
            alert("Nama Karyawan harus diisi");
            $("#nama_karyawan").focus();
        } else if(bidang_pekerjaan === ""){
            alert("Bidang Pekerjaan harus dipilih");
            $("#bidang_pekerjaan").focus();
        } else if(email === ""){
            alert("Email harus diisi");
            $("#email").focus();
        } else if(password === ""){
            alert("Password harus diisi");
            $("#password").focus();
        } else{
            $.post("/Klinik/KaryawanCTR",
                {
                    page: page,
                    id_karyawan: id_karyawan,
                    nama_karyawan: nama_karyawan,
                    tgl_lahir: tgl_lahir,
                    bidang_pekerjaan: bidang_pekerjaan,
                    jenis_kelamin: jenis_kelamin,
                    alamat: alamat,
                    no_hp: no_hp,
                    no_ktp: no_ktp,
                    npwp: npwp,
                    email: email,
                    password: password,
                    waktu: waktu,
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
    $("#tableKaryawan tbody").on('click', '#btnEdit', function(){
        $("#myModal").show();
        $("#title1").hide();
        $("#title2").show();
        $("#id_karyawan").prop('disabled', true);
        page = "tampil";
        
        //dapetin baris yang di klik
        var baris = $(this).closest('tr');
        var id_karyawan = baris.find("td:eq(0)").text();
        $.post("/Klinik/KaryawanCTR",
            {
                page: page,
                id_karyawan: id_karyawan
            },
            function(data, status){
                var jk="";
                if(data.jenis_kelamin === "Laki-Laki"){
                    jk="L";
                } else if(data.jenis_kelamin === "Perempuan"){
                    jk="P";
                }
                var bp="";
                if(data.bidang_pekerjaan === "Administrasi"){
                    bp="administrasi";
                } else if(data.bidang_pekerjaan === "Suster"){
                    bp="suster";
                } else if(data.bidang_pekerjaan === "Rekam Medis"){
                    bp="rm";
                } else if(data.bidang_pekerjaan === "Apoteker"){
                    bp="apoteker";
                }
                let d = new Date(new Date().toString().split('GMT')[0]+' UTC').toISOString().split('.')[0];
                $("#id_karyawan").val(data.id_karyawan);
                $("#nama_karyawan").val(data.nama_karyawan);
                $("#tgl_lahir").val(data.tgl_lahir);
                $("#bidang_pekerjaan").val(bp);
                $("#jenis_kelamin").val(jk);
                $("#alamat").val(data.alamat);
                $("#no_hp").val(data.no_hp);
                $("#no_ktp").val(data.no_ktp);
                $("#npwp").val(data.npwp);
                $("#email").val(data.email);
                $("#password").val(data.password);
                $("#waktu").val(d);
                $("#id_user").val(data.id_user);
            }
        );
        page = "edit";
    });
    
    //hapus data
    $("#tableKaryawan tbody").on('click', '#btnDelete', function(){
        //dapetin baris yang di click
        var baris = $(this).closest('tr');
        var id_karyawan = baris.find("td:eq(0)").text();
        var nama_karyawan = baris.find("td:eq(1)").text();
        page = "hapus";
        //konfirmasi jika akan dihapus
        if(confirm("Apakah anda yakin akan menghapus data : '" + id_karyawan + " - " + nama_karyawan +"' ?")){
            $.post("/Klinik/KaryawanCTR",
                {
                    page: page,
                    id_karyawan: id_karyawan
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
        $("#id_karyawan").val("");
        $("#id_karyawan").prop('disabled', false);
        $("#nama_karyawan").val("");
        $("#tgl_lahir").val("");
        $("#alamat").val("");
        $("#bidang_pekerjaan").val("");
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