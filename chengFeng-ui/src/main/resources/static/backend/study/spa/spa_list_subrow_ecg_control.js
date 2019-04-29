var spaSubRowEcgControl = function() {
	//var ecgPhoto; 假如需要拼接 这样是错误的类型没指定 写在外面不行 每次没清空
	//var ecgPhoto = "";
    var handleAdd = function(result) {
    			    var id = result.examId;
					$("#seriesPics_"+id).empty();
					$("#seriesPics1_"+id).empty();
					$("#seriesPics2_"+id).empty();
					// 没有心电图
					if ($("#currentRolecode").val() == '4' && result.status == 10) {
                       var ecgPhoto1 = '<a class="btn btn-sm blue" style="text-decoration:none;" href="javascript:spaSubRowEcgControl.ecgCollect(\'' + result.examId + '\');"> 心电采集 </a>';
                        $("#seriesPics1_"+id).append(ecgPhoto1);
                       var ecgPhoto2 = '<a class="btn btn-sm blue" style="text-decoration:none;" href="javascript:spaSubRowEcgControl.toUploadEcgFile(\'' + result.examId + '\', \'' + result.sndSiteId + '\');"> 上传心电 </a>';
                        $("#seriesPics2_"+id).append(ecgPhoto2);
					}
					//有心电图显示缩略图 点击调用插件
					if(result.haveEcgImage){
                        lookupEcgMin(id);
                    }
				}
    // 心电采集
    var ecgCollect = function(examId) {
        $.ajax({
            type	:	"get",
            url		:	ctx + "ecg/examination/obtainHWCollectionUrl/" + examId,
            dataType:	"json",
            success	:	function(data) {
                data = data.data;
                if(data){
                    location.href = data;
                }else{
                    alert("当前检查单不存在心电图!");
                }
            },
            error   :   function(data) {
                console.log(data.responseText);
            }
        });
    }

    // 打开上传心电图弹窗
    var toUploadEcgFile = function (examId, sndSiteId){
        TableDatatablesManaged.examId = examId;
        TableDatatablesManaged.sndSiteId = sndSiteId;
        $('#uploadEcgImage').val("");
        $('#procebar').css('width','0%');
        $('#procebar').empty();
        $('#modal2').modal();
    }

    // 查看心电图
    var lookupEcgImages = function(examId) {
        var url = null;
        if($("#currentRolecode").val() == '5') {
            // 专业判读插件
            url = 'ecg/examination/obtainNisViewerUrl/';
        }else {
            // 翰纬判读插件
            url = 'ecg/examination/obtainHWViewerUrl/';
        }
        if(url) {
            $.ajax({
                type: "get",
                url: ctx + url + examId,
                dataType: 'json',
                success: function (data) {
                    data = data.data;
                    if (data) {
                        location.href = data;
                    }
                },
                error: function (data) {
                    console.log(data.responseText);
                }
            });
        }
    }

    // 查看心电缩略图
    var lookupEcgMin = function (examId) {
        $.ajax({
            type	:	"get",
            url		:	ctx + "ecg/examination/ecgImages?examId=" + examId,
            dataType:   'json',
            async : false,
            success	:	function(data){
                if(data && data.length > 0){
                    var data = data[0];
                        var url = ctx + "ecg/examination/ecgImages?examId=" + examId;
                        var img = '<div class="col-md-12 col-sm-12 col-xs-12 " style="padding: 0px 0;" >' +
                            '<a  href="javascript:spaSubRowEcgControl.lookupEcgImages(\'' + examId + '\');" class="thumbnail">' +
                            '<img  width="100%" height="100%" src="' + ctx + 'data/'+data.filePath.substring(0,data.filePath.lastIndexOf('.')) + '_min.png' + '">' +
                            '</a>' +
                            '</div>';
                    $("#seriesPics_"+examId).append(img);
                }
            },
            error	:	function(data){
                console.log(data);
            }
        });
    }

	return {
		init: function(rowData) {
		    handleAdd(rowData);
		},
        ecgCollect: ecgCollect,
        toUploadEcgFile: toUploadEcgFile,
        lookupEcgImages: lookupEcgImages,
        lookupEcgMin: lookupEcgMin,
	};
}();
