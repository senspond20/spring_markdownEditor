package com.sensweb.myapp.test;

import com.vladsch.flexmark.util.ast.Node;
import com.vladsch.flexmark.html.HtmlRenderer;
import com.vladsch.flexmark.parser.Parser;
import com.vladsch.flexmark.util.data.MutableDataSet;

// import org.commonmark.node.*;
// import org.commonmark.parser.Parser;
// import org.commonmark.renderer.html.HtmlRenderer;
public class BasicSample {
    public static void main(String[] args) {
        MutableDataSet options = new MutableDataSet();

        // uncomment to set optional extensions
        //options.set(Parser.EXTENSIONS, Arrays.asList(TablesExtension.create(), StrikethroughExtension.create()));

        // uncomment to convert soft-breaks to hard breaks
        //options.set(HtmlRenderer.SOFT_BREAK, "<br />\n");


Parser parser = Parser.builder().build();
// Node document = parser.parse("This is *Sparta*");
// HtmlRenderer renderer = HtmlRenderer.builder().build();
//renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"

     //   Parser parser = Parser.builder(options).build();

         // 이대로 사용하면 코드블럭안에 스크립트가 인식된다.
    //    HtmlRenderer renderer = HtmlRenderer.builder(options).build();

        // 그래서 다음과 같이 
       HtmlRenderer renderer = HtmlRenderer.builder().escapeHtml(true).build();
        // You can re-use parser and renderer instances
        // Node document = parser.parse("```js\n<script>alert('hi')</script>```This is *Sparta*");

        Node document = parser.parse("|  No   |   Title   3   |  Writer  |\n|:-----:|:------------:|:--------:|\n|  3    | 안녕하세요    | senshig  |\n|  2    | 해피뉴이어    | admin    |\n|  1    | 안녕하세요    | sujan    |\n");
        String html = renderer.render(document);  // "<p>This is <em>Sparta</em></p>\n"
        System.out.println(html);
    }
}
