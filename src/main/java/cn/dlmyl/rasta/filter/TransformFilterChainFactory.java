package cn.dlmyl.rasta.filter;

import com.google.common.collect.Lists;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.ListableBeanFactory;
import org.springframework.stereotype.Component;

import java.util.Comparator;
import java.util.List;
import java.util.Map;

/**
 * 短链转换过滤器链工厂
 *
 * @author <a href="https://github.com/dlmyL">dlmyL</a>
 * @version 1.0
 */
@Component
public class TransformFilterChainFactory implements BeanFactoryAware {

    private ListableBeanFactory beanFactory;

    @SuppressWarnings("NullableProblems")
    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (ListableBeanFactory) beanFactory;
    }

    public TransformFilterChain buildTransformFilterChain(TransformContext context) {
        Map<String, TransformFilter> filters = beanFactory.getBeansOfType(TransformFilter.class);
        List<TransformFilterInstance> instances = Lists.newArrayList();
        filters.forEach((k, v) -> instances.add(new TransformFilterInstance(v, v.order(), k)));
        instances.sort(Comparator.comparingInt(TransformFilterInstance::getOrder));
        DefaultTransformFilterChain filterChain = new DefaultTransformFilterChain();
        instances.forEach(instance -> {
            TransformFilter filter = instance.getFilter();
            BaseNamingTransformFilter baseNamingTransformFilter = new BaseNamingTransformFilter(filter) {
                @Override
                public String filterName() {
                    return instance.getFilterName();
                }
            };
            filterChain.addTransformFilter(baseNamingTransformFilter);
            baseNamingTransformFilter.init(context);
        });
        return filterChain;
    }

    @Getter
    @RequiredArgsConstructor
    private static class TransformFilterInstance {
        private final TransformFilter filter;
        private final int order;
        private final String filterName;
    }

}
