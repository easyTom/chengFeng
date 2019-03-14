var applyIndex = function () {

    var request = function (url,method,data,success){
        $.ajax({
            url: window.location.protocol + '//' + window.location.host + url,
            type: method,
            data: data,
            cache: false,
            dataType: "json",
            async:false,  //false 同步请求
            // processData: false,
            success: success,
            error:function (XMLHttpRequest, textStatus, errorThrown){
                console.log(XMLHttpRequest);
            }
        });
    }

    var findTask = function(){
        var action = "/nmis/api/applyTask/taskList";
        var method = "post";
        var userId = $("#userId").val();
        var data = {"userId":userId};
        request(action,method,data,function(response){
            if(response.resultCode == '200'){
                var data = response.data;
                var authority = $("#index_id").html("");
                var index = 0;
                for(var i=0;i<data.length;i++){
                    index++;
                    var color = "";
                    var statusMsg = "未填写";
                    switch (data[i].status){
                        case 1: color = "active"
                            statusMsg = "待审核"
                            break;
                        case 2: color = "done"
                            statusMsg = "审核通过"
                            break;
                        case 3: color = "error"
                            statusMsg = "审核未通过"
                            break;
                    }
                    authority.append('<a href="'+ctx+data[i].taskUrl+'?userId='+data[i].userId+'&status='+data[i].status+'">'
                        +'<div class="col-md-4 bg-grey mt-step-col '+color+' " style="border:1px solid #fff;">'
                        +'<div class="mt-step-number bg-white font-grey"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">'+index+'</font></font></div>'
                        +'<div class="mt-step-title uppercase font-grey-cascade"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">'+data[i].taskName+'</font></font></div>'
                        +'<div class="mt-step-content font-grey-cascade"><font style="vertical-align: inherit;"><font style="vertical-align: inherit;">'+statusMsg+'</font></font></div>');
                        +'</div>'
                        +'</a>'

                }
            }
        });
        return false;
    }

    return {
        init: function () {
            findTask();
        },
    };
}();

jQuery(document).ready(function() {
    applyIndex.init();
});