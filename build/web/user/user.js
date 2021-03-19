/* global $scope */

$(document).ready(function(){
    //deklarasi variabel
    var id_user, username, password, nama, role, page;
    
    //get data dari database ke table
    $.ajax ({
        url: "/controller/UserCtr",
        method: "GET",
        dataType: "json",
        success:
            function(data){
                $("#tabelUser").dataTable({
                    serverside: true,
                    processing: true,
                    data: data,
                    sort: true,
                    searching: true,
                    paging: true,
                    columns: [
                        {'data': 'id_user', 'name': 'id_user', 'type': 'string'},
                        {'data': 'username'},
                        {'data': 'password'},
                        {'data': 'nama'},
                        {'data': 'role'},
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
        id_user = $("#id_user").val();
        username = $("#username").val();
        password = $("#password").val();
        nama = $("#nama").val();
        role = $("#role").val();
        
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
            alert("ID User harus diisi");
            $("#id_user").focus();
        } else if(username === ""){
            alert("username harus diisi");
            $("#username").focus();
        } else if(password=== ""){
            alert("password harus diisi");
            $("#password").focus();
        } else if(nama === ""){
            alert("nama harus diisi");
            $("#nama").focus();
        }  else if(role === ""){
            alert("Role harus diisi");
            $("#role").focus();
        }else{
            $.post("/controller/UserCtr",
                {
                    page: page,
                    id_user: id_user,
                    username: username,
                    password: password,
                    nama: nama,
                    role: role,
                    
                },
                function(data, status){
                    alert(data+" "+status);
                    if(data === "Data berhasil disimpan"){location.reload();}
                }
            );
        }
    });
    
    //edit data
    $("#tabelUser tbody").on('click', '#btnEdit', function(){
        $("#myModal").show();
        $("#title1").hide();
        $("#title2").show();
        $("#id_user").prop('disabled', true);
        page = "tampil";
        
        //dapetin baris yang di klik
        var baris = $(this).closest('tr');
        var id_dokter = baris.find("td:eq(0)").text();
        $.post("/controller/UserCtr",
            {
                page: page,
                id_user: id_user
            },
            function(data, status){
                //karena data dari database itu sudah diubah di DAO, 
                //dimana jika L menjadi Laki-Laki, dan P menjadi Perempuan,
                //karena value di dropdown itu pakai L dan P, 
                //supaya ketika di tampilkan ke database gak perlu ubah lagi
                
                //ubah format waktu dari database ke format yang benar (YYYY-MM-ddThh:mm:ss
                let d = new Date(new Date().toString().split('GMT')[0]+' UTC').toISOString().split('.')[0];
                $("#id_user").val(data.id_user);
                $("#username").val(data.username);
                $("#password").val(data.password);
                $("#nama").val(data.nama);
                $("#role").val(data.role);
              
            }
        );
        page = "edit";
    });
    
    //hapus data
    $("#tabelUser tbody").on('click', '#btnDelete', function(){
        //dapetin baris yang di click
        var baris = $(this).closest('tr');
        var id_user = baris.find("td:eq(0)").text();
        var username = baris.find("td:eq(1)").text();
        page = "hapus";
        //konfirmasi jika akan dihapus
        if(confirm("Apakah anda yakin akan menghapus data : '" + id_user + " - " + username +"' ?")){
            $.post("/controller/UserCtr",
                {
                    page: page,
                    id_user: id_user
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
//    function loadPoli() {
//        loadPoli = 1;
//        $.ajax({
//            url: "/Klinik/PoliCTR",
//            method: "GET", 
//            dataType: "json",
//            success: function(data){
//                $("#tabelLookupPoli").dataTable({
//                serverside: true,
//                processing: true,
//                data: data,
//                sort: true,
//                searching: true,
//                paging: true,
//                columns: [
//                        {'data': 'id_poli', 'name': 'id_poli', 'type': 'string'},
//                        {'data': 'nama_poli'},
//                        {'data': null, 'className': 'dt-right', 'mRender': function(o){
//                                return "<a class='btn btn-warning btn-sm'"
//                                + "id = 'btnInsertPoli'>Insert</a>";
//                            }
//                        }
//                    ]
//                });
//            }
//        });
//    }
    
    //jika tombol lookup di klik
//    $("#btn-lookup-poli").click(function() {
//        $("#modalLookupPoli").modal('show');
//        if (loadPoli !== 1) {
//            loadPoli();
//        }
//        $("#tabelLookupPoli tbody").on('click', '#btnInsertPoli', function() {
//            let baris = $(this).closest('tr');
//            let id_poli = baris.find("td:eq(0)").text();
//            $("#id_poli").val(id_poli);
//            $("#modalLookupPoli").modal("hide");
//        });
//    });
    
    //buat function untuk menampilkan data poli ke tabelLookupUser
//    function loadUser() {
//        loadUser = 1;
//        $.ajax({
//            url: "/Klinik/UserCTR",
//            method: "GET", 
//            dataType: "json",
//            success: function(data){
//                $("#tabelLookupUser").dataTable({
//                    serverside: true,
//                    processing: true,
//                    data: data,
//                    sort: true,
//                    searching: true,
//                    paging: true,
//                    columns: [
//                            {data: 'id_user', 'name': 'id_user', 'type': 'string'},
//                            {data: 'nama'},
//                            {data: 'level'},
//                            {data: null, orderable:false, 'className': 'dt-right', 'mRender': function(o){
//                                    return "<a class='btn btn-warning btn-sm'"
//                                    + "id = 'btnInsertUser'>Insert</a>";
//                                }
//                            }
//                        ]
//                });
//            }
//        });
//    }
    
//    //jika tombol lookup di klik
//    $("#btn-lookup-user").click(function() {
//        $("#modalLookupUser").modal('show');
//        if (loadUser !== 1) {
//            loadUser();
//        }
//        $("#tabelLookupUser tbody").on('click', '#btnInsertUser', function() {
//            let baris = $(this).closest('tr');
//            let id = baris.find("td:eq(0)").text();
//            $("#id_user").val(id);
//            $("#modalLookupUser").modal("hide");
//        });
//    });
    
    //clear input 
    function clearForm() {
        $("#id_user").val("");
        $("#id_user").prop('disabled', false);
        $("#username").val("");
        $("#password").val("");
        $("#nama").val("");
        $("#role").val("");
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