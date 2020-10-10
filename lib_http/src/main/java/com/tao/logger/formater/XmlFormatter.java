package com.tao.logger.formater;

import com.tao.logger.utils.Utils;

import java.io.StringReader;
import java.io.StringWriter;

import javax.xml.transform.OutputKeys;
import javax.xml.transform.Source;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.stream.StreamResult;
import javax.xml.transform.stream.StreamSource;

/**
 * @project android_lib_logger
 * @class name：com.nj.baijiayun.logger.formater
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2019/4/30 10:07 AM
 * @change
 * @time
 * @describe
 */
public class XmlFormatter implements IFormatter<String> {
    private static final int XML_INDENT = 4;

    @Override
    public String format(String xml) throws FormatException{
        String formattedString;
        if (xml == null || xml.trim().length() == 0) {
            throw new FormatException("XML empty.");
        }
        try {
            Source xmlInput = new StreamSource(new StringReader(xml));
            StreamResult xmlOutput = new StreamResult(new StringWriter());
            Transformer transformer = TransformerFactory.newInstance().newTransformer();
            transformer.setOutputProperty(OutputKeys.INDENT, "yes");
            transformer.setOutputProperty("{http://xml.apache.org/xslt}indent-amount",
                    String.valueOf(XML_INDENT));
            transformer.transform(xmlInput, xmlOutput);
            formattedString = xmlOutput.getWriter().toString().replaceFirst(">", ">"
                    + Utils.lineSeparator);
        } catch (Exception e) {
            throw new FormatException("Parse XML error. XML string:" + xml, e);
        }
        return formattedString;
    }
}
