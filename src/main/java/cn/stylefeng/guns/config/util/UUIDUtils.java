package cn.stylefeng.guns.config.util;

import java.nio.ByteBuffer;
import java.util.Base64;
import java.util.UUID;

/**
 * UUID生成工具类
 *
 * @author JasonX
 * @Date 2018-12-08
 */
public class UUIDUtils {

    public static String getUUID(){
        return UUID.randomUUID().toString().replace("-", "");
    }

    public static String getBase64UUID(){
        UUID uuid = UUID.randomUUID();
        ByteBuffer buffer=ByteBuffer.allocate(16);
        buffer.putLong(uuid.getLeastSignificantBits());
        buffer.putLong(uuid.getMostSignificantBits());

        return Base64.getUrlEncoder().withoutPadding().encodeToString(buffer.array());
    }
    public static String getUUIDByBase64(String base){
        byte[] decode = Base64.getUrlDecoder().decode(base);
        ByteBuffer buffer=ByteBuffer.allocate(16);
        buffer.put(decode);
        buffer.flip();

        long leastSign=buffer.getLong();
        long mostSign=buffer.getLong();

        UUID uuid = new UUID(mostSign, leastSign);
        return uuid.toString();
    }
}
