package cn.dlmyl.rasta.repository;

import cn.dlmyl.rasta.mapper.DomainMapper;
import cn.dlmyl.rasta.model.entity.Domain;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 短链域名持久层
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Repository
public class DomainRepository extends BaseRepository<DomainMapper, Domain> {

    public Domain selectByDomain(String domain) {
        return this.baseMapper.selectOne(
                new LambdaQueryWrapper<Domain>()
                        .eq(Domain::getDomainValue, domain)
        );
    }

    public List<Domain> selectAll() {
        return this.list();
    }

}
