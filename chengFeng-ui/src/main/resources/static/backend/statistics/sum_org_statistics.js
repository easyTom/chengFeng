
var exhibition = function(){

    var reurl = window.location.origin+ctx+"/nmis/sumBusiness";

    var orgCharts = echarts.init(document.getElementById('org'));

    var doctorCharts = echarts.init(document.getElementById('doctor'));

    function orgType() {
        option = {
            title: {
                text: '机构类别',
                left: 'center'
            },
            tooltip : {
                trigger: 'item',
                formatter: "{b} : {c} ({d}%)"
            },
            legend: {
                show:false,
                // orient: 'vertical',
                top: '20%',
                bottom: 10,
                left: 'center',
                data: []
            },
            series : [
                {
                    type: 'pie',
                    radius : '80%',
                    center: ['50%', '50%'],
                    selectedMode: 'single',
                    data:[
                    ],
                    itemStyle: {
                        emphasis: {
                            shadowBlur: 10,
                            shadowOffsetX: 0,
                            shadowColor: 'rgba(0, 0, 0, 0.5)'
                        }
                    }
                }
            ]
        };
        orgCharts.setOption(option);
        doctorCharts.setOption(option);
        orgCharts.setOption({
            title : {
                text: '机构类别',
            },
            legend: {
                data: ["三级甲等","三级乙等","三级丙等","三级特等"]
            },
            series: [
                {
                    data: [
                        {value:3,name:"三级甲等"},
                        {value:5,name:"三级乙等"},
                        {value:10,name:"三级丙等"},
                        {value:2,name:"三级特等"},
                    ],
                },]
        });

        doctorCharts.setOption({
            title : {
                text: '人员类别',
            },
            legend: {
                data: ["主任医师","副主任医师","主治医师"]
            },
            series: [
                {
                    data: [
                        {value:13,name:"主任医师"},
                        {value:24,name:"副主任医师"},
                        {value:52,name:"主治医师"},
                    ],
                },]
        });
    }


    return{
        init:function(){
            orgType();
        },
    }
}();
jQuery(document).ready(function(){
    exhibition.init();
    // $("#sdate").datepicker({
    //     rtl: App.isRTL(),
    //     orientation: "left",
    //     language: 'zh-CN',
    //     autoclose: true,
    //     format: 'yyyy-mm-dd',
    //     endDate: new Date()
    // });
    // $("#edate").datepicker({
    //     rtl: App.isRTL(),
    //     orientation: "left",
    //     language: 'zh-CN',
    //     autoclose: true,
    //     format: 'yyyy-mm-dd',
    //     endDate: new Date()
    // });
});
