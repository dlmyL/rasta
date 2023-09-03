package cn.dlmyl.rasta.repository;

import cn.dlmyl.rasta.mapper.MappingMapper;
import cn.dlmyl.rasta.model.entity.Mapping;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 短链映射持久层
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Repository
public class MappingRepository extends BaseRepository<MappingMapper, Mapping> {

    public List<Mapping> selectAll() {
        return this.list();
    }

    public Mapping selectByCompressionCode(String compressionCode) {
        return this.baseMapper.selectOne(
                new LambdaQueryWrapper<Mapping>()
                        .eq(Mapping::getCompressionCode, compressionCode)
        );
    }

    public void insertSelective(Mapping urlMap) {
        this.baseMapper.insert(urlMap);
    }

}
