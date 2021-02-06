# Make Suppoting Markdown WYSIWYG Web Editor

+ spring boot 2.4 / Maven
+ spring boot web/dev tools
+ **dependencies**
  + lombok
  + spring data jpa
  + spring data jdbc
  + h2 database
  + Thyemleaf

+ **goal**
  + Make Markdown to Html Converter(Using RegEx) 😊
  + Make WYSIWYG Web Editor 😊
  + static html build 😊


```
─resources
│  │      ├─static
│  │      │  ├─assets
│  │      │  │  ├─css
│  │      │  │  └─js
│  │      │  └─build
│  │      └─templates
```

### logback config
+ logback-spring.xml 
+ classpath:/

### Thymleaf Config

```java
public class WebConfig implements WebMvcConfigurer{
    ...
}
```
