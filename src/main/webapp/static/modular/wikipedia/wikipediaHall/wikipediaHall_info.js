/**
 * 初始化百科讲堂详情对话框
 */
var WikipediaHallInfoDlg = {
    editor:null,
    wikipediaHallInfoData : {}
};

/**
 * 清除数据
 */
WikipediaHallInfoDlg.clearData = function() {
    this.wikipediaHallInfoData = {};
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WikipediaHallInfoDlg.set = function(key, val) {
    this.wikipediaHallInfoData[key] = (typeof val == "undefined") ? $("#" + key).val() : val;
    return this;
}

/**
 * 设置对话框中的数据
 *
 * @param key 数据的名称
 * @param val 数据的具体值
 */
WikipediaHallInfoDlg.get = function(key) {
    return $("#" + key).val();
}

/**
 * 关闭此对话框
 */
WikipediaHallInfoDlg.close = function() {
    parent.layer.close(window.parent.WikipediaHall.layerIndex);
}

/**
 * 收集数据
 */
WikipediaHallInfoDlg.collectData = function() {
    this.wikipediaHallInfoData['content'] = WikipediaHallInfoDlg.editor.txt.html();
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
WikipediaHallInfoDlg.addSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/wikipediaHall/add", function(data){
        Feng.success("添加成功!");
        window.parent.WikipediaHall.table.refresh();
        WikipediaHallInfoDlg.close();
    },function(data){
        Feng.error("添加失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.wikipediaHallInfoData);
    ajax.start();
}

/**
 * 提交修改
 */
WikipediaHallInfoDlg.editSubmit = function() {

    this.clearData();
    this.collectData();

    //提交信息
    var ajax = new $ax(Feng.ctxPath + "/wikipediaHall/update", function(data){
        Feng.success("修改成功!");
        window.parent.WikipediaHall.table.refresh();
        WikipediaHallInfoDlg.close();
    },function(data){
        Feng.error("修改失败!" + data.responseJSON.message + "!");
    });
    ajax.set(this.wikipediaHallInfoData);
    ajax.start();
}

$(function() {
    //初始化编辑器
    var E = window.wangEditor;
    var editor = new E('#editor');
    editor.create();
    editor.txt.html($("#contentVal").val());
    WikipediaHallInfoDlg.editor = editor;
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
