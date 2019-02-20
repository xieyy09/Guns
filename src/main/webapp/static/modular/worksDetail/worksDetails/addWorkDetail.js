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
                message: '描述最少80个字,最多150个字'
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
    }
}

function nextMiaoshuEvent(){
var imgurl= "";
$("input[name=detailImg]").each(function(){
    if($(this).val().length>0){
        imgurl = "1";
    }
})
if(imgurl.length==0){
    alert("请上传照片")
    return;
}
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
$("#myForm").html();
    // var obj =$("#dataForm").serializeJson();
    // console.log($("#dataForm").serializeJson())
    // console.log(JSON.stringify(obj));
    $('#page3Form').data("bootstrapValidator").resetForm();
    $('#page3Form').bootstrapValidator('validate');
    var valid=$("#page3Form").data('bootstrapValidator').isValid();
    if(!valid) {
        return ;
    }
    var worksDetailsDto = {};
    var imgremark = [];
    var index = 0;
    $(".js_picDiv").each(function(i,div){
        var detailImg = $(div).find("input[name=detailImg]").val();
        var remark = $(div).find("input[name=remark]").val();
        if(detailImg!=undefined && detailImg.length>0){
            index = index +1;
            var remarkDto = {};
            remarkDto["detailIndex"]=index;
            remarkDto["detailImg"]=detailImg;
            remarkDto["remark"]=remark;
            imgremark.push(remarkDto);
        }
    })
    worksDetailsDto["worksTitle"]=$("#worksTitle").val();
    worksDetailsDto["uid"]=$("#uid").find("option:selected").val();
    worksDetailsDto["activityId"]=$("#activityId").find("option:selected").val();
    worksDetailsDto["pohtoTime"]=$("#pohtoTime").val();
    worksDetailsDto["address"]=$("#address").val();
    worksDetailsDto["takenAuthor"]=$("#takenAuthor").val();
    worksDetailsDto["authorSchool"]=$("#authorSchool").val();
    worksDetailsDto["authorAge"]=$("#authorAge").val();
    worksDetailsDto["authorTeacher"]=$("#authorTeacher").val();
    worksDetailsDto["takenTool"]=$("#takenTool").val();
    worksDetailsDto["contact"]=$("#contact").val();
    worksDetailsDto["content"]=$("#content").val();
    worksDetailsDto["answerOne"]=$("#answerOne").val();
    worksDetailsDto["answerTwo"]=$("#answerTwo").val();
    worksDetailsDto["answerThree"]=$("#answerThree").val();
    worksDetailsDto["answerFour"]=$("#answerFour").val();
    worksDetailsDto["answerFive"]=$("#answerFive").val();
    worksDetailsDto["answerSix"]=$("#answerSix").val();
    worksDetailsDto["takenTool"]=$("#takenTool").val();
    worksDetailsDto["hasPoint"]="0";
    worksDetailsDto["worksImgDetailsList"]=imgremark;
    worksDetailsDto["imgNumber"]=imgremark.length;
    worksDetailsDto["imgUrl"]=imgremark[0]["detailImg"];
    worksDetailsDto["imgRemark"]=imgremark[0]["remark"];


    $.ajax({
        type:"POST",
        url: "/photo/worksDetails/add",
        data:JSON.stringify(worksDetailsDto),
        dataType: "json",
        contentType:"application/json",
        success:function(data){
            if(data.success){
                    Feng.success("添加成功！")
                    window.parent.WorksDetails.table.refresh();
                           parent.layer.close(window.parent.WorksDetails.layerIndex);
                }else{
                    alert(data.message)
                }
        },
        error:function(){
            alert("提交失败");
        }
    })
    }

function backIndexEvent(){
    $("#page1").show();
    $("#page2").hide();
}
function backEvent(){
    $("#page2").show();
    $("#page3").hide();
}
var pcAddFormGroup=1;
function addformGroup() {
    if(pcAddFormGroup>6){
        alert("最多可以提6个问题");
        return;
    }
    $("#page3Form").find(".form-group").eq(pcAddFormGroup).show();
    pcAddFormGroup++;
}
$(function() {
    Feng.initValidator("page1Form", page1);
    Feng.initValidator("page2Form", page2);
    Feng.initValidator("page3Form", page3);

    $("input[type=file]").bind("change",function(){
                                                    var inp = $(this).parents(".js_picDiv").eq(0).find("input[name=detailImg]");
                                                    var formIn = $(this).parents(".js_picDiv").eq(0).find('#imgUploadForm');
                                                    var data = new FormData(formIn[0]);
                                                    $.ajax({
                                                        url: Feng.ctxPath + '/activityDetails/upload',
                                                        type: 'POST',
                                                        data: data,
                                                        async: false,
                                                        cache: false,
                                                        contentType: false,
                                                        processData: false,
                                                        success: function (data) {
                                                           inp.val(data)
                                                            console.log(data);
                                                        },
                                                        error: function (data) {
                                                            console.log(data.status);
                                                        }
                                                    });
                                                })
    //$('#imgPicID').change()


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