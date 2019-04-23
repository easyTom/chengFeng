var stompClient = null ;
function setConnected (connected) {
    $("#connect").prop("disabled", connected);
    $("#disconnect").prop("disabled", !connected);
    if (connected) {
        $("#conversation").show();
        $("#chat").show();
    } else {
        $("#conversation").hide();
        $("#chat").hide();
    }
    $("#greetings").html("");
}
function connect() {
    if(!$("#name").val()){
        return ;
    }
    var socket = new SockJS('/chat');
    stompClient = Stomp.over (socket);
    stompClient. connect({} , function (frame){
        setConnected(true);
        stompClient .subscribe('/topic/tests',function (greeting) {
        showTest(JSON.parse(greeting. body));

        });
    });
}
function disconnect (){
    if(stompClient!=null){
        stompClient.disconnect();
    }
    setConnected(false);
}

function sendName() {
    stompClient.send("/app/hello",{},
        JSON.stringify({'name':$("#name").val(),'content':$("#contentText").val()}));
}
function showTest(m) {
    $("#test")
        .append("<div>"+m.name+":"+m.content+"</div>");
}
$(function () {
   $("#connect").click(function () {
       connect();
   });
   $("#disconnect").click(function () {
       disconnect();
   });
   $("#send").click(function () {
      sendName();
   });
});