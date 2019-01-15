$(function(){
    $.get("/photo/weChatApi/getNickname",function(data){
        if(data.success){
            location.href="/";
        }
    })


    $(".login100-form-btn").click(function(){
        var username=$("#username").val();
        var pass=$("#pass").val();
        if(username==null || username=="" || pass==null || pass==""){
            return;
        }
        $.post("/photo/weChatApi/userNameLogin",$("#loginForm").serialize(),function (data) {
           if(data.success){
               location.href="/";
           }else{
               alert(data.message);
           }
        });
    })
})