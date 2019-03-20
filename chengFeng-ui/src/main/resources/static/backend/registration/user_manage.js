var TableDatatablesManaged = function () {
    var oTable;
    var initTable = function () {
        oTable = $('#myTable')
            .dataTable(
                {
                    "bProcessing" : true,
                    "bServerSide" : true,
                    "bFilter" : false,// 去掉搜索框
                    "sServerMethod" : "POST",
                    "bLengthChange":false,
                    "iDisplayLength" : 10,
                    "bAutoWidth" : false,
                    "jQueryUI" : false,
                    "ordering" : false,// 是否可以排序
                    "sAjaxSource" : ctx+"/tom/api/user/list",
                    'fnServerParams' : function(aoData) {
                    },
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
                    "aoColumns" : [
                        { "data": "createdAt","sClass": "center"},
                        { "data": "userName","bSortable": false,"sClass": "center"},
                        { "data": "realName","bSortable": false,"sClass": "center" },
                        { "data": "mobile","bSortable": false,"sClass": "center" },
                        { "data": "orgName","bSortable": false,"sClass": "center" },
                        { "data": "status","bSortable": false,"sClass": "center" },
                        { "data": "userId","bSortable": false,"sClass": "center" } ],
                    "createdRow" : function(row, data,
                                            index) {
                        var userId = data.userId;
                        var operlook = '<a href="javascript:TableDatatablesManaged.onLook(\''+userId +'\')" class="btn btn-xs btn-default"><i class="fa fa-folder-open-o"></i> '
                            + '查看' + ' </a>';
                        $('td', row).eq(6).addClass(
                            "hidden-480").html(
                            operlook);
                        var status = data.status;
                        if(status == 0){
                            $('td', row).eq(5).html("未启用");
                        }else if(status == 1){
                            $('td', row).eq(5).html("已启用");
                        }
                    }
                });

    }

    var onLook =  function onLook(userId){
        window.location.href=ctx+"/tom/user/manage/detail/"+userId;
    }

    return {
        init: function () {
            initTable();
        },
        onLook:function (userId) {
            onLook(userId);
        }
    };

}();
jQuery(document).ready(function() {
    TableDatatablesManaged.init();
});
