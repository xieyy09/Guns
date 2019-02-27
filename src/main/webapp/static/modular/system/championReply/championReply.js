/**
 * championReply管理初始化
 */
var ChampionReply = {
    id: "ChampionReplyTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ChampionReply.initColumn = function () {
    return [
        {field: 'selectItem', radio: true},
            {title: 'id', field: 'id', visible: false, align: 'center', valign: 'middle'},
            {title: '业务ID', field: 'businessId', visible: false, align: 'center', valign: 'middle'},
            {title: '作品名称', field: 'businessTitle', visible: true, align: 'center', valign: 'middle'},
            {title: '模块', field: 'model', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){
                if(value=='worksDetail'){
                    return "作品";
                }else if(value==''){
                    return "其他";
                }
            }},
            {title: '评论人id', field: 'championId', visible: false, align: 'center', valign: 'middle'},
            {title: '评论人', field: 'championName', visible: false, align: 'center', valign: 'middle'},
            {title: '评论内容', field: 'content', visible: true, align: 'center', valign: 'middle'},
            {title: '评论时间', field: 'createTime', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){
                DateUtils.expandDate();
                var date = new Date(value);
                return date.format('yyyy-MM-dd');
            }},
            {title: '状态', field: 'replyDelete', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){
                if(value==0){
                    return "发布";
                }else if(value==1){
                    return "删除";
                }
            }}
    ];
};

/**
 * 检查是否选中
 */
ChampionReply.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ChampionReply.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加championReply
 */
ChampionReply.openAddChampionReply = function () {
    var index = layer.open({
        type: 2,
        title: '添加擂主回复',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/championReply/championReply_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看championReply详情
 */
ChampionReply.openChampionReplyDetail = function () {
    if (this.check()) {
        var index = layer.open({
            type: 2,
            title: '擂主回复详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/championReply/championReply_update/' + ChampionReply.seItem.id
        });
        this.layerIndex = index;
    }
};

/**
 * 删除championReply
 */
ChampionReply.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/championReply/delete", function (data) {
            Feng.success("删除成功!");
            ChampionReply.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("championReplyId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询championReply列表
 */
ChampionReply.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ChampionReply.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ChampionReply.initColumn();
    var table = new BSTable(ChampionReply.id, "/championReply/list", defaultColunms);
    table.setPaginationType("client");
    ChampionReply.table = table.init();
});
