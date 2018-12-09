@/*
    表单中input框标签中各个参数的说明:

    hidden : input hidden框的id
    id : input框id
    name : input框名称
    readonly : readonly属性
    clickFun : 点击事件的方法名
    style : 附加的css属性
@*/
<div class="form-group">
    <label class="col-sm-3 control-label">${name}</label>
    <div class="col-sm-9">
        <input id="${id}" name="path" type="hidden"/>
        <form id="${id}UploadForm" enctype='multipart/form-data'>
            <input id="path" name="path" type="hidden" value="${path}"/>
            <div class="form-group">
                <div class="fileinput fileinput-new" data-provides="fileinput" id="${id}InputUpload">
                    <div class="fileinput-new thumbnail" style="width: 200px;height: auto;max-height:150px;">
                        <img id='picImg' style="width: 100%;height: auto;max-height: 140px;" src="/activityDetails/loadImg?path=${path}&filename=${value!'noimage.png'}" alt="" />
                    </div>
                    <div class="fileinput-preview fileinput-exists thumbnail" style="max-width: 200px; max-height: 150px;"></div>
                    <div>
                        <span class="btn btn-primary btn-file">
                        <span class="fileinput-new">选择文件</span>
                        <span class="fileinput-exists">换一张</span>
                        <input type="file" name="file" id="${id}PicID" accept="image/gif,image/jpeg,image/x-png" />
                        </span>
                    </div>
                </div>
            </div>
        </form>
    </div>
</div>
@if(isNotEmpty(underline) && underline == 'true'){
    <div class="hr-line-dashed"></div>
@}


