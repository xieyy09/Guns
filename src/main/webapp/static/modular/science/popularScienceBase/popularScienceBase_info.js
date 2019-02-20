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
    PopularScienceBaseInfoDlg.editor = editor;

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
