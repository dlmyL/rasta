package cn.dlmyl.rasta.repository;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;

/**
 * Base Repository
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
public abstract class BaseRepository<M extends BaseMapper<T>, T> extends ServiceImpl<M, T> {

}
