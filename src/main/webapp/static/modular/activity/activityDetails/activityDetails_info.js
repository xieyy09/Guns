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
    .set('state');
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
    editor.create();
    editor.txt.html($("#contentVal").val());
    ActivityDetailsInfoDlg.editor = editor;
});
