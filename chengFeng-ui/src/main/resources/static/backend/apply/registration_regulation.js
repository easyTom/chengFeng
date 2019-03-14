var registrationRegulation = function () {

    var request = function (url,method,data,success){
        $.ajax({
            url: window.location.protocol + '//' + window.location.host + ctx + url,
            type: method,
            data: data,
            cache: false,
            dataType: "json",
            async:false,  //false 同步请求
            processData: false,
            contentType: false,
            success: function(respose){
                if(respose.resultCode == '200'){
                    success(respose);
                }else{
                    alert(respose.resultMsg);
                }
            },
            error:function (XMLHttpRequest, textStatus, errorThrown){
                console.log(XMLHttpRequest);
            }
        });
    }

    var handleOnsubmit = function(form){
        var error1 = $('.alert-danger', form);
        var success1 = $('.alert-success', form);
        form.validate({
            errorElement: 'span',
            errorClass: 'help-block help-block-error',
            focusInvalid: false,
            ignore: "",
            rules: {
                regulation1File: {
                    required: true
                },
                regulation2File: {
                    required: true
                },
                regulation3File: {
                    required: true
                },
                regulation4File: {
                    required: true
                },
                regulation5File: {
                    required: true
                },
                regulation6File: {
                    required: true
                },
                regulation7File: {
                    required: true
                },
                regulation8File: {
                    required: true
                },
                regulation9File: {
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
            submitHandler: function (form1) {
                success1.show();
                error1.hide();
                var action = form.attr("action");
                var method = form.attr("method");
                var formData = new FormData(form[0]);
                request(action,method,formData,function(response){
                    location.href = ctx + '/nmis/apply/';
                });
            }
        });
    }

    var handleInitView = function(id){
        request("/nmis/api/apply/registrationRegulation?userId="+ id,"get",'',function(response){
            console.log(response);
            for(var key in response.data){
                var value = response.data[key];
                var document=  $("#" + key);
                document.append(value);
                if(value!=null&&document.length > 0&&document[0].localName =='a'){
                    document.attr('href',document.attr('href') + '?id=' + id + '&key=' + key);
                }
            }
        });
    }

    return {
        init: function() {
            var id = $("#userId").val();
            if(id){
                handleInitView(id);
            }
        },
        initForm: function (button) {
            var form = $("#form");
            if(form){
                handleOnsubmit(form);
            }
        },
    };
}();
jQuery(document).ready(function() {
    registrationRegulation.init();
    registrationRegulation.initForm()
});