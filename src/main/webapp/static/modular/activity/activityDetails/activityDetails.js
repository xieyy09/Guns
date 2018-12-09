/**
 * 活动管理管理初始化
 */
var ActivityDetails = {
    id: "ActivityDetailsTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ActivityDetails.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '标题', field: 'title', visible: true, align: 'center', valign: 'middle'},
            {title: '开始时间', field: 'beginTime', visible: true, align: 'center', valign: 'middle'},
            {title: '结束时间', field: 'endTime', visible: true, align: 'center', valign: 'middle'},
            {title: '状态', field: 'activityState', visible: true, align: 'center', valign: 'middle'},
            {title: '发布人', field: 'uid', visible: true, align: 'center', valign: 'middle'},
            {title: '发布时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
            {title: '参加人数', field: 'userNumber', visible: true, align: 'center', valign: 'middle'},
            {title: '发布状态', field: 'state', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
ActivityDetails.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ActivityDetails.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加活动管理
 */
ActivityDetails.openAddActivityDetails = function () {
    var index = layer.open({
        type: 2,
        title: '添加活动管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/activityDetails/activityDetails_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看活动管理详情
 */
ActivityDetails.openActivityDetailsDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '活动管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/activityDetails/activityDetails_update/' + ActivityDetails.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除活动管理
 */
ActivityDetails.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/activityDetails/delete", function (data) {
            Feng.success("删除成功!");
            ActivityDetails.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("activityDetailsId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询活动管理列表
 */
ActivityDetails.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ActivityDetails.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ActivityDetails.initColumn();
    var table = new BSTable(ActivityDetails.id, "/activityDetails/list", defaultColunms);
    table.setPaginationType("client");
    ActivityDetails.table = table.init();
});
