package cn.mysdp.utils;

import lombok.Getter;
import lombok.Setter;

import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

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
public class KeywordUtil {
    private static Map<String, Set<String>> keys = new ConcurrentHashMap<>();

    private static void initMap(String type) {
        synchronized (keys) {
            if (!keys.containsKey(type)) {
                keys.put(type, new HashSet<>());
            }
            if ("java".equals(type)) {
                String[] words = new String[]{
                        "abstract",
                        "assert",
                        "boolean",
                        "break",
                        "byte",
                        "case",
                        "catch",
                        "char",
                        "class",
                        "const",
                        "continue",
                        "default",
                        "do",
                        "double",
                        "else",
                        "extends",
                        "final",
                        "finally",
                        "float",
                        "for",
                        "goto",
                        "if",
                        "implements",
                        "import",
                        "instanceof",
                        "int",
                        "interface",
                        "long",
                        "native",
                        "new",
                        "null",
                        "package",
                        "private",
                        "protected",
                        "public",
                        "return",
                        "short",
                        "static",
                        "strictfp",
                        "super",
                        "switch",
                        "synchronized",
                        "this",
                        "throw",
                        "throws",
                        "transient",
                        "try",
                        "void",
                        "volatile",
                        "while"};
                for(String word: words) {
                    keys.get(type).add(word);
                }
            }
        }
    }

    public static boolean isKeyword(String key, String type) {
        initMap(type);
        return keys.get(type).contains(key);
    }

}
