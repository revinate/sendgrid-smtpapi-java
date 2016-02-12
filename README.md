# SendGrid SMTP API Java Bindings

This module lets you build SendGrid's SMTP API headers with simplicity.

[![BuildStatus](https://travis-ci.org/revinate/sendgrid-smtpapi-java.svg?branch=master)](https://travis-ci.org/revinate/sendgrid-smtpapi-java)
[![BuildStatus](https://maven-badges.herokuapp.com/maven-central/com.revinate/sendgrid-smtpapi-java/badge.svg)](https://maven-badges.herokuapp.com/maven-central/com.revinate/sendgrid-smtpapi-java)

## Requirements

Java 1.6 and later.

## Installation

### Maven

Add this dependency to your project's POM:

```xml
<dependency>
  <groupId>com.revinate</groupId>
  <artifactId>sendgrid-smtpapi-java</artifactId>
  <version>2.0.0</version>
</dependency>
```

### Gradle

Add this dependency to your project's build script:

```groovy
compile 'com.revinate:sendgrid-smtpapi-java:2.0.0'
```

## Usage

### Create header

```java
import com.revinate.sendgrid.smtpapi.*;
SmtpApi header = new SmtpApiImpl();
```

### Get header value

```java
String headerValue = header.toSmtpApiHeader();
```

If you need the unescaped JSON string:

```java
String rawHeaderValue = header.toRawSmtpApiHeader();
```

### [To](http://sendgrid.com/docs/API_Reference/SMTP_API/index.html)

```java
header.addSmtpApiTo("email@email.com");
// or
header.addSmtpApiTo("email@email.com", "Email User");

List<String> tos = header.getSmtpApiTos();
```

### [Substitutions](http://sendgrid.com/docs/API_Reference/SMTP_API/substitution_tags.html)

```java
header.addValueToSubstitution("key", "value");

List<String> substitution = header.getSubstitution("key");
```

### [Unique Arguments](http://sendgrid.com/docs/API_Reference/SMTP_API/unique_arguments.html)

```java
header.setUniqueArg("key", "value");

String arg = header.getUniqueArg("key");
```

### [Categories](http://sendgrid.com/docs/API_Reference/SMTP_API/categories.html)

```java
header.addCategory("category");

List<String> categories = header.getCategories();
```

### [Sections](http://sendgrid.com/docs/API_Reference/SMTP_API/section_tags.html)

```java
header.setSection("key", "section");

String section = header.getSection("key");
```

### [Filters](http://sendgrid.com/docs/API_Reference/SMTP_API/apps.html)

```java
header.setSettingInFilter("filter", "setting", "value");
header.setSettingInFilter("filter", "setting", 1);

Map<String, Object> filter = header.getFilter("filter");
```

### [ASM Group ID](https://sendgrid.com/docs/User_Guide/advanced_suppression_manager.html)

```java
header.setAsmGroupId(1);

Integer groupId = header.getAsmGroupId();
```

### [Scheduling](https://sendgrid.com/docs/API_Reference/SMTP_API/scheduling_parameters.html)

```java
header.setSendAt(1416427645);

Integer sendAt = header.getSendAt();
```

### IP Pool
```java
header.setIpPool("transactional");

String ipPool = header.getIpPool();
```

## License

Licensed under the MIT License.
