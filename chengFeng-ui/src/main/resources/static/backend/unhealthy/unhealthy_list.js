var unhealthyList = function () {
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
			"sAjaxSource" : ctx+"/nmis/api/unhealthy/list",
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
				{ "data": "occurDatetime","sClass": "center" },
				{ "data": "patientName","bSortable": false,"sClass": "center" },
				{ "data": "diagnosis","bSortable": false,"sClass": "center" },
				{ "data": "accidentLevel","bSortable": false,"sClass": "center" },
				{ "data": "reportDatetime","bSortable": false,"sClass": "center" },
				{ "data": "status","bSortable": false,"sClass": "center" },
				{ "data": "replyDatetime","bSortable": false,"sClass": "center" },
				{ "data": "id","bSortable": false,"sClass": "center" }
			],
			"createdRow" : function(row, data, index) {
				var id = data.id;
				var status = data.status;
				if(status == 0){
					$('td', row).eq(5).html("已上报");
				}else if(status == 1){
					$('td', row).eq(5).html("已批复");
				}
				var proccess = '<a href="javascript:unhealthyList.onProccess(\'' + id + '\')" class="btn btn-xs btn-default"><i class="fa fa-folder-open-o"></i> ' + '处理' + ' </a>';
				$('td', row).eq(7).html(proccess);
			}
		});
	}
	
	var addValidate = function (id) {
		$("#modal #submit_form").validate({
			errorClass : 'errorStyle',
			messages : {
				memo : {
					required : '事件批复不能为空'
				}
			},
			rules : {
				memo : {
					required : false
				}
			},
			submitHandler : function(form) {
				var param = {
					memo : $("#modal #memo").val(),
					id : id
				};
				
				$.ajax({
					type : 'post',
					url : ctx + '/nmis/api/unhealthy/reply',
					async : false,
					data : param,
					dataType : 'json',
					success : function(data){
						if(data.resultCode == '200'){
							oTable.fnDraw(false);
						}else{
							alert("批复失败");
						}
						$("#modal").modal('hide');
					},
					error : function(data){
						console.log(data.responseText);
					}
				});
			}
		});
	}
	
	return {
		'init' : function () {
			initTable();
		},
		'onProccess' : function (id) {
			addValidate(id);
			$("#modal").modal('show');
		}
	};
}();

$(function () {
	unhealthyList.init();
});