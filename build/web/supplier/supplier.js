/* global $scope */

$(document).ready(function(){
    //deklarasi variabel
    var id_supplier, nama_supplier, alamat, no_hp, email, waktu, id_user, id_user, page;
    
    //get data dari database ke table
    $.ajax ({
        url: "/controller/SuplierCtr",
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
                        {'data': 'id_supplier', 'name': 'id_supplier', 'type': 'string'},
                        {'data': 'nama_supplier'},
                        {'data': 'alamat'},
                        {'data': 'no_hp'},
                        {'data': 'email'},
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
        id_supplier = $("#id_supplier").val();
        nama_supplier = $("#nama_supplier").val();
        alamat = $("#alamat").val();
        no_hp = $("#no_hp").val();
        email = $("#email").val();
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
        if(id_supplier === ""){
            alert("ID sup harus diisi");
            $("#id_supplier").focus();
        } else if(nama_supplier=== ""){
            alert("nama supplier harus diisi");
            $("#nama_supplier").focus();
        } else if(alamat=== ""){
            alert("alamat harus diisi");
            $("#alamat").focus();
        } else if(no_hp === ""){
            alert("no hp harus diisi");
            $("#no_hp").focus();
        }  else if(email === ""){
            alert("emial supplier harus diisi");
            $("#email").focus();
        }else if(waktu === ""){
            alert("waktu harus diisi");
            $("#waktu").focus();
        }else{
            $.post("/controller/SuplierCtr",
                {
                    page: page,
                    id_supplier: id_supplier,
                    nama_supplier: nama_supplier,
                    alamat: alamat,
                    no_hp: no_hp,
                    email: email,
                    waktu: waktu,
                    id_user: id_user,
     
                    
                },
                function(data, status){
                    alert(data+" "+status);
                    if(data === "Data berhasil disimpan"){location.reload();}
                }
            );
        }
    });
    
    //edit data
    $("#tabelSupplier tbody").on('click', '#btnEdit', function(){
        $("#myModal").show();
        $("#title1").hide();
        $("#title2").show();
        $("#id_supplier").prop('disabled', true);
        page = "tampil";
        
        //dapetin baris yang di klik
        var baris = $(this).closest('tr');
        var id_kamar = baris.find("td:eq(0)").text();
        $.post("/controller/SuplierCtr",
            {
                page: page,
                id_supplier: id_supplier
            },
            function(data, status){
                //karena data dari database itu sudah diubah di DAO, 
                //dimana jika L menjadi Laki-Laki, dan P menjadi Perempuan,
                //karena value di dropdown itu pakai L dan P, 
                //supaya ketika di tampilkan ke database gak perlu ubah lagi
                
                //ubah format waktu dari database ke format yang benar (YYYY-MM-ddThh:mm:ss
                let d = new Date(new Date().toString().split('GMT')[0]+' UTC').toISOString().split('.')[0];
                $("#id_supplier").val(data.id_supplier);
                $("#nama_supplier").val(data.nama_supplier);
                $("#alamat").val(data.alamat);
                $("#no_hp").val(data.no_hp);
                $("#email").val(data.email);
                $("#waktu").val(data.waktu);
                $("#id_user").val(data.id_user);

              
            }
        );
        page = "edit";
    });
    
    //hapus data
    $("#tabelUser tbody").on('click', '#btnDelete', function(){
        //dapetin baris yang di click
        var baris = $(this).closest('tr');
        var id_kamar = baris.find("td:eq(0)").text();
        var nama_kamar = baris.find("td:eq(1)").text();
        page = "hapus";
        //konfirmasi jika akan dihapus
        if(confirm("Apakah anda yakin akan menghapus data : '" + id_kamar + " - " + nama_kamar +"' ?")){
            $.post("/controller/SuplierCtr",
                {
                    page: page,
                    id_supplier: id_supplier
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
    function loadUser() {
       loadUser = 1;
       $.ajax({
            url: "/controller/UserCtr",
            method: "GET", 
            dataType: "json",
            success: function(data){
                $("#tabelLookupSuplier").dataTable({
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
        $("#modalLookupSupplier").modal('show');
        if (loadUser !== 1) {
            loadUser();
        }
        $("#tabelLookupSupplier tbody").on('click', '#btnInsertSupplier', function() {
            let baris = $(this).closest('tr');
            let id = baris.find("td:eq(0)").text();
            $("#id_user").val(id);
            $("#modalLookupSupplier").modal("hide");
        });
    });
    
    //clear input 
    function clearForm() {
        $("#id_supplier").val("");
        $("#nama_supplier").prop('disabled', false);
        $("#alamat").val("");
        $("#no_hp").val("");
        $("#email").val("");
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





