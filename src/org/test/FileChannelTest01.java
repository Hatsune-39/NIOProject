package org.test;

import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: Hatsuner
 * @Description:
 * @Date: Created in 16:55 2019/12/17
 */
public class FileChannelTest01 {
    public static void main(String[] args) throws IOException {
        RandomAccessFile randomAccessFile = new RandomAccessFile("test.txt","rw");
        FileChannel fileChannel = randomAccessFile.getChannel();

        //将文件数据传入Buffer
        ByteBuffer buffer = ByteBuffer.allocate(32);
        while (fileChannel.read(buffer) != -1){
            buffer.flip();
            System.out.println(new String(buffer.array()));
            System.out.println(buffer);
        }

        //将Buffer数据传入文件
        ByteBuffer buffer1 = ByteBuffer.wrap("加油啊".getBytes());
        fileChannel.write(buffer1);
        System.out.println("打印");
        
        fileChannel.close();
        randomAccessFile.close();
    }
}
