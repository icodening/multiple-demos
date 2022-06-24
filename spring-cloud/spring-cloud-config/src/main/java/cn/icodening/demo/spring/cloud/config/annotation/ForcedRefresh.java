package cn.icodening.demo.spring.cloud.config.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标记需要强制刷新的配置类,配合@ConfigurationProperties、@RefreshScope来使用.
 * 表示没有该配置类对应的KEY时，也要强制刷新为默认值
 *
 * @author icodening
 * @date 2022.06.25
 */
@Target({ElementType.TYPE, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
public @interface ForcedRefresh {

}
