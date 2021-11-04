package cn.mysdp.utils;

import org.springframework.util.StringUtils;

import java.io.*;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @ClassName:
 * @Description:
 * @Author: SDP
 * @Date: 2020-10-30
 * Table:
 * Comment:
 */
public class FileUtil {

    public static Map<String, String> fileDateMap = new HashMap<>();
    public static Map<String, String> fileAuthMap = new HashMap<>();
    public static Map<String, String> fileOldMap = new HashMap<>();

    public static void clear() {
        fileDateMap = new HashMap<>();
        fileAuthMap = new HashMap<>();
        fileOldMap = new HashMap<>();
    }

    public static void addOld(String name, String prop, String tsp) {
        fileOldMap.put(name+";"+prop, tsp);
    }

    public static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

    public static String getOld(String name, String prop, String defaultValue) {
        String tsp = fileOldMap.get(name + ";" + prop);
        if (!StringUtils.isEmpty(tsp)) {
            return tsp.trim();
        }
        if ("* @Date:".equals(prop)) {
            return dateFormat.format(new Date());
        } else {
            return defaultValue;
        }
    }

    public static boolean hasOld(String name, String prop) {
        String tsp = fileOldMap.get(name + ";"+prop);
        if (!StringUtils.isEmpty(tsp)) {
            return true;
        }
        return false;
    }

    public static void fetchFileOldProperty(File file) throws IOException {
        if (!file.exists()) {
            return;
        }
        FileInputStream fis = null;
        InputStreamReader read = null;
        BufferedReader bufferedReader = null;
        try {
            fis = new FileInputStream(file);
            read = new InputStreamReader(fis, "utf-8");
            bufferedReader = new BufferedReader(read);
            String txt;
            String[] props = new String[]{"* @Date:", "* @Author:", "* @Version:"};

            while ((txt = bufferedReader.readLine()) != null) {
                txt = txt.trim();
                for (String prop : props) {
                    if (txt.startsWith(prop)) {
                        FileUtil.addOld(file.getAbsolutePath(), prop, txt.replace(prop, ""));
                        break;
                    }
                }
            }
            bufferedReader.close();
            bufferedReader = null;
            read.close();
            read = null;
            fis.close();
            fis = null;

        } catch (Exception ex) {
            ex.printStackTrace();
        }finally {
            if (bufferedReader != null) {
                bufferedReader.close();
            }
            if (read != null) {
                read.close();
            }
            if (fis != null) {
                fis.close();
            }
        }
    }
}
