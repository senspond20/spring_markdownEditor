package com.sensweb.myapp.web.board.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@ToString
public class BoardVo {
    private int id;
    private String title;
    private String content;
}
