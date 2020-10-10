package com.tao.logger.formater;


import org.json.JSONArray;
import org.json.JSONObject;

/**
 * @project android_lib_logger
 * @class name：com.nj.baijiayun.logger.formater
 * @describe
 * @anthor houyi QQ:1007362137
 * @time 2019/4/29 6:06 PM
 * @change
 * @time
 * @describe
 */
public class JsonFormatter implements IFormatter<String> {
    private static final int JSON_INDENT = 4;

    @Override
    public String format(String json) throws FormatException {
        String formattedString = null;
        if (json == null || json.trim().length() == 0) {
            return "";
        }
        try {
            if (startsWith(json, "{")) {
                JSONObject jsonObject = new JSONObject(json);
                formattedString = jsonObject.toString(JSON_INDENT);
            } else if (startsWith(json, "[")) {
                JSONArray jsonArray = new JSONArray(json);
                formattedString = jsonArray.toString(JSON_INDENT);
            } else {
                throw new FormatException("Parse JSON error. JSON string:" + json);
            }
        } catch (Exception e) {
            throw new FormatException("Parse JSON error. JSON string:" + json, e);
        }
        return formattedString;
    }

    private boolean startsWith(String json, String s) {
        if (json.startsWith(s)) {
            return true;
        }
        String lineSeparator = System.lineSeparator();
        int lineSize = 0;
        while (json.startsWith(lineSeparator, lineSize * lineSeparator.length())) {
            lineSize++;
        }
        return json.startsWith(s, lineSize * lineSeparator.length());
    }
}
