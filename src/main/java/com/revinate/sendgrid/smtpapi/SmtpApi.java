package com.revinate.sendgrid.smtpapi;

import java.util.List;
import java.util.Map;

public interface SmtpApi {

    String getVersion();
    String toSmtpApiHeader();
    String toRawSmtpApiHeader();
    Integer getAsmGroupId();
    SmtpApi setAsmGroupId(Integer val);
    Integer getSendAt();
    SmtpApi setSendAt(Integer val);
    String getIpPool();
    SmtpApi setIpPool(String ipPool);
    List<String> getSmtpApiTos();
    SmtpApi setSmtpApiTos(List<String> tos);
    SmtpApi addSmtpApiTo(String to);
    SmtpApi addSmtpApiTo(String to, String name);
    List<String> getCategories();
    SmtpApi setCategories(List<String> categories);
    SmtpApi addCategory(String category);
    Map<String, String> getUniqueArgs();
    SmtpApi setUniqueArgs(Map<String, String> args);
    String getUniqueArg(String key);
    SmtpApi setUniqueArg(String key, String val);
    Map<String, String> getSections();
    SmtpApi setSections(Map<String, String> sections);
    String getSection(String key);
    SmtpApi setSection(String key, String val);
    Map<String, List<String>> getSubstitutions();
    SmtpApi setSubstitutions(Map<String, List<String>> substitutions);
    List<String> getSubstitution(String key);
    SmtpApi setSubstitution(String key, List<String> vals);
    SmtpApi addValueToSubstitution(String key, String val);
    Map<String, Map<String, Object>> getFilters();
    SmtpApi setFilters(Map<String, Map<String, Object>> filters) throws SmtpApiException;
    Map<String, Object> getFilter(String filterName);
    SmtpApi setFilter(String filterName, Map<String, Object> filter) throws SmtpApiException;
    SmtpApi setSettingInFilter(String filterName, String settingName, Object settingVal) throws SmtpApiException;
}
