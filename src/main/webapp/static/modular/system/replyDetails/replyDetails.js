/**
 * 回复管理管理初始化
 */
var ReplyDetails = {
    id: "ReplyDetailsTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
ReplyDetails.initColumn = function () {
    return [
        {field: 'selectItem', radio: true,visible: false},
            {title: '模块', field: 'model', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){
                if(value=='WORKS_DETAILS'){
                    return "作品管理";
                }else if(value=='ANSWER'){
                    return "问答";
                }else if(value==1){
                    return "其他";
                }
            }},
            {title: '父评论ID', field: 'parentId', visible: false, align: 'center', valign: 'middle'},
            {title: '评论内容', field: 'content', visible: true, align: 'center', valign: 'middle'},
            {title: '评论时间', field: 'createTime', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){
                DateUtils.expandDate();
                var date = new Date(value);
                return date.format('yyyy-MM-dd');
            }},
            {title: '状态(0待审核1审核通过 -1 不通过)', field: 'replyState', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){
                if(value==-1){
                    return "不通过";
                }else if(value==0){
                    return "待审核";
                }else if(value==1){
                    return "通过";
                }
            }},
            {title: '擂主回复(0不是 1是)', field: 'championReply', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){
               if(value==0){
                    return "否";
                }else if(value==1){
                    return "是";
                }
            }},
            {title: '操作',field: 'id',formatter:function(value, row, index){
                var html='<button type="button" class="btn btn-info" onclick="ReplyDetails.openReplyDetailsDetail(\''+value+'\')">详情</button>&nbsp;';
                if(row.replyState==0){
                    html+='<button type="button" class="btn btn-primary" onclick="ReplyDetails.reviewData(\''+value+'\',\'tg\')">通过</button>&nbsp;';
                }
                if(row.replyState==0) {
                    html += '<button type="button" class="btn btn-danger" onclick="ReplyDetails.reviewData(\'' + value + '\',\'bh\')">驳回</button>';
                }
            return html;
        }}
    ];
};

/**
 * 检查是否选中
 */
ReplyDetails.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        ReplyDetails.seItem = selected[0];
        return true;
    }
};

/**
 * 点击添加回复管理
 */
ReplyDetails.openAddReplyDetails = function () {
    var index = layer.open({
        type: 2,
        title: '添加回复管理',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/replyDetails/replyDetails_add'
    });
    this.layerIndex = index;
};

/**
 * 打开查看回复管理详情
 */
ReplyDetails.openReplyDetailsDetail = function (id) {
        var index = layer.open({
            type: 2,
            title: '回复管理详情',
            area: ['800px', '420px'], //宽高
            fix: false, //不固定
            maxmin: true,
            content: Feng.ctxPath + '/replyDetails/replyDetails_update/' + id
        });
        this.layerIndex = index;
};


ReplyDetails.reviewData=function(id,type){
    var messageType=type=="tg"?'通过':'驳回';
    var ajax = new $ax(Feng.ctxPath + "/replyDetails/review", function (data) {
        Feng.success(messageType+"成功!");
        ReplyDetails.table.refresh();
    }, function (data) {
        Feng.error(messageType+"失败!" + data.responseJSON.message + "!");
    });
    ajax.set("replyDetailId",id);
    ajax.set("type",type);
    ajax.start();
}

/**
 * 删除回复管理
 */
ReplyDetails.delete = function () {
    if (this.check()) {
        var ajax = new $ax(Feng.ctxPath + "/replyDetails/delete", function (data) {
            Feng.success("删除成功!");
            ReplyDetails.table.refresh();
        }, function (data) {
            Feng.error("删除失败!" + data.responseJSON.message + "!");
        });
        ajax.set("replyDetailsId",this.seItem.id);
        ajax.start();
    }
};

/**
 * 查询回复管理列表
 */
ReplyDetails.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    ReplyDetails.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = ReplyDetails.initColumn();
    var table = new BSTable(ReplyDetails.id, "/replyDetails/list", defaultColunms);
    table.setPaginationType("client");
    ReplyDetails.table = table.init();
});
