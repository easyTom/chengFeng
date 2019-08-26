/**
 *
 * initMagnifySetting使用方法：
 * tomMagnifyControl.init(true) true为全屏 false为非全屏
 *
 * 前端页面：
 *  <a   data-magnify href="">
         <img  src="">
    </a>
 *
 * AutoSize使用方法：
 * tomMagnifyControl.AutoSize(....)
 * 前端页面：
     <img onload="tomMagnifyControl.AutoSize(this,150,150,150) src="">
 */
var tomMagnifyControl = function () {

    //初始化放大镜的设置
    var initMagnifySetting = function(magnFlag){
        $('[data-magnify]').magnify({
            //模态拖动
            draggable:false,
            //调整大小
            resizable:false,
            //顶部工具栏
            headToolbar: [
                'maximize',
                'close'
            ],
            //键盘
            keyboard:true,
            //底部工具栏
            footToolbar: [
                'maximize',
                'zoomIn',
                'zoomOut',
                'prev',
                'fullscreen',
                'next',
                'actualSize',
                'rotateLeft',
                'rotateRight'
            ],
            //画布初始化大小是否占据浏览器最大大小,默认为false
            initMaximized: magnFlag,
            //图片展示时左上角是否显示标题,默认为true
            title: false,
            //设置图片展示框的堆叠顺序,值越大则越靠上层,越小则越靠下层,默认为1090
            zIndex: 9999,
            callbacks: {
                beforeOpen: function(el){
                },
                opened: function(el){
                    console.log("image open...")
                },
                beforeClose: function(el){
                },
                closed: function(el){
                    console.log("image close...")
                },
                beforeChange: function(index){
                },
                changed: function(index){
                }
            }
        });
    }

    //等比例放大缩小图片(适应容器)    图片,最大宽,最大高,父元素高
    //可以在img 里面 onload="AutoSize(XXX)"
    var AutoSize = function (Img, maxWidth, maxHeight,fatherHeight) {
        var image = new Image();
        //原图片原始地址（用于获取原图片的真实宽高，当<img>标签指定了宽、高时不受影响）
        image.src = Img.src;
        // 当图片比图片框小时不做任何改变
        if (image.width < maxWidth&& image.height < maxHeight) {
            Img.width = image.width;
            Img.height = image.height;
        }
        else //原图片宽高比例 大于 图片框宽高比例,则以框的宽为标准缩放，反之以框的高为标准缩放
        {
            if (maxWidth/ maxHeight  <= image.width / image.height) //原图片宽高比例 大于 图片框宽高比例
            {
                Img.width = maxWidth;   //以框的宽度为标准
                Img.height = maxWidth* (image.height / image.width);
            }
            else {   //原图片宽高比例 小于 图片框宽高比例
                Img.width = maxHeight  * (image.width / image.height);
                Img.height = maxHeight  ;   //以框的高度为标准
            }
        }
        //居中
        var h = (fatherHeight - Img.height) / 2
        $(Img).attr("style","margin-top:"+h+"px");
    }

    return{
        init:initMagnifySetting,
        AutoSize:AutoSize

    }
}();
