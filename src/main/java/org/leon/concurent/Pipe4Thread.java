package org.leon.concurent;

import java.io.IOException;
import java.io.PipedReader;
import java.io.PipedWriter;

/**
 * PipeIO 主要用于线程之间的数据传输
 *
 * Created by LeonWong on 16/4/21.
 */
public class Pipe4Thread {
    public static void main(String[] args) throws Exception {
        PipedWriter out = new PipedWriter();
        PipedReader in = new PipedReader();
        // 将输出流和输入流进行连接,否则在使用是会抛出IOException
        out.connect(in);
        Thread printThread = new Thread(new Print(in), "PrintThread");
        printThread.start();
        int receive;
        try {
            while ((receive = System.in.read()) != -1)
                out.write(receive);
        } finally {
            out.close();
        }
    }

    private static class Print implements Runnable {

        private PipedReader in;

        Print(PipedReader in) {
            this.in = in;
        }

        @Override
        public void run() {
            int receive;
            try {
                while ((receive = in.read()) != -1) {
                    System.out.print((char) receive);
                }
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }
}
