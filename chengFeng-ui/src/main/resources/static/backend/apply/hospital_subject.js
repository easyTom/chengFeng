var hospitalSubject = function () {

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

    var handleSubjectDelete = function (id) {
        request("/nmis/api/apply/hospitalSubject/delete?id=" + id,"post",null,function () {
            hospitalSubject.init();
        });
    }

    var getTemplate = function(subject) {
        return '<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">\n' +
            '\t\t\t\t\t\t\t<div class="dashboard-stat2 ">\n' +
            '\t\t\t\t\t\t\t\t<div class="display">\n' +
            '\t\t\t\t\t\t\t\t\t<div class="number">\n' +
            '\t\t\t\t\t\t\t\t\t\t<h3 class="font-green-sharp">\n' +
            '\t\t\t\t\t\t\t\t\t\t\t<span data-counter="counterup" >'+ hospitalSubject_dic.getHospitalSubjectNameByCode(subject.subject) + '</span>\n' +
            '\t\t\t\t\t\t\t\t\t\t</h3>\n' +
            '\t\t\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t\t\t\t<div class="icon">\n' +
            '\t\t\t\t\t\t\t\t\t\t<a class="fa fa-close" onclick="hospitalSubject.deleteSubject(\''+ subject.id + '\')"></a>\n' +
            '\t\t\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t</div>';
    }

    var getTemplateReadOnly = function(subject) {
        return '<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">\n' +
            '\t\t\t\t\t\t\t<div class="dashboard-stat2 ">\n' +
            '\t\t\t\t\t\t\t\t<div class="display">\n' +
            '\t\t\t\t\t\t\t\t\t<div class="number">\n' +
            '\t\t\t\t\t\t\t\t\t\t<h3 class="font-green-sharp">\n' +
            '\t\t\t\t\t\t\t\t\t\t\t<span data-counter="counterup" >'+ hospitalSubject_dic.getHospitalSubjectNameByCode(subject.subject) + '</span>\n' +
            '\t\t\t\t\t\t\t\t\t\t</h3>\n' +
            '\t\t\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t\t\t\t<div class="icon">\n' +
            '\t\t\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t</div>';
    }

    var handleInitView = function(userId){
        request("/nmis/api/apply/hospitalSubject?userId="+ userId,"get",'',function(response){
            var content = $("#contentRow");
            content.empty();
            var temp = $("#status").val() === '0' ? getTemplate : getTemplateReadOnly;
            for(var key in response.data){
                var value = response.data[key];
                content.append(temp(value));
            }
        });
    }

    var handleModelSelect = function (){
        hospitalSubject_dic.getHospitalSubjectList(function(dicList){
            var select2 = $('#subjects');
            select2.html('');
            for(var i in dicList){
                select2.append(`<option value="${dicList[i].code}">${dicList[i].name}</option>`);
            }
        })
    }

    var handleSubjectAdd = function (userId,subjects){
        var fd = new FormData();
        fd.append("userId", userId);
        fd.append("subjects",subjects);
        request('/nmis/api/apply/hospitalSubject','post',fd,function(res){
            hospitalSubject.init();
        })
    }

    return {
        init: function() {
            handleModelSelect();
            var userId = $("#userId").val();
            if(userId){
                handleInitView(userId);
            }
        },
        add: function(){
            var userId = $("#userId").val();
            var subjects = $("#subjects").val();
            if(userId && subjects){
                handleSubjectAdd(userId, subjects);
                $("#subjects").select2("val", "");
            }
        },
        deleteSubject: function(id) {
            handleSubjectDelete(id);
        }
    };
}();
jQuery(document).ready(function() {
    hospitalSubject.init();
});