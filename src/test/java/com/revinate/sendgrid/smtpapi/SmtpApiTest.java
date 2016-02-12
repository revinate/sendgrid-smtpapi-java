package com.revinate.sendgrid.smtpapi;

import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static org.hamcrest.Matchers.*;
import static org.junit.Assert.assertThat;
import static org.junit.Assert.fail;

public class SmtpApiTest {

    SmtpApi test;

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Before
    public void initialize() {
        test = new SmtpApiImpl();
    }

    @Test
    public void getVersion_shouldMatchGradleVersion() {
        try {
            BufferedReader br = new BufferedReader(new FileReader("./build.gradle"));
            String line = br.readLine();
            String regex = "version\\s*=\\s*'" + test.getVersion() + "'";

            while (line != null) {
                if (line.matches(regex)) {
                    br.close();
                    return;
                }
                line = br.readLine();
            }
            br.close();
            fail("build.gradle version does not match");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void toSmtpApiHeader_shouldReturnJson() throws Exception {
        test.addCategory("カテゴリUñicode");
        test.addCategory("カテゴリ2Unicode");
        test.addCategory("鼖");
        String expected = "{\"category\":[\"\\u30ab\\u30c6\\u30b4\\u30eaU\\u00f1icode\",\"\\u30ab\\u30c6\\u30b4\\u30ea2Unicode\",\"\\ud87e\\ude1b\"]}";
        assertThat(test.toSmtpApiHeader(), equalTo(expected));
    }

    @Test
    public void toRawSmtpApiHeader_shouldReturnRawJson() throws Exception {
        test.addCategory("カテゴリUñicode");
        test.addCategory("カテゴリ2Unicode");
        test.addCategory("鼖");
        String expected = "{\"category\":[\"カテゴリUñicode\",\"カテゴリ2Unicode\",\"鼖\"]}";
        assertThat(test.toRawSmtpApiHeader(), equalTo(expected));
    }

    @Test
    public void getASMGroupId_shouldBeNull() throws Exception {
        assertThat(test.getAsmGroupId(), nullValue());
    }

    @Test
    public void setASMGroupId_shouldSetValue() throws Exception {
        test.setAsmGroupId(1);
        assertThat(test.getAsmGroupId(), equalTo(1));
    }

    @Test
    public void getSendAt_shouldBeNull() throws Exception {
        assertThat(test.getSendAt(), nullValue());
    }

    @Test
    public void setSendAt_shouldSetValue() throws Exception {
        test.setSendAt(12345);
        assertThat(test.getSendAt(), equalTo(12345));
    }

    @Test
    public void getIpPool_shouldBeNull() throws Exception {
        assertThat(test.getIpPool(), nullValue());
    }

    @Test
    public void setIpPool_shouldSetValue() throws Exception {
        test.setIpPool("transactional");
        assertThat(test.getIpPool(), equalTo("transactional"));
    }

    @Test
    public void getSmtpApiTos_shouldBeEmpty() throws Exception {
        assertThat(test.getSmtpApiTos(), emptyCollectionOf(String.class));
    }

    @Test
    public void setSmtpApiTos_shouldSetTos() throws Exception {
        test.setSmtpApiTos(Arrays.asList("john@doe.com", "doe@john.com"));
        assertThat(test.getSmtpApiTos(), contains("john@doe.com", "doe@john.com"));
    }

    @Test
    public void addSmtpApiTo_shouldAddTos() throws Exception {
        test.addSmtpApiTo("john@doe.com");
        assertThat(test.getSmtpApiTos(), contains("john@doe.com"));

        test.addSmtpApiTo("jane@doe.com");
        assertThat(test.getSmtpApiTos(), contains("john@doe.com", "jane@doe.com"));

        test.addSmtpApiTo("test@email.com", "A Tester");
        assertThat(test.getSmtpApiTos(), contains("john@doe.com", "jane@doe.com", "A Tester <test@email.com>"));
    }

    @Test
    public void getCategories_shouldBeEmpty() throws Exception {
        assertThat(test.getCategories(), emptyCollectionOf(String.class));
    }

    @Test
    public void setCategories_shouldSetCategories() throws Exception {
        test.setCategories(Arrays.asList("test", "test2"));
        assertThat(test.getCategories(), contains("test", "test2"));
    }

    @Test
    public void addCategory_shouldAddCategories() throws Exception {
        test.addCategory("test");
        assertThat(test.getCategories(), contains("test"));

        test.addCategory("test2");
        assertThat(test.getCategories(), contains("test", "test2"));
    }

    @Test
    public void addCategory_shouldAddUnicodeCategories() throws Exception {
        test.addCategory("カテゴリUñicode");
        test.addCategory("カテゴリ2Unicode");
        assertThat(test.getCategories(), contains("カテゴリUñicode", "カテゴリ2Unicode"));
    }

    @Test
    public void getUniqueArgs_shouldBeEmpty() throws Exception {
        assertThat(test.getUniqueArgs(), notNullValue());
        assertThat(test.getUniqueArgs().isEmpty(), equalTo(true));
    }

    @Test
    public void setUniqueArgs_shouldSetUniqueArgs() throws Exception {
        Map<String, String> args = new HashMap<String, String>();
        args.put("key", "value");
        test.setUniqueArgs(args);
        assertThat(test.getUniqueArgs().size(), equalTo(1));
        assertThat(test.getUniqueArgs(), hasEntry("key", "value"));
    }

    @Test
    public void getUniqueArg_shouldReturnArg() throws Exception {
        test.setUniqueArg("key", "value");
        assertThat(test.getUniqueArg("key"), equalTo("value"));
    }

    @Test
    public void setUniqueArg_shouldSetNewUniqueArgs() throws Exception {
        test.setUniqueArg("key", "value");
        assertThat(test.getUniqueArgs().size(), equalTo(1));
        assertThat(test.getUniqueArgs(), hasEntry("key", "value"));

        test.setUniqueArg("key2", "value2");
        assertThat(test.getUniqueArgs().size(), equalTo(2));
        assertThat(test.getUniqueArgs(), hasEntry("key", "value"));
        assertThat(test.getUniqueArgs(), hasEntry("key2", "value2"));
    }

    @Test
    public void setUniqueArg_shouldUpdateUniqueArg() throws Exception {
        test.setUniqueArg("key", "value");
        assertThat(test.getUniqueArgs().size(), equalTo(1));
        assertThat(test.getUniqueArgs(), hasEntry("key", "value"));

        test.setUniqueArg("key", "value2");
        assertThat(test.getUniqueArgs().size(), equalTo(1));
        assertThat(test.getUniqueArgs(), hasEntry("key", "value2"));
    }

    @Test
    public void getSections_shouldBeEmpty() throws Exception {
        assertThat(test.getSections(), notNullValue());
        assertThat(test.getSections().isEmpty(), equalTo(true));
    }

    @Test
    public void setSections_shouldSetSections() throws Exception {
        Map<String, String> sections = new HashMap<String, String>();
        sections.put("key", "value");
        test.setSections(sections);
        assertThat(test.getSections().size(), equalTo(1));
        assertThat(test.getSections(), hasEntry("key", "value"));
    }

    @Test
    public void getSection_shouldReturnSection() throws Exception {
        test.setSection("key", "value");
        assertThat(test.getSection("key"), equalTo("value"));
    }

    @Test
    public void setSection_shouldSetNewSections() throws Exception {
        test.setSection("key", "value");
        assertThat(test.getSections().size(), equalTo(1));
        assertThat(test.getSections(), hasEntry("key", "value"));

        test.setSection("key2", "value2");
        assertThat(test.getSections().size(), equalTo(2));
        assertThat(test.getSections(), hasEntry("key", "value"));
        assertThat(test.getSections(), hasEntry("key2", "value2"));
    }

    @Test
    public void setSection_shouldUpdateSection() throws Exception {
        test.setSection("key", "value");
        assertThat(test.getSections().size(), equalTo(1));
        assertThat(test.getSections(), hasEntry("key", "value"));

        test.setSection("key", "value2");
        assertThat(test.getSections().size(), equalTo(1));
        assertThat(test.getSections(), hasEntry("key", "value2"));
    }

    @Test
    public void getSubstitutions_shouldBeEmpty() throws Exception {
        assertThat(test.getSubstitutions(), notNullValue());
        assertThat(test.getSubstitutions().isEmpty(), equalTo(true));
    }

    @Test
    public void setSubstitutions_shouldSetSubstitutions() throws Exception {
        Map<String, List<String>> subs = new HashMap<String, List<String>>();
        subs.put("-name-", Arrays.asList("John", "Doe"));
        test.setSubstitutions(subs);
        assertThat(test.getSubstitutions(), hasEntry(equalTo("-name-"), contains("John", "Doe")));
    }

    @Test
    public void getSubstitution_shouldReturnSubstitution() throws Exception {
        test.setSubstitution("key", Arrays.asList("value1", "value2"));
        assertThat(test.getSubstitution("key"), contains("value1", "value2"));
    }

    @Test
    public void setSubstitution_shouldSetNewSubstitution() throws Exception {
        test.setSubstitution("-name-", Arrays.asList("John", "Doe"));
        assertThat(test.getSubstitutions().size(), equalTo(1));
        assertThat(test.getSubstitutions(), hasEntry(equalTo("-name-"), contains("John", "Doe")));
    }

    @Test
    public void setSubstitution_shouldUpdateSubstitution() throws Exception {
        test.setSubstitution("-name-", Arrays.asList("John", "Doe"));
        assertThat(test.getSubstitutions().size(), equalTo(1));
        assertThat(test.getSubstitutions(), hasEntry(equalTo("-name-"), contains("John", "Doe")));

        test.setSubstitution("-name-", Arrays.asList("Jane", "Doe"));
        assertThat(test.getSubstitutions().size(), equalTo(1));
        assertThat(test.getSubstitutions(), hasEntry(equalTo("-name-"), contains("Jane", "Doe")));
    }

    @Test
    public void addValueToSubstitution_shouldAddNewSubstitutionValues() throws Exception {
        test.addValueToSubstitution("-name-", "John");
        assertThat(test.getSubstitutions().size(), equalTo(1));
        assertThat(test.getSubstitutions(), hasEntry(equalTo("-name-"), contains("John")));

        test.addValueToSubstitution("-name-", "Doe");
        assertThat(test.getSubstitutions().size(), equalTo(1));
        assertThat(test.getSubstitutions(), hasEntry(equalTo("-name-"), contains("John", "Doe")));
    }

    @Test
    public void getFilters_shouldBeEmpty() throws Exception {
        assertThat(test.getFilters(), notNullValue());
        assertThat(test.getFilters().isEmpty(), equalTo(true));
    }

    @Test
    public void setFilters_shouldSetFilters() throws Exception {
        Map<String, Map<String, Object>> filters = new HashMap<String, Map<String, Object>>();
        Map<String, Object> filter1 = new HashMap<String, Object>();
        filter1.put("setting1", "value1");
        filter1.put("setting2", 2);
        filters.put("filter1", filter1);
        Map<String, Object> filter2 = new HashMap<String, Object>();
        filter2.put("setting3", "value3");
        filters.put("filter2", filter2);
        test.setFilters(filters);

        assertThat(test.getFilters(), hasEntry(equalTo("filter1"), hasEntry("setting1", (Object) "value1")));
        assertThat(test.getFilters(), hasEntry(equalTo("filter1"), hasEntry("setting2", (Object) 2)));
        assertThat(test.getFilters(), hasEntry(equalTo("filter2"), hasEntry("setting3", (Object) "value3")));
    }

    @Test
    public void setFilters_shouldThrowException() throws Exception {
        Map<String, Map<String, Object>> filters = new HashMap<String, Map<String, Object>>();
        Map<String, Object> filter1 = new HashMap<String, Object>();
        filter1.put("setting1", 1.2);
        filters.put("filter1", filter1);

        thrown.expect(SmtpApiException.class);
        thrown.expectMessage("Filter setting value must be an integer or a String");

        test.setFilters(filters);
    }

    @Test
    public void getFilter_shouldReturnFilter() throws Exception {
        Map<String, Object> filter1 = new HashMap<String, Object>();
        filter1.put("setting1", "value1");
        test.setFilter("filter1", filter1);
        assertThat(test.getFilter("filter1"), notNullValue());
        assertThat(test.getFilter("filter1").size(), equalTo(1));
        assertThat(test.getFilter("filter1"), hasEntry("setting1", (Object) "value1"));
    }

    @Test
    public void setFilter_shouldSetNewFilter() throws Exception {
        Map<String, Object> filter1 = new HashMap<String, Object>();
        filter1.put("setting1", "value1");
        filter1.put("setting2", 2);
        test.setFilter("filter1", filter1);
        assertThat(test.getFilters(), hasEntry(equalTo("filter1"), hasEntry("setting1", (Object) "value1")));
        assertThat(test.getFilters(), hasEntry(equalTo("filter1"), hasEntry("setting2", (Object) 2)));
    }

    @Test
    public void setFilter_shouldUpdateFilter() throws Exception {
        Map<String, Object> filter1 = new HashMap<String, Object>();
        filter1.put("setting1", "value1");
        test.setFilter("filter1", filter1);
        assertThat(test.getFilters().size(), equalTo(1));
        assertThat(test.getFilters(), hasEntry(equalTo("filter1"), hasEntry("setting1", (Object) "value1")));

        Map<String, Object> filter2 = new HashMap<String, Object>();
        filter2.put("setting2", "value2");
        test.setFilter("filter1", filter2);
        assertThat(test.getFilters().size(), equalTo(1));
        assertThat(test.getFilters(), hasEntry(equalTo("filter1"), hasEntry("setting2", (Object) "value2")));
    }

    @Test
    public void setFilter_shouldCreateEmptyFilter() throws Exception {
        test.setFilter("filter1", null);
        assertThat(test.getFilters(), hasKey("filter1"));
        assertThat(test.getFilters().get("filter1"), notNullValue());
        assertThat(test.getFilters().get("filter1").isEmpty(), equalTo(true));
    }

    @Test
    public void setFilter_shouldThrowException() throws Exception {
        Map<String, Object> filter1 = new HashMap<String, Object>();
        filter1.put("setting1", 1.2);

        thrown.expect(SmtpApiException.class);
        thrown.expectMessage("Filter setting value must be an integer or a String");

        test.setFilter("filter1", filter1);
    }

    @Test
    public void setSettingInFilter_shouldCreateNewFilter() throws Exception {
        test.setSettingInFilter("filter1", "setting1", "value1");
        test.setSettingInFilter("filter2", "setting2", 2);
        assertThat(test.getFilters(), hasEntry(equalTo("filter1"), hasEntry("setting1", (Object) "value1")));
        assertThat(test.getFilters(), hasEntry(equalTo("filter2"), hasEntry("setting2", (Object) 2)));
    }

    @Test
    public void setSettingInFilter_shouldThrowException() throws Exception {
        thrown.expect(SmtpApiException.class);
        thrown.expectMessage("Filter setting value must be an integer or a String");

        test.setSettingInFilter("filter1", "settings1", 1.2);
    }
}
