package com.springboot.springbootweb04.entity;

import lombok.Data;
import org.springframework.context.annotation.Bean;

@Data
public class MainPageData {
    private Integer userCount = 18;
    private Integer accessCount = 244;
    private Integer uploadCount = 15;
    private Integer downloadCount = 23;
}
