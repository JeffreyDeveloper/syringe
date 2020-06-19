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
    public void configure(Linker binder) {

    }

}
```
So, we will make a binding, from Foo to Bar
 *Foo must implement Bar*
```java
@Override
public void configure(Linker binder) {
    binder.binding(Foo.class).to(Bar.class);
}
```
We can also make generic links using `Token`
```java
@Override
public void configure(Linker binder) {
    // The {} are important!
    binder.binding(new Token<List<String>>() {}).toInstance(new ArrayList<>());
    // The links to instance are Singleton by default
}
```
It is also possible to make links with annotations (called Qualifiers)
```java
@Override
public void configure(Linker binder) {
    binder.binding(Foo.class)
        .qualified(FooAnnotation.class)
        .to(Bar.class);
}
```
So the injection would look like this:
```java
@Inject @FooAnnotation
private Foo foo;
```
*(FooAnnotation must be annotated with `@LinkingAnnotation`)*
Something like this:
```java
@Retention(RetentionPolicy.RUNTIME)
@Target({ ElementType.FIELD, ElementType.PARAMETER })
@LinkingAnnotation
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
### Maven dependency:
```xml
<dependency>
    <groupId>me.yushust.inject</groupId>
    <artifactId>syringe-core</artifactId>
    <version>0.1.0</version>
</dependency>
```
