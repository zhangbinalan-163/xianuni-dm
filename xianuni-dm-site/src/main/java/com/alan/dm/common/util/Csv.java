package com.alan.dm.common.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Csv {
    private List<String> data = new ArrayList<String>();

    public void load(InputStream in) throws IOException {
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line = null;
        while ((line = reader.readLine()) != null) {
            addLine(line);
        }
    }

    public void addLine(String line) {
        data.add(line);
    }

    public void addLine(List<String> list) {
        StringBuilder sb = new StringBuilder();
        for (String item: list) {
            if (item == null) {
                sb.append("");
            } else if (item.indexOf(",") == -1) {
                sb.append(item);
            } else {
                item = item.replaceAll("\"", "\"\"");
                sb.append("\"").append(item).append("\"");
            }
            sb.append(",");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        data.add(sb.toString());
    }

    public void addLine(String... arr) {
        StringBuilder sb = new StringBuilder();
        for (String item: arr) {
            if (item == null) {
                sb.append("");
            } else if (item.indexOf(",") == -1) {
                sb.append(item);
            } else {
                item = item.replaceAll("\"", "\"\"");
                sb.append("\"").append(item).append("\"");
            }
            sb.append(",");
        }
        if (sb.length() > 0) {
            sb.deleteCharAt(sb.length() - 1);
        }
        data.add(sb.toString());
    }

    public int getLineNum() {
        return data.size();
    }

    public String getLine(int num) {
        if (num < 1 || num > data.size()) {
            return null;
        }
        return data.get(num - 1);
    }

    public String[] getLineArray(int num) {
        String line = data.get(num - 1);
        String reg = "(\".*?\",)|([^,]*?,)";
        Pattern p = Pattern.compile(reg);
        Matcher m = p.matcher(line + ",");
        List<String> list = new ArrayList<String>();
        while (m.find()) {
            String item = m.group().replaceAll("\"\"", "\"");
            item = item.substring(0, item.length() - 1);
            if (item.startsWith("\"") && item.endsWith("\"")) {
                item = item.substring(1, item.length() - 1);
            }
            list.add(item);
        }
        return (String[]) list.toArray();
    }

    public String getContent() {
        StringBuilder sb = new StringBuilder();
        for (String line: data) {
            sb.append(line).append("\r\n");
        }
        return sb.toString();
    }

    public void clear() {
        data.clear();
    }

    /**
     * @param line
     * @return
     */
    public static List<String> parseLine(String line) {
        List<String> list = new ArrayList<String>(6);
        StringBuilder tmp = new StringBuilder();
        char[] cs = line.trim().toCharArray();
        boolean isNewField = true;
        boolean isQuota = false;
        for (int i = 0; i < cs.length; i++) {
            char c = cs[i];
            switch (c) {
                case ',':
                case '，':
                    isNewField = false;
                    if (isQuota) {
                        tmp.append(c);
                    } else {
                        list.add(tmp.toString());
                        tmp = new StringBuilder();
                        isNewField = true;
                        isQuota = false;
                    }
                    break;
                case '"':
                    if (isNewField) {
                        isQuota = true;
                        isNewField = false;
                    } else if (isQuota && i < cs.length - 1) {
                        if (cs[i + 1] == '"') {
                            tmp.append(c);
                            i++;
                        } else if (cs[i + 1] == ',' || cs[i + 1] == '，') {
                            list.add(tmp.toString());
                            tmp = new StringBuilder();
                            isNewField = true;
                            isQuota = false;
                            i++;
                        }
                    }else if(isQuota){
                        break;
                    }else {
                        tmp.append(c);
                    }
                    break;
                default:
                    isNewField = false;
                    tmp.append(c);
                    break;
            }
        }
        if (tmp.length() > 0) {
            list.add(tmp.toString());
        }
        return list;
    }
}
