/**
 * 作品管理管理初始化
 */
var WorksDetails = {
    id: "WorksDetailsTable",	//表格id
    seItem: null,		//选中的条目
    table: null,
    layerIndex: -1
};

/**
 * 初始化表格的列
 */
WorksDetails.initColumn = function () {
    return [
        {field: 'selectItem', radio: true,visible: false},
            {title: '评论数量', field: 'replyNumber', visible: true, align: 'center',width:'90', valign: 'middle'},
            {title: '转发数量', field: 'forwardNumber', visible: true, align: 'center',width:'90', valign: 'middle'},
            {title: '点赞数量', field: 'giveLikeNumber', visible: true, align: 'center',width:'90', valign: 'middle'},
            {title: '创建时间', field: 'createTime', visible: true, align: 'center', valign: 'middle',formatter:function(value, row, index){
                DateUtils.expandDate();
                var date = new Date(value);
                return date.format('yyyy-MM-dd');
            }},
            {title: '审核状态', field: 'state', visible: true, align: 'center',width:'90', valign: 'middle',formatter:function(value, row, index){
                if(value==-1){
                    return "不通过";
                }else if(value==0){
                    return "待审核";
                }else if(value==1){
                    return "通过";
                }
            }},
            // {title: '是否定位', field: 'hasPoint', visible: false, align: 'center', valign: 'middle',formatter:function(value, row, index){
            //     if(value==0){
            //         return "无定位";
            //     }else if(value==1){
            //         return "有定位";
            //     }
            // }},
            {title: '作品名称', field: 'worksTitle', visible: true, align: 'center', valign: 'middle'},
            {title: '图片数量', field: 'imgNumber', visible: true,width:'90', align: 'center', valign: 'middle'},
            {title: '操作',field: 'id',formatter:function(value, row, index){
                    var html='<button type="button" class="btn btn-info" onclick="WorksDetails.openWorksDetailsDetail(\''+value+'\')">详情</button>&nbsp;';
                    if(row.state==0){
                        html+='<button type="button" class="btn btn-primary" onclick="WorksDetails.reviewData(\''+value+'\',\'tg\')">通过</button>&nbsp;';
                    }
                    if(row.state==0) {
                        html += '<button type="button" class="btn btn-danger" onclick="WorksDetails.reviewData(\'' + value + '\',\'bh\')">驳回</button>';
                    }
                    return html;
                }}
    ];
};

/**
 * 检查是否选中
 */
WorksDetails.check = function () {
    var selected = $('#' + this.id).bootstrapTable('getSelections');
    if(selected.length == 0){
        Feng.info("请先选中表格中的某一记录！");
        return false;
    }else{
        WorksDetails.seItem = selected[0];
        return true;
    }
};

WorksDetails.reviewData=function(id,type){
    var messageType=type=="tg"?'通过':'驳回';
    var ajax = new $ax(Feng.ctxPath + "/worksDetails/review", function (data) {
        Feng.success(messageType+"成功!");
        WorksDetails.table.refresh();
    }, function (data) {
        Feng.error(messageType+"失败!" + data.responseJSON.message + "!");
    });
    ajax.set("worksDetailsId",id);
    ajax.set("type",type);
    ajax.start();
}
/**
 * 点击添加作品管理
 */
 WorksDetails.openAddWorksDetails = function () {
     var index = layer.open({
         type: 2,
         title: '添加作品管理',
         area: ['800px', '420px'], //宽高
         fix: false, //不固定
         maxmin: true,
         content: Feng.ctxPath + '/worksDetails/worksDetails_add'
     });
     this.layerIndex = index;
 };

/**
 * 打开查看作品管理详情
 */
WorksDetails.openWorksDetailsDetail = function (id) {
    var index = layer.open({
        type: 2,
        title: '作品管理详情',
        area: ['800px', '420px'], //宽高
        fix: false, //不固定
        maxmin: true,
        content: Feng.ctxPath + '/worksDetails/worksDetails_update/' + id
    });
    layer.full(index);
    this.layerIndex = index;
};

/**
 * 删除作品管理
 */
// WorksDetails.delete = function () {
//     if (this.check()) {
//         var ajax = new $ax(Feng.ctxPath + "/worksDetails/delete", function (data) {
//             Feng.success("删除成功!");
//             WorksDetails.table.refresh();
//         }, function (data) {
//             Feng.error("删除失败!" + data.responseJSON.message + "!");
//         });
//         ajax.set("worksDetailsId",this.seItem.id);
//         ajax.start();
//     }
// };

/**
 * 查询作品管理列表
 */
WorksDetails.search = function () {
    var queryData = {};
    queryData['condition'] = $("#condition").val();
    WorksDetails.table.refresh({query: queryData});
};

$(function () {
    var defaultColunms = WorksDetails.initColumn();
    var table = new BSTable(WorksDetails.id, "/worksDetails/list", defaultColunms);
    table.setPaginationType("client");
    WorksDetails.table = table.init();
});
