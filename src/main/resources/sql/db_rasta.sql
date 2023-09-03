CREATE DATABASE `db_rasta` CHARSET 'utf8mb4' COLLATE 'utf8mb4_0900_ai_ci';

USE `db_rasta`;

CREATE TABLE `r_domain` (
    `id`                  BIGINT UNSIGNED    NOT NULL    AUTO_INCREMENT                                          COMMENT '主键',

    `domain_value`        VARCHAR(128)       NOT NULL                                                            COMMENT '域名',
    `protocol`            VARCHAR(8)         NOT NULL    DEFAULT 'https'                                         COMMENT '协议,https或者http',
    `domain_status`       TINYINT(1)         NOT NULL    DEFAULT 1                                               COMMENT '域名状态,1:正常,2:已失效',

    `create_time`         DATETIME           NOT NULL    DEFAULT CURRENT_TIMESTAMP                               COMMENT '创建时间',
    `edit_time`           DATETIME           NOT NULL    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP   COMMENT '更新时间',
    `creator`             VARCHAR(32)        NOT NULL    DEFAULT 'admin'                                         COMMENT '创建者',
    `editor`              VARCHAR(32)        NOT NULL    DEFAULT 'admin'                                         COMMENT '更新者',
    `deleted`             TINYINT(1)         NOT NULL    DEFAULT 0                                               COMMENT '软删除标识,0:正常,1:删除',

    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_domain`(`domain_value`)
) COMMENT = '短链域名配置表';

INSERT INTO `r_domain` (`id`, `domain_value`, `protocol`, `domain_status`, `create_time`, `edit_time`, `creator`, `editor`, `deleted`)
     VALUES (1, '127.0.0.1:9099', 'http', 1, '2023-09-03 22:10:18', '2023-09-03 22:10:18', 'admin', 'admin', 0);


CREATE TABLE `r_code` (
    `id`                  BIGINT UNSIGNED    NOT NULL    AUTO_INCREMENT                                          COMMENT '主键',

    `compression_code`    VARCHAR(16)        NOT NULL                                                            COMMENT '压缩码',
    `code_status`         TINYINT(1)         NOT NULL    DEFAULT 1                                               COMMENT '压缩码状态,1:未使用,2:已使用,3:已失效',
    `sequence_value`      VARCHAR(64)        NOT NULL                                                            COMMENT '序列(盐)',
    `strategy`            VARCHAR(8)         NOT NULL    DEFAULT 'sequence'                                      COMMENT '生成策略,sequence或者hash',

    `create_time`         DATETIME           NOT NULL    DEFAULT CURRENT_TIMESTAMP                               COMMENT '创建时间',
    `edit_time`           DATETIME           NOT NULL    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP   COMMENT '更新时间',
    `creator`             VARCHAR(32)        NOT NULL    DEFAULT 'admin'                                         COMMENT '创建者',
    `editor`              VARCHAR(32)        NOT NULL    DEFAULT 'admin'                                         COMMENT '更新者',
    `deleted`             TINYINT(1)         NOT NULL    DEFAULT 0                                               COMMENT '软删除标识,0:正常,1:删除',

    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_compression_code`(`compression_code`)
) COMMENT = '短码表';


