var TableDatatablesManaged = function () {

    var oTable;
    var initTable3 = function () {
        var table = $('#myTable');
        //oTable = table.dataTable({   这玩意D大写就好使  d小写就有些方法不好使
        oTable = table.DataTable({
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
            "sAjaxSource": ctx + "/tom/api/study/upload/list",
            "sServerMethod": "POST",
            "fnServerParams": function ( aoData ) {
                var type = $("#typeName").val();
                aoData.push({
                    "name": "conditions['type']",
                    "value": type
                });
            },
            "aoColumns": [
                { "data": "fileName","bSortable": false,"sClass": "text-center"},
                { "data": "type","bSortable": false,"sClass": "text-center" },
                { "data": "fileSize","bSortable": false,"sClass": "text-center" },
                { "data": "id","bSortable": false,"sClass": "text-center" },
            ],
            "createdRow": function ( row, data, index ) {
                var rid = data.id;
                var fileSize = data.fileSize;
                fileSize = fileSize/1000;
                $('td',row).eq(2).html(fileSize.toFixed(2)+"KB");
                var html = "";
                html+='<a onclick="javascript:TableDatatablesManaged.del(\''+rid+'\');">删除 </a>';
                $('td',row).eq(3).html(html);

                /*if( $("#initTableRow").val() == data.examId){
                    var open = setInterval(function() {
                        $(`#myTable`).find('tbody').find(`tr:eq(${index})`).find('td:eq(2)').click();
                        clearInterval(open)
                    }, 10)
                }*/

            }
        });
        $("#btn_search").on('click',function(){
            $('#myTable').dataTable().fnClearTable(0);
            $('#myTable').dataTable().fnDraw();
        })

        //点击之后下面出现一行
        $('#myTable tbody').on('click', 'td', function (event) {
            if (this != event.target) return;
            var tr = $(this).closest('tr');
            var row = oTable.row(tr);
            if (row.length == 0) {
                return;
            }
            var eventData = {
                row: row,
                eventEle: this,
                oTable: oTable
            };
            if ( row.child.isShown() ) {
                // This row is already open - close it
                row.child.hide();
                tr.removeClass('shown');
            }
            else {
                subrowControl.format(eventData);
                tr.addClass('shown');
            }
        });
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
                title: {
                    required: true
                },
                contentFile: {
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
                var formData = new FormData(form[0]);
                var action = form.attr("action");
                var method = form.attr("method");
                //图片转为base64
                var imageObj = document.getElementById("contentFile").files[0];
                var fr = new FileReader();
                fr.readAsDataURL(imageObj);
                fr.onload = function (event) {
                    var content = event.target.result; //此处获得的data是base64格式的数据
                    formData.append("content",content);
                    request(action,method,formData,function(response){
                        window.location.reload();
                    });
                }
            }
        });
    }
    function del(id) {
        var url = ctx+'/tom/api/study/upload/del';
        var formData = new FormData();
        formData.append("id",id);
        var f = confirm("你确定要删除吗?");
        if(!f){
            return false;
        }
        TableDatatablesManaged.request(url,"post",formData,function(response){
            $('#myTable').dataTable().fnClearTable(0);
            $('#myTable').dataTable().fnDraw();
        });
    }


    return {
        init: function () {
            if (!jQuery().dataTable) {
                return;
            }
            initTable3();
        },
        initForm: function (button) {
            var formAdd = $("#formAdd");
            if(formAdd){
                handleOnsubmit(formAdd);
            }
        },
        request:request,
        del:del,
    };
}();

jQuery(document).ready(function() {
    TableDatatablesManaged.init();
    TableDatatablesManaged.initForm();
});


function back() {
    $("#addModal").modal('hide');
}