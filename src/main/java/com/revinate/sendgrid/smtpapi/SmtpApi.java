package com.revinate.sendgrid.smtpapi;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.*;

public class SmtpApi {

    private static final String VERSION = "1.2.1";

    private static final String FIELD_ASM_GROUP_ID = "asm_group_id";
    private static final String FIELD_SEND_AT = "send_at";
    private static final String FIELD_IP_POOL = "ip_pool";
    private static final String FIELD_TO = "to";
    private static final String FIELD_CATEGORY = "category";
    private static final String FIELD_UNIQUE_ARGS = "unique_args";
    private static final String FIELD_SECTION = "section";
    private static final String FIELD_SUB = "sub";
    private static final String FIELD_FILTERS = "filters";
    private static final String FIELD_SETTINGS = "settings";
    private static final String EMAIL_FORMAT = "%s <%s>";

    private JSONObject header = new JSONObject();

    public String getVersion() {
        return VERSION;
    }

    public String toSmtpApiHeader() {
        return escapeUnicode(header.toString());
    }

    public String toRawSmtpApiHeader() {
        return header.toString();
    }

    public Integer getAsmGroupId() {
        return getInt(FIELD_ASM_GROUP_ID);
    }

    public SmtpApi setAsmGroupId(Integer val) {
        header.put(FIELD_ASM_GROUP_ID, val);
        return this;
    }

    public Integer getSendAt() {
        return getInt(FIELD_SEND_AT);
    }

    public SmtpApi setSendAt(Integer val) {
        header.put(FIELD_SEND_AT, val);
        return this;
    }

    public String getIpPool() {
        return header.optString(FIELD_IP_POOL, null);
    }

    public SmtpApi setIpPool(String ipPool) {
        header.put(FIELD_IP_POOL, ipPool);
        return this;
    }

    public List<String> getSmtpApiTos() {
        return toListOfStrings(header.optJSONArray(FIELD_TO));
    }

    public SmtpApi setSmtpApiTos(List<String> tos) {
        header.put(FIELD_TO, tos);
        return this;
    }

    public SmtpApi addSmtpApiTo(String to) {
        header.append(FIELD_TO, to);
        return this;
    }

    public SmtpApi addSmtpApiTo(String to, String name) {
        return addSmtpApiTo(String.format(EMAIL_FORMAT, name, to));
    }

    public List<String> getCategories() {
        return toListOfStrings(header.optJSONArray(FIELD_CATEGORY));
    }

    public SmtpApi setCategories(List<String> categories) {
        header.put(FIELD_CATEGORY, categories);
        return this;
    }

    public SmtpApi addCategory(String category) {
        header.append(FIELD_CATEGORY, category);
        return this;
    }

    public Map<String, String> getUniqueArgs() {
        return toMapOfStrings(header.optJSONObject(FIELD_UNIQUE_ARGS));
    }

    public SmtpApi setUniqueArgs(Map<String, String> args) {
        header.put(FIELD_UNIQUE_ARGS, args);
        return this;
    }

    public String getUniqueArg(String key) {
        return getStringFromMap(FIELD_UNIQUE_ARGS, key);
    }

    public SmtpApi setUniqueArg(String key, String val) {
        setInMap(FIELD_UNIQUE_ARGS, key, val);
        return this;
    }

    public Map<String, String> getSections() {
        return toMapOfStrings(header.optJSONObject(FIELD_SECTION));
    }

    public SmtpApi setSections(Map<String, String> sections) {
        header.put(FIELD_SECTION, sections);
        return this;
    }

    public String getSection(String key) {
        return getStringFromMap(FIELD_SECTION, key);
    }

    public SmtpApi setSection(String key, String val) {
        setInMap(FIELD_SECTION, key, val);
        return this;
    }

    public Map<String, List<String>> getSubstitutions() {
        return toMapOfListsOfStrings(header.optJSONObject(FIELD_SUB));
    }

    public SmtpApi setSubstitutions(Map<String, List<String>> substitutions) {
        header.put(FIELD_SUB, substitutions);
        return this;
    }

    public List<String> getSubstitution(String key) {
        return getListOfStringsFromMap(FIELD_SUB, key);
    }

    public SmtpApi setSubstitution(String key, List<String> vals) {
        setInMap(FIELD_SUB, key, vals);
        return this;
    }

    public SmtpApi addValueToSubstitution(String key, String val) {
        appendToListInMap(FIELD_SUB, key, val);
        return this;
    }

    public Map<String, Map<String, Object>> getFilters() {
        Map<String, Map<String, Object>> filters = new HashMap<String, Map<String, Object>>();
        JSONObject json = header.optJSONObject(FIELD_FILTERS);
        if (json != null) {
            for (Object filterName : json.keySet()) {
                if (filterName instanceof String) {
                    String filterNameString = (String) filterName;
                    Map<String, Object> filter = toFilter(json.optJSONObject(filterNameString));
                    filters.put(filterNameString, filter);
                }
            }
        }
        return filters;
    }

    public SmtpApi setFilters(Map<String, Map<String, Object>> filters) throws SmtpApiException {
        Map<String, Map<String, Map<String, Object>>> filtersMap =
                new HashMap<String, Map<String, Map<String, Object>>>();
        for (Map.Entry<String, Map<String, Object>> filter : filters.entrySet()) {
            filtersMap.put(filter.getKey(), toFilterMap(filter.getValue()));
        }
        header.put(FIELD_FILTERS, filtersMap);
        return this;
    }

