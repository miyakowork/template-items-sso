package me.wuwenbin.items.sso.service.support.util;

import org.apache.shiro.codec.Base64;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
 * 序列化工具
 * @author wuwenbin
 */
public class SerializableUtils {

    /**
     * 序列化
     *
     * @param t
     * @return
     */
    public static <T> String serialize(T t) {
        try {
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ObjectOutputStream oos = new ObjectOutputStream(bos);
            oos.writeObject(t);
            return Base64.encodeToString(bos.toByteArray());
        } catch (Exception e) {
            throw new RuntimeException("serialize session error", e);
        }
    }

    /**
     * 反序列化
     *
     * @param serializeString
     * @return
     */
    public static <T> T deserialize(String serializeString) {
        try {
            ByteArrayInputStream bis = new ByteArrayInputStream(Base64.decode(serializeString));
            ObjectInputStream ois = new ObjectInputStream(bis);
            //noinspection unchecked
            return (T) ois.readObject();
        } catch (Exception e) {
            throw new RuntimeException("deserialize session error", e);
        }
    }

}
