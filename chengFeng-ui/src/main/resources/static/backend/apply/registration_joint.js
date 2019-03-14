var registrationJoint = function () {

    var request = function (url,method,data,success){
        $.ajax({
            url: window.location.protocol + '//' + window.location.host + ctx + url,
            type: method,
            data: data,
            cache: false,
            dataType: "json",
            async:false,  //false 同步请求
            processData: false,
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
        var action = form.attr("action");
        var method = form.attr("method");
        request(action,method,form.serialize(),function(response){
            location.href = ctx + '/nmis/apply/';
        });
        return false;
    }

    var handleInitView = function(userId){
        request("/nmis/api/apply/registrationJoint?userId="+ userId,"get",'',function(response){
            for(var key in response.data){
                var value = response.data[key];
                if(value === true){
                    value = '是';
                }else if(value === false){
                    value = '否';
                }else if(value === null){
                    value = '';
                }else if(value === ''){
                    value = '';
                }
                $("#" + key).append(value);
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
        onsubmit: function (button) {
            var form = $("#form");
            handleOnsubmit(form);
        },
        onCheckRadio: function(status,targetId){
            var target = $("#" + targetId).parent();
            if(status === '1') {
                target.attr('style','display:inline')
            }else {
                target.attr('style','display:none')
            }
        }
    };
}();
jQuery(document).ready(function() {
    registrationJoint.init();
});