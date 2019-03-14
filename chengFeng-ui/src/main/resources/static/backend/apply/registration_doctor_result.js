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
            var url = ctx+'/nmis/api/registrationDoctor/'+id;
            request(url,'GET',null,function (response) {
                for(var key in response.data){
                    if (key == 'photo'){
                        $('#photo').attr('src',response.data[key]);
                        continue;
                    }
                    if (key == 'signature'){
                        $('#signature').attr('src',response.data[key]);
                        continue;
                    }
                    if (key == 'gender') {
                        $('#gender').append(gender_dic.getGenderNameByCode(response.data[key]));
                        continue;
                    }
                    if (key == 'ethnicity') {
                        $('#ethnicity').append(ethnicity_dic.getEthnicityNameByCode(response.data[key]));
                        continue;
                    }
                    if (key == 'education') {
                        $('#education').append(education_dic.getEducationNameByCode(response.data[key]));
                        continue;
                    }
                    if (key == 'cardType') {
                        $('#cardType').append(cardType_dic.getCardTypeNameByCode(response.data[key]));
                        continue;
                    }
                    if (key == 'positionalTitle') {
                        $('#positionalTitle').append(positionalTitle_dic.getPositionalTitleNameByCode(response.data[key]));
                        continue;
                    }
                    if (key == 'professionalTitle') {
                        $('#professionalTitle').append(professionalTitle_dic.getProfessionalTitleNameByCode(response.data[key]));
                        continue;
                    }
                    $('#'+key).append(response.data[key]);
                }
            });
        },
    };

}();

$(function () {
    registrationDoctorResult.getDoctorById($('#doctorId').val());
});
