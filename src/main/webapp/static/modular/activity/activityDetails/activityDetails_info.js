/**
 * 初始化活动管理详情对话框
 */
var ActivityDetailsInfoDlg = {
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
    this
    .set('id')
    .set('title')
    .set('img')
    .set('content')
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

$(function() {

});
