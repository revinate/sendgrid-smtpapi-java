package com.revinate.sendgrid.smtpapi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SMTPAPI {

    private static final String VERSION = "1.2.1";

    private JSONObject header = new JSONObject();

    public SMTPAPI() {
    }

    public SMTPAPI(JSONObject header) {
        this.header = header;
    }

    public String getVersion() {
        return VERSION;
    }

    private static String[] toArray(JSONArray json) {
        List<String> parse = new ArrayList<String>();
        for (int i = 0; i < json.length(); i++) {
            parse.add(json.getString(i));
        }
        return parse.toArray(new String[parse.size()]);
    }

    public SMTPAPI addTo(String to) throws JSONException {
        if (!this.header.has("to")) {
            this.header.put("to", new JSONArray());
        }
        this.header.accumulate("to", to);
        return this;
    }

    public SMTPAPI addTos(String[] tos) throws JSONException {
        for (String to : tos) {
            addTo(to);
        }
        return this;
    }

    public SMTPAPI setTos(String[] tos) throws JSONException {
        this.header.put("to", new JSONArray(tos));
        return this;
    }

    public String[] getTos() throws JSONException {
        return SMTPAPI.toArray(this.header.getJSONArray("to"));
    }

    public SMTPAPI addSubstitution(String key, String val) throws JSONException {
        if (this.header.isNull("sub")) {
            this.header.put("sub", new JSONObject());
        }
        JSONObject subs = this.header.getJSONObject("sub");
        if (!subs.has(key)) {
            subs.put(key, new JSONArray());
        }
        subs.accumulate(key, val);
        return this;
    }

    public SMTPAPI addSubstitutions(String key, String[] vals) throws JSONException {
        for (String val : vals) {
            addSubstitution(key, val);
        }
        return this;
    }

    public SMTPAPI setSubstitutions(JSONObject subs) throws JSONException {
        this.header.put("sub", subs);
        return this;
    }

    public JSONObject getSubstitutions() throws JSONException {
        return this.header.getJSONObject("sub");
    }

    public SMTPAPI addUniqueArg(String key, String val) throws JSONException {
        if (this.header.isNull("unique_args")) {
            this.header.put("unique_args", new JSONObject());
        }
        this.header.getJSONObject("unique_args").put(key, val);
        return this;
    }

    public SMTPAPI setUniqueArgs(Map<String, String> args) throws JSONException {
        this.header.put("unique_args", args);
        return this;
    }

    public SMTPAPI setUniqueArgs(JSONObject args) throws JSONException {
        this.header.put("unique_args", args);
        return this;
    }

    public JSONObject getUniqueArgs() throws JSONException {
        return this.header.getJSONObject("unique_args");
    }

    public SMTPAPI addCategory(String val) throws JSONException {
        if (!this.header.has("category")) {
            this.header.put("category", new JSONArray());
        }
        this.header.accumulate("category", val);
        return this;
    }

    public SMTPAPI addCategories(String[] vals) throws JSONException {
        for (String val : vals) {
            addCategory(val);
        }
        return this;
    }

    public SMTPAPI setCategories(String[] categories) throws JSONException {
        this.header.put("category", categories);
        return this;
    }

    public String[] getCategories() throws JSONException {
        return SMTPAPI.toArray(this.header.getJSONArray("category"));
    }

    public SMTPAPI addSection(String key, String val) throws JSONException {
        if (this.header.isNull("section")) {
            this.header.put("section", new JSONObject());
        }
        this.header.getJSONObject("section").put(key, val);
        return this;
    }

    public SMTPAPI setSections(Map<String, String> sections) throws JSONException {
        return this.setSections(new JSONObject(sections));
    }

    public SMTPAPI setSections(JSONObject sections) throws JSONException {
        this.header.put("section", sections);
        return this;
    }

    public JSONObject getSections() throws JSONException {
        return this.header.getJSONObject("section");
    }

    public SMTPAPI addFilter(String filter, String setting, String val) throws JSONException {
        if (this.header.isNull("filters")) {
            this.header.put("filters", new JSONObject());
        }
        if (this.header.getJSONObject("filters").isNull(filter)) {
            this.header.getJSONObject("filters").put(filter, new JSONObject());
            this.header.getJSONObject("filters").getJSONObject(filter).put("settings", new JSONObject());
        }
        this.header.getJSONObject("filters").getJSONObject(filter).getJSONObject("settings").put(setting, val);
        return this;
    }

    public SMTPAPI addFilter(String filter, String setting, int val) throws JSONException {
        if (this.header.isNull("filters")) {
            this.header.put("filters", new JSONObject());
        }
        if (this.header.getJSONObject("filters").isNull(filter)) {
            this.header.getJSONObject("filters").put(filter, new JSONObject());
            this.header.getJSONObject("filters").getJSONObject(filter).put("settings", new JSONObject());
        }
        this.header.getJSONObject("filters").getJSONObject(filter).getJSONObject("settings").put(setting, val);
        return this;
    }

    public SMTPAPI setFilters(JSONObject filters) throws JSONException {
        this.header.put("filters", filters);
        return this;
    }

    public JSONObject getFilters() throws JSONException {
        return this.header.getJSONObject("filters");
    }

    public SMTPAPI setASMGroupId(int val) throws JSONException {
        this.header.put("asm_group_id", val);
        return this;
    }

    public Integer getASMGroupId() throws JSONException {
        return this.header.has("asm_group_id") ? this.header.optInt("asm_group_id") : null;
    }

    public SMTPAPI setSendAt(int sendAt) throws JSONException {
        this.header.put("send_at", sendAt);
        return this;
    }

    public int getSendAt() throws JSONException {
        return this.header.getInt("send_at");
    }

    public SMTPAPI setIpPool(String ipPool) throws JSONException {
        this.header.put("ip_pool", ipPool);
        return this;
    }

    public String getIpPool() throws JSONException {
        return this.header.getString("ip_pool");
    }

    // convert from string to code point array
    private int[] toCodePointArray(String input) {
        int len = input.length();
        int[] codePointArray = new int[input.codePointCount(0, len)];
        for (int i = 0, j = 0; i < len; i = input.offsetByCodePoints(i, 1)) {
            codePointArray[j++] = input.codePointAt(i);
        }
        return codePointArray;
    }

    private String escapeUnicode(String input) {
        StringBuilder sb = new StringBuilder();
        int[] codePoints = toCodePointArray(input);
        for (int codePoint : codePoints) {
            if (codePoint > 65535) {
                // surrogate pair
                int hi = (codePoint - 0x10000) / 0x400 + 0xD800;
                int lo = (codePoint - 0x10000) % 0x400 + 0xDC00;
                sb.append(String.format("\\u%04x\\u%04x", hi, lo));
            } else if (codePoint > 127) {
                sb.append(String.format("\\u%04x", codePoint));
            } else {
                sb.append(String.format("%c", codePoint));
            }
        }
        return sb.toString();
    }

    public String jsonString() {
        return escapeUnicode(this.header.toString());
    }

    public String rawJsonString() {
        return this.header.toString();
    }
}
