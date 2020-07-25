# Syringe Dependency Injection Framework
A fast and (very) lightweight Dependency Injection Framework for Java

## How to use
Use `InjectorFactory.create` to create an Injector.
Example:
```java
Injector injector = InjectorFactory.create(modules);
```
Where "modules" is a `Module` array or `Iterable<Module>`
And get the instance of a class by doing this:
```java
Foo foo = injector.getInstance(Foo.class);
```
Or injecting dependencies to already instantiated objects
```java
Foo foo = new Foo();
injector.injectMembers(foo);
```

### Create a module
Creating a module is as simple as creating a class that extends `Module` and override `configure` method
The way to configure bindings is very similar to the way to configure bindings in [Guice](https://github.com/google/guice)
```java
import team.unnamed.inject.bind.Module;
import team.unnamed.inject.bind.Binder;

public class MySimpleModule implements Module {

    @Override
    public void configure(Binder binder) {

    }

}
```
So, we will make a binding, from Foo to Bar
 *Foo must implement Bar*
```java
@Override
public void configure(Binder binder) {
    binder.bind(Foo.class).to(Bar.class);
}
```
We can also make generic bindings using `TypeReference`
```java
@Override
public void configure(Binder binder) {
    // The {} are important!
    binder.bind(new TypeReference<List<String>>() {}).toInstance(new ArrayList<>());
    // The links to instance are Singleton by default
}
```
It is also possible to make bindings with annotations (called Qualifiers)
```java
@Override
public void configure(Binder binder) {
    binder.bind(Foo.class)
        .qualified(FooAnnotation.class)
        .to(Bar.class);
}
```
So the injection would look like this:
```java
@Inject @FooAnnotation
private Foo foo;
```
*(FooAnnotation must be annotated with `@BindingAnnotation` or `@Qualifier`)*
Something like this:
```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@BindingAnnotation
public @interface FooAnnotation {

}
```

### Maven repository
```xml
<repository>
    <id>unnamed-releases</id>
    <url>https://repo.unnamed.team/repository/unnamed-releases/</url>
</repository>
```
### Maven dependency
```xml
<dependency>
    <groupId>team.unnamed.inject</groupId>
    <artifactId>syringe-core</artifactId>
    <version>0.3.0</version>
</dependency>
```
