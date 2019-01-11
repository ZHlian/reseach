package com.rm.zhlian.module_transport_server;

import android.util.Log;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.net.InetAddress;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created on 2019-01-11.
 */

public class Server implements Runnable {
    private static final String TAG = "Server";

    final private ExecutorService executorService;
    private ConcurrentHashMap<Long, SocketSession> mClientList;
    private ServerSocket mServerSocket;
    private int mPort;

    private volatile boolean mIsRunning = true;

    public Server(int port) {
        mPort = port;
        executorService = Executors.newFixedThreadPool(10);
        mClientList = new ConcurrentHashMap<>();
    }

    public void shutDownServer() {
        mIsRunning = false;
        executorService.shutdownNow();
        mClientList.clear();
        mClientList = null;
    }

    private void addToClientList(long id, SocketSession socket) {
        mClientList.put(id, socket);
    }

    private void deleteFromClientList(long id) {
        mClientList.remove(id);
    }

    @Override
    public void run() {
        try {
            mServerSocket = new ServerSocket(mPort);
            while (mIsRunning) {

                Socket socket = mServerSocket.accept();
                long id = System.currentTimeMillis();
                Log.e(TAG, "new connect comming--" + socket + id);

            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }


    class SocketSession implements Runnable,SessionMessageCallBack{

        private long mId;
        private Socket socket;
        private boolean mIsRunning = true;
        private DataReader sessionDataReader;
        private DataWriter sessionWriter;

        public SocketSession(long mId, Socket socket) {
            this.mId = mId;
            this.socket = socket;
            sessionDataReader = new DataReader(mId,socket);
            sessionWriter = new DataWriter(mId,socket);
        }

        public void closeSession(){
            sessionWriter.stopIO();
            sessionDataReader.stopIO();
            if (null!=socket){
                try {
                    socket.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        public void sendSessionMsg(byte[]data){
            if (null!= sessionWriter){
                sessionWriter.addMessage(data);
            }
        }

        @Override
        public void run() {
            sessionDataReader.runIO();
            sessionWriter.runIO();
        }

        @Override
        public void onReceiveMessage(int msgHead, byte[] msgData) {

        }
    }

    private static final class DataReader implements IIOLooper {
        private long mId;
        private Socket socket;
        private boolean reading = true;

        public DataReader(long id, Socket socket) {
            this.mId = id;
            this.socket = socket;
        }

        @Override
        public void runIO() {
            if (null != socket) {
                InputStream inputStream = null;
                try {
                    inputStream = socket.getInputStream();
                    byte[] data = new byte[10240];
                    while (reading) {
                        try {
                            int readLen = readWithLen(data,12,inputStream);
                            int msgHead = getIntFromByteArray(data,0);
                            int leftLen = getIntFromByteArray(data,4);
                            Log.e(TAG,"[session id :]"+mId+"msgHead is:"+msgHead+" leftLen is:"+leftLen);

                        } catch (IOException e) {
                            e.printStackTrace();
                            reading = false;
                            break;
                        }
                    }
                } catch (IOException e) {
                    e.printStackTrace();
                }finally {
                    if (null != inputStream){
                        try {
                            inputStream.close();
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    }
                }
            }
        }

        @Override
        public void stopIO() {
            reading = false;
        }

        private int readWithLen(byte[] buf, int len, InputStream inputStream) throws IOException {
            if (inputStream == null) {
                throw new IOException("input stream null");
            }
            if (len <= 0)
                return len;
            int read = 0;
            int left = len - read;
            int read2;
            while ((read2 = inputStream.read(buf, read, left)) >= 0) {
                read += read2;
                if (read2 == left) break;
                left = len - read;
            }
            return read;

        }

        private int getIntFromByteArray(byte[]array,int startIndex){
            if (array==null||array.length<startIndex+4){
                return -1;
            }
            byte[] resultArray = new byte[4];
            for (int i = 0;i<4;i++){
                resultArray[i]=array[startIndex+i];
            }
            return NumByteArrayUtil.byteArray2Int(resultArray);

        }
    }

    private static final class DataWriter implements IIOLooper{
        private long mId;
        private Socket socket;
        private boolean writting = true;
        private LinkedBlockingQueue<byte[]> messageBlockingQueue;

        public DataWriter(long mId, Socket socket) {
            this.mId = mId;
            this.socket = socket;
            messageBlockingQueue = new LinkedBlockingQueue<byte[]>();
        }

        public void addMessage(byte[]msg){
            try {
                messageBlockingQueue.put(msg);
            } catch (InterruptedException e) {
                e.printStackTrace();
                Log.e(TAG,"add msg failed"+mId);
            }
        }

        @Override
        public void runIO() {
            OutputStream outputStream = null;
            try {
                outputStream = socket.getOutputStream();
                while (writting){
                    if (null != outputStream){
                        try {
                            outputStream.write(messageBlockingQueue.take());
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        } catch (IOException e) {
                            e.printStackTrace();
                            writting = false;
                            break;
                        }
                    }else {
                        break;
                    }
                }
            } catch (IOException e) {
                e.printStackTrace();
            }finally {
                if (null != outputStream){
                    try {
                        outputStream.close();
//                        socket.close();
                    } catch (IOException e) {
                        e.printStackTrace();
                    }
                }
            }


        }

        @Override
        public void stopIO() {
            writting = false;
        }
    }

    private interface SessionMessageCallBack{
        void onReceiveMessage(int msgHead,byte[] msgData);
    }



}
