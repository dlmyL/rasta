package cn.dlmyl.rasta.infra.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.time.ZoneId;
import java.time.ZoneOffset;

/**
 * Time常量
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Getter
@RequiredArgsConstructor
public enum TimeZoneConstant {

    CHINA(ZoneId.of("Asia/Shanghai"), "上海-中国时区", ZoneOffset.of("+08:00")),

    ;

    private final ZoneId zoneId;
    private final String description;
    private final ZoneOffset offset;

}
