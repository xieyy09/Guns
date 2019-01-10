/**
 * 管理初始化
 */
var HeadlineInfo = {
    id: "HeadlineInfoTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
HeadlineInfo.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: '头条标题', field: 'title', visible: true, align: 'center', valign: 'middle'},
            {title: '图片描述', field: 'imgContent', visible: true, align: 'center', valign: 'middle'},
            {title: '模块', field: 'module', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){
                    if(value=="activity"){
                        return "活动模块";
                    }else if(value=="champion"){
                        return "擂主模块";
                    }else if(value=="science"){
                        return "科普模块";
                    }else if(value=="worksDetail"){
                        return "作品模块";
                    }else if(value=="other"){
                        return "其他";
                    }
                }},
            {title: '是否发布', field: 'status', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){
                    if(value==0){
                        return "未发布";
                    }else if(value==1){
                        return "已发布";
                    }
                }},
            {title: '创建时间', field: 'createtime', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){
                    DateUtils.expandDate();
                    var date = new Date(value);
                    return date.format('yyyy-MM-dd');
                }}
    ];
};

/**
 * 检查是否选中
 */
HeadlineInfo.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        HeadlineInfo.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加
 */
HeadlineInfo.openAddHeadlineInfo = function () {
    var index = layer.open({
        type: 2,
        title: '添加',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/headlineInfo/headlineInfo_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看详情
 */
HeadlineInfo.openHeadlineInfoDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/headlineInfo/headlineInfo_update/' + HeadlineInfo.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除
 */
HeadlineInfo.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/headlineInfo/delete", function (data) {
            Feng.success("删除成功!");
            HeadlineInfo.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("headlineInfoId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询列表
 */
HeadlineInfo.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    HeadlineInfo.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = HeadlineInfo.initColumn();
    var table = new BSTable(HeadlineInfo.id, "/headlineInfo/list", defaultColunms);
    table.setPaginationType("client");
    HeadlineInfo.table = table.init();
});
