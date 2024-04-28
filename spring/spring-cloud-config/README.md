# Spring Cloud Config Demo
一个通过配置中心动态刷新环境变量的demo。
``PS:不是Spring Cloud Config组件demo``  
其中对``配置刷新``行为进行了扩展，用于适配需要重置配置的场景。

# 场景
配置中心有如下配置项, 我们希望在配置中心移除以下配置后，将list置为empty，不是null
````properties
custom.config.list[0]=hello
custom.config.list[1]=world
custom.config.list[2]=spring
````
* 未扩展:  
  如果移除这些KEY，实际上在代码中还是会拿到这个集合，并未按照预期移除里面的元素。
* 扩展后:  
  如果移除这些KEY，可以获取到一个empty list，符合场景预期结果。

# 实现原理
Spring Cloud在动态刷新配置类时会在监听到{@link EnvironmentChangeEvent}事件时，
调用``ConfigurationPropertiesRebinder#rebind()``对当前容器下的配置类Bean进行重绑定。
重新绑定其实就是``destroyBean``、``initializeBean``。  
而真正把配置类Bean的属性进行赋值则是通过BeanPostProcessor来实现的,其对应的实现为``ConfigurationPropertiesBindingPostProcessor``。
那么我们要解决该问题的话则可以通过BeanPostProcessor对重新创建一个配置类，并再次进行属性绑定(``Binder#bindOrCreate``)，并将新绑定得到的Bean的属性重新赋值到原Bean中即可
