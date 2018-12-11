/**
 * 科普基地管理初始化
 */
var PopularScienceBase = {
    id: "PopularScienceBaseTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
PopularScienceBase.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '基地名称', field: 'title', visible: true, align: 'center', valign: 'middle'},
            {title: '基地说明', field: 'remark', visible: true, align: 'center', valign: 'middle'},
            {title: '基地内容', field: 'content', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
            {title: '顺序', field: 'ind', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
PopularScienceBase.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        PopularScienceBase.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加科普基地
 */
PopularScienceBase.openAddPopularScienceBase = function () {
    var index = layer.open({
        type: 2,
        title: '添加科普基地',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/popularScienceBase/popularScienceBase_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看科普基地详情
 */
PopularScienceBase.openPopularScienceBaseDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '科普基地详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/popularScienceBase/popularScienceBase_update/' + PopularScienceBase.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除科普基地
 */
PopularScienceBase.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/popularScienceBase/delete", function (data) {
            Feng.success("删除成功!");
            PopularScienceBase.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("popularScienceBaseId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询科普基地列表
 */
PopularScienceBase.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    PopularScienceBase.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = PopularScienceBase.initColumn();
    var table = new BSTable(PopularScienceBase.id, "/popularScienceBase/list", defaultColunms);
    table.setPaginationType("client");
    PopularScienceBase.table = table.init();
});
