package org.post.springboot.dto.CJ;

import com.fasterxml.jackson.annotation.JsonFormat;

import java.time.LocalDateTime;

public class Result {

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss.S")
    private LocalDateTime dTime;

    private String regBranNm;

    private String scanNm;

    public LocalDateTime getdTime() {
        return dTime;
    }

    public String getRegBranNm() {
        return regBranNm;
    }

    public String getScanNm() {
        return scanNm;
    }
}
