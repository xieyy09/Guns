$(function(){
    init();
})
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

function chooseImage() {
    wx.chooseImage({
        count: 6, // 默认9
        sizeType: ['original', 'compressed'], // 可以指定是原图还是压缩图，默认二者都有
        sourceType: ['album', 'camera'], // 可以指定来源是相册还是相机，默认二者都有
        success: function (res) {
            var localIds = res.localIds; // 返回选定照片的本地ID列表，localId可以作为img标签的src属性显示图片
            uploadImage(localIds);
        }

    });
}

function uploadImage(localIds){
    wx.uploadImage({
        localId: localIds.toString(), // 需要上传的图片的本地ID，由chooseImage接口获得
        isShowProgressTips: 1, // 默认为1，显示进度提示
        success: function (res) {
            var mediaId = res.serverId; // 返回图片的服务器端ID，即mediaId
            //将获取到的 mediaId 传入后台 方法savePicture
            $.post("/photo/weChatApi/upload",{mediaId:mediaId,"path":"worksdetails"},function(res){
                if(res.t == 'success'){
                    $("#imghead").attr("src", "/photo/weChatApi/loadImg?path=worksdetails&filename=" + result.path);
                }else{
                    alert(res.msg)
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