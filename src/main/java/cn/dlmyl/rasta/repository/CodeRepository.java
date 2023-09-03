package cn.dlmyl.rasta.repository;

import cn.dlmyl.rasta.mapper.CodeMapper;
import cn.dlmyl.rasta.model.entity.Code;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;

/**
 * 短码持久层
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Repository
public class CodeRepository extends BaseRepository<CodeMapper, Code> {

    public Code getLatestAvailableCompressionCode() {
        return this.baseMapper.selectOne(
                new LambdaQueryWrapper<Code>()
                        .eq(Code::getCodeStatus, 1)
                        .orderByAsc(Code::getId)
                        .last("limit 1")
        );
    }

    public void insertSelective(Code code) {
        this.baseMapper.insert(code);
    }

    public void updateByPrimaryKeySelective(Code code) {
        this.baseMapper.updateById(code);
    }

}