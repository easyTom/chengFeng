var spaSubRowPhotoControl = function() {

	function initSubRowHtml(rowData) {
	    var id = rowData.id;
        $("#spa_photo_"+examId).empty();
        if(result.type == 'photo'){
            tomUploadControl.min("GET",result.id,ctx + "/tom/api/study/upload/ecgImages?id=" + result.id,"spa_photo_");
        }
    }


	return {
		init: function(rowData) {
		    initSubRowHtml(rowData);
		},
	};
}();
