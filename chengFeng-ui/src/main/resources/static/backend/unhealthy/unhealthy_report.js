var unhealthyReport = function () {
	var addDate = function () {
		$("#occurDatetime").datepicker({
			rtl: App.isRTL(),
			orientation: "left",
			language: 'zh-CN',
			format: 'yyyy-mm-dd',
			endDate: new Date(),
			autoclose: true
		});
	}

	var addValidate = function () {
		$("#submit_form").validate({
			errorClass : 'errorStyle',
			messages : {
				patientName : {
					required : '患者姓名不能为空'
				},
				diagnosis : {
					required : '患者病情不能为空'
				},
				place : {
					required : '发生场所不能为空'
				},
				result : {
					required : '发生后果不能为空'
				},
				accidentLevel : {
					required : '事件等级不能为空'
				},
				occurDatetime : {
					required : '发生时间不能为空'
				},
				process : {
					required : '发生过程不能为空'
				},
				accidentType : {
					required : '事件类别不能为空'
				},
				cause : {
					required : '原因分析不能为空'
				},
				handle : {
					required : '处理情况不能为空'
				}
			},
			rules : {
				patientName : {
					required : true
				},
				diagnosis : {
					required : true
				},
				place : {
					required : true
				},
				result : {
					required : true
				},
				accidentLevel : {
					required : true
				},
				occurDatetime : {
					required : true
				},
				process : {
					required : true
				},
				accidentType : {
					required : true
				},
				cause : {
					required : true
				},
				handle : {
					required : true
				}
			},
			submitHandler : function(form) {
				var param = {
					patientName : $("#patientName").val(),
					diagnosis : $("#diagnosis").val(),
					place : $("#place").val(),
					result : $("#result").val(),
					accidentLevel : $("#accidentLevel").val(),
					occurDatetime : $("#occurDatetime").val(),
					process : $("#process").val(),
					accidentType : function () {
						var arr = $("#accidentType").val();
						if(arr.length > 0){
							var str = "";
							$(arr).each(function (index, element) {
								if(index < (arr.length - 1)){
									str += element + "|";
								}else{
									str += element;
								}
							});
							return str;
						}else{
							return "";
						}
					},
					cause : $("#cause").val(),
					handle : $("#handle").val(),
					selfOpinion : $("#selfOpinion").val()
				};
				
				$.ajax({
					type : 'post',
					url : ctx + '/nmis/api/unhealthy/report',
					async : false,
					data : param,
					dataType : 'json',
					success : function(data){
						if(data.resultCode == '200'){
							alert("递交成功");
							$("#submit_form")[0].reset();
						}else{
							alert("递交失败");
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
			addDate();
			addValidate();
		}
	};
}();

$(function () {
	unhealthyReport.init();
});