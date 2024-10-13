package com.aice.filter;

import feign.Logger;
import lombok.extern.log4j.Log4j2;

@Log4j2
public class FilterFeignLogger extends Logger {
    @Override
    protected void log(String configKey,String format,Object... args) {
        log.info(String.format(methodTag(configKey) + format, args));
    }
}
