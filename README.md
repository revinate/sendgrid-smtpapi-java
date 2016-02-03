# SendGrid SMTPAPI for Java

This module will let you build SendGrid's SMTP API headers with simplicity.

[![BuildStatus](https://travis-ci.org/revinate/sendgrid-smtpapi-java.svg?branch=master)](https://travis-ci.org/revinate/sendgrid-smtpapi-java)

## Installing

### Maven w/ Gradle

```groovy
...
dependencies {
  ...
  compile 'com.sendgrid:smtpapi-java:1.2.0'
}

repositories {
  mavenCentral()
}
...
```

Then import the library - in the file appropriate to your Java project.

```java
import com.sendgrid.smtpapi.SMTPAPI;
```

## Examples

### Create headers

```java
import com.sendgrid.smtpapi.SMTPAPI;
SMTPAPI header = new SMTPAPI();
```

### [To](http://sendgrid.com/docs/API_Reference/SMTP_API/index.html)
```java
header.addTo("email@email.com");
// or
header.addTo(["email@email.com"]);
// or
header.setTos(["email@email.com"]);

String[] tos = header.getTos();
```

### [Substitutions](http://sendgrid.com/docs/API_Reference/SMTP_API/substitution_tags.html)

```java
header.addSubstitution("key", "value");

JSONObject subs = header.getSubstitutions();
```

### [Unique Arguments](http://sendgrid.com/docs/API_Reference/SMTP_API/unique_arguments.html)

```java
header.addUniqueAarg("key", "value");
// or
Map map = new HashMap<String, String>();
map.put("unique", "value");
header.setUniqueArgs(map);
// or
JSONObject map = new JSONObject();
map.put("unique", "value");
header.setUniqueArgs(map);

JSONObject args = header.getUniqueArgs();
```
### [Categories](http://sendgrid.com/docs/API_Reference/SMTP_API/categories.html)

```java
header.addCategory("category");
// or
header.addCategory(["categories"]);
// or
header.setCategories(["category1", "category2"]);

String[] cats = header.getCategories();
```

### [Sections](http://sendgrid.com/docs/API_Reference/SMTP_API/section_tags.html)

```java
header.addSection("key", "section");
// or
Map newSec = new HashMap();
newSec.put("-section-", "value");
header.setSections(newSec);
// or
JSONObject newSec = new JSONObject();
newSec.put("-section-", "value");
header.setSections(newSec);

JSONObject sections = header.getSections();
```

### [Filters](http://sendgrid.com/docs/API_Reference/SMTP_API/apps.html)

```java
header.addFilter("filter", "setting", "value");
header.addFilter("filter", "setting", 1);

JSONObject filters = header.getFilters();
```

### [ASM Group Id](https://sendgrid.com/docs/User_Guide/advanced_suppression_manager.html)

```java
header.setASMGroupId(1);

Integer groupId = header.getASMGroupId();
```

### [Scheduling](https://sendgrid.com/docs/API_Reference/SMTP_API/scheduling_parameters.html)

```java
header.setSendAt(1416427645)

int sendAt = header.getSendAt();
```

### Get Headers

```java
String headers = header.jsonString();
```

If you need the unescaped JSON string.
```java
String rawJson = header.rawJsonString();
```

## Running Tests

```
./gradlew check
```

## MIT
