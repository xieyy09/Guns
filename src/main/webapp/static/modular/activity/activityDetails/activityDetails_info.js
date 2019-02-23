/**
 * 初始化活动管理详情对话框
 */
var ActivityDetailsInfoDlg = {
    editor:null,
    activityDetailsInfoData : {}
};

/**
 * 清除数据
 */
ActivityDetailsInfoDlg.clearData = function() {
    this.activityDetailsInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ActivityDetailsInfoDlg.set = function(key, val) {
    this.activityDetailsInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ActivityDetailsInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ActivityDetailsInfoDlg.close = function() {
    parent.layer.close(window.parent.ActivityDetails.layerIndex);
}

/**
 * 收集数据
 */
ActivityDetailsInfoDlg.collectData = function() {
    this.activityDetailsInfoData['content'] = ActivityDetailsInfoDlg.editor.txt.html();
    this
    .set('id')
    .set('title')
    .set('img')
    .set('beginTime')
    .set('endTime')
    .set('activityState')
    .set('uid')
    .set('createTime')
    .set('userNumber')
    .set('state').set('activityType');
}

/**
 * 提交添加
 */
ActivityDetailsInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/activityDetails/add", function(data){
        Feng.success("添加成功!");
        window.parent.ActivityDetails.table.refresh();
        ActivityDetailsInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.activityDetailsInfoData);
    console.log(this.activityDetailsInfoData)
    ajax.start();
}

/**
 * 提交修改
 */
ActivityDetailsInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/activityDetails/update", function(data){
        Feng.success("修改成功!");
        window.parent.ActivityDetails.table.refresh();
        ActivityDetailsInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.activityDetailsInfoData);
    ajax.start();
}

$(function () {
    //初始化是否发布选项
    $("#state").val($("#stateValue").val());
    //初始化编辑器
    var E = window.wangEditor;
    var editor = new E('#editor');
    // 上传图片到服务器
    editor.customConfig.uploadImgTimeout = 10000; //自定义上传图片超时时间 ms
    editor.customConfig.uploadFileName = 'file'; //设置文件上传的参数名称
    editor.customConfig.uploadImgServer = '/photo/activityDetails/upload?path=wangEditor'; //设置上传文件的服务器路径
    editor.customConfig.uploadImgMaxSize = 3 * 1024 * 1024; // 将图片大小限制为 3M
    //自定义上传图片事件
    editor.customConfig.uploadImgHooks = {
        before: function (xhr, editor, files) {

        },
        success: function (xhr, editor, result) {
            console.log("上传成功");
        },
        fail: function (xhr, editor, result) {
            console.log("上传失败,原因是" + result);
        },
        error: function (xhr, editor) {
            console.log("上传出错");
        },
        timeout: function (xhr, editor) {
            console.log("上传超时");
        }
    }
    editor.create();
    editor.txt.html($("#contentVal").val());
    ActivityDetailsInfoDlg.editor = editor;
});
