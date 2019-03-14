
var exhibition = function(){

    var reurl = window.location.origin+ctx+"/nmis/sumBusiness";

    var ychzCharts = echarts.init(document.getElementById('ychz'));

    var ycyxCharts = echarts.init(document.getElementById('ycyx'));

    var ycxdCharts = echarts.init(document.getElementById('ycxd'));

    var ycjyCharts = echarts.init(document.getElementById('ycjy'));

    var ycwzCharts = echarts.init(document.getElementById('ycwz'));

    function Chart() {
        var xData = ["张自林","徐天鹏","金自才","秦叔逵","马晶","徐勇","郭巧英","赵长全","李承新","尹淑英"];
        ychz = [22,17,16,14,12,10,9,7,5,5];
        ycyx = [30,25,21,19,18,17,15,13,11,10];
        ycxd = [32,30,28,23,20,18,15,14,12,11];
        ycjy = [8,7,6,6,5,5,4,3,2,2];
        ycwz = [29,28,26,22,20,19,16,15,15,12];
        option = {
            title : {
                text: '',
            },
            color: ['#3398DB'],
            tooltip : {
                trigger: 'axis',
                axisPointer : {            // 坐标轴指示器，坐标轴触发有效
                    type : 'shadow'        // 默认为直线，可选为：'line' | 'shadow'
                }
            },
            grid: {
                left: '3%',
                right: '4%',
                bottom: '3%',
                containLabel: true
            },
            xAxis : [
                {
                    type : 'category',
                    data : [],
                    axisTick: {
                        alignWithLabel: true
                    }
                }
            ],
            yAxis : [
                {
                    type : 'value'
                }
            ],
            series : [
                {
                    name:'',
                    type:'bar',
                    barWidth: '60%',
                    data:[]
                }
            ],
            noDataLoadingOption: {
                text: '暂无数据',
                effect: 'bubble',
                effectOption: {
                    effect: {
                        n: 0
                    }
                }
            },
        };
        ychzCharts.setOption(option);
        ycyxCharts.setOption(option);
        ycxdCharts.setOption(option);
        ycjyCharts.setOption(option);
        ycwzCharts.setOption(option);
        ychzCharts.setOption({
            title : {
                text: '远程会诊',
            },
            xAxis: {
                data: xData
            },
            series: [
                {
                    data: ychz,
                    name: "远程会诊"
                },]
        });
        ycyxCharts.setOption({
            title : {
                text: '远程影像',
            },
            xAxis: {
                data: xData
            },
            series: [
                {
                    data: ycyx,
                    name: "远程影像"
                },]
        });
        ycxdCharts.setOption({
            title : {
                text: '远程心电',
            },
            xAxis: {
                data: xData
            },
            series: [
                {
                    data: ycxd,
                    name: "远程心电"
                },]
        });
        ycjyCharts.setOption({
            title : {
                text: '远程教育',
            },
            xAxis: {
                data: xData
            },
            series: [
                {
                    data: ycjy,
                    name: "远程教育"
                },]
        });
        ycwzCharts.setOption({
            title : {
                text: '互联网问诊',
            },
            xAxis: {
                data: xData
            },
            series: [
                {
                    data: ycwz,
                    name: "互联网问诊"
                },]
        });
    }

    // function ButtonChart(){
    //     var time = $("#time").val();
    //     var city = $("#city").val();
    //     $.ajax({
    //         type: "get",
    //         url: reurl + "/categoryList",
    //         data: {button:time,city:city},
    //         dataType: "json", //返回数据形式为json
    //         success: function (result) {
    //             var xychzData = [];
    //             var yychzData = [];
    //             var xycyxData = [];
    //             var yycyxData = [];
    //             var xycxdData = [];
    //             var yycxdData = [];
    //             var xycjyData = [];
    //             var yycjyData = [];
    //             var xycwzData = [];
    //             var yycwzData = [];
    //             if (result.resultCode == '200') {
    //                 data = result.data;
    //                 if (data) {
    //                     if (data[0]) {
    //                         for (var i = 0; i < data[0].length; i++) {
    //                             xychzData.push(data[0][i].sndDate)
    //                             yychzData.push(data[0][i].value)
    //                         }
    //                     }
    //                     if (data[1]) {
    //                         for (var i = 0; i < data[1].length; i++) {
    //                             xycyxData.push(data[1][i].sndDate)
    //                             yycyxData.push(data[1][i].value)
    //                         }
    //                     }
    //                     if (data[2]) {
    //                         for (var i = 0; i < data[2].length; i++) {
    //                             xycxdData.push(data[2][i].sndDate)
    //                             yycxdData.push(data[2][i].value)
    //                         }
    //                     }
    //                     if (data[3]) {
    //                         for (var i = 0; i < data[3].length; i++) {
    //                             xycjyData.push(data[3][i].sndDate);
    //                             yycjyData.push(data[3][i].value)
    //                         }
    //                     }
    //                     if (data[4]) {
    //                         for (var i = 0; i < data[4].length; i++) {
    //                             xycwzData.push(data[4][i].sndDate);
    //                             yycwzData.push(data[4][i].value)
    //                         }
    //                     }
    //                 }
    //             }
    //             ychzCharts.setOption({
    //                 title : {
    //                     text: '远程会诊',
    //                 },
    //                 xAxis: {
    //                     data: xychzData
    //                 },
    //                 series: [
    //                     {
    //                         data: yychzData,
    //                         name: "远程会诊"
    //                     },]
    //             });
    //             ycyxCharts.setOption({
    //                 title : {
    //                     text: '远程影像',
    //                 },
    //                 xAxis: {
    //                     data: xycyxData
    //                 },
    //                 series: [
    //                     {
    //                         data: yycyxData,
    //                         name: "远程影像"
    //                     },]
    //             });
    //             ycxdCharts.setOption({
    //                 title : {
    //                     text: '远程心电',
    //                 },
    //                 xAxis: {
    //                     data: xycxdData
    //                 },
    //                 series: [
    //                     {
    //                         data: yycxdData,
    //                         name: "远程心电"
    //                     },]
    //             });
    //             ycjyCharts.setOption({
    //                 title : {
    //                     text: '远程教育',
    //                 },
    //                 xAxis: {
    //                     data: xycjyData
    //                 },
    //                 series: [
    //                     {
    //                         data: yycjyData,
    //                         name: "远程教育"
    //                     },]
    //             });
    //             ycwzCharts.setOption({
    //                 title : {
    //                     text: '互联网问诊',
    //                 },
    //                 xAxis: {
    //                     data: xycwzData
    //                 },
    //                 series: [
    //                     {
    //                         data: yycwzData,
    //                         name: "互联网问诊"
    //                     },]
    //             });
    //         }
    //     });
    //     $('.actions a').each(function(){
    //         $(this).removeClass("blue");
    //         $(this).addClass("default");
    //         var html = $(this).html();
    //         if(html == city){
    //             $(this).removeClass("default");
    //             $(this).addClass("blue");
    //         }
    //     })
    // }
    $("#btn_day").click(function () {
        $("#time").val("day");
        Chart();
    });
    $("#btn_month").click(function () {
        $("#time").val("month");
        Chart();
    });
    $("#btn_year").click(function () {
        $("#time").val("year");
        Chart();
    });

    return{
        init:function(){
            $("#time").val("day");
            Chart();
        },
    }
}();
jQuery(document).ready(function(){
    exhibition.init();
});