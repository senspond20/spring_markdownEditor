package com.sensweb.myapp.common.model;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.ToString;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@ToString
public class FileSaveResponseDto{
    private boolean isSaveSuccess;
    private FileInfoVO fileInfo;
}
