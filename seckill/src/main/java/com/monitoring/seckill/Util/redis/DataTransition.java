package com.monitoring.seckill.Util.redis;

import org.apache.commons.io.IOUtils;

import java.io.*;

import static redis.clients.util.SafeEncoder.encode;

/**
 * Redis 数据类型转换工具
 *
 * @author XinmingYan @time 2018/12/4 19:10
 */
public class DataTransition {

    /**
     * 把java对象转成二进制
     *
     * @param obj
     * @return
     */
    public static byte[] obj2bytes(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof String) {
            return encode(obj.toString());
        }

        ByteArrayOutputStream os = null;
        ObjectOutputStream oos = null;
        try {
            os = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(os);
            oos.writeObject(obj);
            return os.toByteArray();
        } catch (IOException e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(oos);
            IOUtils.closeQuietly(os);
        }
    }

    /**
     * 把二进制转成java对象
     *
     * @param data  二进制数
     * @param clazz 对象类型
     * @return
     */
    public static <T> T bytes2obj(byte[] data, Class<T> clazz) {
        if (data == null) {
            return null;
        }
        if (String.class.equals(clazz)) {
            return (T) encode(data);
        }
        ObjectInputStream oos = null;
        ByteArrayInputStream os = null;
        try {
            os = new ByteArrayInputStream(data);
            oos = new ObjectInputStream(os);
            return (T) oos.readObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        } finally {
            IOUtils.closeQuietly(oos);
            IOUtils.closeQuietly(os);
        }
    }

}
