/*

 */
var registrationDoctorResult = function () {

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

    return {
        getDoctorById: function (id) {
            var url = ctx+'/nmis/api/registrationDepartExtra/'+id;
            request(url,'GET',null,function (response) {
                for(var key in response.data){
                    $('#'+key).append(response.data[key]);
                }
            });
        },
    };

}();

$(function () {
    registrationDoctorResult.getDoctorById($('#userId').val());
});
