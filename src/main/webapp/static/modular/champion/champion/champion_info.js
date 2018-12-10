/**
 * 初始化擂主管理详情对话框
 */
var ChampionInfoDlg = {
    editor:null,
    championInfoData : {}
};

/**
 * 清除数据
 */
ChampionInfoDlg.clearData = function() {
    this.championInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ChampionInfoDlg.set = function(key, val) {
    this.championInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
ChampionInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
ChampionInfoDlg.close = function() {
    parent.layer.close(window.parent.Champion.layerIndex);
}

/**
 * 收集数据
 */
ChampionInfoDlg.collectData = function() {
    this.championInfoData['championRemark'] = ChampionInfoDlg.editor.txt.html();
    this
    .set('id')
    .set('championName')
    .set('uid')
    .set('img')
    .set('professionalField')
    .set('sign')
    .set('createTime')
    .set('createUid')
    .set('replyNumber')
    .set('state');
}

/**
 * 提交添加
 */
ChampionInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/champion/add", function(data){
        Feng.success("添加成功!");
        window.parent.Champion.table.refresh();
        ChampionInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.championInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
ChampionInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/champion/update", function(data){
        Feng.success("修改成功!");
        window.parent.Champion.table.refresh();
        ChampionInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.championInfoData);
    ajax.start();
}

$(function() {
    //初始化是否发布选项
    $("#state").val($("#stateValue").val());
    //初始化编辑器
    var E = window.wangEditor;
    var editor = new E('#editor');
    editor.create();
    editor.txt.html($("#contentVal").val());
    ChampionInfoDlg.editor = editor;

    $('#imgPicID').change(function(){
        var data = new FormData($('#imgUploadForm')[0]);
        $.ajax({
            url: '/activityDetails/upload',
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
