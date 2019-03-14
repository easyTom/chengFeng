var registrationRegulation = function () {
    var list = new Array();
    var handleOnUpload = function(btn){
        var str = btn.id;
        var flag = true;
        list.forEach(function (string) {
            if(string == str){
                flag = false;
            }
        })
        if(flag){
            list.push(str);
        }
        $("#"+str).text("已上传");
        return false;
    };
    
    var handleOnSubmit = function(){
        var flag = true;
        var i = 0;
        var fd = new FormData();
        if(null == list || list.length == 0){
            flag = false;
        }
        list.forEach(function (str) {
            var downfile = document.getElementById(str).parentElement.children[1].children[3].files[0];
            if(str!="regulation10"){
                i = i + 1;
            }
            fd.append('files', downfile, downfile.name);
            fd.append(str, downfile.name);
        });
        if(flag&&i>8){
            var xhr = new XMLHttpRequest();
            xhr.addEventListener("load", uploadComplete, false);
            xhr.addEventListener("error", uploadFailed, false);
            //发送文件和表单自定义参数
            xhr.open("POST", "/nmis/api/registrationRegulation/save");
            xhr.send(fd);
        }else {
            bootbox.alert("请上传必传项!");
            return false;
        }


        //上传失败
        function uploadFailed() {
            bootbox.alert("上传失败_请检查网络或服务器");
        }

        //上传成功响应
        function uploadComplete(evt) {
            bootbox.alert("上传成功!");
        }

        return false;
    };

    return {
        doupload: handleOnUpload,
        onsubmit: handleOnSubmit,
    };

}();