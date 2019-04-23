//显示所有方法
function show(o) {
    $("#m"+o).modal("show");
}
//m2
function test2() {
    console.clear();
    var li = $("#test2 li");  //JQ
    console.log("JQ的结果：");
    console.log(li);
    var l = li[0];      //DOM
    var l1 = li.get(0); //DOM
    console.log("DOM的结果：");
    console.log(l);
    console.log("DOM的结果：");
    console.log(l1);
    console.log("DOM获取值：");
    console.log(l.innerHTML);
    console.log("DOM获取值：");
    console.log(l1.innerHTML);
    $("#t2").show();
    $("#t3").hide();
}
function test3() {
    console.clear();
    var li = document.getElementById("test2").getElementsByTagName("li"); //DOM
    console.log("DOM的结果：");
    console.log(li);
    var l = $(li[0]);  //JQ
    var all = $(li);  //JQ
    console.log("JQ的结果：");
    console.log(l);
    console.log("所有JQ的结果：");
    console.log(all);
    console.log("JQ获取值：");
    console.log(l.html());
    console.log("所有JQ获取值：");
    console.log(all.html());
    $(document.getElementById("t3")).show();
    $(document.getElementById("t2")).hide();
}
//演示动画
$(function () {
    $("#donghua div").click(function () {
        if($(this).hasClass("red")){
            $(this)
                .animate({left:120})
                .animate({left:240})
                .animate({left:0})
                .animate({left:240})
                .animate({left:120});
        }
    });
})