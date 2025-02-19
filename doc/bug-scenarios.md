# Scenarios of failure

Below are the known scenarios how evolutions of your code, if not mitigated, result in loss of data or degradation of performance.

Module [examples](/examples) demonstrates these scenarios. The code contains `TODO [x]` items which, when commented or uncommented, simulate the evolution 
leading to a bug. After un-/commenting the line, rerun the unit tests to see which will detect the bug.

------------------------------------------------------------------------------------------------------

## Incomplete copy constructor/builder

**Risk:** data loss

**Scenario:** When a new field is added to a class which has a copy constructor/copy builder without having the latter updated, this field will not be copied.

**Demonstration:** Uncomment line `TODO [3]` to demonstrate this bug.

**Mitigation:** build the copy and test it against the original field-by-field
```java
ImmutableEntity copy = new ImmutableEntity.Builder(original).build();
assertThat(copy).as("Copy builder is incomplete").usingRecursiveComparison().isEqualTo(original);
```

**Example:** [CopyBuilderTest](/examples/src/test/java/com/github/neboskreb/red/and/blue/example/CopyBuilderTest.java)

------------------------------------------------------------------------------------------------------

## Incomplete mapper

**Risk:** data loss

**Scenario:** When a generated mapper is not defined properly, or a manually written mapper is incomplete, some fields will not be copied.

**Demonstration:** Comment line `TODO [1]` and/or `TODO [2]` to demonstrate this bug.

**Mitigation:** use reflective mapping to obtain the copy and test it against the original field-by-field
```java
Entity copy = mapper.toEntity(mapper.toDTO(entity));
assertThat(copy).as("Mapper is incomplete").usingRecursiveComparison().isEqualTo(entity);
```

**Example:** [MapperTest](/examples/src/test/java/com/github/neboskreb/red/and/blue/example/MapperTest.java)

------------------------------------------------------------------------------------------------------

## Incomplete DTO / DAO

**Risk:** data loss

**Scenario:** When a field is added to the Entity object but the DTO/DAO is not updated accordingly, this field will not be transferred/stored.

**Demonstration:** Comment line `TODO [6]` to demonstrate this bug.

**Mitigation:** use reflective mapping to obtain the copy and test it against the original field-by-field
```java
Entity copy = mapper.toEntity(mapper.toDTO(entity));
assertThat(copy).as("DTO is incomplete").usingRecursiveComparison().isEqualTo(entity);
```

**Example:** [MapperTest](/examples/src/test/java/com/github/neboskreb/red/and/blue/example/MapperTest.java)

------------------------------------------------------------------------------------------------------

## Excessive DTO 

**Risk:** performance degradation

**Scenario:** In a client-server setting, when a field is removed from the Entity object on the client's end but the DTO is not updated accordingly,
the server will still populate this field, effectively wasting the CPU cycles and the bandwidth.

**Demonstration:** Uncomment line `TODO [5]` to demonstrate this bug.

**Mitigation:** use reflective mapping to obtain the copy of DTO and test it against the original DTO field-by-field
```java
EntityDTO dtoCopy = mapper.toDTO(mapper.toEntity(dto));
assertThat(dtoCopy).as("DTO is excessive").usingRecursiveComparison().isEqualTo(dto);
```

**Example:** [MapperTest](/examples/src/test/java/com/github/neboskreb/red/and/blue/example/MapperTest.java)
