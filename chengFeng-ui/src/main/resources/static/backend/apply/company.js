var company = function () {

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
            success:function(respose){
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

    var handleDatePickers = function () {
        if (jQuery().datepicker) {
            $('.date-picker').datepicker({
                rtl: App.isRTL(),
                orientation: "left",
                language: 'zh-CN',
                format: 'yyyy-mm-dd',
                autoclose: true
            });
        }
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
                orgCode: {
                    minlength: 2,
                    required: true
                },
                orgName: {
                    minlength: 2,
                    required: true
                },
                address: {
                    minlength: 2,
                    required: true
                },
                representative: {
                    minlength: 2,
                    required: true
                },
                contact: {
                    minlength: 2,
                    required: true
                },
                mobile: {
                    number: true,
                    required: true
                },
                regCode: {
                    minlength: 2,
                    required: true
                },
                issueDepart: {
                    minlength: 2,
                    required: true
                },
                issueDate: {
                    required: true
                },
                regCapital: {
                    required: true
                },
                regScope: {
                    required: true
                },
                attachmentFile: {
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

    var handleInitView = function(userId){
        request("/nmis/api/apply/company?userId="+ userId,"get",'',function(response){
            for(var key in response.data){
                var value = response.data[key];
                var document=  $("#" + key);
                document.append(value);
                if(document.length > 0 &&document[0].localName =='a'){
                    document.attr('href',document.attr('href') + '?id=' + response.data.id + '&key=' + key);
                }
            }
        });
    }

    return {
        init: function() {
            var userId = $("#userId").val();
            if(userId){
                handleInitView(userId);
            }
        },
        initForm: function (button) {
            handleDatePickers();
            var form = $("#form");
            if(form){
                handleOnsubmit(form);
            }
        },
    };
}();
jQuery(document).ready(function() {
    company.initForm()
    company.init();
});