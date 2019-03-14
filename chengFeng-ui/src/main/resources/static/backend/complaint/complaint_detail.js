var complaintDetail = function () {
	
	var initTable = function () {
		$.ajax({
			type : 'get',
			url : ctx + '/nmis/api/complaint/detail/' + id,
			dataType : 'json',
			success : function (data) {
				var complaint = data.data;
				if(complaint != null){
					$("td#orgCode").html(complaint.orgCode);
					$("td#targetName").html(complaint.targetName);
					$("td#patientName").html(complaint.patientName);
					$("td#content").html(complaint.content);
					$("td#appeal").html(complaint.appeal);
					$("td#submitter").html(complaint.submitter);
					$("td#telephone").html(complaint.telephone);
					$("td#submitDatetime").html(complaint.submitDatetime);
					$("td#status").html(complaint.status);
					$("td#handleUserId").html(complaint.handleUserId);
					$("td#handleResult").html(complaint.handleResult);
					$("td#handleDatetime").html(complaint.handleDatetime);
					$("td#orgCode").html(complaint.orgCode);
					$("td#targetName").html(complaint.targetName);
					$("td#patientName").html(complaint.patientName);
					$("td#content").html(complaint.content);
					$("td#appeal").html(complaint.appeal);
					$("td#submitter").html(complaint.submitter);
					$("td#telephone").html(complaint.telephone);
					$("td#submitDatetime").html(complaint.submitDatetime);
					if(complaint.status == 0){
						$("td#status").html('未处理');
						$("#processButton").css({display:''});
					}else{
						$("td#status").html('已处理');
						$("#processButton").css({display:'none'});
					}
					$("td#handleUserId").html(complaint.handleUserId);
					$("td#handleResult").html(complaint.handleResult);
					$("td#handleDatetime").html(complaint.handleDatetime);
				}
			},
			error : function (data) {
				console.log(data.responseText);
			}
		});
	}
	
	var addValidate = function () {
		$("#modal #submit_form").validate({
			errorClass : 'errorStyle',
			messages : {
				handleResult : {
					required : '处理结果不能为空'
				}
			},
			rules : {
				handleResult : {
					required : true
				}
			},
			submitHandler : function(form) {
				$.ajax({
					type : 'post',
					url : ctx + '/nmis/api/complaint/process/' + id,
					async : false,
					data : {handleResult:$("#modal #handleResult").val()},
					dataType : 'json',
					success : function(data){
						if(data.data){
							location.reload();
						}else{
							alert("处理失败");
						}
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
		'process' : function () {
			addValidate();
			$("#modal").modal("show");
		}
	};
}();

$(document).ready(function () {
	complaintDetail.init();
});