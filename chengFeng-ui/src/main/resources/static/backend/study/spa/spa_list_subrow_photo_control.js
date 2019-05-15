var spaSubRowPhotoControl = function() {

	function initSubRowHtml(result) {
        $("#spa_photo_"+ result.id).empty();
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
