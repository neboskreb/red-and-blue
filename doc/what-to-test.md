------

**Data loss is not a joke. Prevent it by unit tests.**

------


# What should be tested?

Have a test for any code which involves copying/transforming the object's content, e.g. builders/mappers/copiers/mutators/etc. You want to test not the
DAO/DTO/Entity object itself but the Builder/Mapper/Copier which copies the fields of your object.

# How to test

## Builders

E.g., if an immutable class offers a copy builder it can be tested like this:
```java
@Test
void testCopyBuilder(@RedInstance ImmutableEntity red, @BlueInstance ImmutableEntity blue) {
    // WHEN
    ImmutableEntity redCopy = new ImmutableEntity.Builder(red).build();
    ImmutableEntity blueCopy = new ImmutableEntity.Builder(blue).build();

    // THEN
    assertThat(redCopy).usingRecursiveComparison().isEqualTo(red);
    assertThat(blueCopy).usingRecursiveComparison().isEqualTo(blue);
}
```

## Copy constructors

Testing copy constructors is quite similar to testing copy builders.


## Mutators

If instead of builder your immutable class offers mutator methods, they can be tested same way:
```java
@Test
void testMutator(@RedInstance ImmutableEntity red, @BlueInstance ImmutableEntity blue) {
    // WHEN field `counter` is mutated
    ImmutableEntity incrementedRed = red.incrementCounter(1);
    ImmutableEntity incrementedBlue = blue.incrementCounter(1);

    // THEN assert all other fields were copied properly 
    assertThat(incrementedRed).usingRecursiveComparison().ignoringFields("counter").isEqualTo(red);
    assertThat(incrementedBlue).usingRecursiveComparison().ignoringFields("counter").isEqualTo(blue);
}
```

## Mappers

Testing mappers can be tricky because usually the structure of the result class differs from the original class (hence the whole point to have a mapper). The easiest way is to test it reflectively, i.e. A mapped into B mapped back into A should match the original:
```java
@Test
void testMapper(@RedInstance Entity red, @BlueInstance Entity blue) {
    // WHEN
    Entity redCopy = mapper.toEntity(mapper.toDTO(red));
    Entity blueCopy = mapper.toEntity(mapper.toDTO(blue));

    // THEN
    assertThat(redCopy).usingRecursiveComparison().isEqualTo(red);
    assertThat(blueCopy).usingRecursiveComparison().isEqualTo(blue);
}
```
