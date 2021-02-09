package com.sensweb.myapp.web.board;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import com.sensweb.myapp.common.model.CommandMapDto;
import com.sensweb.myapp.common.utils.FileUtils;
import com.sensweb.myapp.web.board.model.BoardVo;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@Controller
@RequestMapping("/board")
public class BoardController {
    private final String baseURl = "board/";

    @Autowired
    private ResourceLoader resourceLoader;
    @Autowired
    private FileUtils fu;

    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @GetMapping("")
    public String index() {
        return "grid/toast-ui-gird";
    }

    @GetMapping("/convert")
    @ResponseBody
    public Object covert(CommandMapDto cmap){
        BoardVo board = new BoardVo(1,"안녕하세요","제목입니다.");
    
        cmap = fu.objectToCommandMapDto(board);
        return cmap;
    }


    @GetMapping("/make3")
    @ResponseBody
    public Object makeBoard() throws IOException {
        List<BoardVo> boarList = new ArrayList<>();

        for(int i = 1; i < 15; i ++){
            boarList.add(new BoardVo(i, "안녕하세요" + i, "내용입니다." + i));
        }

        Resource staticPath = resourceLoader.getResource("classpath:/static");
        
        logger.debug("staticPath {}", staticPath.getURL().getPath());

        logger.debug("spsp {}", fu.getStaticPath());
        logger.debug("js {}", fu.ObjectToJsonStr(boarList, true));

        fu.saveObjToJsonFile(fu.getStaticPath(), "board.json", boarList, false);
        // File f = new File("./board.json");
        // if(!f.exists()){
        //     f.mkdirs();

        // fu.saveFile(fu.getStaticPath(), "board.json", fu.ObjectToJsonStr(boarList, false));
            
        // }

        return boarList;
    }

    @GetMapping("/make3")
    @ResponseBody
    public boolean makeBoard3() {
        List<BoardVo> boarList = new ArrayList<>();
        for(int i = 1; i <= 1000000; i ++){
            boarList.add(new BoardVo(i, "안녕하세요" + i, "내용입니다." + i));
        }

        return fu.saveObjToJsonFile(fu.getApplicationStaticPath() + "/build/data", "board.json", boarList, false);
    }

}
