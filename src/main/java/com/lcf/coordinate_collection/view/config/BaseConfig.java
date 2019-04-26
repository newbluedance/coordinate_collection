package com.lcf.coordinate_collection.view.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;

/**
 * 〈一句话功能简述〉<br> 〈MQTT接收消息处理〉
 *
 * @author lenovo
 * @since 1.0.0
 */
@Configuration
public class BaseConfig {


    @Value("${hwa.client.left.panel.width}")
    private Integer width;

    @Value("${hwa.client.left.panel.height}")
    private Integer height;

}


