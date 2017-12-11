package com.phuag.sample.common.logging;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Created by vvvvvv on 2017/12/6.
 */
@Data
@AllArgsConstructor
public class LoggerDefine {
    private String module;
    private String describe;
    private String type;
}