CREATE TABLE `r_mapping` (
    `id`                BIGINT UNSIGNED     NOT NULL AUTO_INCREMENT                                             COMMENT '主键',

    `short_url`         VARCHAR(64)         NOT NULL                                                            COMMENT '短链URL',
    `long_url`          VARCHAR(768)        NOT NULL                                                            COMMENT '长链URL',
    `short_url_digest`  VARCHAR(128)        NOT NULL                                                            COMMENT '短链摘要',
    `long_url_digest`   VARCHAR(128)        NOT NULL                                                            COMMENT '长链摘要',
    `compression_code`  VARCHAR(16)         NOT NULL                                                            COMMENT '短链压缩码',
    `description`       VARCHAR(256)            NULL    DEFAULT NULL                                            COMMENT '短链描述',
    `url_status`        TINYINT(1)          NOT NULL    DEFAULT 1                                               COMMENT 'URL状态,1:正常,2:已失效',

    `create_time`       DATETIME            NOT NULL    DEFAULT CURRENT_TIMESTAMP                               COMMENT '创建时间',
    `edit_time`         DATETIME            NOT NULL    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP   COMMENT '更新时间',
    `creator`           VARCHAR(32)         NOT NULL    DEFAULT 'admin'                                         COMMENT '创建者',
    `editor`            VARCHAR(32)         NOT NULL    DEFAULT 'admin'                                         COMMENT '更新者',
    `deleted`           TINYINT(1)          NOT NULL    DEFAULT 0                                               COMMENT '软删除标识,0:正常,1:删除',

    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_compression_code`(`compression_code`),
    INDEX `idx_short_url`(`short_url`),
    INDEX `idx_short_url_digest`(`short_url_digest`),
    INDEX `idx_long_url_digest`(`long_url_digest`)
) COMMENT = '短链映射表';


CREATE TABLE `r_transform_event_record` (
    `id`                bigint UNSIGNED     NOT NULL AUTO_INCREMENT                                             COMMENT '主键',

    `unique_identity`   varchar(128)        NOT NULL                                                            COMMENT '唯一身份标识,SHA-1(客户端IP-UA)',
    `client_ip`         varchar(64)         NOT NULL                                                            COMMENT '客户端IP',
    `short_url`         varchar(64)         NOT NULL                                                            COMMENT '短链URL',
    `long_url`          varchar(768)        NOT NULL                                                            COMMENT '长链URL',
    `short_url_digest`  varchar(128)        NOT NULL                                                            COMMENT '短链摘要',
    `long_url_digest`   varchar(128)        NOT NULL                                                            COMMENT '长链摘要',
    `compression_code`  varchar(16)         NOT NULL                                                            COMMENT '压缩码',
    `record_time`       datetime            NOT NULL                                                            COMMENT '记录时间戳',
    `user_agent`        varchar(2048)           NULL    DEFAULT NULL                                            COMMENT 'UA',
    `cookie_value`      varchar(2048)           NULL    DEFAULT NULL                                            COMMENT 'cookie',
    `query_param`       varchar(2048)           NULL    DEFAULT NULL                                            COMMENT 'URL参数',
    `province`          varchar(32)             NULL    DEFAULT NULL                                            COMMENT '省份',
    `city`              varchar(32)             NULL    DEFAULT NULL                                            COMMENT '城市',
    `phone_type`        varchar(64)             NULL    DEFAULT NULL                                            COMMENT '手机型号',
    `browser_type`      varchar(64)             NULL    DEFAULT NULL                                            COMMENT '浏览器类型',
    `browser_version`   varchar(128)            NULL    DEFAULT NULL                                            COMMENT '浏览器版本号',
    `os_type`           varchar(32)             NULL    DEFAULT NULL                                            COMMENT '操作系统型号',
    `device_type`       varchar(32)             NULL    DEFAULT NULL                                            COMMENT '设备型号',
    `os_version`        varchar(32)             NULL    DEFAULT NULL                                            COMMENT '操作系统版本号',
    `transform_status`  tinyint(1)          NOT NULL    DEFAULT 0                                               COMMENT '转换状态,1:转换成功,2:转换失败,3:重定向成功,4:重定向失败',

    `create_time`       datetime            NOT NULL    DEFAULT CURRENT_TIMESTAMP                               COMMENT '创建时间',
    `edit_time`         datetime            NOT NULL    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP   COMMENT '更新时间',
    `creator`           varchar(32)         NOT NULL    DEFAULT 'admin'                                         COMMENT '创建者',
    `editor`            varchar(32)         NOT NULL    DEFAULT 'admin'                                         COMMENT '更新者',
    `deleted`           tinyint(1)          NOT NULL    DEFAULT 0                                               COMMENT '软删除标识,0:正常,1:删除',

    PRIMARY KEY (`id`),
    INDEX `idx_record_time`(`record_time`),
    INDEX `idx_compression_code`(`compression_code`),
    INDEX `idx_short_url_digest`(`short_url_digest`),
    INDEX `idx_long_url_digest`(`long_url_digest`),
    INDEX `idx_unique_identity`(`unique_identity`)
) COMMENT = '短链访问事件转换表';


CREATE TABLE `r_visit_statistics` (
    `id`                            BIGINT UNSIGNED     NOT NULL    AUTO_INCREMENT                                          COMMENT '主键',

    `pv_count`                      BIGINT UNSIGNED     NOT NULL    DEFAULT 0                                               COMMENT '页面流量数',
    `uv_count`                      BIGINT UNSIGNED     NOT NULL    DEFAULT 0                                               COMMENT '独立访客数',
    `ip_count`                      BIGINT UNSIGNED     NOT NULL    DEFAULT 0                                               COMMENT '独立IP数',
    `effective_redirection_count`   BIGINT UNSIGNED     NOT NULL    DEFAULT 0                                               COMMENT '有效跳转数',
    `ineffective_redirection_count` BIGINT UNSIGNED     NOT NULL    DEFAULT 0                                               COMMENT '无效跳转数',
    `compression_code`              VARCHAR(16)         NOT NULL                                                            COMMENT '压缩码',
    `short_url_digest`              VARCHAR(128)        NOT NULL                                                            COMMENT '短链摘要',
    `long_url_digest`               VARCHAR(128)        NOT NULL                                                            COMMENT '长链摘要',
    `statistics_date`               DATE                NOT NULL    DEFAULT '1970-01-01'                                    COMMENT '统计日期',

    `create_time`                   DATETIME            NOT NULL    DEFAULT CURRENT_TIMESTAMP                               COMMENT '创建时间',
    `edit_time`                     DATETIME            NOT NULL    DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP   COMMENT '更新时间',
    `creator`                       VARCHAR(32)         NOT NULL    DEFAULT 'admin'                                         COMMENT '创建者',
    `editor`                        VARCHAR(32)         NOT NULL    DEFAULT 'admin'                                         COMMENT '更新者',
    `deleted`                       TINYINT(1)          NOT NULL    DEFAULT 0                                               COMMENT '软删除标识,0:正常,1:删除',

    PRIMARY KEY (`id`),
    UNIQUE INDEX `uk_date_code_digest`(`statistics_date`, `compression_code`)
) COMMENT = '短链访问数据统计表';
