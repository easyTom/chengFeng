var zzControl = function () {

    var initZZ = ()=> tomChartsControl.initZZ('/tom/api/study/echarts/getZZData');
    var initBZ = ()=> tomChartsControl.initBZ('/tom/api/study/echarts/getBZData');
    var initZS = ()=> tomChartsControl.initZS('/tom/api/study/echarts/getZSData');


    return{
        initZZ: initZZ,
        initBZ: initBZ,
        initZS: initZS,


        //init:initZZ() 这样就自动执行了?
    }
}();

