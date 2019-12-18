package org.demo;

import java.nio.ByteBuffer;

/**
 * @Author: Hatsuner
 * @Description:
 * @Date: Created in 11:21 2019/12/16
 */
public class BufferDemo {
    private static ByteBuffer buffer = ByteBuffer.allocate(128);
    public static void main(String[] args) {
        System.out.println("----Test reset----");
        buffer.clear();//清空此缓冲区
        buffer.position(5);//设置这个缓冲区的位置(position)
        buffer.mark();//将此缓冲区的标记(mark)设置到position所在的位置,mark默认为-1
        buffer.position(10);
        System.out.println("before reset:" + buffer);//pos(position)位置,lim(limit)限制,cap(capacity)容量
        buffer.reset();//将此缓冲区的位置重置为先前标记(mark)的位置，如果mark没有设置即为-1，出现InvalidMarkException(无效的标记异常)
        System.out.println("after reset:" + buffer);

        System.out.println("----Test rewind----");
        buffer.clear();
        buffer.position(10);
        buffer.limit(15);
        System.out.println("before rewind:" + buffer);
        buffer.rewind();//重置这个缓冲区，将position设置为0，将mark设置为-1(丢弃),其他不变
        System.out.println("after rewind:" + buffer);

        System.out.println("----Test compact----");
        buffer.clear();
        buffer.put("hatsune miku".getBytes());//向缓冲区存入一组Byte类型的数组
        System.out.println("before compact:" + buffer);
        System.out.println(new String(buffer.array()));
        buffer.flip();//反转这个缓冲区，将限制(limit)设置为当前位置(position)，然后将位置(position)设置为0，如果有标记(mark)，将遗弃标记，即mark = -1
        System.out.println("after flip:" + buffer);
        System.out.println((char)buffer.get());
        System.out.println((char)buffer.get());
        System.out.println((char)buffer.get());
        System.out.println("after three gets:" + buffer);//经过三次获取后，position变为3
        System.out.println("\t" + new String(buffer.array()));
        buffer.compact();//将position此时所在位置及其之后的元素复制到position = 0的位置及以后覆盖原有数据，这里position为3，所以运行后缓冲区变为sune mikuiku
        System.out.println("after compact:" + buffer);
        System.out.println("\t" + new String(buffer.array()));

        System.out.println("----Test get----");
        buffer = ByteBuffer.allocate(32);
        buffer.put((byte)'a').put((byte)'b').put((byte)'c').put((byte)'d')
                .put((byte)'e').put((byte)'f');
        System.out.println("before flip()" + buffer);
        //转换成读取模式
        buffer.flip();
        System.out.println("before get():" + buffer);
        System.out.println((char)buffer.get());//相对获取方法，读取该缓冲区当前位置的字节，然后增加位置(position+1)
        System.out.println("after get():" + buffer);
        //get(index)不影响position的值
        System.out.println((char) buffer.get(2));
        System.out.println("after get(index):" + buffer);
        byte[] dst = new byte[10];
        buffer.get(dst,2,3);//dst数组从buffer中position(1)所在的位置开始获取了length(3)个数的元素，存入到从下标offset(2)开始的位置
        System.out.println("after get(dst,3,2):" + buffer);
        System.out.println("\t dst:" + new String(dst));
        System.out.println("buffer no is:" + buffer);
        System.out.println("\t" + new String(buffer.array()));

        System.out.println("----Test put----");
        ByteBuffer bb = ByteBuffer.allocate(32);
        System.out.println("before put(byte):" + bb);
        System.out.println("after put(byte):" + bb.put((byte)'z'));//put(byte b)将position+1
        System.out.println("\t" + bb.put(2,(byte)'c'));
        //put(2,(byte)'c')不改变position的位置
        System.out.println("after put(2,(byte)'c'):" + bb);
        System.out.println("\t" + new String(bb.array()));
        //这里的buffer是abcdef[pos=4 lim=6 cap=32]
        bb.put(buffer);//从buffer的position位置开始将元素复制到bb的position位置
        System.out.println("after put(buffer):" + bb);
        System.out.println("\t" + new String(bb.array()));
        System.out.println(buffer);
    }
}
