$(document).ready(function(){
    $.ajax ({
        url: "/WebKoperasi/KaryawanCTR",
        method: "GET",
        dataType: "json",
        success:
            function(data){
                $("#tableDokter").dataTable({
                    serverside: true,
                    processing: true,
                    data: data,
                    sort: true,
                    searching: true,
                    paging: true,
                    columns: [
                        {'data': 'nik', 'name': 'nik', 'type': 'string'},
                        {'data': 'nama'},
                        {'data': 'gender'},
                        {'data': 'tmplahir'},
                        {'data': 'tgllahir', className: 'text-center'},
                        {'data': 'alamat'},
                        {'data': 'telepon'},
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
});

