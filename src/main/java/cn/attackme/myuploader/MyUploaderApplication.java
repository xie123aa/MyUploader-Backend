package cn.attackme.myuploader;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
@MapperScan("cn.attackme.myuploader.mapper")
public class MyUploaderApplication {

    public static void main(String[] args) {
        SpringApplication.run(MyUploaderApplication.class, args);
    }
}
