package ru.practicum.dto;

import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class StatsView {

    private String app;

    private String uri;

    private Long hits;
}
