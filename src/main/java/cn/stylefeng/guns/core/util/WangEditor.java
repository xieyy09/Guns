package cn.stylefeng.guns.core.util;

import lombok.Data;

/**
 * 富文本编辑上传图片
 *
 * @author jasonx
 * @create 1/21/19 5:41 PM
 **/
@Data
public class WangEditor {

    private Integer errno;//错误代码，0 表示没有错误

    private String[] data;//已上传的图片路径

    public WangEditor() {
    }

    public WangEditor(String[] data) {
        this.data = data;
        this.errno=0;
    }
}
