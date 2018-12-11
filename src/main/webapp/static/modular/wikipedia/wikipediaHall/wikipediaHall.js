/**
 * 百科讲堂管理初始化
 */
var WikipediaHall = {
    id: "WikipediaHallTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
WikipediaHall.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '讲堂名称', field: 'title', visible: true, align: 'center', valign: 'middle'},
            {title: '讲堂说明', field: 'remark', visible: true, align: 'center', valign: 'middle'},
            {title: '讲堂内容', field: 'content', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle'},
            {title: '顺序', field: 'ind', visible: true, align: 'center', valign: 'middle'}
    ];
};

/**
 * 检查是否选中
 */
WikipediaHall.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        WikipediaHall.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加百科讲堂
 */
WikipediaHall.openAddWikipediaHall = function () {
    var index = layer.open({
        type: 2,
        title: '添加百科讲堂',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/wikipediaHall/wikipediaHall_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看百科讲堂详情
 */
WikipediaHall.openWikipediaHallDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '百科讲堂详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/wikipediaHall/wikipediaHall_update/' + WikipediaHall.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除百科讲堂
 */
WikipediaHall.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/wikipediaHall/delete", function (data) {
            Feng.success("删除成功!");
            WikipediaHall.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("wikipediaHallId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询百科讲堂列表
 */
WikipediaHall.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    WikipediaHall.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = WikipediaHall.initColumn();
    var table = new BSTable(WikipediaHall.id, "/wikipediaHall/list", defaultColunms);
    table.setPaginationType("client");
    WikipediaHall.table = table.init();
});
