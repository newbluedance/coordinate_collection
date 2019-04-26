package com.lcf.coordinate_collection;

import com.lcf.coordinate_collection.view.view.WjFrame;
import javax.annotation.Resource;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;

/**
 * @author lichunfeng
 **/
@SpringBootApplication
public class CoordinateCollectionApplication implements CommandLineRunner {

    @Resource
    private WjFrame wjFrame;

    public static void main(String[] args) {
        SpringApplicationBuilder builder = new SpringApplicationBuilder(CoordinateCollectionApplication.class);
        builder.headless(false).run(args);
    }

    @Override
    public void run(String... args) {
        wjFrame.showFrame();
    }
}