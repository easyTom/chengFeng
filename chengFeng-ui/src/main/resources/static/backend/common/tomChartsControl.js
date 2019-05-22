// 后台接口(返回值为id 和 count) 传参 元素初始化位置ID(默认为chart)
var tomChartsControl = function () {

    var chartData = null;
    //柱状图
    var initZZchart = function (url,data,elementId) {
        if(!elementId){
            elementId = 'chart'
        }
        if(!data){
            data = null;
        }
        $.post(ctx + url,data,function(data){
            var d1 = new Array();
            for(var i=0;i<data.length;i++){
                d1.push(
                    data[i].id,
                );
            }
            var d2 = new Array();
            for(var i=0;i<data.length;i++){
                d2.push(
                    data[i].count
                );
            }
            var myChart = echarts.init(document.getElementById(elementId));
            option = null;
            option = {
                /*title: {
                    text: '柱状图统计'	//左上角大标题
                },*/
                color : [ '#3398DB' ],	//柱子颜色
                tooltip : {
                    trigger : 'axis',
                    axisPointer : { // 坐标轴指示器，坐标轴触发有效
                        type : 'shadow' // 默认为直线，可选为：'line' | 'shadow'
                    }
                },
                grid : {		//图形大小
                    left : '3%',		//距离左侧大小
                    right : '2%',	//距离右侧大小 (太小会看不见右侧东西)
                    bottom : '2%',	//距离下面大小
                    containLabel : true
                },
                xAxis : [ {
                    type : 'category',
                    data : d1,
                    axisTick : {
                        alignWithLabel : true
                    },
                    //x轴字体换行
                    axisLabel:{
                        interval: 0,
                        formatter:function(params){
                            var newParamsName = "";
                            var paramsNameNumber = params.length;
                            var provideNumber = 8;
                            var rowNumber = Math.ceil(paramsNameNumber / provideNumber);
                            if (paramsNameNumber > provideNumber) {
                                for (var p = 0; p < rowNumber; p++) {
                                    var tempStr = "";
                                    var start = p * provideNumber;
                                    var end = start + provideNumber;
                                    if (p == rowNumber - 1) {
                                        tempStr = params.substring(start, paramsNameNumber);
                                    } else {
                                        tempStr = params.substring(start, end) + "\n";
                                    }
                                    newParamsName += tempStr;
                                }

                            } else {
                                newParamsName = params;
                            }
                            return newParamsName
                        }
                    }
                } ],
                yAxis : [ {
                    type : 'value',
                    //name : '数量'	//y轴上面文字
                } ],
                series : [ {
                    //	name : '数量',
                    type : 'bar',
                    barWidth : '10%',		//柱子粗细
                    data :d2
                } ]
            };
            // 使用刚指定的配置项和数据显示图表。
            myChart.setOption(option);

        },"json");
    }
    //饼状图
    var initBZchart = function (url,data,elementId) {
        if(!elementId){
            elementId = 'chart'
        }
        $.ajax({
            url: url,
            type: "post",
            success: function (o) {
               chartData = o;
            },
            datatype: "json",
            async: false
        });
        var chart = AmCharts.makeChart(elementId, {
            "type": "pie",
            "theme": "light",
            "fontFamily": 'Open Sans',
            "color": '#888',
            "dataProvider": chartData,
            "valueField": "count",
            "titleField": "id",
        });

        $('#'+elementId).closest('.portlet').find('.fullscreen').click(function () {
            chart.invalidateSize();
        });
    }
    //饼状图
    var initZSchart = function (url,data,elementId) {
        if(!elementId){
            elementId = 'chart'
        }
        var date= {};
        $.post(ctx  +url,data,function(data){
            var dataarr = new Array();
            if(data.length > 0){
                for(var i=0;i<data.length;i++){
                    dataarr.push({
                        "date" :data[i].id.substring(data[i].id.indexOf("-")+1),
                        "value" : data[i].count
                    });
                }
            }
        var chart = AmCharts.makeChart(elementId, {
            "type": "serial",
            "theme": "light",
            "marginRight": 10,
            "marginLeft": 40,
            "autoMarginOffset": 20,
            "dataDateFormat": "YYYY-MM-DD",
            "valueAxes": [ {
                "id": "v1",
                "axisAlpha": 0,
                "position": "left",
                "ignoreAxisWidth": true
            } ],
            "balloon": {
                "borderThickness": 1,
                "shadowAlpha": 0
            },
            "graphs": [ {
                "id": "g1",
                "balloon": {
                    "drop": true,
                    "adjustBorderColor": false,
                    "color": "#ffffff",
                    "type": "smoothedLine"
                },
                "fillAlphas": 0.2,
                "bullet": "round",
                "bulletBorderAlpha": 1,
                "bulletColor": "#FFFFFF",
                "bulletSize": 5,
                "hideBulletsCount": 50,
                "lineThickness": 2,
                "title": "red line",
                "useLineColorForBulletBorder": true,
                "valueField": "value",
                "balloonText": "<span style='font-size:18px;'>[[value]]</span>"
            } ],
            "chartCursor": {
                "valueLineEnabled": true,
                "valueLineBalloonEnabled": true,
                "cursorAlpha": 0,
                "zoomable": false,
                "valueZoomable": true,
                "valueLineAlpha": 0.5
            },
            "valueScrollbar": {
                "autoGridCount": true,
                "color": "#000000",
                "scrollbarHeight": 50
            },
            "categoryField": "date",
            "categoryAxis": {
//    		    "parseDates": true,
                "dashLength": 1,
                "minorGridEnabled": true
            },
            "export": {
                "enabled": true
            },
            "dataProvider": dataarr
        });
        $(".amcharts-chart-div a").hide();
    });
    }
    return{
        initZZ:initZZchart,
        initBZ:initBZchart,
        initZS:initZSchart,
    }
}();