[![Codacy Badge](https://app.codacy.com/project/badge/Grade/7763e3b2712d4201b28e2b8034a7fd7d)](https://www.codacy.com/manual/iYushu/syringe?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=yusshu/syringe&amp;utm_campaign=Badge_Grade)
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
`Linker` is like [Guice](https://github.com/google/guice)'s `Binder`
The way to configure links is very similar to the way to configure bindings in  [Guice](https://github.com/google/guice)
```java
import me.yushust.inject.bind.Module;
import me.yushust.inject.bind.Binder;

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
We can also make generic links using `Token`
```java
@Override
public void configure(Binder binder) {
    // The {} are important!
    binder.bind(new Token<List<String>>() {}).toInstance(new ArrayList<>());
    // The links to instance are Singleton by default
}
```
It is also possible to make links with annotations (called Qualifiers)
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

## Notes
Syringe does not detect cycle dependencies. So, you should avoid making confusing links.

### Download
```yml
# Clone the repository
git clone https://github.com/yusshu/syringe
# Move into the folder
cd syringe
# Install syringe using Maven
mvn clean install 
```
### Maven dependency
```xml
<dependency>
    <groupId>me.yushust.inject</groupId>
    <artifactId>syringe-core</artifactId>
    <version>0.1.1</version>
</dependency>
```
