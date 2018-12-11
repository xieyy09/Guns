/**
 * 初始化科普基地详情对话框
 */
var PopularScienceBaseInfoDlg = {
    editor:null,
    popularScienceBaseInfoData : {}
};

/**
 * 清除数据
 */
PopularScienceBaseInfoDlg.clearData = function() {
    this.popularScienceBaseInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PopularScienceBaseInfoDlg.set = function(key, val) {
    this.popularScienceBaseInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
PopularScienceBaseInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
PopularScienceBaseInfoDlg.close = function() {
    parent.layer.close(window.parent.PopularScienceBase.layerIndex);
}

/**
 * 收集数据
 */
PopularScienceBaseInfoDlg.collectData = function() {
    this.popularScienceBaseInfoData['content'] = PopularScienceBaseInfoDlg.editor.txt.html();
    this
    .set('id')
    .set('title')
    .set('img')
    .set('remark')
    .set('createTime')
    .set('uid')
    .set('ind');
}

/**
 * 提交添加
 */
PopularScienceBaseInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/popularScienceBase/add", function(data){
        Feng.success("添加成功!");
        window.parent.PopularScienceBase.table.refresh();
        PopularScienceBaseInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.popularScienceBaseInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
PopularScienceBaseInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/popularScienceBase/update", function(data){
        Feng.success("修改成功!");
        window.parent.PopularScienceBase.table.refresh();
        PopularScienceBaseInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.popularScienceBaseInfoData);
    ajax.start();
}

$(function() {
    //初始化编辑器
    var E = window.wangEditor;
    var editor = new E('#editor');
    editor.create();
    editor.txt.html($("#contentVal").val());
    PopularScienceBaseInfoDlg.editor = editor;

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
