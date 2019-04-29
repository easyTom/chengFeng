var spaSubRowAttControl = function() {
	//var ecgPhoto; 假如需要拼接 这样是错误的类型没指定 写在外面不行 每次没清空
	//var ecgPhoto = "";
    var handleAdd = function(result) {
        var id = result.id;
        $("#spa_attchments_"+id).empty();
        if(result.content){
            var str = "";
            var img = '<div class="col-md-6 col-sm-6 col-xs-6 " style="padding: 0px 0;height: 120px" >' +
                '<a  class="manman"  style="max-height:120px;" data-magnify="group1" data-caption=" " href="/data/M6)B0TM(C6]NDO])(H8GP]I.gif" class="thumbnail">' +
/*
                '<img style="max-height:95px;" width="100%" height="100%" src="' + ctx + 'ecg/examination/getFileData?id=' + id + '&fileName=' + e.actualFileName.substr(0,e.actualFileName.lastIndexOf('.')) + '_min.png' + '">' +
*/
                '<img id="ooo" style="max-height:95px;" width="100%" height="100%" src="/data/M6)B0TM(C6]NDO])(H8GP]I_min.png">' +
                '</a>' +
                '</div>';
            str += img;
            $("#spa_attchments_"+id).append(str);
        }
        //if(result.haveEcgAttachment){
            //如果有附件 应该直接显示缩略图
           /* $.ajax({
                type	:	"get",
                url		:	ctx + "ecg/examination/getFileListData/" + id,
                dataType:   'json',
                async : false,
                success	:	function(data){
                    data = data.data;
                    var str = "";
                    if(data && data.length > 0){
                        $(data).each(function (i, e) {
                            var url = ctx + "ecg/examination/getFileData?id=" + id + "&fileName=" + e.actualFileName;
                            var img = '<div class="col-md-6 col-sm-6 col-xs-6 " style="padding: 0px 0;height: 120px" >' +
                                '<a style="max-height:120px;" data-magnify="group1" data-caption=" " href="' + url + '" class="thumbnail">' +
                                    '<img style="max-height:95px;" width="100%" height="100%" src="' + ctx + 'ecg/examination/getFileData?id=' + id + '&fileName=' + e.actualFileName.substr(0,e.actualFileName.lastIndexOf('.')) + '_min.png' + '">' +
                                '</a>' +
                                '</div>';
                            str += img;
                        });
                    }
                    $("#spa_attchments_"+id).append(str);
                },
                error	:	function(data){
                    console.log(data);
                }
            });*/

        //}
        //上传附件
       /* if($("#currentRolecode").val() == '4' && result.status == 20){
            //uploadAttachment = '<a style="text-decoration:none;" href="javascript:TableDatatablesManaged.toUploadFiles(\'' + id + '\');">[ 上传附件 ]</a>';
            //str2 += uploadAttachment + '&nbsp;';
            //上传附件改成个图
            var att = '<a class="btn btn-sm blue" style="text-decoration:none;" href="javascript:TableDatatablesManaged.toUploadFiles(\'' + id + '\');"> 上传附件 </a>';
            $("#att_"+id).append(att);
        }*/
    }

	return {
		init: function(rowData) {
            $("[data-magnify = gallery]").magnify();
		    handleAdd(rowData);

		},
	};
}();
