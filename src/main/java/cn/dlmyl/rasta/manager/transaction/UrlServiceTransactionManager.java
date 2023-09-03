package cn.dlmyl.rasta.manager.transaction;

import cn.dlmyl.rasta.model.entity.Code;
import cn.dlmyl.rasta.model.entity.Mapping;
import cn.dlmyl.rasta.repository.CodeRepository;
import cn.dlmyl.rasta.repository.MappingRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

/**
 * 短链映射业务事务管理
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Component
@RequiredArgsConstructor
public class UrlServiceTransactionManager {

    private final CodeRepository codeRepository;
    private final MappingRepository mappingRepository;

    @Transactional(rollbackFor = Exception.class)
    public void saveUrlMapAndUpdateCompressCode(Mapping urlMap, Code code) {
        codeRepository.updateByPrimaryKeySelective(code);
        mappingRepository.insertSelective(urlMap);
    }

}
