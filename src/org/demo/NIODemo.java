package org.demo;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * @Author: Hatsuner
 * @Description:用NIO完成文件的读取输出来复制一份文件
 * @Date: Created in 14:11 2019/12/13
 */
public class NIODemo {
    public static void main(String[] args) {
        int count = 0;

        //设置输入流&输出流 = 文件
        String infile = "Dragon.jpg";
        String outfile = "image/Dragon.jpg";

        try {
            //1.获取数据源和目标传送地的输入输出流(此处以数据源 = 文件为例)
            FileInputStream fileInputStream = new FileInputStream(infile);
            FileOutputStream fileOutputStream = new FileOutputStream(outfile);

            //2.获取数据源的输入输出通道
            FileChannel fci = fileInputStream.getChannel();
            FileChannel fco = fileOutputStream.getChannel();

            //3.创建缓冲区对象
            ByteBuffer buff = ByteBuffer.allocate(1024);

            while(true){

                //4.从通道读取数据 & 写入到缓冲区
                //注：若已经读取到该通道数据的末尾，则返回-1
                int read = fci.read(buff);
                if (read == -1){
                    break;
                }

                //5.传出数据准备：调用flip()方法
                buff.flip();

                //6.从Buffer中读取数据 & 传出数据到通道
                fco.write(buff);
//                buff.mark();
//                fco.write(buff);
//                buff.reset();
//                fco.write(buff);
                //7.重置缓冲区
                buff.clear();

                count++;
            }
        } catch (IOException e) {
            e.printStackTrace();
        }finally {
            System.out.println("复制成功，共复制了" + count + "次");
        }
    }
}
