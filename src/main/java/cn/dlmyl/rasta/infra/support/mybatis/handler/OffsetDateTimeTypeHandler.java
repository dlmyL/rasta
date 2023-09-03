package cn.dlmyl.rasta.infra.support.mybatis.handler;

import cn.dlmyl.rasta.infra.common.TimeZoneConstant;
import org.apache.ibatis.type.BaseTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.MappedJdbcTypes;
import org.apache.ibatis.type.MappedTypes;

import java.sql.CallableStatement;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.time.OffsetDateTime;
import java.util.Optional;

/**
 * OffsetDateTime 类型处理器
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@MappedTypes(OffsetDateTime.class)
@MappedJdbcTypes(JdbcType.TIMESTAMP)
public class OffsetDateTimeTypeHandler extends BaseTypeHandler<OffsetDateTime> {

    @Override
    public void setNonNullParameter(PreparedStatement ps, int i, OffsetDateTime parameter, JdbcType jdbcType) throws SQLException {
        ps.setTimestamp(i, Timestamp.from(parameter.toInstant()));
    }

    @Override
    public OffsetDateTime getNullableResult(ResultSet rs, String columnName) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnName);
        return getOffsetDateTime(timestamp);
    }

    @Override
    public OffsetDateTime getNullableResult(ResultSet rs, int columnIndex) throws SQLException {
        Timestamp timestamp = rs.getTimestamp(columnIndex);
        return getOffsetDateTime(timestamp);
    }

    @Override
    public OffsetDateTime getNullableResult(CallableStatement cs, int columnIndex) throws SQLException {
        Timestamp timestamp = cs.getTimestamp(columnIndex);
        return getOffsetDateTime(timestamp);
    }

    private static OffsetDateTime getOffsetDateTime(Timestamp timestamp) {
        return Optional.ofNullable(timestamp)
                .map(ts -> OffsetDateTime.ofInstant(ts.toInstant(), TimeZoneConstant.CHINA.getOffset()))
                .orElse(null);
    }

}
