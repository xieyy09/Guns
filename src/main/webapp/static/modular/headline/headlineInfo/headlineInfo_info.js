/**
 * 初始化详情对话框
 */
var HeadlineInfoInfoDlg = {
    headlineInfoInfoData : {}
};

/**
 * 清除数据
 */
HeadlineInfoInfoDlg.clearData = function() {
    this.headlineInfoInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
HeadlineInfoInfoDlg.set = function(key, val) {
    this.headlineInfoInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
HeadlineInfoInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
HeadlineInfoInfoDlg.close = function() {
    parent.layer.close(window.parent.HeadlineInfo.layerIndex);
}

/**
 * 收集数据
 */
HeadlineInfoInfoDlg.collectData = function() {
    this
    .set('id')
    .set('title')
    .set('img')
    .set('imgContent')
    .set('module')
    .set('oid')
    .set('url')
    .set('status')
    .set('createtime')
    .set('createuser');
}

/**
 * 提交添加
 */
HeadlineInfoInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/headlineInfo/add", function(data){
        Feng.success("添加成功!");
        window.parent.HeadlineInfo.table.refresh();
        HeadlineInfoInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.headlineInfoInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
HeadlineInfoInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/headlineInfo/update", function(data){
        Feng.success("修改成功!");
        window.parent.HeadlineInfo.table.refresh();
        HeadlineInfoInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.headlineInfoInfoData);
    ajax.start();
}
function selModule(obj){
    var val=$(obj).val();
    console.log(val);
    selModuleInfo(val);
}
function selModuleInfo(val){
    if(val=="other"){
        $("#oiddiv").hide();
        $("#oiddivline").hide()
        $("#urldiv").show();
        $("#urldivline").show()
    }else{
        $("#urldiv").hide();
        $("#urldivline").hide()
        $("#oiddiv").show();
        $("#oiddivline").show()
        var url="";
        var col="";
        if(val=="activity"){
            url="/activityDetails/list";
            col="title";
        }else if(val=="champion"){
            url="/champion/list";
            col="championName";
        }else if(val=="science"){
            url="/popularScienceBase/list";
            col="title";
        }else if(val=="worksDetail"){
            url="/worksDetails/list";
            col="worksTitle";
        }
        var ajax =new $ax(Feng.ctxPath+url,function(data){
            $("#oid").empty();
            for(var i=0;i<data.length;i++){
                var option='<option value="'+data[i]["id"]+'">'+data[i][col]+'</option>';
                $("#oid").append(option);
            }
        });
        ajax.start();

    }
}
$(function() {
    $("#status").val($("#statusValue").val());
    $("#module").val($("#moduleValue").val());
    var val=$("#module").val();
    selModuleInfo(val);
    $("#obj").val($("#objValue").val());
    $('#imgPicID').change(function(){
        var data = new FormData($('#imgUploadForm')[0]);
        $.ajax({
            url: Feng.ctxPath + '/activityDetails/upload',
            type: 'POST',
            data: data,
            async: false,
            cache: false,
            contentType: false,
            processData: false,
            success: function (data) {
                $("#img").val(data)
                console.log(data);
            },
            error: function (data) {
                console.log(data.status);
            }
        });
    })
});
