package ru.practicum.dto;



import com.sun.istack.NotNull;
import lombok.Builder;
import lombok.Data;


@Data
@Builder
public class StatsDto {

    private Long id;
    @NotNull
    private String app;
    @NotNull
    private String uri;
    @NotNull
    private String ip;
    @NotNull

    private String timestamp;

}
