package com.ls.web.controller;

import com.ls.dto.FileInfo;
import org.apache.commons.io.IOUtils;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.util.Date;

/**
 * @author Liang Shan
 * @date 2019/11/04 11:55
 * @version 1.0
 */
@RestController
@RequestMapping("/file")
public class FileController {

    /** 
    * @Description:  dsadsa
    * @Param: [file] 
    * @return: com.ls.dto.FileInfo 
    * @Author: Liang Shan 
    * @Date: 2019/11/4 0004 
    */ 
    @PostMapping
    public FileInfo upload(MultipartFile file) throws IOException {
        File projectPathFile = new File("");
        String projectPath = projectPathFile.getAbsolutePath();
        System.out.println("请求参数名:"+file.getName());
        System.out.println("文件名:" + file.getOriginalFilename());
        System.out.println("文件大小:" + file.getSize());
        File localFile = new File(projectPath,
                                   new Date().getTime() + file.getOriginalFilename().substring( file.getOriginalFilename().lastIndexOf(".")));
        System.out.println("文件路径:" + localFile.getAbsolutePath());
        file.transferTo(localFile);
        return new FileInfo(localFile.getAbsolutePath());
    }



   /**
   * @author: Liang Shan
   * @date: 2019-11-04
   * @time: 13:46
   * @param: id
   * @Param: request
   * @Param: response
   * @description: 文件下载
   */
   @GetMapping("/{id}")
    public void download(@PathVariable(name = "id") String id, HttpServletRequest request, HttpServletResponse response) {
        // JDK1.7的特性，把流写到try块的括号中，就可以在执行后自动关闭流，而不用手动关闭
        try (InputStream inputStream = new FileInputStream(new File("C:\\Users\\Administrator\\IdeaProjects\\security\\ls-security\\ls-security-demo", "file" + id + ".txt"));
             OutputStream outputStream = response.getOutputStream()) {
            response.setContentType("application/x-download");
            // 定义传输的文件名，前端接收到的文件名
            response.setHeader("Content-Disposition", "attachment;filename=test.txt");
            // 将文件写入到响应流中
            IOUtils.copy(inputStream, outputStream);
            outputStream.flush();
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }
}
