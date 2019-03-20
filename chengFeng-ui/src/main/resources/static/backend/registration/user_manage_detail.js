var userManageDetail = function () {

    var findNmUser = function () {
        $.ajax({
            type : "post",
            url : ctx+"/tom/api/user/get/"+userId,
            dataType : "json", //返回数据形式为json
            success : function(result) {
                if (result.resultCode == '200') {
                    var data = result.data;
                    $("#user_id").html(data.userId);
                    $("#user_name").html(data.userName);
                    $("#real_name").html(data.realName);
                    $("#mobile").html(data.mobile);
                    $("#org_name").html(data.orgName);
                    $("#token").html(data.token);
                    $("#created_at").html(data.createdAt);
                    var status = data.status;
                    if(status == 0){
                        $("#status_ht").html("未启用");

                    }else if(status == 1){
                        $("#status_ht").html("已启用");
                        $("input[name='status']").eq(1).attr('checked', 'true');
                        $("#parentId").find("option[value = '"+data.parentId+"']").attr("selected","selected");
                        $("#memo").val(data.memo);
                        getUserRight(data.userId);
                    }
                }
            },
            error : function(errorMsg) {
            }
        })
    }

    var getUserRight = function (userId) {
        var data = {"userId":userId};
        $.post(ctx+"/tom/api/userRight/getByUserId",data,function(res){
            if(res.resultCode == '200'){
                var data = res.data;
                $(data).each(function (i,dom){
                    $(":checkbox[value='"+dom+"']").prop("checked",true);
                });
                //注释了 复选框可以点  不注释复选框不可以点
                //$("input[name='authority']").attr('disabled', 'disabled');
            }
        });
    }

    var handleAdd = function() {
        $('#submit_form').attr("action",ctx+"/tom/api/user/insert");
        var form1 = $('#submit_form');
        var success1 = $('.alert-success', form1);

        form1.validate({
            errorElement: 'span', //default input error message container
            errorClass: 'help-block help-block-error', // default input error message class
            focusInvalid: false, // do not focus the last invalid input
            ignore: "",  // validate all fields including form hidden input
            messages: {
                authority:{
                    required:''
                },
            },
            rules: {
                authority: {
                    required: true
                },
            },
            invalidHandler: function (event, validator) { //display error alert on form submit
                success1.hide();
            },
            errorPlacement: function (error, element) { // render error placement for each input type
                error.appendTo( element.parent().next() );
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
            submitHandler: function (form) {
                    $.ajaxSettings.async = false; //同步
                    $.post(form1.attr("action"),form1.serialize(),function(data,status){
                        if(data.resultCode == '200'){
                            $('#modal').modal('hide');
                            window.location.reload();
                        }
                    });
            }
        });
    }

    var getAction = function () {
        var data = {};
        $.ajaxSettings.async = false; //同步
        $.post(ctx+"/tom/api/action/actionList",data,function(res){
            if(res.resultCode == '200'){
                var data = res.data;
                var authority = $("#authority_div").html("");
                for(var i=0;i<data.length;i++){
                    authority.append('<label class="mt-checkbox mt-checkbox-outline">'
                    +'<input type="checkbox" name="authority" value="'+data[i].actionAlias+'"> '+data[i].actionName+''
                    +'<span></span>'
                    +'</label>');
                }
            }
        });
    }

    return {
        init: function () {
            findNmUser();
            getAction();
            handleAdd();
        },
        add:function () {
            $("#submit_form").validate().resetForm();
            $('#modal').modal();
        }
    };
}();
jQuery(document).ready(function() {
    userManageDetail.init();
});
