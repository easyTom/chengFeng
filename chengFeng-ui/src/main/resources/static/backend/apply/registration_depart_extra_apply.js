/*

 */
var registrationDoctor = function () {

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
    }

    var handleOnsubmit = function(form,event){
        if (!form.valid()){
            return false;
        }
        var action = form.attr("action");
        var method = form.attr("method");
        var formEle = document.getElementById("myForm");
        var formData = new FormData(formEle);
        request(action,method,formData,function(response){
            console.log(response);
            //TODO 重定向到任务状态页面
            if (response && response.resultCode == 200) {
                alert('添加成功');
                var userId = $('#userId').val();
                var status = 1;
                location.href = ctx + '/nmis/apply/registration_depart_extra_apply?userId='+userId + '&status='+status;
            } else {
                alert('错误! 添加失败!');
            }
        });
        return false;
    }

    var formValidate = function(form){
        var error1 = $('.alert-danger', form);
        var success1 = $('.alert-success', form);
        form.validate({
            errorElement: 'span',
            errorClass: 'help-block help-block-error',
            focusInvalid: false,
            ignore: "",
            rules: {
                qaDepart: {
                    minlength: 2,
                    required: true
                },
                qaContact: {
                    required: true
                },
                qaContactTel: {
                    required: true
                },
                itDepart: {
                    minlength: 2,
                    required: true
                },
                itContact: {
                    required: true
                },
                itContactTel: {
                    required: true
                },
                drugDepart: {
                    minlength: 2,
                    required: true
                },
                drugContact: {
                    required: true
                },
                drugContactTel: {
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
            submitHandler: function (){handleOnsubmit($('#myForm'))}
        });
    }
    
    return {
        onsubmit: function (button) {
            var form = $("#myForm");
            // formValidate(form);
        },
        handleOnsubmit: handleOnsubmit,
        formValidate: formValidate,
    };

}();

$(document).ready(function () {
    registrationDoctor.formValidate($("#myForm"));
});
