package org.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.charset.StandardCharsets;

/**
 * @Author: Hatsuner
 * @Description:
 * @Date: Created in 15:50 2019/12/13
 */
public class ChannelDamo {
    public static void main(String[] args) {
        byte[] bytes = new byte[32];//数组大小表示可以一次性复制的字节数

        FileInputStream fis = null;
        FileOutputStream fos = null;

        FileChannel fci = null;
        FileChannel fco = null;

        try{
            //1.获取数据源 和 目标传输地的输入输出流
            fis = new FileInputStream("test01.txt");
            fos = new FileOutputStream("test02.txt");

            //2.获取数据源的输入输出通道
            fci = fis.getChannel();
            fco = fos.getChannel();

            //3.创建 缓冲区 对象：Buffer(共有2种方法)
            //方法1：使用allocate()静态方法
            ByteBuffer buff01 = ByteBuffer.allocate(128);//创建一个容量为256字节的ByteBuffer
            //注：如果缓冲区太小，也可以重新创建一个大小合适的

            //方法2：通过包装一个已有的数组来创建
            //注：通过包装的方法创建的缓冲区保留了被包装数组内保存的数据
            ByteBuffer buff02 = ByteBuffer.wrap(bytes);

            //额外：如果要将一个字符串存入ByteBuffer,则如下
            String str = "加油！！";
            ByteBuffer buffer = ByteBuffer.wrap(str.getBytes(StandardCharsets.UTF_8));

            //4.从通道读取数据 & 写入到缓冲区
            //注：若已经读取到该通道数据的末尾，则返回-1
            fci.read(buff01);//如果在输入通道里write()，则会报错NonWritableChannelException不可写通道异常

            //5.传出数据准备：将缓存区的 写模式 转换 --> 读模式
            buff01.flip();

            //6.从Buffer中读取数据 & 传出数据到通道
            fco.write(buff01);
            fco.write(buffer);

            //7.重置缓冲区
            //目的：重现现在的缓冲区，即不必为了每次读写都创建新的缓冲区，在再次读取之前要重置缓冲区
            //注：不会改变缓冲区数据，只是重置缓冲区的主要索引值
            buff01.clear();
            buff02.clear();
            buffer.clear();
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            try {
                assert fco != null;
                fco.close();
                fci.close();
                fos.close();
                fis.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
