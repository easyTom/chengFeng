var TableDatatablesManaged = function () {
    var oTable;
    var initTable3 = function () {
        var table = $('#MyTable');
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
            "sAjaxSource": ctx + "/nmis/supervise/list",
            "sServerMethod": "POST",
            "fnServerParams": function ( aoData ) {
                var userId=$('#userId').val();
                aoData.push({
                        "name" : "conditions['userId']",
                        "value" : userId
                    });
            },
            "aoColumns": [
                { "data": "ruleGroup","bSortable": false,"sClass": "text-center"},
                { "data": "ruleTitle","bSortable": true,"sClass": "text-center"},
                { "data": "ruleMemo","bSortable": false,"sClass": "text-center" },
                { "data": "validator","bSortable": false,"sClass": "text-center" },
                { "data": "enabled","bSortable": false,"sClass": "text-center" },
                { "data": "enabled","bSortable": false,"sClass": "text-center" },
            ],
            "createdRow": function ( row, data, index ) {
                var rid = data.id;
                $('td',row).eq(5).html('<a style="text-decoration:none;" onclick="javascript:TableDatatablesManaged.view(\''+rid+'\');"> 修改 </a>'+
                    '<a style="text-decoration:none;" onclick="del(\''+rid+'\');"> 删除 </a>');
            }
        });
    }
    
    function infoView(id) {
        getSuperviseById(id);
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
    var handleOnsubmit = function(form){
        var error1 = $('.alert-danger', form);
        var success1 = $('.alert-success', form);
        form.validate({
            errorElement: 'span',
            errorClass: 'help-block help-block-error',
            focusInvalid: false,
            ignore: "",
            rules: {
                ruleTitle: {
                    required: true
                },
                ruleGroup: {
                    required: true
                }
            },

            invalidHandler: function (event, validator) { //display error alert on form submit
                success1.hide();
                error1.show();
                App.scrollTo(error1, -200);
            },
            errorPlacement: function (error, element) { // render error placement for each input type
                // var cont = $(element).parent('.input-group');
                // if (cont.size() > 0) {
                //     cont.after(error);
                // } else {
                //     element.after(error);
                // }
            },
            highlight: function (element) { // hightlight error inputs
                $(element)
                    .closest('.form-group').addClass('has-error'); // set error class to the control group
            },

            unhighlight: function (element) { // revert the change done by hightlight
                $(element)
                    .closest('.form-group').removeClass('has-error'); // set error class to the control group
            },
            success: function (label) {
                label
                    .closest('.form-group').removeClass('has-error'); // set success class to the control group
            },
            submitHandler: function (form1) {
                success1.show();
                error1.hide();
                var action = form.attr("action");
                var method = form.attr("method");
                var formData = new FormData(form[0]);
                request(action,method,formData,function(response){
                    window.location.reload();
                });
            }
        });
    }
    function getSuperviseById(id) {
        var url = ctx+'/nmis/supervise/'+id;
        request(url,'GET',null,function (response) {
            console.log(response)
            console.log(response.data.enabled==true)
            if(response.data.enabled){
                $("#enabledUpdateTrue").prop('checked',true);
            }else{
                $("#enabledUpdateFalse").prop('checked',true);
            }
            $("[name=id]").val(response.data.id);
            for(var key in response.data){
                $("#"+key+"Update").val(response.data[key])
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
        view: infoView,
        initForm: function (button) {
            var formUpdate = $("#formUpdate");
            if(formUpdate){
                handleOnsubmit(formUpdate);
            }
            var formAdd = $("#formAdd");
            if(formAdd){
                handleOnsubmit(formAdd);
            }
        },
        request:request,
    };
}();

jQuery(document).ready(function() {
    TableDatatablesManaged.init();
    TableDatatablesManaged.initForm();
});

function add() {
    $("#addModal").modal('show');
}
function del(id) {
    var url = ctx+'/nmis/supervise/del';
    var f  = confirm("是否删除本条数据");
    var formData = new FormData();
    formData.append("id",id);
    if(f){
        TableDatatablesManaged.request(url,"post",formData,function(response){
            window.location.reload();
        });
    }else{

    }
}
