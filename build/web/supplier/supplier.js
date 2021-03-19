/* global $scope */

$(document).ready(function(){
    //deklarasi variabel
    var id_supplier, nama_dokter, alamat, no_hp, email, password, waktu, id_user, page;
    
    //dapetin value dari setiap input
    function getInputValue(){
        id_supplier = $("#id_supplier").val();
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
    
    //get data dari database ke table
    $.ajax ({
        url: "/Klinik/SupplierCTR",
        method: "GET",
        dataType: "json",
        success:
            function(data){
                $("#tabelSupplier").dataTable({
                    serverside: true,
                    processing: true,
                    data: data,
                    sort: true,
                    searching: true,
                    paging: true,
                    columns: [
                        {'data': 'id_supplier', 'name': 'id_supplier', 'type': 'string'},
                        {'data': 'nama_supplier'},
                        {'data': 'alamat'},
                        {'data': 'no_hp'},
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
        if(id_supplier === ""){
            alert("ID Supplier harus diisi");
            $("#id_supplier").focus();
        } else if(nama_dokter === ""){
            alert("Nama Dokter harus diisi");
            $("#nama_supplier").focus();
        } else if(email === ""){
            alert("Email harus diisi");
            $("#email").focus();
        } else if(password === ""){
            alert("Password harus diisi");
            $("#password").focus();
        } else{
            $.post("/Klinik/SupplierCTR",
                {
                    page: page,
                    id_supplier: id_supplier,
                    nama_dokter: nama_dokter,
                    alamat: alamat,
                    no_hp: no_hp,
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
    $("#tabelSupplier tbody").on('click', '#btnEdit', function(){
        $("#myModal").show();
        $("#title1").hide();
        $("#title2").show();
        $("#id_supplier").prop('disabled', true);
        page = "tampil";
        
        //dapetin baris yang di klik
        var baris = $(this).closest('tr');
        var id_supplier = baris.find("td:eq(0)").text();
        $.post("/Klinik/SupplierCTR",
            {
                page: page,
                id_supplier: id_supplier
            },
            function(data, status){
                //ubah format waktu dari database ke format yang benar (YYYY-MM-ddThh:mm:ss
                let d = new Date(data.waktu.toString().split('GMT')[0]+' UTC').toISOString().split('.')[0];
                $("#id_supplier").val(data.id_supplier);
                $("#nama_supplier").val(data.nama_supplier);
                $("#alamat").val(data.alamat);
                $("#no_hp").val(data.no_hp);
                $("#email").val(data.email);
                $("#password").val(data.password);
                $("#waktu").val(d);
                $("#id_user").val(data.id_user);
            }
        );
        page = "edit";
    });
    
    //hapus data
    $("#tabelSupplier tbody").on('click', '#btnDelete', function(){
        //dapetin baris yang di click
        var baris = $(this).closest('tr');
        var id_supplier = baris.find("td:eq(0)").text();
        var nama_dokter = baris.find("td:eq(1)").text();
        page = "hapus";
        //konfirmasi jika akan dihapus
        if(confirm("Apakah anda yakin akan menghapus data : '" + id_supplier + " - " + nama_dokter +"' ?")){
            $.post("/Klinik/SupplierCTR",
                {
                    page: page,
                    id_supplier: id_supplier
                },
                function(data, status){
                    alert(data);
                    location.reload();
                }
            );
        }
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
        $("#id_supplier").val("");
        $("#id_supplier").prop('disabled', false);
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