package com.jojun.groupbuy.pojo;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class HotRecommendation {
    private String id;
    private List<String> pictures;
    private String title;
    private String alt;
    private String target;
    private String type;

    @JsonIgnore
    private String picturesJson; // 数据库中的 JSON 字符串

    private static final ObjectMapper objectMapper = new ObjectMapper();

    // 将数据库中的 JSON 字符串转换为 Java List
    public void setPicturesJson(String picturesJson) {
        this.picturesJson = picturesJson;
        if (picturesJson != null) {
            try {
                this.pictures = objectMapper.readValue(picturesJson, new TypeReference<List<String>>() {});
            } catch (JsonProcessingException e) {
                // 处理异常
                this.pictures = null;
            }
        }
    }
}