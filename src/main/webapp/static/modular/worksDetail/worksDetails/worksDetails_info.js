/**
 * 初始化作品管理详情对话框
 */
var WorksDetailsInfoDlg = {
    worksDetailsInfoData : {}
};

/**
 * 清除数据
 */
WorksDetailsInfoDlg.clearData = function() {
    this.worksDetailsInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WorksDetailsInfoDlg.set = function(key, val) {
    this.worksDetailsInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WorksDetailsInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
WorksDetailsInfoDlg.close = function() {
    parent.layer.close(window.parent.WorksDetails.layerIndex);
}

/**
 * 收集数据
 */
WorksDetailsInfoDlg.collectData = function() {
    this
    .set('id')
    .set('activityId')
    .set('uid')
    .set('replyNumber')
    .set('forwardNumber')
    .set('giveLikeNumber')
    .set('createTime')
    .set('state')
    .set('hasPoint')
    .set('worksTitle')
    .set('imgNumber')
    .set('imgUrl')
    .set('imgRemark')
    .set('pohtoTime')
    .set('weather')
    .set('address')
    .set('takenAuthor')
    .set('takenTool')
    .set('content')
    .set('answerOne')
    .set('answerTwo')
    .set('answerThree')
    .set('answerFour')
    .set('answerFive')
    .set('answerSix');
}

/**
 * 提交添加
 */
WorksDetailsInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/worksDetails/add", function(data){
        Feng.success("添加成功!");
        window.parent.WorksDetails.table.refresh();
        WorksDetailsInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.worksDetailsInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
WorksDetailsInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/worksDetails/update", function(data){
        Feng.success("修改成功!");
        window.parent.WorksDetails.table.refresh();
        WorksDetailsInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.worksDetailsInfoData);
    ajax.start();
}

$(function() {

});
