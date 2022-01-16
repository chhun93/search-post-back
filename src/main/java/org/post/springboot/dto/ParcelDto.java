package org.post.springboot.dto;

import java.util.List;

public class ParcelDto {

    private String companyName;

    private List<ParcelDetailDto> result;

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName;
    }

    public List<ParcelDetailDto> getResult() {
        return result;
    }

    public void setResult(List<ParcelDetailDto> result) {
        this.result = result;
    }
}
