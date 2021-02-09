package com.sensweb.myapp.web.wysiwyg.controller;
// import com.vladsch.flexmark.util.ast.Node;

// import com.vladsch.flexmark.html.HtmlRenderer;
// import com.vladsch.flexmark.parser.Parser;
// import com.vladsch.flexmark.util.data.MutableDataSet;

import org.springframework.data.relational.core.mapping.Embedded.Nullable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.BufferedReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import org.commonmark.node.*;
import org.commonmark.parser.Parser;
import org.commonmark.renderer.html.HtmlRenderer;
import org.json.JSONObject;

@Getter
@NoArgsConstructor
@AllArgsConstructor
class MarkdownRequestDto {
    private String markdownContent;
    private String option;

    public MarkdownRequestDto(String markdownContent) {
        this.markdownContent = markdownContent;
    }
}

@Getter
@Setter
@NoArgsConstructor
class MarkdownResponseDto {
    private int status = HttpStatus.OK.value();
    private final String description = "markdown to html API, Require Post Method/Request Body";
    private String result = "";
    private String contentType = "";
    private String message = HttpStatus.OK.getReasonPhrase();
    private long tiemstamp;
    private final List<MarkdownRequestDto> sample = new ArrayList<>(
            Arrays.asList(new MarkdownRequestDto("# 안녕하세요"), new MarkdownRequestDto("# 안녕하세요", "true")));
    private String path;
    public MarkdownResponseDto(HttpServletRequest request,  String result){
        this.path = request.getRequestURI();
        this.contentType = request.getContentType();
        this.tiemstamp = System.currentTimeMillis();
        this.result = result;
    }
    public MarkdownResponseDto(HttpServletRequest request, HttpStatus status, String result) {
        this.path = request.getRequestURI();
        this.contentType = request.getContentType();
        this.status = status.value();
        this.message = status.getReasonPhrase();
        this.tiemstamp = System.currentTimeMillis();
        this.result = result;
    }

}

@RestController
public class MarkdownController {


    public  JSONObject readJSONStringFromRequestBody(HttpServletRequest request){
        StringBuffer json = new StringBuffer();
        String line = null;

        try {
            BufferedReader reader = request.getReader();
            while((line = reader.readLine()) != null) {
                json.append(line);
            }

        }catch(Exception e) {
          System.out.println("bad");
        }

        JSONObject jObj = new JSONObject(json.toString());
        return jObj;
    }

    @RequestMapping(value = "/api/mark", produces = "application/json; charset=UTF-8")
    public ResponseEntity<?> markdownToHTML(HttpServletRequest request, HttpStatus status,
                                            @RequestBody MarkdownRequestDto dto) {
        HtmlRenderer renderer = null;
        Node document = null;
        status = HttpStatus.BAD_REQUEST;
        // JSONObject json = readJSONStringFromRequestBody(request);
        
        // ResponseEntity.status(status).body(new MarkdownResponseDto(request, status,""));
        try{
            Parser parser = Parser.builder().build();
            document = parser.parse(dto.getMarkdownContent());
            // HtmlRenderer renderer = HtmlRenderer.builder().build();
           renderer = HtmlRenderer.builder().escapeHtml(true).build();

        }catch(Exception e){
            status = HttpStatus.INTERNAL_SERVER_ERROR;
            // return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("{\"msg\" : \"파싱 오류\"}");  // --> ResponseMessageDto
            return ResponseEntity.status(status).body(new MarkdownResponseDto(request, status,""));
        }
        // System.out.println(renderer.render(document));
        return ResponseEntity.ok().body(new MarkdownResponseDto(request, renderer.render(document)));
    }
}
