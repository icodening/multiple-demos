package cn.icodening.demo.spring.cloud.config.processor;

import cn.icodening.demo.spring.cloud.config.annotation.ForcedRefresh;
import cn.icodening.demo.spring.cloud.config.configuration.CustomConfigurationProperties;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.boot.context.properties.ConfigurationPropertiesBean;
import org.springframework.boot.context.properties.ConfigurationPropertiesBindingPostProcessor;
import org.springframework.boot.context.properties.bind.BindHandler;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.cloud.context.environment.EnvironmentChangeEvent;
import org.springframework.cloud.context.properties.ConfigurationPropertiesRebinder;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ApplicationObjectSupport;
import org.springframework.core.env.Environment;

/**
 * 自定义的强制刷新属性处理器
 * <p>
 * 作用: 解决配置中心场景下，移除Key时也能保证对应的配置项被初始化
 * 例子:
 * 在配置中心中存在以下配置项，
 * custom.config.list[0]=hello
 * custom.config.list[1]=world
 * custom.config.list[2]=spring
 * 那么调用{@link CustomConfigurationProperties#getList()} 的结果是 ["hello","world","spring"].
 * 但如果在配置中心上直接移除所有 "custom.config.list[x]" 的配置项，
 * 则调用{@link CustomConfigurationProperties#getList()}的结果依然是 ["hello","world","spring"],而不是[]的空集合，这样的结果无法满足一些特殊场景.
 * <p>
 * 原理：
 * Spring Cloud在动态刷新配置类时会在监听到{@link EnvironmentChangeEvent}事件时，调用 {@link ConfigurationPropertiesRebinder#rebind()}，对当前
 * 容器下的配置类Bean进行重绑定，简而言之其实就是destroyBean、initializeBean。而真正把配置类Bean的属性进行赋值则是通过BeanPostProcessor来实现的，
 * 其对应的实现为{@link ConfigurationPropertiesBindingPostProcessor}.
 * 那么我们要解决该问题的话则可以通过BeanPostProcessor对配置类Bean再次进行属性绑定({@link Binder#bindOrCreate}), 并将新绑定得到的Bean的属性重新赋值到原Bean中即可
 *
 * @author icodening
 * @date 2022.06.24
 * @see ConfigurationPropertiesBindingPostProcessor
 * @see Binder
 * @see ForcedRefresh
 */
public class ForcedRefreshPropertiesPostProcessor
        extends ApplicationObjectSupport implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        ApplicationContext applicationContext = obtainApplicationContext();
        ConfigurationPropertiesBean configurationPropertiesBean = ConfigurationPropertiesBean
                .get(applicationContext, bean, beanName);
        ForcedRefresh forcedRefresh = applicationContext.findAnnotationOnBean(beanName, ForcedRefresh.class);
        if (configurationPropertiesBean == null || forcedRefresh == null) {
            return BeanPostProcessor.super.postProcessBeforeInitialization(bean,
                    beanName);
        }
        Environment environment = applicationContext.getEnvironment();
        String configPrefix = configurationPropertiesBean.getAnnotation().prefix();
        ConfigurationPropertyName propertyName = ConfigurationPropertyName.of(configPrefix);
        Object newConfigurationPropertiesBean = Binder.get(environment).bindOrCreate(propertyName,
                configurationPropertiesBean.asBindTarget(), BindHandler.DEFAULT);
        BeanUtils.copyProperties(newConfigurationPropertiesBean, bean);
        return bean;
    }
}
