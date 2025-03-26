# Red and Blue extension
JUnit 5 extension for easy injection of Red and Blue objects

This extension helps keeping the [data integrity](doc/problem.md) during the code evolutions. There are well-identified [scenarios of evolution](doc/bug-scenarios.md)
leading to the data corruption, and corresponding mitigations which must be applied. 


# Solution

The code which copies/transforms the data between objects must be unit tested. But using arbitrary or random data might not always detect the issue.
For 100% detection the data known as "Red" and "Blue" objects should be used.

## What are "Red" and "Blue" objects?

Two objects of the same class having in each of their fields a value non-equal to the value of this field in the other object,
are known as [Red and Blue objects](doc/red-and-blue.md) of that class. 

## What they are used for?

They are essential for checking the correctness and completeness of data copying, e.g. in a mapper between a DTO and entity. 
Typical areas of failure and testing strategies are described in chapter [What should be tested](doc/what-to-test.md).

While you can create such objects manually, letting this extension inject them for you is much easier.


# Setup

Maven
```xml
<dependency>
    <groupId>io.github.neboskreb</groupId>
    <artifactId>red-and-blue</artifactId>
    <version>1.3.1</version>
    <scope>test</scope>
</dependency>
```

Gradle
```groovy
dependencies {
    testImplementation 'io.github.neboskreb:red-and-blue:1.3.1'
}
```

## Version matching
Red-and-Blue Extension relies on internal object factories from [EqualsVerifier library](https://github.com/jqno/equalsverifier) to populate the objects. This internal API might change between the versions.
Hence, newer versions of Red-and-Blue extension are not compatible with older versions of EqualsVerifier, and vice versa.

| Red-and-Blue | Equals Verifier |
|--------------|-----------------|
|   1.1.0      |   < 3.17        |
|   1.2.0      |  >= 3.17        | 
|   1.3.0      |  >= 3.19        |



# Usage

## Applying the extension

Enable the extension on your JUnit5 test class:
```java
@ExtendWith(RedAndBlueExtension.class)
class MyClassTest {
    ...
}
```

## Injection

Inject Red and Blue instances into your test method as parameters:
```java
@ExtendWith(RedAndBlueExtension.class)
class MyClassTest {
    @Test
    void testParameterInjection(@RedInstance MyClass red, @BlueInstance MyClass blue) {
        ...
    }
}
```

Or inject into fields:
```java
@ExtendWith(RedAndBlueExtension.class)
class MyClassTest {
    
    @RedInstance
    private MyClass red;
    
    @BlueInstance
    private MyClass blue;
    ...
}
```

## Prefabricating 

In cases when automatic creation of instances is not possible (e.g., when the class contains circular reference to itself), you can provide
prefabricated instances - one for Red and one for Blue:
```java
public class MyComplexClass {
    private TrickyClass tricky;
    ...
}

// Assume that, for some reason, TrickyClass cannot be created automatically.
// Provide prefabricated instances:

@ExtendWith(RedAndBlueExtension.class)
class MyComplexClassTest {
    
    // Provide the instance directly:
    @PrefabRed
    private TrickyClass trickyRed = new TrickyClass(...);
    
    // Or via a factory method:
    @PrefabBlue
    private static TrickyClass trickyProvider() {
        return new TrickyClass(...);
    }

    // Now you can inject MyComplexClass as usual:
    @Test
    void testParameterInjection(@RedInstance MyComplexClass red, @BlueInstance MyComplexClass blue) {
        ...
    }
}
```

## Tests structure

Inheriting fields and methods from a base class is supported:
```java
abstract class MyClassTestBase {
    @RedInstance
    protected MyClass red;
    @BlueInstance
    protected MyClass blue;
}

@ExtendWith(RedAndBlueExtension.class)
class MyClassTest extends MyClassTestBase {
    @Test
    void testBaseParameterInjection() {
        // You can use the inherited fields `red` and `blue` here
        assertEquals(..., red);
        assertEquals(..., blue);
    }
}
```


# Contribution
Pull requests are welcome! If you plan to open one, please first create an issue where you describe the problem/gap your contribution closes, and tag the keeper(s) of this repo so they could get back to you with help.
