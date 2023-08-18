package com.xl;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

@Component
@RefreshScope
public class DynamicConfig {

    @Value(("${blow:0}"))
    private String blow;

    public String getBlow() {
        return blow;
    }

    public void setBlow(String blow) {
        this.blow = blow;
    }
}
