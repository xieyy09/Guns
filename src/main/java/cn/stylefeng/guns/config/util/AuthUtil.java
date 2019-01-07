package cn.stylefeng.guns.config.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;

import java.io.IOException;

public class AuthUtil {
    public static final String OPENID = "OPENID";
    public static final String NICKNAME = "NICKNAME";
    public static final String APPID = "wx8212f6193ad8e227";
    public static final String APPSECRET = "3de6a459ef5b8c0cf4f90eb46cb69ea6";
//    public static final String SERVER = "http://193.112.92.40/photo";
    public static final String SERVER = "http://ycsshj.oicp.net";
    public static final String TOKEN = "weixi";

    public static JSONObject doGetJson(String url) throws ClientProtocolException, IOException {
        JSONObject jsonObject = null;
        //首先初始化HttpClient对象
        DefaultHttpClient client = new DefaultHttpClient();
        //通过get方式进行提交
        HttpGet httpGet = new HttpGet(url);
        //通过HTTPclient的execute方法进行发送请求
        HttpResponse response = client.execute(httpGet);
        //从response里面拿自己想要的结果
        HttpEntity entity = response.getEntity();
        if(entity != null){
            String result = EntityUtils.toString(entity,"UTF-8");
            jsonObject = JSON.parseObject(result);
        }
        //把链接释放掉
        httpGet.releaseConnection();
        return jsonObject;
    }
}
