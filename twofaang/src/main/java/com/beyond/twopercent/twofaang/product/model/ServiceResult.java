package com.beyond.twopercent.twofaang.product.model;

import lombok.Data;

@Data
public class ServiceResult {

    boolean result;
    String message;

    public ServiceResult(){
        result = true;
    }

    public ServiceResult(boolean result, String message){
        this.result = result;
        this.message = message;
    }
}
