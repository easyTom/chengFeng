/***
 *
 */
var subrowControl = function () {

    function subrowFormat(eventData) {
        var row = eventData.row;
        var rowData = row.data();
        //获得整行的数据
        console.log(rowData)
        var id = rowData.id;
        var childEle = row.child(`<div class="row">
                       <div class="col-md-12">
                            <!-- BEGIN Portlet PORTLET-->
                            <div class="portlet box red-sunglo">
                                <div class="portlet-body">
                                    <div class="row">
                                        <div class="col-md-3">
                                            <!-- BEGIN Portlet PORTLET-->
                                            <div class="portlet box green">
                                                <div class="portlet-title">
                                                    <div class="caption">
                                                        <i class="fa fa-gift"></i>心电图 </div>
                                                </div>
                                                <div class="portlet-body">
                                                        <div style="max-height:220px!important;overflow: hidden" class="spa_subrow pre-scrollable" id="seriesPics_${id}">
                                                        
</div>
                                                        <div class="col-md-6" id="seriesPics1_${id}"></div>
                                                        <div class="col-md-6" id="seriesPics2_${id}"></div>
                                                        <div style="clear:both;" ></div>
                                                </div>
                                            </div>
                                            <!-- END Portlet PORTLET-->
                                        </div>
                                        <div class="col-md-3">
                                            <!-- BEGIN Portlet PORTLET-->
                                            <div class="portlet box purple">
                                                <div class="portlet-title">
                                                    <div class="caption">
                                                        <i class="fa fa-gift"></i>病例附件 </div>
                                                </div>
                                                <div class="portlet-body" >
                                                    <div class="row bottom text-center" id="att_${id}" style="margin-bottom: 10px" >
                                                        
                                                    </div>
                                                    <div style="max-height:220px!important;overflow-y: auto" class="spa_subrow pre-scrollable " id="spa_attchments_${id}">
                                                    
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- END Portlet PORTLET-->
                                        </div>
                                        <div class="col-md-3">
                                            <!-- BEGIN Portlet PORTLET-->
                                            <div class="portlet box yellow">
                                                <div class="portlet-title">
                                                    <div class="caption">
                                                        <i class="fa fa-gift"></i>诊断报告 </div>
                                                </div>
                                                <div class="portlet-body">
                                                    <div style="max-height:220px!important;"class="row bottom text-center" id="spa_report_${id}">
                                                        
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- END Portlet PORTLET-->
                                        </div>
                                        <div class="col-md-3">
                                            <!-- BEGIN Portlet PORTLET-->                                            <div class="portlet box blue">
                                                <div class="portlet-title">
                                                    <div class="caption">
                                                        <i class="fa fa-gift"></i>视频咨询 </div>
                                                </div>
                                                <div class="portlet-body">
                                                    <div style="max-height:220px!important;"  class="row bottom text-center" id="spa_video_${id}">
                                                    
                                                    </div>
                                                </div>
                                            </div>
                                            <!-- END Portlet PORTLET-->
                                        </div>
                                    </div>
                                </div>
                            </div>
                            <!-- END Portlet PORTLET-->
                        </div>
                    </div>`);
        childEle.show();
          initAtt(rowData);
          /*initEcg(rowData);
          initReport(rowData);*/
    }

    function initEcg(rowData) {
        spaSubRowEcgControl.init(rowData);
    }
    function initAtt(rowData) {
        spaSubRowAttControl.init(rowData);
    }
    function initReport(rowData) {
        spaSubRowReportControl.init(rowData);
    }

    return {
        format: subrowFormat
    }
}();
