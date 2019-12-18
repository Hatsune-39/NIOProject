package org.test;

import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.Selector;

/**
 * @Author: Hatsuner
 * @Description:
 * @Date: Created in 17:27 2019/12/13
 */
public class Server {
    private Selector selector;
    private ByteBuffer readBuffer = ByteBuffer.allocate(1024);//调整缓冲区大小为1024
    private ByteBuffer sendBuffer = ByteBuffer.allocate(1024);
    String str;

    public void start() throws IOException{
        //打开服务器套接字通道
    }

    public static void main(String[] args) throws IOException {
        System.out.println("server start...");
        new Server().start();
    }
}
