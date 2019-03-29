var TableDatatablesManaged = function () {

    var addDate = function () {
        $("#sdate").datepicker({
            rtl: App.isRTL(),
            orientation: "left",
            language: 'zh-CN',
            format: 'yyyy-mm-dd',
            endDate: new Date(),
            autoclose: true
        });

        $("#edate").datepicker({
            rtl: App.isRTL(),
            orientation: "left",
            language: 'zh-CN',
            format: 'yyyy-mm-dd',
            endDate: new Date(),
            autoclose: true
        });
    }
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
            "sAjaxSource": ctx + "/tom/api/study/mistake/list",
            "sServerMethod": "POST",
            "fnServerParams": function ( aoData ) {
                var name = $("#cname").val();
                var content = $("#ccontent").val();
                var ctype = $("#ctype").val();
                var sdate = $("#sdate").val();
                var edate = $("#edate").val();
                aoData.push({
                    "name": "conditions['name']",
                    "value": name
                },{
                    "name": "conditions['content']",
                    "value": content
                },{
                    "name": "conditions['ctype']",
                    "value": ctype
                },{
                    "name": "conditions['sdate']",
                    "value": sdate
                },{
                    "name": "conditions['edate']",
                    "value": edate
                });
            },
            "aoColumns": [
                { "data": "updateAt","bSortable": false,"sClass": "text-center"},
                { "data": "type","bSortable": true,"sClass": "text-left"},
                { "data": "name","bSortable": false,"sClass": "text-center" },
                { "data": "content","bSortable": false,"sClass": "text-center" },
                { "data": "userName","bSortable": false,"sClass": "text-center" },
                { "data": "important","bSortable": false,"sClass": "text-center" },
                { "data": "id","bSortable": false,"sClass": "text-center" },
            ],
            "createdRow": function ( row, data, index ) {
                var rid = data.id;
                var content = data.content;
                var updateAt = data.updateAt;
                var important = data.important;
                var html = "";
                var showHtml = "";
                var contentHtml = "";
                html+='<a onclick="javascript:TableDatatablesManaged.view(\''+rid+'\');" class="btn btn-sm btn-default"><i class="fa fa-edit"></i> 修改 </a>';
                if(important == '1'){
                    showHtml = '<a href="javascript:TableDatatablesManaged.updateImportant(\''+rid+'\',0)" class="label label-sm label-success" > 是</a>';
                }else if(important == '0'){
                    showHtml = '<a href="javascript:TableDatatablesManaged.updateImportant(\''+rid+'\',1)" class="label label-sm label-default" > 否</a>';
                }
                    contentHtml += '<span style="cursor:pointer" onclick="javascript:TableDatatablesManaged.showText(\''+content+'\')";>'+content+'</span>';
                $('td',row).eq(5).html(showHtml)
                $('td',row).eq(3).html(contentHtml);
                $('td',row).eq(6).html(html);

            }
        });
        $("#btn_search").on('click',function(){
            oTable.fnDraw(false);
        })
    }


    function infoView(id) {
        getMistakesById(id);
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
            invalidHandler: function (event, validator) { //display error alert on form submit
                success1.hide();
                error1.show();
                App.scrollTo(error1, -200);
            },

            rules: {
                name: {
                    required: true
                },
                content: {
                    required: true
                }
            },
            errorPlacement: function (error, element) { // render error placement for each input type
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
    function getMistakesById(id) {
        var url = ctx+'/tom/api/study/mistake/getMistakesById'+id;
        request(url,'GET',null,function (response) {
            $("[name=id]").val(response.data.id);
            for(var key in response.data){
                $("#"+key+"Update").val(response.data[key])
            }
        });

    }

    function updateImportant(id,status) {
        var url = ctx+'/tom/api/study/mistake/updateImportant';
        var formData = new FormData();
        formData.append("id",id);
        formData.append("important",status);
        TableDatatablesManaged.request(url,"post",formData,function(response){
            oTable.fnClearTable(0);
            oTable.fnDraw();
        });
    }

    function add() {
        $("#addModal").modal('show');
    }

    function showText(o) {
        $("#text").empty();
        $("#text").append("<p>"+o+"<p/>");
        $("#textModal").modal('show');
    }


    return {
        init: function () {
            if (!jQuery().dataTable) {
                return;
            }
            initTable3();
            addDate();
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
        updateImportant:updateImportant,
        showText:showText,
        add:add
    };
}();

jQuery(document).ready(function() {
    TableDatatablesManaged.init();
    TableDatatablesManaged.initForm();
});


function back() {
    $("#myModal").modal('hide');
    $("#addModal").modal('hide');
}