package cn.icodening.demo.spring.boot.conditional;

import org.springframework.beans.BeanUtils;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.context.properties.bind.Bindable;
import org.springframework.boot.context.properties.bind.Binder;
import org.springframework.boot.context.properties.source.ConfigurationPropertyName;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.ResolvableType;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.core.annotation.MergedAnnotation;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;
import org.springframework.lang.NonNull;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;

/**
 * @author icodening
 * @date 2024.02.02
 */
public class OnPropertiesEnabledCondition implements Condition {

    @Override
    public boolean matches(@NonNull ConditionContext context, @NonNull AnnotatedTypeMetadata metadata) {
        MergedAnnotation<ConditionalOnPropertiesEnabled> conditionalOnPropertiesEnabledMergedAnnotation = metadata.getAnnotations().get(ConditionalOnPropertiesEnabled.class);
        if (!conditionalOnPropertiesEnabledMergedAnnotation.isPresent()) {
            return false;
        }
        Class<?> propertiesClass = conditionalOnPropertiesEnabledMergedAnnotation.getClass("value");
        Field enabledField = ReflectionUtils.findField(propertiesClass, "enabled");
        if (enabledField == null) {
            throw new IllegalArgumentException("The properties class must have a field named 'enabled'");
        }
        Class<?> fieldType = enabledField.getType();
        if (!fieldType.isPrimitive() && !Boolean.class.isAssignableFrom(fieldType)) {
            throw new IllegalArgumentException("The field named 'enabled' must be a 'boolean or java.lang.Boolean' type");
        }
        ConfigurationProperties configurationPropertiesAnnotation = AnnotationUtils.findAnnotation(propertiesClass, ConfigurationProperties.class);
        if (configurationPropertiesAnnotation == null) {
            throw new IllegalArgumentException("The properties class must have a 'ConfigurationProperties' annotation");
        }
        Environment environment = context.getEnvironment();
        ConfigurationPropertyName propertyName = ConfigurationPropertyName.of(configurationPropertiesAnnotation.prefix());
        Object propertiesInstance = Binder.get(environment).bind(propertyName, Bindable.of(ResolvableType.forClass(propertiesClass)))
                .orElseGet(() -> BeanUtils.instantiateClass(propertiesClass));
        return getBoolean(propertiesInstance, enabledField);
    }


    private static boolean getBoolean(Object target, Field field) {
        try {
            ReflectionUtils.makeAccessible(field);
            Object value = field.get(target);
            if (value == null) {
                return false;
            }
            return (boolean) value;
        } catch (IllegalAccessException e) {
            return false;
        }
    }
}
