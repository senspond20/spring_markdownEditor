package com.sensweb.myapp.web.wysiwyg.controller;
// import com.vladsch.flexmark.util.ast.Node;
// import com.vladsch.flexmark.html.HtmlRenderer;
// import com.vladsch.flexmark.parser.Parser;
// import com.vladsch.flexmark.util.data.MutableDataSet;

import org.springframework.data.relational.core.mapping.Embedded.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import lombok.Getter;
import lombok.NoArgsConstructor;

import java.sql.Time;

import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;

@Getter
@NoArgsConstructor
class MarkdownRequestDto{
    private String markdownContent;
}

@Getter
@NoArgsConstructor
class MarkdownResponseDto{
    int status = HttpStatus.OK.value();
    private String convertedHtml = HttpStatus.OK.getReasonPhrase();
    private String message;
    private long tiemstamp;
    private String path = "/api/mark";
    public MarkdownResponseDto(HttpStatus status, String convertedHtml){
        this.status = status.value();
        this.message = status.getReasonPhrase();
        this.tiemstamp = System.currentTimeMillis();
        this.convertedHtml = convertedHtml;
    }
}

@RestController
public class MarkdownController {

    @PostMapping(value = "/api/mark", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> markdownToHTML(@RequestBody MarkdownRequestDto dto) {
            HtmlRenderer renderer = null;
            Node document = null;
        try{
            // Object markdownContent = request.getParameter("markdownContent");
            
            if(dto.getMarkdownContent().isEmpty() ) {
                return ResponseEntity.badRequest()
                                     .body(new MarkdownResponseDto(HttpStatus.BAD_REQUEST,""));    // --> ResponseMessageDto
            }

            Parser parser = Parser.builder()
                                  .build();

            document = parser.parse(dto.getMarkdownContent());
            // HtmlRenderer renderer = HtmlRenderer.builder().build();
           renderer = HtmlRenderer.builder()
                                  .escapeHtml(true)
                                  .build();

        }catch(Exception e){
            // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"msg\" : \"파싱 오류\"}");  // --> ResponseMessageDto
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                                 .body(new MarkdownResponseDto(HttpStatus.INTERNAL_SERVER_ERROR,""));
        }
        // System.out.println(renderer.render(document));
        return ResponseEntity.ok().body(renderer.render(document));
    }
}
