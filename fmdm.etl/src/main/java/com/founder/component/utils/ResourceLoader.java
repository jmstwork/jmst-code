package com.founder.component.utils;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Properties;

/**
 * 配置文件读取类�?<BR>
 * 
 * @version 1.0
 */
public final class ResourceLoader {

    /** */
    private static ClassLoader defaultClassLoader = null;

    /**
     * コンストラク�?
     */
    private ResourceLoader() {
    }

    /**
     * デフォルトクラスロー�?��を取得する�?
     * 
     * @return デフォルトクラスロー�?��
     */
    public static ClassLoader getDefaultClassLoader() {
        return defaultClassLoader;
    }

    /**
     * デフォルトクラスロー�?��を設定する�?
     * 
     * @param classLoader デフォルトクラスロー�?��
     */
    public static void setDefaultClassLoader(ClassLoader classLoader) {
        defaultClassLoader = classLoader;
    }

    /**
     * 入力ストリームを取得する�?
     * 
     * @param resource ファイル�?
     * @return 入力ストリー�?
     */
    public static InputStream getResourceAsStream(String resource) {
        return getResourceAsStream(getClassLoader(), resource);
    }

    /**
     * 入力ストリームを取得する�?
     * 
     * @param loader クラスローダ�?
     * @param resource ファイル�?
     * @return 入力ストリー�?
     */
    public static InputStream getResourceAsStream(ClassLoader loader,
            String resource) {
        InputStream in = null;
        if (loader != null) {
            in = loader.getResourceAsStream(resource);
        }
        if (in == null) {
            in = ClassLoader.getSystemResourceAsStream(resource);
        }
        if (in == null) {
            throw new RuntimeException("Could not find resource " + resource);
        } else {
            return in;
        }
    }

    /**
     * プロパティ内容を取得する�?
     * 
     * @param resource ファイル�?
     * @return プロパティ内�?
     */
    public static Properties getResourceAsProperties(String resource) {
        Properties props;
        InputStream in;
        try {
            props = new Properties();
            in = getResourceAsStream(resource);
            props.load(in);
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        try {
            in.close();
        } catch (IOException ignore) {
            throw new RuntimeException(ignore);
        }
        return props;
    }

    /**
     * プロパティ内容を取得する�?
     * 
     * @param resource ファイル�?
     * @return プロパティ内�?
     */
    public static String getResourceAsString(String resource) {
        InputStream inputStream;
        StringBuffer sbXml = new StringBuffer("");
        BufferedReader target = null;
        try {
            inputStream = getResourceAsStream(resource);
            InputStreamReader input = new InputStreamReader(inputStream,
                    "UTF-8");
            target = new BufferedReader(input);
            String targetLine = (String)target.readLine();
            while (targetLine != null) {
                sbXml.append(targetLine);
                targetLine = (String)target.readLine();
            }
        } catch (IOException ioe) {
            throw new RuntimeException(ioe);
        }
        try {
            target.close();
        } catch (IOException ignore) {
            throw new RuntimeException(ignore);
        }
        return sbXml.toString();
    }

    /**
     * クラスローダーを取得する�?
     * 
     * @return クラスローダ�?
     */
    private static ClassLoader getClassLoader() {
        if (defaultClassLoader != null) {
            return defaultClassLoader;
        } else {
            return Thread.currentThread().getContextClassLoader();
        }
    }
}
