var tomMapControl = function () {

        var name = "jianghua";

        var mapData = [{name: "湘江乡", value: 7}, {name: "大锡乡", value: 7},{name: "大锡乡", value: 3}];

        var myChartMap = echarts.init(document.getElementById('map'));

        var cityData = ["码市镇","湘江乡","大锡乡","桥市乡","涛圩镇","涔天河镇","蔚竹口乡","河路口镇","大圩镇","白芒营镇","大石桥乡","沱江镇","界牌乡","小圩壮族乡","水口镇","大路铺镇","妇幼"];
        var geoCoordMap = {
            "码市镇": [111.9891,24.9437],
            "湘江乡":  [111.8683,25.1192],
            "大锡乡": [111.9328,24.8104],
            "桥市乡":  [111.6070,25.0489],
            "涛圩镇":  [111.5727,24.7948],
            "涔天河镇":  [111.7138,25.1161],
            "蔚竹口乡":  [111.8408,24.8503],
            "河路口镇":  [111.5311,24.7225],
            "大圩镇":  [111.7227,24.8434],
            "白芒营镇": [111.5363,24.9400],
            "大石桥乡":  [111.5384,24.8618],
            "沱江镇":  [111.5826,25.1962],
            "界牌乡":  [111.6060,25.2801],
            "小圩壮族乡": [111.6977,24.9207],
            "水口镇":  [111.7715,24.9854],
            "大路铺镇":  [111.5229,25.0402],
            "妇幼":  [111.6348,25.2402]
        };

        var optionMap={
            title: {
                text: "江华",
                textStyle:{
                    color:'#fff'
                },
                left:'90%'
            },
            geo: {
                map: '江华',
                label: {
                    emphasis: {
                        show:true
                    },
                    align:'right',
                    normal: {
                        show: true,
                        color: "#fff",
                        fontSize:13,
                        formatter: function(params) {
                            var cityName = params.name;
                            return  cityName ;
                        }
                    },
                },
                roam: true,
                itemStyle: {
                    normal: {
                        areaColor: '#00A8FF',
                        borderColor: '#004C98',
                    },
                    emphasis: {
                        areaColor: '#2B91B7'
                    }
                },
            },
            series : [

            ],
            layoutCenter: ['50%', '50%'],
            // 如果宽高比大于 1 则宽度为 100，如果小于 1 则高度为 100，保证了不超过 100x100 的区域
            layoutSize: 550
        };

        var convertData = function (data) {
            var res = [];
            if(data){
                for (var i = 0; i < data.length; i++) {
                    var geoCoord = geoCoordMap[data[i].name];
                    if (geoCoord) {
                        res.push({
                            name: data[i].name,
                            value: geoCoord.concat(data[i].value)
                        });
                    }
                }
            }

            return res;
        };

        var mapload = function(){
            var uploadedDataURL = ctx+"/backend/echarts/map/json/province/"+name+".json";
            $.getJSON(uploadedDataURL, function(geoJson) {
                echarts.registerMap('jianghua', geoJson);
                myChartMap.setOption(optionMap);
            });
        }


    var maploads = function(cityName){
        var hznum=[];
        var yxnum=[];
        var xdnum=[];
        var jynum=[];
        var toolTipData=[];
        for(var i=0;i<cityData.length;i++){
            var hzshu=0;
            var yxshu=0;
            var xdshu=0;
            var jyshu=0;
            if(hznum){
                for(var k=0;k<hznum.length;k++){
                    if(cityData[i]==hznum[k].name){
                        hzshu = hznum[k].value;
                    }
                }
            }
            if(yxnum){
                for(var k=0;k<yxnum.length;k++){
                    if(cityData[i]==yxnum[k].name){
                        yxshu = yxnum[k].value;
                    }
                }
            }
            if(xdnum){
                for(var k=0;k<xdnum.length;k++){
                    if(cityData[i]==xdnum[k].name){
                        xdshu = xdnum[k].value;
                        console.log(xdshu);
                    }
                }
            }
            if(jynum){
                for(var k=0;k<jynum.length;k++){
                    if(cityData[i]==jynum[k].name){
                        jyshu = jynum[k].value;
                    }
                }
            }

            toolTipData.push({
                name:cityData[i],
                value:[
                    {
                        name:"远程会诊",
                        value:hzshu
                    },
                    {
                        name:"远程影像",
                        value:yxshu
                    },
                    {
                        name:"远程心电",
                        value:xdshu
                    },
                    {
                        name:"远程教育",
                        value:jyshu
                    }
                ]
            });
        }
        if(mapData){
            optionMap.series=[
                {
                    name: '远程影像',
                    type: 'scatter',
                    coordinateSystem: 'geo',
                    data: convertData(mapData),
                    // symbolSize:15,
                    symbolSize: function (val) {
                        return val[2] / 0.5;
                    },
                    label: {
                        normal: {
                            formatter: '{b}',
                            position: 'right',
                            show: false
                        },
                        emphasis: {
                            show: true
                        }
                    },
                    itemStyle: {
                        normal: {
                            color: '#ebe300'
                        }
                    }
                },
                {
                    name: '远程会诊',
                    type: 'effectScatter',
                    coordinateSystem: 'geo',
                    data: convertData(mapData.sort(function (a, b) {
                        return b.value - a.value;
                    }).slice(0, 14)),
                    // symbolSize: 17,
                    symbolSize: function (val) {
                        return val[2] / 1;
                    },
                    showEffectOn: 'render',
                    rippleEffect: {
                        brushType: 'fill',
                        scale:8,

                    },
                    hoverAnimation: true,
                    // label: {
                    //     normal: {
                    //         formatter: '{b}',
                    //         color:"#FFF",
                    //         fontSize:"12",
                    //         position: 'right',
                    //         show: true
                    //     }
                    // },
                    itemStyle: {
                        normal: {
                            color: '#ebe300',
                            shadowBlur: 10,
                            shadowColor: '#05C3F9'
                        }
                    },
                    zlevel: 1
                },
                {
                    name: '远程影像',
                    type: 'effectScatter',
                    coordinateSystem: 'geo',
                    data: convertData(mapData.sort(function (a, b) {
                        return b.value - a.value;
                    }).slice(0, 14)),
                    // symbolSize: 17,
                    symbolSize: function (val) {
                        return val[2] / 1;
                    },
                    showEffectOn: 'render',
                    rippleEffect: {
                        brushType: 'fill',
                        scale:8,

                    },
                    hoverAnimation: true,
                    // label: {
                    //     normal: {
                    //         formatter: '{b}',
                    //         color:"#FFF",
                    //         fontSize:"12",
                    //         position: 'right',
                    //         show: true
                    //     }
                    // },
                    itemStyle: {
                        normal: {
                            color: '#ebe300',
                            shadowBlur: 10,
                            shadowColor: '#05C3F9'
                        }
                    },
                    zlevel: 1
                },
            ]
        }
        optionMap.tooltip={
            trigger: 'item',
            formatter: function(params) {
                if (typeof(params.value)[2] == "undefined") {
                    var toolTiphtml = ''
                    for (var i = 0; i < toolTipData.length; i++) {
                        if (params.name == toolTipData[i].name) {
                            toolTiphtml += toolTipData[i].name + ':<br>'
                            for (var j = 0; j < toolTipData[i].value.length; j++) {
                                toolTiphtml += toolTipData[i].value[j].name + '：' + toolTipData[i].value[j].value + "<br>"
                            }
                        }
                    }
                    return toolTiphtml;
                } else {
                    var toolTiphtml = ''
                    for (var i = 0; i < toolTipData.length; i++) {
                        if (params.name == toolTipData[i].name) {
                            toolTiphtml += toolTipData[i].name + ':<br>'
                            for (var j = 0; j < toolTipData[i].value.length; j++) {
                                toolTiphtml += toolTipData[i].value[j].name + '：' + toolTipData[i].value[j].value + "<br>"
                            }
                        }
                    }
                    // console.log(convertData(data))
                    return toolTiphtml;
                }
            }
        }

        myChartMap.setOption(optionMap,true);

    }


    return{
        init:function () {
          mapload();
          maploads();
        },
    }
}();

jQuery(document).ready(function() {
    tomMapControl.init();
});