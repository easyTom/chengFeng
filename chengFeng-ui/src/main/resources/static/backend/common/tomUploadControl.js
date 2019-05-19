/**
 * XXX为传过来的参数
 * 后台路径为/api/XXXFile
 * 参数：
 *    type：XXX
 *    XXXFile:上传文件
 *
 */
var tomUploadControl = function (o) {
    console.log("括号传过来的参数为:"+o);
    var prev = "";
    var simplePrev = "";
    var xhr = new XMLHttpRequest();

    //初始化前缀参数。 根据此参数拼接
    var initParams = function (param) {
        prev = "#"+param;
        simplePrev = param;
        console.log("参数为:"+simplePrev);
    }

    //补充传参
    var setParams = function (formData) {

    }

    // 打开上传弹窗
    var toUploadEcgFile = function (id){
        switch(simplePrev)
        {
            case "ecg":
                $("#UploadImage").attr("accept",".xml");
                break;
            case "photo":
                $("#UploadImage").attr("accept",".jpg,.png,.gif,.bmp");
                break;
            default:
        }
        tomUploadControl.id = id;
        $('#UploadImage').val("");
        $('#Procebar').css('width','0%');
        $('#Procebar').empty();
        $("#h4").html("上传" + simplePrev);
        $("#h5").html("上传" + simplePrev + "<span class='required' aria-required='true'> * </span>");
        $('#Modal').modal();
    }

    //上传
    var doUploadEcgFile = function (){
        var fileInput = document.getElementById("UploadImage").files[0];
        if(fileInput == "" || fileInput == null){
            bootbox.alert("请选择上传文件!");
            return false;
        }
        var formData = new FormData();
        setParams(formData);
        formData.append("type",simplePrev);
        formData.append(simplePrev+"File",fileInput);
        // 监听事件
        xhr.upload.addEventListener("progress", uploadProgress, false);
        xhr.addEventListener("load", uploadComplete, false);
        xhr.addEventListener("error", uploadFailed, false);
        xhr.addEventListener("abort", uploadCanceled, false);
        // 发送文件和表单自定义参数
        xhr.open("POST", "/api/"+simplePrev+"File");
        xhr.send(formData);
    }

    // 上传进度
    function uploadProgress(evt) {
        if (evt.lengthComputable) {
            var percentComplete = Math.round(evt.loaded * 100 / evt.total);
            $('#Procebar').css('width',percentComplete.toString()+'%');
            $('#Procebar').empty();
            $('#Procebar').html(percentComplete.toString()+'%');
            if(percentComplete == 100){
                $('#Procebar').html("上传成功");
            }
        }
        else {
            $('#Procebar').html("未上传");
        }
    }

    // 上传成功
    function uploadComplete(evt) {
        // 服务断接收完文件返回的结果
        var text = evt.target.responseText;
        console.log(text)
        var data = eval("(" + text + ")");
        if(data.result){
            bootbox.alert("上传成功!");
            // 刷新当前页面
            location.reload();
        }else{
            bootbox.alert("上传失败!");
        }
    }

    // 上传失败
    function uploadFailed(evt) {
        bootbox.alert("上传失败!");
    }

    // 取消上传
    function uploadCanceled(obj) {
        document.getElementById('progress').innerHTML = "0%";
        console.log(obj);
    }

    //根据id回显缩略图 点击放大   方法,主键,路径,追加选择器前缀
    var lookupMin = function (method,id,url,idNamePre) {
        $.ajax({
            type	:	method,
            url		:	url,
            dataType:   'json',
            async : false,
            success	:	function(data){
                if(data){
                    //当ctx为空的时候需要ctx+/data/ data前面需要加/
                    var url =  ctx + '/data/'+data.filePath.substring(0,data.filePath.lastIndexOf('.'));
                    var img = '<div class="col-md-12 col-sm-12 col-xs-12 " style="padding: 0px 0;" >' +
                        '<a class="thumbnail" style="max-width: 150px" href="' +url+ '.png' + '" data-magnify="group1" data-caption="">' +
                        '<img onload="tomUploadControl.AutoSize(this,150,150,150)" src="' + url + '_min.png' + '">' +
                        '</a>' +
                        '</div>';
                    $("#"+idNamePre+id).append(img);
                }
            },
            error	:	function(data){
                console.log(data);
            }
        });
    }

    //等比例放大缩小图片(适应容器)    图片,最大宽,最大高,父元素高
    //可以在img 里面 onload="AutoSize(XXX)"
    var AutoSize = function (Img, maxWidth, maxHeight,fatherHeight) {
        var image = new Image();
        //原图片原始地址（用于获取原图片的真实宽高，当<img>标签指定了宽、高时不受影响）
        image.src = Img.src;
        // 当图片比图片框小时不做任何改变
        if (image.width < maxWidth&& image.height < maxHeight) {
            Img.width = image.width;
            Img.height = image.height;
        }
        else //原图片宽高比例 大于 图片框宽高比例,则以框的宽为标准缩放，反之以框的高为标准缩放
        {
            if (maxWidth/ maxHeight  <= image.width / image.height) //原图片宽高比例 大于 图片框宽高比例
            {
                Img.width = maxWidth;   //以框的宽度为标准
                Img.height = maxWidth* (image.height / image.width);
            }
            else {   //原图片宽高比例 小于 图片框宽高比例
                Img.width = maxHeight  * (image.width / image.height);
                Img.height = maxHeight  ;   //以框的高度为标准
            }
        }
        //居中
        var h = (fatherHeight - Img.height) / 2
        $(Img).attr("style","margin-top:"+h+"px");
    }

    return{
        upload:doUploadEcgFile,
        init:function (param) {
            initParams(param);
            toUploadEcgFile();
        },
        min:lookupMin,
        AutoSize:AutoSize

    }
}(123);
//()内作为参数可以作为参数传入方法
$(function () {

    $(document).mouseup(function(e){
        var _con = $('.magnify-modal');   // 设置目标区域
        if(!_con.is(e.target) && _con.has(e.target).length === 0){
            _con.hide();
            $('html').css({ 'overflow': '', 'padding-right': 0 });
        }
    });
})