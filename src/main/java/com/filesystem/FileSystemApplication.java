package com.filesystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 文件管理系统启动类
 *
 * @author System
 * @since 2024-01-01
 */
@SpringBootApplication
@MapperScan("com.filesystem.mapper")
public class FileSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(FileSystemApplication.class, args);
        System.out.println("\n========================================");
        System.out.println("文件管理系统启动成功！");
        System.out.println("访问地址: http://localhost:8080/api");
        System.out.println("========================================\n");
    }
}
