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
        var formEle = document.getElementById("doctorForm");
        var formData = new FormData(formEle);
        request(action,method,formData,function(response){
            console.log(response);
            //TODO 重定向到任务状态页面
            if (response && response.resultCode == 200) {
                alert('添加成功');
                var userId = $('#userId').val();
                var status = 1;
                location.href = ctx + '/nmis/apply/registration_doctor?userId='+userId + '&status='+status;
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
                name: {
                    minlength: 2,
                    required: true
                },
                certificateId: {
                    required: true
                },
                gender: {
                    required: true
                },
                ethnicity: {
                    required: true
                },
                departId: {
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
            submitHandler: function (){handleOnsubmit($('#doctorForm'))}
        });
    }

    function getDepartList() {
        var userId = $('#userId').val();
        var url = ctx + '/nmis/api/apply/registrationDepart?userId='+userId;
        request(url,'get',null,function (response) {
            console.log(response);

            if (response && response.data) {
                for(var i in response.data){
                    $('#departId').append(`<option value="${response.data[i].id}">${response.data[i].depart}</option>`)
                }
            }
        })
    }

    return {
        onsubmit: function (button) {
            var form = $("#doctorForm");
            // formValidate(form);
        },
        handleOnsubmit: handleOnsubmit,
        formValidate: formValidate,
        getDepartList: getDepartList
    };

}();

$(document).ready(function () {
    document.getElementById("cover-image").addEventListener("change",function (e) {
        // console.log(e.target);
        var imgFile = e.target.files[0];
        var fileR = new FileReader();
        fileR.readAsDataURL(imgFile);

        fileR.onload = function () {
            var imgEle = document.getElementById("image-show");
            imgEle.src = fileR.result;
            document.getElementById("photo").value = fileR.result;
        };
    });
    document.getElementById("cover-image2").addEventListener("change",function (e) {
        // console.log(e.target);
        var imgFile = e.target.files[0];
        var fileR = new FileReader();
        fileR.readAsDataURL(imgFile);

        fileR.onload = function () {
            var imgEle = document.getElementById("image-show2");
            imgEle.src = fileR.result;
            document.getElementById("signature").value = fileR.result;
        };
    });

    registrationDoctor.formValidate($("#doctorForm"));
    registrationDoctor.getDepartList();
    initDic();
});

function initDic() {
    //性别
    gender_dic.getGenderList(function (genderList) {
        for(var i in genderList){
            $('#gender').append(`<option value="${genderList[i].code}">${genderList[i].name}</option>`);
        }
    });
    // 民族
    ethnicity_dic.getEthnicityList(function (dicList) {
        for(var i in dicList){
            $('#ethnicity').append(`<option value="${dicList[i].code}">${dicList[i].name}</option>`);
        }
    });
    // 证件
    cardType_dic.getCardTypeList(function (dicList) {
        for(var i in dicList){
            $('#cardType').append(`<option value="${dicList[i].code}">${dicList[i].name}</option>`);
        }
    });
    // 专业职称
    professionalTitle_dic.getProfessionalTitleList(function (dicList) {
        for(var i in dicList){
            $('#professionalTitle').append(`<option value="${dicList[i].code}">${dicList[i].name}</option>`);
        }
    });
    // 学历
    education_dic.getEducationList(function (dicList) {
        for(var i in dicList){
            $('#education').append(`<option value="${dicList[i].code}">${dicList[i].name}</option>`);
        }
    })
}