package com.ls.dto;

import lombok.Data;

/**
 * @program: ls-security
 * @description: 文件信息
 * @author: Liang Shan
 * @create: 2019-11-04 10:03
 **/
@Data
public class FileInfo {
    public FileInfo(String path) {
        this.path = path;
    }
    private String path;
}
