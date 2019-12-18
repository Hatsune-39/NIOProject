package org.test;

import java.io.*;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: Hatsuner
 * @Description:
 * @Date: Created in 19:51 2019/12/16
 */
public class FileChannelTest {
    public static void main(String[] args) throws IOException {
        //1.创建一个RandomAccessFile(随机访问文件)对象
        RandomAccessFile raf = new RandomAccessFile("test01.txt","rw");
//        FileInputStream raf = new FileInputStream("test01.txt");
        //通过RandomAccessFile对象的getChannel方法。FileChannel是抽象类。
        FileChannel inChannel = raf.getChannel();
        //2.创建一个读数据缓冲区对象
        ByteBuffer inBuffer = ByteBuffer.allocate(32);
        //3.从通道中读取数据
        int bytesRead = inChannel.read(inBuffer);
        //创建一个写数据缓冲区对象
        ByteBuffer outBuffer = ByteBuffer.allocate(32);
        //写入数据
        outBuffer.put("残酷正义号".getBytes());
        outBuffer.flip();
        inChannel.write(outBuffer);
        while(bytesRead != -1){
            System.out.println("Read" + bytesRead);
            //Buffer有两种模式，写模式和读模式。在写模式下调用flip()后，Buffer从写模式变成读模式
            inBuffer.flip();
            //如果还有未读内容
            while (inBuffer.hasRemaining()){
                System.out.print((char)inBuffer.get());
            }
            //清空缓存区
            inBuffer.clear();
            bytesRead = inChannel.read(inBuffer);
        }
        //关闭RandomAccessFile(随机访问文件)对象
        raf.close();
    }
}
