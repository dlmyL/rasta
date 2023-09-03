package cn.dlmyl.rasta.mapper;

import cn.dlmyl.rasta.model.entity.Domain;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Mapper;

/**
 * 短链域名 Mapper 层
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Mapper
public interface DomainMapper extends BaseMapper<Domain> {

}