    public Map<String, Object> getFilter(String filterName) {
        JSONObject json = header.optJSONObject(FIELD_FILTERS);
        if (json != null) {
            return toFilter(json.optJSONObject(filterName));
        } else {
            return Collections.emptyMap();
        }
    }

    public SmtpApi setFilter(String filterName, Map<String, Object> filter) throws SmtpApiException {
        setInMap(FIELD_FILTERS, filterName, toFilterMap(filter));
        return this;
    }

    public SmtpApi setSettingInFilter(String filterName, String settingName, Object settingVal) throws SmtpApiException {
        if (!(settingVal instanceof Integer) && !(settingVal instanceof String)) {
            throw new SmtpApiException("Filter setting value must be an integer or a String");
        }
        JSONObject filters = header.optJSONObject(FIELD_FILTERS);
        if (filters == null) {
            filters = new JSONObject();
        }
        JSONObject filter = filters.optJSONObject(filterName);
        if (filter == null) {
            filter = new JSONObject();
        }
        JSONObject settings = filter.optJSONObject(FIELD_SETTINGS);
        if (settings == null) {
            settings = new JSONObject();
        }
        settings.put(settingName, settingVal);
        filter.put(FIELD_SETTINGS, settings);
        filters.put(filterName, filter);
        header.put(FIELD_FILTERS, filters);
        return this;
    }

    private Integer getInt(String field) {
        try {
            return header.getInt(field);
        } catch (JSONException e) {
            return null;
        }
    }

    private String getStringFromMap(String field, String key) {
        JSONObject json = header.optJSONObject(field);
        return json == null? null : json.optString(key, null);
    }

    private List<String> getListOfStringsFromMap(String field, String key) {
        JSONObject json = header.optJSONObject(field);
        if (json != null) {
            return toListOfStrings(json.optJSONArray(key));
        }
        return Collections.emptyList();
    }

    private void setInMap(String field, String key, String val) {
        JSONObject json = header.optJSONObject(field);
        if (json == null) {
            json = new JSONObject();
        }
        json.put(key, val);
        header.put(field, json);
    }

    private void setInMap(String field, String key, List<String> vals) {
        JSONObject json = header.optJSONObject(field);
        if (json == null) {
            json = new JSONObject();
        }
        json.put(key, vals);
        header.put(field, json);
    }

    private void setInMap(String field, String key, Map<String, Map<String, Object>> val) {
        JSONObject json = header.optJSONObject(field);
        if (json == null) {
            json = new JSONObject();
        }
        json.put(key, val);
        header.put(field, json);
    }

    private void appendToListInMap(String field, String key, String val) {
        JSONObject json = header.optJSONObject(field);
        if (json == null) {
            json = new JSONObject();
        }
        json.append(key, val);
        header.put(field, json);
    }

    private List<String> toListOfStrings(JSONArray json) {
        List<String> parse = new ArrayList<String>();
        if (json != null) {
            for (int i = 0; i < json.length(); i++) {
                parse.add(json.optString(i, null));
            }
        }
        return parse;
    }

    private Map<String, Object> toMap(JSONObject json) {
        Map<String, Object> parse = new HashMap<String, Object>();
        if (json != null) {
            for (Object key : json.keySet()) {
                if (key instanceof String) {
                    String keyString = (String) key;
                    parse.put(keyString, json.opt(keyString));
                }
            }
        }
        return parse;
    }

    private Map<String, String> toMapOfStrings(JSONObject json) {
        Map<String, String> parse = new HashMap<String, String>();
        if (json != null) {
            for (Object key : json.keySet()) {
                if (key instanceof String) {
                    String keyString = (String) key;
                    parse.put(keyString, json.optString(keyString, null));
                }
            }
        }
        return parse;
    }

    private Map<String, List<String>> toMapOfListsOfStrings(JSONObject json) {
        Map<String, List<String>> parse = new HashMap<String, List<String>>();
        if (json != null) {
            for (Object key : json.keySet()) {
                if (key instanceof String) {
                    String keyString = (String) key;
                    JSONArray array = json.optJSONArray(keyString);
                    parse.put(keyString, toListOfStrings(array));
                }
            }
        }
        return parse;
    }

    private Map<String, Map<String, Object>> toFilterMap(Map<String, Object> filter) throws SmtpApiException {
        Map<String, Map<String, Object>> filterMap = new HashMap<String, Map<String, Object>>();
        if (filter != null) {
            Map<String, Object> settingsMap = new HashMap<String, Object>();
            for (Map.Entry<String, Object> setting : filter.entrySet()) {
                Object settingVal = setting.getValue();
                if (!(settingVal instanceof Integer) && !(settingVal instanceof String)) {
                    throw new SmtpApiException("Filter setting value must be an integer or a String");
                }
                settingsMap.put(setting.getKey(), settingVal);
            }
            filterMap.put(FIELD_SETTINGS, settingsMap);
        }
        return filterMap;
    }

    private Map<String, Object> toFilter(JSONObject json) {
        if (json != null) {
            return toMap(json.optJSONObject(FIELD_SETTINGS));
        } else {
            return Collections.emptyMap();
        }
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

    private int[] toCodePointArray(String input) {
        int len = input.length();
        int[] codePointArray = new int[input.codePointCount(0, len)];
        for (int i = 0, j = 0; i < len; i = input.offsetByCodePoints(i, 1)) {
            codePointArray[j++] = input.codePointAt(i);
        }
        return codePointArray;
    }
}
