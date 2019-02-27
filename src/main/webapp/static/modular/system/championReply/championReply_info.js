/**
 * 初始化championReply详情对话框
 */
var ChampionReplyInfoDlg = {
    championReplyInfoData : {}
};

/**
 * 清除数据
 */
ChampionReplyInfoDlg.clearData = function() {
    this.championReplyInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ChampionReplyInfoDlg.set = function(key, val) {
    this.championReplyInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ChampionReplyInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ChampionReplyInfoDlg.close = function() {
    parent.layer.close(window.parent.ChampionReply.layerIndex);
}

/**
 * 收集数据
 */
ChampionReplyInfoDlg.collectData = function() {
    this
    .set('id')
    .set('businessId')
    .set('model')
    .set('championId')
    .set('content')
    .set('createTime')
    .set('championName')
    .set('businessTitle')
    .set('replyDelete');
}

/**
 * 提交添加
 */
ChampionReplyInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/championReply/add", function(data){
        Feng.success("添加成功!");
        window.parent.ChampionReply.table.refresh();
        ChampionReplyInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.championReplyInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ChampionReplyInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/championReply/update", function(data){
        Feng.success("修改成功!");
        window.parent.ChampionReply.table.refresh();
        ChampionReplyInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.championReplyInfoData);
    ajax.start();
}

$(function() {
$("#championId").change(function(e){
    var title =  $("#championId").find("option:selected").attr("title")
    $("#championName").val(title);
});
$("#businessId").change(function(e){
    var title =  $("#businessId").find("option:selected").attr("title")
    $("#businessTitle").val(title);
});
});
