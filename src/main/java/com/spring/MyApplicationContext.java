package com.spring;

import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.File;
import java.lang.reflect.Field;
import java.net.URL;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Data
@NoArgsConstructor
public class MyApplicationContext {

    private Class config;

    //单例池
    private ConcurrentHashMap<String, Object> singletonObjects = new ConcurrentHashMap<>();
    private ConcurrentHashMap<String, BeanDefinition> beanDefinitionMap = new ConcurrentHashMap<>();

    public MyApplicationContext(Class config) {
        this.config = config;
        //TODO 1.解析配置类
        //@ComponentScan("com.zhong.service") --扫描路径--扫描
        scan(config);
        //TODO 2.创建单例bean对象
        for (Map.Entry<String, BeanDefinition> entry : beanDefinitionMap.entrySet()) {
            if (entry.getValue().getScope().equals("singleton")) {
                //创建单例bean对象
                Object bean = createBean(entry.getValue());
                singletonObjects.put(entry.getKey(), bean);
            }
        }

    }

    public Object createBean(BeanDefinition beanDefinition) {
        Class aClass = beanDefinition.getAClass();
        Object instance = null;
        try {
            instance = aClass.getDeclaredConstructor().newInstance();
            //依赖注入 对属性进行赋值
            for (Field declaredField : aClass.getDeclaredFields()) {
                if (declaredField.isAnnotationPresent(Autowired.class)) {
                    //给这个对象中对这个属性去赋值
                    Object bean = getBean(declaredField.getName());
                    //开启权限
                    declaredField.setAccessible(true);
                    declaredField.set(instance, bean);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return instance;
    }

    private void scan(Class config) {
        ComponentScan componentScanAnnotation = (ComponentScan) config.getDeclaredAnnotation(ComponentScan.class);
        //路径 com.zhong.service
        String path = componentScanAnnotation.value();
        //扫描
        ClassLoader classLoader = MyApplicationContext.class.getClassLoader();//应用类加载器
        URL resource = classLoader.getResource(path.replace(".", "/"));
        File file = new File(resource.getFile());
        if (file.isDirectory()) {
            for (File listFile : file.listFiles()) {
                // /Users/pengzhong/IdeaProjects/sourceCode/target/classes/com/zhong/service/UserService.class
                String fileName = listFile.getAbsolutePath();
                if (fileName.endsWith(".class")) {
                    String className = fileName.substring(fileName.indexOf("com"), fileName.indexOf(".class"));
                    //com.zhong.service.UserService
                    className = className.replace("/", ".");
                    try {
                        Class<?> aClass = classLoader.loadClass(className);
                        //此类上面是否存在此注解
                        if (aClass.isAnnotationPresent(Component.class)) {
                            //如果有component注解，则此类是一个bean
                            //判断当前bean是单例bean还是prototype的bean
                            //BeanDefinition
                            BeanDefinition beanDefinition = new BeanDefinition();
                            beanDefinition.setAClass(aClass);
                            Component componentAnnotation = aClass.getAnnotation(Component.class);
                            String beanName = componentAnnotation.value();
                            if (aClass.isAnnotationPresent(Scope.class)) {
                                Scope scopeAnnotation = aClass.getDeclaredAnnotation(Scope.class);
                                beanDefinition.setScope(scopeAnnotation.value());
                            } else {
                                beanDefinition.setScope("singleton");
                            }
                            beanDefinitionMap.put(beanName, beanDefinition);
                        }
                    } catch (ClassNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
    }

    public Object getBean(String beanName) {
        if (beanDefinitionMap.containsKey(beanName)) {
            BeanDefinition beanDefinition = beanDefinitionMap.get(beanName);
            if ("singleton".equals(beanDefinition.getScope())) {
                return singletonObjects.get(beanName);
            } else {
                //创建bean对象
                return createBean(beanDefinition);
            }
        } else {
            throw new RuntimeException("不存在这个bean：" + beanName);
        }
    }

}
