/**
 * 初始化回复管理详情对话框
 */
var ReplyDetailsInfoDlg = {
    replyDetailsInfoData : {}
};

/**
 * 清除数据
 */
ReplyDetailsInfoDlg.clearData = function() {
    this.replyDetailsInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ReplyDetailsInfoDlg.set = function(key, val) {
    this.replyDetailsInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ReplyDetailsInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ReplyDetailsInfoDlg.close = function() {
    parent.layer.close(window.parent.ReplyDetails.layerIndex);
}

/**
 * 收集数据
 */
ReplyDetailsInfoDlg.collectData = function() {
    this
    .set('id')
    .set('businessId')
    .set('model')
    .set('parentId')
    .set('uid')
    .set('content')
    .set('createTime')
    .set('replyState')
    .set('giveLikeNumber')
    .set('championReply');
}

/**
 * 提交添加
 */
ReplyDetailsInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/replyDetails/add", function(data){
        Feng.success("添加成功!");
        window.parent.ReplyDetails.table.refresh();
        ReplyDetailsInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.replyDetailsInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ReplyDetailsInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/replyDetails/update", function(data){
        Feng.success("修改成功!");
        window.parent.ReplyDetails.table.refresh();
        ReplyDetailsInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.replyDetailsInfoData);
    ajax.start();
}

$(function() {

});
