package com.woo.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author woo
 * @date 2024/01/10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class HotArtcileList {
    private Long id;
    private String title;
    private Long viewCount;
}
