$(function() {
    init();

    $.get("/photo/weChatApi/activity/processing", function (data) {
        if (data.success) {
            var arr = data.data.records;
            for (var i = 0; i < arr.length; i++) {
                var option = '<option value="' + arr[i]["id"] + '">' + arr[i]["title"] + '</option>';
                $("#activityId").append(option);
            }
        }
    });
    // $.ajaxSetup({
    //     contentType: 'application/json'
    // });
    // (function($){
    //     $.fn.serializeJson=function(){
    //         var serializeObj={};
    //         var array=this.serializeArray();
    //         var str=this.serialize();
    //         $(array).each(function(){
    //             if(serializeObj[this.name]){
    //                 if($.isArray(serializeObj[this.name])){
    //                     serializeObj[this.name].push(this.value);
    //                 }else{
    //                     serializeObj[this.name]=[serializeObj[this.name],this.value];
    //                 }
    //             }else{
    //                 serializeObj[this.name]=this.value;
    //             }
    //         });
    //         return serializeObj;
    //     };
    // })(jQuery);
});
var page1 ={
    activityId: {
        validators: {
            notEmpty: {
                message: '请选择活动'
            }
        }
    },
    worksTitle: {
        validators: {
            notEmpty: {
                message: '请输入作品名称'
            }
        }
    }
}
var page2 ={
    pohtoTime: {
        validators: {
            notEmpty: {
                message: '请选择拍摄时间'
            }
        }
    },
    weather: {
        validators: {
            notEmpty: {
                message: '请输入天气'
            }
        }
    }
}
function init(){
    $.ajax({
        url : "/photo/weChatApi/jssdk",
        type : 'post',
        dataType : 'json',
        contentType : "application/x-www-form-urlencoded; charset=utf-8",
        data : {
            'url' : location.href.split('#')[0]
        },
        success : function(data) {
            wx.config({
                debug : true,
                appId : data.appId,
                timestamp : data.timestamp,
                nonceStr : data.nonceStr,
                signature : data.signature,
                jsApiList : ['chooseImage',
                    'previewImage',
                    'uploadImage',
                    'downloadImage','getLocation']
            });
        }
    });
}

function chooseImage(obj) {
    if($(".js_picDiv").length>6){
        alert("最多可上传6张图片!")
        return ;
    }
    wx.chooseImage({
        count: 6, // 默认9
        sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
        sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
        success: function (res) {
            var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
            uploadImage(localIds,obj);
        }

    });
}

function uploadImage(localIds,obj){
    wx.uploadImage({
        localId: localIds.toString(), // 需要上传的图片的本地ID，由chooseImage接口获得
        isShowProgressTips: 1, // 默认为1，显示进度提示
        success: function (res) {
            var mediaId = res.serverId; // 返回图片的服务器端ID，即mediaId
            //将获取到的 mediaId 传入后台 方法savePicture
            $.post("/photo/weChatApi/upload",{mediaId:mediaId,"path":"worksdetails"},function(res){
                if(res.success){
                    var index=$(".js_picDiv").length;
                    var html='<div style="padding: 5px;" class="js_picDiv"><img style="margin-left: 11px;width: 55px;height: 55px;" ' +
                        'src="/photo/weChatApi/loadImg?path=worksdetails&filename='+result.data+'" />' +
                        '<input name="worksImgDetailsList['+index+'].detailImg" type="hidden" value="'+result.data+'"/>' +
                        '<input name="worksImgDetailsList['+index+'].detailIndex" type="hidden" value="'+index+'"/>' +
                        '<input id="titile" type="text" name="worksImgDetailsList['+index+'].remark" placeholder="请输入图片说明" ' +
                        '  style="max-width: 13;margin-top: 5px;border: 1px solid #ccc;border-top: 1px solid white;border-left: 1px solid white;border-right: 1px solid white;border-radius: 3px 3px 3px 3px;width: 100%;height: 33px;margin-right: 5px;"></div>';
                    $(obj).append(html);
                }else{
                    alert(res.message)
                }
            })
        },
        fail: function (res) {
            alertModal('上传图片失败，请重试')
        }
    });
}

function getLocation(){
    wx.getLocation({
        type : 'wgs84', // 默认为wgs84的gps坐标，如果要返回直接给openLocation用的火星坐标，可传入'gcj02'
        success : function(res) {
            //使用微信内置地图查看位置接口
            wx.openLocation(res);
        },
        cancel : function(res) {
            //用户拒绝授权获取地理位置
        }
    });
}
function nextMiaoshuEvent(){
    $("#page1").hide();
    $("#page2").show();
}
function nextQuestionEvent(){
    $("#page2").hide();
    $("#page3").show();
}
function submitEvent() {
    // var obj =$("#dataForm").serializeJson();
    // console.log($("#dataForm").serializeJson())
    // console.log(JSON.stringify(obj));
    $.post("/photo/weChatApi/authc/worksdetails/add",$("#dataForm").serialize(),function(data){
        console.log(data)
    });
}