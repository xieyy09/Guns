/**
 * 擂主管理管理初始化
 */
var Champion = {
    id: "ChampionTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
Champion.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '擂主姓名', field: 'championName', visible: true, align: 'center', valign: 'middle'},
            {title: '擂主说明', field: 'championRemark', visible: true, align: 'center', valign: 'middle'},
            {title: '联系电话', field: 'phone', visible: true, align: 'center', valign: 'middle'},
            {title: '专业领域', field: 'professionalField', visible: true, align: 'center', valign: 'middle'},
            {title: '签名', field: 'sign', visible: true, align: 'center', valign: 'middle'},
            {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){
                    DateUtils.expandDate();
                    var date = new Date(value);
                    return date.format('yyyy-MM-dd');
                }},
            {title: '评论数量', field: 'replyNumber', visible: true, align: 'center', valign: 'middle'},
            {title: '状态', field: 'state', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){
                    if(value==0){
                        return "未发布";
                    }else if(value==1){
                        return "已发布";
                    }
                }}
    ];
};

/**
 * 检查是否选中
 */
Champion.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        Champion.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加擂主管理
 */
Champion.openAddChampion = function () {
    var index = layer.open({
        type: 2,
        title: '添加擂主管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/champion/champion_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看擂主管理详情
 */
Champion.openChampionDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '擂主管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/champion/champion_update/' + Champion.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除擂主管理
 */
Champion.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/champion/delete", function (data) {
            Feng.success("删除成功!");
            Champion.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("championId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询擂主管理列表
 */
Champion.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    Champion.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = Champion.initColumn();
    var table = new BSTable(Champion.id, "/champion/list", defaultColunms);
    table.setPaginationType("client");
    Champion.table = table.init();
});
