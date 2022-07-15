package cn.mysdp.utils;

import lombok.Getter;
import lombok.Setter;

/**
 * @ClassName:
 * @Description:
 * @Author: SDP
 * @Date: 2020-10-30
 * Table:
 * Comment:
 */
@Getter
@Setter
public class ByteWithPos {
    public ByteWithPos(int length) {
        bytes = new byte[length];
        pos = 0;
    }
    public byte[] bytes;
    public int pos;

    public static void appendDestBytes(ByteWithPos destBytes, String v) throws Exception {
        byte[] srcBytes = v.getBytes("UTF-8");
        appendDestBytes(destBytes, srcBytes, 0, srcBytes.length);
    }

    public static void appendLineSeperator(ByteWithPos destBytes) throws Exception {
        appendDestBytes(destBytes, System.lineSeparator());
    }

    public static void appendDestBytes(ByteWithPos destBytes, byte[] srcBytes, int srcPos, int srcEndPos) throws Exception {
        if (srcEndPos >= srcBytes.length) {
            srcEndPos = srcBytes.length;
        }
        if (srcPos >= srcBytes.length) {
            return;
        }
        if (srcEndPos <= srcPos) {
            return;
        }
        if (destBytes.pos + srcEndPos - srcPos >= destBytes.bytes.length - 1) {
            byte[]newDestBytes = new byte[(destBytes.pos + srcEndPos - srcPos + 1024)*2];
            if (destBytes.pos > 0) {
                System.arraycopy(destBytes.bytes, 0, newDestBytes, 0, destBytes.pos);
            }
            destBytes.bytes = newDestBytes;
        }
        try {
            System.arraycopy(srcBytes, srcPos, destBytes.bytes, destBytes.pos, srcEndPos - srcPos);
        }catch(Exception ex) {
            ex.printStackTrace();
        }
        destBytes.pos += srcEndPos - srcPos;
    }

}
