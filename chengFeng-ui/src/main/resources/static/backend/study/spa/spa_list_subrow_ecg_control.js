var spaSubRowEcgControl = function() {
	//var ecgPhoto; 假如需要拼接 这样是错误的类型没指定 写在外面不行 每次没清空
	//var ecgPhoto = "";
    var handleAdd = function(result) {
					if(result.type == 'ecg'){
                        // 查看心电缩略图
                        tomUploadControl.min("GET",result.id,ctx + "/tom/api/study/upload/ecgImages?id=" + result.id,"seriesPics_");
                    }
				}

	return {
		init: function(rowData) {
		    handleAdd(rowData);
		},
	};
}();
