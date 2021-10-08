package com.marchsoft.secondkill.common.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class StringUtils extends org.apache.commons.lang3.StringUtils {

    private static final Logger LOGGER = LoggerFactory.getLogger(StringUtils.class);

    private static Pattern htmlPattern = null;

    private static Pattern scriptPattern = null;

    private static Pattern stylePattern = null;

    private static Pattern spacePattern = null;

    static {
        try {
            htmlPattern = Pattern.compile("</?[^>]+?>");
            spacePattern = Pattern.compile("\\&nbsp;|\\s|\\t|\\r|\\n|\\&quot;|\\&amp;|\\&lt;|\\&gt;");
            scriptPattern = Pattern.compile("<[\\s]*?script[^>]*?>[\\s\\S]*?<[\\s]*?///[\\s]*?script[\\s]*?>",
                    Pattern.CASE_INSENSITIVE);
            stylePattern = Pattern.compile("<[\\s]*?style[^>]*?>[\\s\\S]*?<[\\s]*?///[\\s]*?style[\\s]*?>",
                    Pattern.CASE_INSENSITIVE);
        } catch (Exception e) {
            LOGGER.error("Init htmlPattern error", e);
        }
    }

    public static String trimLeadingOrTrailingCharacter(String string, char character) {
        if (org.apache.commons.lang3.StringUtils.isBlank(string)) {
            return "";
        }
        String result = org.springframework.util.StringUtils.trimLeadingCharacter(string, character);
        result = org.springframework.util.StringUtils.trimTrailingCharacter(result, character);
        return result;
    }

    public static boolean isHttpUrl(String url) {
        if (StringUtils.isNotBlank(url)) {
            return url.startsWith("https://") || url.startsWith("http://");
        }
        return false;
    }

    public static List<Long> splitList(String str, String sep) {

        List<Long> list = new ArrayList<Long>();
        String[] strArr = StringUtils.split(str, sep);
        if (null != strArr && strArr.length > 0) {
            for (String s : strArr) {
                if (StringUtils.isNumeric(s)) {
                    list.add(Long.valueOf(s));
                }
            }
        }
        return list;
    }

    public static String skipChar(String html) {
        if (StringUtils.isBlank(html)) {
            return "";
        }

        try {
            Matcher sm = spacePattern.matcher(html);
            if (null != sm && sm.find()) {
                return sm.replaceAll("");
            }
        } catch (Exception e) {
            LOGGER.error("Skip html error", e);
        }
        return html;
    }

    public static String skipHtml(String html) {

        if (StringUtils.isBlank(html)) {
            return "";
        }

        try {
            Matcher scm = scriptPattern.matcher(html);
            if (null != scm && scm.find()) {
                html = scm.replaceAll("");
            }
            Matcher stm = stylePattern.matcher(html);
            if (null != stm && stm.find()) {
                html = stm.replaceAll("");
            }
            Matcher sm = htmlPattern.matcher(html);
            if (null != sm && sm.find()) {
                html = sm.replaceAll("");
            }
            Matcher m = spacePattern.matcher(html);
            if (null != m && m.find()) {
                return m.replaceAll(" ");
            }
        } catch (Exception e) {
            LOGGER.error("Skip html error", e);
        }
        return html;
    }

    public static String skipScriptTag(String html) {

        if (StringUtils.isBlank(html)) {
            return "";
        }

        try {
            Matcher scm = scriptPattern.matcher(html);
            if (null != scm && scm.find()) {
                html = scm.replaceAll("");
            }
        } catch (Exception e) {
            LOGGER.error("Skip script tag", e);
        }
        return html;
    }

    public static String skipSpecifyCss(String html) {

        if (StringUtils.isBlank(html)) {
            return "";
        }

        try {
            if (html.indexOf("position:fixed") != -1) {
                html = html.replaceAll("position:fixed", "");
            }
        } catch (Exception e) {
            LOGGER.error("Skip script tag", e);
        }
        return html;
    }

    /**
     * 获取字符长度为len的子串
     *
     * @param str
     * @param len 默认<code>...</code>
     * @return
     */
    public static String subString(String str, int len) {
        String suffix = "...";
        return str.substring(0, len) + suffix;
    }

    public static int getStringByteLenths(String args) {
        try {
            return args != null && args != "" ? args.getBytes("utf-8").length : 0;
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
            return 0;
        }
    }

    public static String substringByte(String orignal, int count) {
        return substringByte(orignal, 0, count) + "...";
    }

    public static String substringByte(String orignal, int start, int count) {

        if (orignal == null || "".equals(orignal)) {
            return orignal;
        }

        if (count <= 0) {
            return orignal;
        }

        if (start < 0) {
            start = 0;
        }

        StringBuffer buff = new StringBuffer();

        try {

            if (start >= getStringByteLenths(orignal)) {
                return null;
            }

            int len = 0;

            char c;

            for (int i = 0; i < orignal.toCharArray().length; i++) {

                c = orignal.charAt(i);

                if (start == 0) {

                    len += String.valueOf(c).getBytes("utf-8").length;
                    if (len <= count) {
                        buff.append(c);
                    } else {
                        break;
                    }

                } else {

                    len += String.valueOf(c).getBytes("utf-8").length;
                    if (len >= start && len <= start + count) {
                        buff.append(c);
                    }
                    if (len > start + count) {
                        break;
                    }

                }
            }

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return new String(buff);
    }

    public static String subText(String str, int len, String... more) {
        if (StringUtils.isBlank(str)) {
            return "";
        }

        String rstr = skipHtml(str);
        if (rstr.length() < len + 1) {
            return rstr;
        }

        String suffix = "...";
        if (null != more && more.length > 0) {
            suffix = more[0];
        }
        return rstr.substring(0, len) + suffix;
    }

    public static String stripNonValidXMLCharacters(String in) {
        StringBuffer out = new StringBuffer(); // Used to hold the output.
        char current; // Used to reference the current character.

        if (StringUtils.isBlank(in)) {
            return null;
        }
        for (int i = 0; i < in.length(); i++) {
            current = in.charAt(i); // NOTE: No IndexOutOfBoundsException caught
            // here; it should not happen.
            if ((current == 0x9) || (current == 0xA) || (current == 0xD) || ((current >= 0x20) && (current <= 0xD7FF))
                    || ((current >= 0xE000) && (current <= 0xFFFD))
                    || ((current >= 0x10000) && (current <= 0x10FFFF))) {
                out.append(current);
            }
        }
        return out.toString();
    }

    /**
     * 对内容进行处理，去除xml的无效字符
     *
     * @param in
     * @return
     */

    public static String stripNonValidXMLCharactersDefaultEmpty(String in) {
        String value = stripNonValidXMLCharacters(in);
        if (StringUtils.isBlank(value)) {
            return StringUtils.EMPTY;
        }
        return value;
    }


    public static String stripUrlLastSlash(String url) {
        if (StringUtils.isNotBlank(url) && url.endsWith("/")) {
            url = url.substring(0, url.length() - 1);
        }
        return url;
    }

    /**
     * 替换，不考虑正则表达式的问题，可能有更好的性能
     *
     * @param original 原串
     * @param tobe     需要替换的
     * @param replace  替换的
     * @return 替换后的字符串
     */
    public static String replaceAll(String original, String tobe, String replace) {
        if (original == null) {
            return null;
        }
        StringBuilder sb = new StringBuilder();
        int end = 0;
        int start = 0;
        while (true) {
            end = original.indexOf(tobe, start);
            if (end != -1) {
                sb.append(original.substring(start, end));
                sb.append(replace);
                start = end + tobe.length();
            } else {
                sb.append(original.substring(start));
                break;
            }
        }
        return sb.toString();
    }

    public static String joinPathToUrl(String baseUrl, String... paths) {
        if (StringUtils.isBlank(baseUrl)) {
            return org.apache.commons.lang3.StringUtils.EMPTY;
        }
        if (paths == null || paths.length <= 0) {
            return baseUrl;
        }
        if (baseUrl.endsWith("/")) {
            baseUrl = baseUrl.substring(0, baseUrl.length() - 1);
        }
        StringBuilder builder = new StringBuilder(baseUrl);
        for (String path : paths) {
            if (StringUtils.isNotBlank(path)) {
                if (path.startsWith("/")) {
                    builder.append(path);
                } else {
                    builder.append("/");
                    builder.append(path);
                }
            }
        }
        return builder.toString();
    }

    public static String md5Encode(String val) {
        try {
            MessageDigest md5 = MessageDigest.getInstance("MD5");
            md5.update(val.getBytes());
            byte[] md5bytes = md5.digest();
            String result = byte2hex(md5bytes);
            return result;
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
            if (n < b.length - 1) {
                hs = hs + ":";
            }
        }
        return hs.toUpperCase();
    }

    public static void main(String[] args) {
        String html = "<p>你猜猜我是谁</p>\n<p>你猜猜我是谁</p>";
        html = StringUtils.subText(html, html.length());
        System.out.println(html);
    }
}
