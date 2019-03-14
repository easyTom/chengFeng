
var exhibition = function(){

    var reurl = window.location.origin+ctx+"/nmis/sumBusiness";

    var durgCharts = echarts.init(document.getElementById('durg'));

    function Chart() {
        var xData = ["青霉素类抗生素","头孢菌素类抗生素","多烯类抗生素","抗真菌类药","抗疟药","非成瘾性镇痛药","抗痛风药","复合维生素类","核酸类药","免疫抑制剂"];
        durg = [32,30,28,23,20,18,15,14,12,11];
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
                    },
                    axisLabel:{
                        interval: 0
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
        durgCharts.setOption(option);
        durgCharts.setOption({
            title : {
                text: '药品统计',
            },
            xAxis: {
                data: xData
            },
            series: [
                {
                    data: durg,
                    name: "远程会诊"
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