var register = function () {
	var addChange = function () {
		$("#submit_form [name='purpose']").on('change',function () {
			var $purpose = $("#purpose");
			if(this.value == '其他'){
				$purpose.removeAttr("disabled");
			}else{
				$purpose.attr("disabled",true);
			}
		});
	}
	
	var addValidation = function(){
		$.validator.addMethod("validateMobile", function(v, e, p) {
			var myreg = /^[1][3,4,5,7,8][0-9]{9}$/;
			if (!myreg.test(v)) {
				return false;
			} else {
				return true;
			}
		});
		
		$("#submit_form").validate({
			errorClass : 'errorStyle',
			messages : {
				userName : {
					required : '用户名不能为空',
					remote : '该用户名已经被使用了'
				},
				password : {
					required : '密码不能为空'
				},
				realName : {
					required : '真实姓名不能为空'
				},
				mobile : {
					required : '手机号不能为空',
					validateMobile : '手机号不合法'
				},
				orgName : {
					required : '所属单位不能为空'
				},
				purpose : {
					required : '注册目的不能为空'
				}
			},
			rules : {
				userName : {
					required : true,
					remote: {
						url: ctx + "/api/frontend/checkUsername",
						type: "get"
					}
				},
				password : {
					required : true
				},
				realName : {
					required : true
				},
				mobile : {
					required : true,
					validateMobile : true
				},
				orgName : {
					required : true
				},
				purpose : {
					required : true
				}
			},
			submitHandler : function(form) {
				var param = {
					userName : $("#userName").val(),
					password : $("#password").val(),
					realName : $("#realName").val(),
					mobile : $("#mobile").val(),
					orgName : $("#orgName").val(),
					purpose : function () {
						var $purpose = $("#submit_form [name='purpose']:checked").val();
						if($purpose != '其他'){
							return $purpose;
						}else if($purpose == '其他'){
							return $("#purpose").val();
						}
						return;
					}
				};
				
				$.ajax({
					type : 'post',
					url : ctx + '/api/frontend/register',
					async : false,
					data : param,
					dataType : 'json',
					success : function(data){
						if(data.data.result){
							alert("注册成功");
							location.href = ctx + "/ui/frontend/login";
						}else{
							alert("注册失败");
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
		'init' : function(){
			addChange();
			addValidation();
		}
	};
}();

$(document).ready(function () {
	register.init();
});