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
public class SplitUtil {
    public static String[] split(String str, String v) {
        String[] strs = str.split(v);
        if (strs.length == 0) {
            return new String[]{""};
        }
        return strs;
    }

}
