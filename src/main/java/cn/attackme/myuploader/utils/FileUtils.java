package cn.attackme.myuploader.utils;


import java.io.*;
import java.net.FileNameMap;
import java.net.URLConnection;
import java.util.UUID;

/**
 * 文件操作工具类
 */
public class FileUtils {

    /**
     * 写入文件
     * @param target
     * @param src
     * @throws IOException
     */
    public static void write(String target, InputStream src) throws IOException {
        OutputStream os = new FileOutputStream(target);
        byte[] buf = new byte[1024];
        int len;
        while (-1 != (len = src.read(buf))) {
            os.write(buf,0,len);
        }
        os.flush();
        os.close();
    }

    /**
     * 分块写入文件
     * @param target
     * @param targetSize
     * @param src
     * @param srcSize
     * @param chunks
     * @param chunk
     * @throws IOException
     */
    public static void writeWithBlok(String target, Long targetSize, InputStream src, Long srcSize, Integer chunks, Integer chunk) throws IOException {
        //断点续传使用的流RandomAccessFile
        RandomAccessFile randomAccessFile = new RandomAccessFile(target,"rw");
        //需要写总文件的大小
        randomAccessFile.setLength(targetSize);
        //如果是最后一块
        if (chunk == chunks - 1) {
            //需要写入的位置，总的减去最后一块的大小
            randomAccessFile.seek(targetSize - srcSize);
        } else {
            //不是最后一块，找到写入的起始位置
            randomAccessFile.seek(chunk * srcSize);
        }
        byte[] buf = new byte[1024];
        int len;
        while (-1 != (len = src.read(buf))) {
            randomAccessFile.write(buf,0,len);
        }
        randomAccessFile.close();
    }

    /**
     * 生成随机文件名
     * @return
     */
    public static String generateFileName() {
        return UUID.randomUUID().toString();
    }

    /**
     * 判断是否为图片或者视频
     * @param fileName
     * @return
     */

    public static int getFileType(String fileName) {
        int i = 0;
        FileNameMap fileNameMap = URLConnection.getFileNameMap();
        String contentTypeFor = fileNameMap.getContentTypeFor(fileName);
        if (contentTypeFor != null) {// 当是图片时不为空，是视频时为空
            i = 1;
        }
        return i;
    }
}
