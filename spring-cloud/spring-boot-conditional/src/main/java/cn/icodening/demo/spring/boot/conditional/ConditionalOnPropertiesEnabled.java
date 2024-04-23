package cn.icodening.demo.spring.boot.conditional;

import org.springframework.context.annotation.Conditional;

import java.lang.annotation.Documented;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * @author icodening
 * @date 2024.02.02
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Conditional(OnPropertiesEnabledCondition.class)
public @interface ConditionalOnPropertiesEnabled {

    /**
     * 获取带有ConfigurationProperties注解的类
     *
     * @return 带有ConfigurationProperties注解的类
     */
    Class<?> type();

    /**
     * 配置类中需要检查的属性名， 如 'enabled' 的 boolean/Boolean 类型字段，用于判断是否满足条件
     *
     * @return 配置类开关属性名
     */
    String property() default "enabled";
}
