var indexControl = function () {
    
    var initCounts = function () {
        $.post(
        ctx + "/index/getCounts",
         function (o) {
             $("#java").text(o.jc.count);
             $("#jc").val(o.jc.id);
             $("#mistake").text(o.mc.count);
             $("#mc").val(o.mc.id);
             $("#remember").text(o.rc.count);
             $("#photo").text(o.cc);
         },
         "json"
        );
    }

    var goToPage = function (type) {
        var storage = window.localStorage;
        if(type == 'photo'){
            location.href=ctx + "/tom/study/code"
        }else if(type == 'rc'){
            location.href=ctx + "/tom/study/remember"
        }else {
            var id = $("#"+type).val();
            storage.setItem("type",id);
            location.href=ctx + "/tom/study/mistake"
        }
    }
    return{
        init:initCounts,
        go:goToPage
    }
}();

jQuery(document).ready(function() {
    indexControl.init();
});