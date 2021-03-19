/* global $scope */

$(document).ready(function(){
    //deklarasi variabel
    var id_kamar, nama_ruang, no_ruang, kelas, des_kamar, kappasitas, terisi, status, page;
    
    //get data dari database ke table
    $.ajax ({
        url: "/controller/KamarCtr",
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
                        {'data': 'id_kamar', 'name': 'id_kamar', 'type': 'string'},
                        {'data': 'nama_ruang'},
                        {'data': 'no_ruang'},
                        {'data': 'kelas'},
                        {'data': 'des_kamar'},
                        {'data': 'kapasitas'},
                        {'data': 'terisi'},
                        {'data': 'status'},
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
        id_kamar = $("#id_kamar").val();
        nama_ruang = $("#nama_ruang").val();
        no_ruang = $("#no_ruang").val();
        kelas = $("#kelas").val();
        des_kamar = $("#des_kamar").val();
        kapasitas = $("#kapasitas").val();
        terisi = $("#terisi").val();
        status = $("#status").val();
        
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
        if(id_kamar === ""){
            alert("ID kamar harus diisi");
            $("#id_kamar").focus();
        } else if(nama_ruang === ""){
            alert("nama ruang harus diisi");
            $("#nama_ruang").focus();
        } else if(no_ruang=== ""){
            alert("nomor ruang harus diisi");
            $("#no_ruang").focus();
        } else if(kelas === ""){
            alert("kelas harus diisi");
            $("#kelas").focus();
        }  else if(des_kamar === ""){
            alert("deskripsi kamar harus diisi");
            $("#des_kamar").focus();
        }else if(kapasitas === ""){
            alert("kapasitas kamar harus diisi");
            $("#kapasitas").focus();
        }else if(terisi === ""){
            alert("form harus diisi");
            $("#terisi").focus();
        }else if(status === ""){
            alert("status kamar harus diisi");
            $("#status").focus();
        }else{
            $.post("/controller/KamarCtr",
                {
                    page: page,
                    id_kamar: id_kamar,
                    nama_ruang: nama_ruang,
                    no_ruang: no_ruang,
                    kelas: kelas,
                    des_kamar: des_kamar,
                    kapasitas: kapasitas,
                    teisi: terisi,
                    status: status,
                    
                },
                function(data, status){
                    alert(data+" "+status);
                    if(data === "Data berhasil disimpan"){location.reload();}
                }
            );
        }
    });
    
    //edit data
    $("#tabelKamar tbody").on('click', '#btnEdit', function(){
        $("#myModal").show();
        $("#title1").hide();
        $("#title2").show();
        $("#id_Kamar").prop('disabled', true);
        page = "tampil";
        
        //dapetin baris yang di klik
        var baris = $(this).closest('tr');
        var id_kamar = baris.find("td:eq(0)").text();
        $.post("/controller/KamarCtr",
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
                $("#id_kamar").val(data.id_kamar);
                $("#nama_ruang").val(data.nama_ruang);
                $("#no_ruang").val(data.no_ruang);
                $("#kelas").val(data.kelas);
                $("#des_kamar").val(data.des_kamar);
                $("#kapasitas").val(data.kapsitas);
                $("#terisi").val(data.terisi);
                $("#status").val(data.status);
              
            }
        );
        page = "edit";
    });
    
    //hapus data
    $("#tabelKamar tbody").on('click', '#btnDelete', function(){
        //dapetin baris yang di click
        var baris = $(this).closest('tr');
        var id_kamar = baris.find("td:eq(0)").text();
        var nama_kamar = baris.find("td:eq(1)").text();
        page = "hapus";
        //konfirmasi jika akan dihapus
        if(confirm("Apakah anda yakin akan menghapus data : '" + id_kamar + " - " + nama_kamar +"' ?")){
            $.post("/controller/KamarCtr",
                {
                    page: page,
                    id_kamar: id_kamar
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
        $("#id_kamar").val("");
        $("#id_kamar").prop('disabled', false);
        $("#nama_ruang").val("");
        $("#no_ruang").val("");
        $("#kelas").val("");
        $("#des_kamar").val("");
        $("#kapasitas").val("");
        $("#terisi").val("");
        $("#status").val("");
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


