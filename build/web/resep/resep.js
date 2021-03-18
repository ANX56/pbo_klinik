/* global $scope */

$(document).ready(function(){
    //deklarasi variabel
    var id_resep, id_obat, jumlah, keterangan, waktu, id_user, page;
    
    //get data dari database ke table
    $.ajax ({
        url: "/Klinik/ResepCTR",
        method: "GET",
        dataType: "json",
        success:
            function(data){
                $("#tabelResep").dataTable({
                    serverside: true,
                    processing: true,
                    data: data,
                    sort: true,
                    searching: true,
                    paging: true,
                    columns: [
                        {'data': 'id_resep', 'name': 'id_resep', 'type': 'string'},
                        {'data': 'id_obat'},
                        {'data': 'jumlah'},
                        {'data': 'keterangan'},
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
        id_resep = $("#id_resep").val();
        id_obat = $("#id_obat").val();
        jumlah = $("#jumlah").val();
        keterangan = $("#keterangan").val();
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
        if(id_resep === ""){
            alert("ID Resep harus diisi");
            $("#id_resep").focus();
        } else if(id_obat === ""){
            alert("ID Obat harus diisi");
            $("#id_obat").focus();
        } else if(jumlah === ""){
            alert("Jumlah harus diisi");
            $("#jumlah").focus();
        } else{
            $.post("/Klinik/ResepCTR",
                {
                    page: page,
                    id_resep: id_resep,
                    id_obat: id_obat,
                    jumlah: jumlah,
                    keterangan: keterangan,
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
    $("#tabelResep tbody").on('click', '#btnEdit', function(){
        $("#myModal").show();
        $("#title1").hide();
        $("#title2").show();
        $("#id_resep").prop('disabled', true);
        page = "tampil";
        
        //dapetin baris yang di klik
        var baris = $(this).closest('tr');
        var id_resep = baris.find("td:eq(0)").text();
        $.post("/Klinik/ResepCTR",
            {
                page: page,
                id_resep: id_resep
            },
            function(data, status){
                let d = new Date(new Date().toString().split('GMT')[0]+' UTC').toISOString().split('.')[0];
                $("#id_resep").val(data.id_resep);
                $("#id_obat").val(data.id_obat);
                $("#jumlah").val(data.jumlah);
                $("#keterangan").val(data.keterangan);
                $("#waktu").val(d);
                $("#id_user").val(data.id_user);
            }
        );
        page = "edit";
    });
    
    //hapus data
    $("#tabelResep tbody").on('click', '#btnDelete', function(){
        //dapetin baris yang di click
        var baris = $(this).closest('tr');
        var id_resep = baris.find("td:eq(0)").text();
        var id_obat = baris.find("td:eq(1)").text();
        page = "hapus";
        //konfirmasi jika akan dihapus
        if(confirm("Apakah anda yakin akan menghapus data : '" + id_resep + " - " + id_obat +"' ?")){
            $.post("/Klinik/ResepCTR",
                {
                    page: page,
                    id_resep: id_resep
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
    function loadObat() {
        loadObat = 1;
        $.ajax({
            url: "/Klinik/ObatCTR",
            method: "GET", 
            dataType: "json",
            success: function(data){
                $("#tabelLookupObat").dataTable({
                serverside: true,
                processing: true,
                data: data,
                sort: true,
                searching: true,
                paging: true,
                columns: [
                        {'data': 'id_obat', 'name': 'id_obat', 'type': 'string'},
                        {'data': 'nama_obat'},
                        {'data': null, 'className': 'dt-right', 'mRender': function(o){
                                return "<a class='btn btn-warning btn-sm'"
                                + "id = 'btnInsertObat'>Insert</a>";
                            }
                        }
                    ]
                });
            }
        });
    }
    
    $("#btn-lookup-obat").click(function() {
        $("#modalLookupObat").modal('show');
        if (loadObat !== 1) {
            loadObat();
        }
        $("#tabelLookupObat tbody").on('click', '#btnInsertObat', function() {
            let baris = $(this).closest('tr');
            let keterangan = baris.find("td:eq(0)").text();
            let nama_poli = baris.find("td:eq(1)").text();
            $("#id_obat").val(keterangan);
            $("#nama_obat").val(nama_poli);
            $("#modalLookupObat").modal("hide");
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
        $("#id_resep").val("");
        $("#id_resep").prop('disabled', false);
        $("#id_obat").val("");
        $("#jumlah").val("");
        $("#alamat").val("");
        $("#keterangan").val("");
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