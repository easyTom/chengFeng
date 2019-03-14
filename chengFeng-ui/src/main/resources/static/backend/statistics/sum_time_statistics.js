
var exhibition = function(){

    var reurl = window.location.origin+ctx+"/nmis/sumBusiness";

    var ychzCharts = echarts.init(document.getElementById('ychz'));

    var ycyxCharts = echarts.init(document.getElementById('ycyx'));

    var ycxdCharts = echarts.init(document.getElementById('ycxd'));

    var ycjyCharts = echarts.init(document.getElementById('ycjy'));

    var ycwzCharts = echarts.init(document.getElementById('ycwz'));
    function getDates() {
        var new_Date = new Date()
        var timesStamp = new_Date.getTime();
        var currenDay = new_Date.getDay();
        var dates = [];
        for(var i = 1; i > -9; i--) {
            dates.push(new Date(timesStamp + 24 * 60 * 60 * 1000 * (i - (currenDay + 6) % 7)).toLocaleDateString().replace(/[年月]/g, '-').replace(/[日上下午]/g, ''));
        }
        return dates.reverse();
    }
    function getMonths() {
        var dataArr = [];
        var data = new Date();
        var year = data.getFullYear();
        data.setMonth(data.getMonth() + 1, 1); //获取到当前月份,设置月份
        for (var i = 0; i < 12; i++) {
            data.setMonth(data.getMonth() - 1); //每次循环一次 月份值减1
            var m = data.getMonth() + 1;
            m = m < 10 ? "0" + m : m;
            dataArr.push(data.getFullYear() + "-" + m);
        }
        return dataArr.reverse();
    }
    function Chart() {

        var time = $("#time").val();
        var xData;
        var ycyx;
        var ychz;
        var ycjy;
        var ycxd;
        var ycwz;

        if(time == "day"){
            xData = getDates();
            ychz = [10,9,8,5,13,7,8,7,11,5];
            ycyx = [3,5,5,7,14,10,8,7,5,13];
            ycxd = [13,11,9,15,4,17,16,13,8,4];
            ycjy = [2,5,5,7,3,4,5,2,1,5];
            ycwz = [20,11,13,18,10,9,21,22,15,18];
        }else if(time == "month"){
            xData = getMonths();
            ychz = [0,0,0,0,0,0,0,0,0,0,55,29];
            ycyx = [0,0,0,0,0,0,0,0,0,0,101,57];
            ycxd = [0,0,0,0,0,0,0,0,0,0,85,40];
            ycjy = [0,0,0,0,0,0,0,0,0,0,23,14];
            ycwz = [0,0,0,0,0,0,0,0,0,0,134,80];
        }else if(time == "year"){
            xData = [2019];
            ychz = [84];
            ycyx = [158]
            ycxd = [125]
            ycjy = [37]
            ycwz = [214]
        }


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