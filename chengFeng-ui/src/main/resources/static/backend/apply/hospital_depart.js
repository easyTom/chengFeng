var hospitalDepart = function () {

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

    var handleDelete = function (id) {
        request("/nmis/api/apply/hospitalDepart/delete?id=" + id,"post",null,function () {
            hospitalDepart.init();
        });
    }

    var getTemplate = function(depart) {
        return '<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">\n' +
            '\t\t\t\t\t\t\t<div class="dashboard-stat2 ">\n' +
            '\t\t\t\t\t\t\t\t<div class="display">\n' +
            '\t\t\t\t\t\t\t\t\t<div class="number">\n' +
            '\t\t\t\t\t\t\t\t\t\t<h3 class="font-green-sharp">\n' +
            '\t\t\t\t\t\t\t\t\t\t\t<span data-counter="counterup" >'+ depart.depart + '</span>\n' +
            '\t\t\t\t\t\t\t\t\t\t</h3>\n' +
            '\t\t\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t\t\t\t<div class="icon">\n' +
            '\t\t\t\t\t\t\t\t\t\t<a class="fa fa-close" onclick="hospitalDepart.delete(\''+ depart.id + '\')"></a>\n' +
            '\t\t\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t</div>';
    }

    var getTemplateReadOnly = function(depart) {
        return '<div class="col-lg-3 col-md-3 col-sm-6 col-xs-12">\n' +
            '\t\t\t\t\t\t\t<div class="dashboard-stat2 ">\n' +
            '\t\t\t\t\t\t\t\t<div class="display">\n' +
            '\t\t\t\t\t\t\t\t\t<div class="number">\n' +
            '\t\t\t\t\t\t\t\t\t\t<h3 class="font-green-sharp">\n' +
            '\t\t\t\t\t\t\t\t\t\t\t<span data-counter="counterup" >'+ depart.depart + '</span>\n' +
            '\t\t\t\t\t\t\t\t\t\t</h3>\n' +
            '\t\t\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t\t\t\t<div class="icon">\n' +
            '\t\t\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t\t</div>\n' +
            '\t\t\t\t\t\t</div>';
    }

    var handleInitView = function(userId){
        request("/nmis/api/apply/hospitalDepart?userId="+ userId,"get",'',function(response){
            var content = $("#contentRow");
            content.empty();
            var temp = $("#status").val() === '0' ? getTemplate : getTemplateReadOnly;
            for(var key in response.data){
                var value = response.data[key];
                content.append(temp(value));
            }
        });
    }

    var handleAdd = function (userId,departs){
        var fd = new FormData();
        fd.append("userId", userId);
        fd.append("departs",departs);
        request('/nmis/api/apply/hospitalDepart','post',fd,function(res){
            hospitalDepart.init();
        })
    }

    var handleClearTags = function() {
        $('#departs').empty();
        $("span").remove(".tag.label.label-info");
    }

    return {
        init: function() {
            var userId = $("#userId").val();
            if(userId){
                handleInitView(userId);
            }
        },
        add: function(){
            var userId = $("#userId").val();
            var departs = $("#departs").val();
            if(userId && departs){
                handleAdd(userId, departs);
                handleClearTags();
            }
        },
        delete: function(id) {
            handleDelete(id);
        }
    };
}();
jQuery(document).ready(function() {
    hospitalDepart.init();
});