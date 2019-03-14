var complaintList = function () {
	
	var oTable;
	
	var initTable = function () {
		oTable = $('#myTable').dataTable({
			"bProcessing" : true,
			"bServerSide" : true,
			"bFilter" : false,
			"sServerMethod" : "GET",
			"bLengthChange":false,
			"iDisplayLength" : 10,
			"bAutoWidth" : false,
			"jQueryUI" : false,
			"ordering" : false,
			"sAjaxSource" : ctx+"/nmis/api/complaint/list",
			'fnServerParams' : function(aoData) {
				/*
				var orgName = $("#orgname").val();
				var patientName = $("#patientname").val();
				aoData.push({
				    "name": "conditions['orgName']",
				    "value": orgName
				},{
				    "name": "conditions['patientName']",
				    "value": patientName
				});
				*/
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
				{ "data": "submitDatetime","sClass": "center"},
				{ "data": "targetName","bSortable": false,"sClass": "center"},
				{ "data": "content","bSortable": false,"sClass": "center" },
				{ "data": "submitter","bSortable": false,"sClass": "center" },
				{ "data": "targetName","bSortable": false,"sClass": "center" },
				{ "data": "status","bSortable": false,"sClass": "center" },
				{ "data": "id","bSortable": false,"sClass": "center" }
			],
			"createdRow" : function(row, data, index) {
				var id = data.id;
				var operlook = '<a href="javascript:complaintList.onLook(\'' + id + '\')" class="btn btn-xs btn-default"><i class="fa fa-folder-open-o"></i> ' + '查看' + ' </a>';
				$('td', row).eq(2).prop("outerHTML",'<td class=" center" title="' + data.content + '">' + data.content.substring(0,5) + (data.content.length > 5 ? '...' : '') + '</td>');
				$('td', row).eq(6).html(operlook);
				var status = data.status;
				if(status == 0){
					$('td', row).eq(5).html("未审核");
				}else if(status == 1){
					$('td', row).eq(5).html("已审核");
				}
			}
		});
	}
	
	return {
		'init' : function () {
			initTable();
		},
		'onLook' : function (id) {
			if(id != null && id != ''){
				location.href = ctx + "/nmis/quality/complaint/complaintDetail/" + id;
			}
		}
	};
}();

$(document).ready(function () {
	complaintList.init();
});