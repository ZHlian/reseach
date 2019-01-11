package com.rm.zhlian.module_transport_server;

/**
 * Created on 2019-01-11.
 */

public class NumByteArrayUtil {
    /**
     * 大端
     *
     * @param num
     * @return
     */
    public static byte[] int2ByteArray(int num) {
        byte[] result = new byte[4];
        result[0] = (byte) ((num >> 24) & 0xff);
        result[1] = (byte) ((num >> 16) & 0xff);
        result[2] = (byte) ((num >> 8) & 0xff);
        result[3] = (byte) ((num >> 0) & 0xff);
        return result;
    }

    /**
     * 大端
     *
     * @param array
     * @return
     */
    public static int byteArray2Int(byte[] array) {
        if (array.length == 4) {
            return (array[0] & 0xff) << 24 | (array[1] & 0xff) << 16 | (array[2] & 0xff) << 8 | (array[3] & 0xff);
        } else {
            throw new IllegalArgumentException("array length is not 4");
        }
    }
}
