package net.cartola.emissorfiscal.util;

import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 *
 * @author Murilo
 */
public class XmlUtil {

    private static final String a = "#NOME_TAG#";
    private static final String b = "(<\\s*[/]{0,1}\\s*#NOME_TAG#(\\s+[^<]*|\\s*)>|<\\s*#NOME_TAG#(\\s+[^<]*|\\s*)/{0,1}\\s*>)";
    private static final String c = "<[^><]*/\\s*>";

    public static List<String> getTagConteudo(String xml, String nomeTag, boolean incluirTag) {
        if (xml == null || nomeTag == null) {
//            return null;
        	return new ArrayList<String>();
        }
        List<String> tags = new ArrayList<String>();
        String regex = "(<\\s*[/]{0,1}\\s*#NOME_TAG#(\\s+[^<]*|\\s*)>|<\\s*#NOME_TAG#(\\s+[^<]*|\\s*)/{0,1}\\s*>)".replace("#NOME_TAG#", nomeTag);
        Matcher matcher = Pattern.compile(regex).matcher(xml);
        int start = 0;
        int end = 0;
        int diff = incluirTag ? 1 : 0;
        String group = null;
        while (matcher.find(start)) {
            if (incluirTag) {
                start = matcher.start();
            } else {
                start = matcher.end();
            }
            group = matcher.group();
            if (Pattern.matches("<[^><]*/\\s*>", group)) {
                if (incluirTag) {
                    tags.add(group);
                    start += diff;
                }
            } else {
                matcher.find(start + diff);
                if (incluirTag) {
                    end = matcher.end();
                } else {
                    end = matcher.start();
                }
                tags.add(xml.substring(start, end));
                start = matcher.end();
            }
        }
        return tags;
//        return tags.isEmpty() ? null : tags;
    }

    public static String alterarTagConteudo(String xml, String tagName,
            String newValue) {
        String startTag = new StringBuilder().append("<").append(tagName).append(">").toString();
        String endTag = new StringBuilder().append("</").append(tagName).append(">").toString();
        String emptyTag = new StringBuilder().append("<").append(tagName).append("/>").toString();
        int indexStart = xml.indexOf(startTag) + startTag.length();
        if (indexStart > 0) {
            int indexClose = xml.indexOf(endTag);
            xml = new StringBuilder().append(xml.substring(0, indexStart)).append(newValue).append(xml.substring(indexClose)).toString();
        } else {
            int indexEmpty = xml.indexOf(emptyTag);
            if (indexEmpty > 0) {
                xml = xml.replaceFirst(emptyTag, new StringBuilder().append(startTag).append(newValue).append(endTag).toString());
            }
        }
        return xml;
    }
    
}

