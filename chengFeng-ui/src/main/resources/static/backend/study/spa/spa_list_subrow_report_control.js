var spaSubRowReportControl = function() {

	function initSubRowHtml(rowData) {
	    var examId = rowData.examId;
        $("#spa_report_"+examId).empty();
        $("#spa_video_"+examId).empty();
        if(rowData.haveEcgAttachment){
            var str = "";
            if($("#currentRolecode").val() == '5' && rowData.status == 20){
                str += '<a style="text-decoration:none;" class="btn btn-sm blue"  href="javascript:TableDatatablesManaged.addReport(\'' + examId + '\');"> 填写报告 </a>';

            }
            if(rowData.status == 30){
                str += '<a style="text-decoration:none;" class="btn btn-sm blue" href="javascript:TableDatatablesManaged.lookupReport(\'' + examId + '\',\'' + rowData.reporterId + '\')"> 查看报告 </a>';
            }
            $("#spa_report_"+examId).append(str);
        }
        //视频咨询
        if($("#currentRolecode").val() == '4' && rowData.status != 10){
            console.log(1)
              var  videoConsult = '<a style="text-decoration:none;" class="btn btn-sm blue" href="javascript:openExpertsList(0);">即时咨询</a>';
            $("#spa_video_"+examId).append(videoConsult);
        }

    }


	return {
		init: function(rowData) {
		    initSubRowHtml(rowData);
		},
	};
}();
