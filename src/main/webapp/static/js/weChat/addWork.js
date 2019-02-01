var page1 ={
    worksTitle: {
        validators: {
            notEmpty: {
                message: '请输入作品名称'
            }
        }
    },
    pohtoTime: {
        validators: {
            notEmpty: {
                message: '请选择拍摄时间'
            }
        }
    },
    address: {
        validators: {
            notEmpty: {
                message: '请输入拍摄地址'
            }
        }
    },
    takenAuthor: {
        validators: {
            notEmpty: {
                message: '请输入作者名称'
            }
        }
    },
    authorSchool: {
        validators: {
            notEmpty: {
                message: '请输入所在学校'
            }
        }
    },
    authorAge: {
        validators: {
            notEmpty: {
                message: '请输入作者年龄'
            },
            regexp: {
                regexp: /^[0-9]+$/,
                message: '只能输入数字'
            }
        }
    },
    authorTeacher: {
        validators: {
            notEmpty: {
                message: '请输入辅导老师'
            }
        }
    },
    takenTool: {
        validators: {
            notEmpty: {
                message: '请输入拍摄工具'
            }
        }
    },
    contact: {
        validators: {
            notEmpty: {
                message: '请输入联系方式'
            }
        }
    }
}
var page2 ={
    content: {
        validators: {
            notEmpty: {
                message: '请输入作品描述'
            },
            stringLength: {/*长度提示*/
                min: 80,
                max: 150,
                message: '描述最少6个字,最多200个字'
            }/*最后一个没有逗号*/
        }
    }
}
var page3 ={
    answerOne: {
        validators: {
            notEmpty: {
                message: '请输入问题'
            }
        }
    },
    answerTwo: {
        validators: {
            notEmpty: {
                message: '请输入问题'
            }
        }
    },
    answerThree: {
        validators: {
            notEmpty: {
                message: '请输入问题'
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
                debug : false,
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
                try{
                if(res.success){
                    var resultData = res.data;
                    var index=$(".js_picDiv").length;
                    var html='<div style="padding: 5px;" class="js_picDiv"><img style="width: 100%;height: 100%;" ' +
                        'src="/photo/weChatApi/loadImg?path=worksdetails&filename='+resultData+'" />' +
                        '<input name="worksImgDetailsList['+index+'].detailImg" type="hidden" value="'+resultData+'"/>' +
                        '<input name="worksImgDetailsList['+index+'].detailIndex" type="hidden" value="'+index+'"/>' +
                        '<input id="titile" type="text" name="worksImgDetailsList['+index+'].remark" placeholder="请输入图片说明" ' +
                        '  style="max-width: 13;margin-top: 5px;border: 1px solid #ccc;border-top: 1px solid white;border-left: 1px solid white;border-right: 1px solid white;border-radius: 3px 3px 3px 3px;width: 100%;height: 33px;margin-right: 5px;"></div>';
                    $(obj).before(html);
                }else{
                    alert(res["message"])
                }
                }catch (e){}
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
    $('#page1Form').data("bootstrapValidator").resetForm();
    $('#page1Form').bootstrapValidator('validate');
    var valid=$("#page1Form").data('bootstrapValidator').isValid();
    if(valid) {
        $("#page1").hide();
        $("#page2").show();
    }
}
function nextQuestionEvent(){
    $('#page2Form').data("bootstrapValidator").resetForm();
    $('#page2Form').bootstrapValidator('validate');
    var valid=$("#page2Form").data('bootstrapValidator').isValid();
    if(valid) {
        $("#page2").hide();
        $("#page3").show();
    }
}
function submitEvent() {
    // var obj =$("#dataForm").serializeJson();
    // console.log($("#dataForm").serializeJson())
    // console.log(JSON.stringify(obj));
    $('#page3Form').data("bootstrapValidator").resetForm();
    $('#page3Form').bootstrapValidator('validate');
    var valid=$("#page3Form").data('bootstrapValidator').isValid();
    if(!valid) {
        return ;
    }
    $.post("/photo/weChatApi/authc/worksdetails/add",$("#dataForm").serialize(),function(data){
        if(data.success){
            alert("添加成功！")
            location.href="/";

        }else{
            alert(data.message)
        }
    });
}
function backIndexEvent(){
    $("#page1").show();
    $("#page2").hide();
}
function backEvent(){
    $("#page2").show();
    $("#page3").hide();
}

$(function() {
    init();
    Feng.initValidator("page1Form", page1);
    Feng.initValidator("page2Form", page2);
    Feng.initValidator("page3Form", page3);
    // $.get("/photo/weChatApi/activity/processing", function (data) {
    //     if (data.success) {
    //         var arr = data.data.records;
    //         for (var i = 0; i < arr.length; i++) {
    //             var option = '<option value="' + arr[i]["id"] + '">' + arr[i]["title"] + '</option>';
    //             $("#activityId").append(option);
    //         }
    //     }
    // });
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