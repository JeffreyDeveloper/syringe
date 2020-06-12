# Syringe Cache Adapters
Syringe lets you manage the Cache (for the Java Reflection API), although there are also some ready-made adapters.

## Caffeine Cache Adapter
There's an adapter for Caffeine, a high performance caching library for Java 8.
### How to Use:
Just add a few options when building the Injector
```java
Injector injector = InjectorFactory.create(
    InjectorOptions.builder()
        .cacheAdapterBuilder(new CaffeineCacheAdapterBuilder(TimeUnit.SECONDS, 1))
        .build(),
    modules
);
```
You can change the time in which the cache objects will be cleared
