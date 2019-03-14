var TableDatatablesManaged = function () {
    var oTable;
    var initTable3 = function () {
        var table = $('#MyTable');
        var hasApplyPermission = $('#hasApplyPermission').length > 0;
        var status = $('#menu_entry').val();
        oTable = table.dataTable({
            "language": {
                "aria": {
                    "sortAscending": ": activate to sort column ascending",
                    "sortDescending": ": activate to sort column descending"
                },
                "emptyTable": "无",
                "info": "显示 _START_ - _END_ 共 _TOTAL_ 条记录",
                "infoEmpty": "无记录",
                "infoFiltered": "(filtered1 from _MAX_ total records)",
                "lengthMenu": "Show _MENU_",
                "search": "查找:",
                "zeroRecords": "无记录",
                "paginate": {
                    "previous":"上一页",
                    "next": "下一页",
                    "last": "最后",
                    "first": "首页"
                }
            },
            "bStateSave": true,
            "bFilter": false,
            "bDeferRender":true,
            "bInfo":true,
            "bLengthChange":false,
            "bAutoWidth":false,
            "bSort" : false,
            "pageLength": 10,
            "bProcessing": true,
            "bServerSide": true,
            "sAjaxSource": ctx + "/nmis/api/registrationDoctor/list",
            "sServerMethod": "POST",
            "fnServerParams": function ( aoData ) {
                var userId=$('#userId').val();
                aoData.push({
                        "name" : "conditions['userId']",
                        "value" : userId
                    });
            },
            "aoColumns": [
                { "data": "name","bSortable": false,"sClass": "text-center"},
                { "data": "gender","bSortable": true,"sClass": "text-center"},
                { "data": "ethnicity","bSortable": false,"sClass": "text-center" },
                { "data": "departId","bSortable": false,"sClass": "text-center" },
                { "data": "professionalTitle","bSortable": false,"sClass": "text-center" },
                { "data": "professionalTitle","bSortable": false,"sClass": "text-center" },
            ],
            "createdRow": function ( row, data, index ) {
                var doctorId = data.id;
                var gender = gender_dic.getGenderNameByCode(data.gender);
                var ethnicity = ethnicity_dic.getEthnicityNameByCode(data.ethnicity);
                var education = education_dic.getEducationNameByCode(data.education);
                var professionalTitle = professionalTitle_dic.getProfessionalTitleNameByCode(data.professionalTitle);

                $('td',row).eq(1).html(gender);
                $('td',row).eq(2).html(ethnicity);

                getDepartById(data.departId, function (response) {
                    $('td',row).eq(3).html(response.data.depart);
                });
                $('td',row).eq(4).html(professionalTitle);

                var op = `<a style="text-decoration:none;" onclick="javascript:TableDatatablesManaged.view('${doctorId}');"> [查看] </a>`;
                /*if ($('#isSelf').length > 0) {
                    op += `<a style="text-decoration:none;" onclick="javascript:TableDatatablesManaged.delete('${doctorId}');"> [删除] </a>`;
                }*/
                $('td',row).eq(5).html(op);
            }
        });
    }

    function getDepartById(departId,callback) {
        var url = ctx + '/nmis/api/apply/registrationDepart/'+departId;
        request(url,'get',null,function (response) {
            if (response && response.data) {
                callback(response);
            }
        })
    }
    
    function infoView(id) {
        getDoctorById(id);
        $("#myModal").modal('show');
    }

    var request = function (url,method,data,success){
        $.ajax({
            url: window.location.protocol + '//' + window.location.host + url,
            type: method,
            data: data,
            cache: false,
            contentType: false,
            dataType: "json",
            async:false,  //false 同步请求
            processData: false,
            success: success,
            error:function (XMLHttpRequest, textStatus, errorThrown){
                console.log(XMLHttpRequest);
            }
        });
    };

    function getDoctorById(id) {
        var url = ctx+'/nmis/api/registrationDoctor/'+id;
        request(url,'GET',null,function (response) {
            for(var key in response.data){
                if (key == 'photo'){
                    $('#photo').attr('src',response.data[key]);
                    continue;
                }
                if (key == 'signature'){
                    $('#signature').attr('src',response.data[key]);
                    continue;
                }
                if (key == 'gender') {
                    $('#gender').html(gender_dic.getGenderNameByCode(response.data[key]));
                    continue;
                }
                if (key == 'ethnicity') {
                    $('#ethnicity').html(ethnicity_dic.getEthnicityNameByCode(response.data[key]));
                    continue;
                }
                if (key == 'education') {
                    $('#education').html(education_dic.getEducationNameByCode(response.data[key]));
                    continue;
                }
                if (key == 'departId') {
                    getDepartById(response.data[key], function (response) {
                        $('#departId').html(response.data.depart);
                    });
                    continue;
                }
                if (key == 'cardType') {
                    $('#cardType').html(cardType_dic.getCardTypeNameByCode(response.data[key]));
                    continue;
                }
                if (key == 'professionalTitle') {
                    $('#professionalTitle').html(professionalTitle_dic.getProfessionalTitleNameByCode(response.data[key]));
                    continue;
                }
                $('#'+key).html(response.data[key]);
            }
        });
    }

    return {
        init: function () {
            if (!jQuery().dataTable) {
                return;
            }
            initTable3();

            $("#myModal").modal({
                backdrop: 'static',/*背景变暗，且关闭模态窗体后才能操作页面上的内容*/
                show: false
            });
        },
        view: infoView
    };
}();

jQuery(document).ready(function() {
    TableDatatablesManaged.init();
    var href = $('#addBtn').attr('href');
    var userId = $('#userId').val();
    var status = $('#status').val();
    href = href + `?userId=${userId}&status=${status}`
    var href = $('#addBtn').attr('href',href);
});
