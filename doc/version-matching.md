# Version matching

## Why?
To populate the objects, Red-and-Blue Extension relies on the internal object factories from the EqualsVerifier library.
This API, though open-source, is internal to EqualsVerifier - which means it might change in a new version, and Red-and-Blue
has to adapt.

Hence, newer versions of Red-and-Blue extension are not compatible with older versions of EqualsVerifier, and vice versa.


## Versions
| Red-and-Blue | Equals Verifier |
|--------------|-----------------|
|   1.1.0      |   < 3.17        |
|   1.2.0      |  >= 3.17        | 
|   1.3.0      |  >= 3.19        |
|   1.3.2      |  >= 3.19 < 4.0  |
|   1.4.0      |  >= 4.0 < 4.2.5 |
|   1.4.2.5    |  >= 4.2.5       |


## Pinning the exact version of EqualsVerifier

Red-and-Blue brings the latest version of EqualsVerifier as a transient dependency. This adds a bit of non-determinism to
your builds when a new version of EqualsVerifier is released. Though it shouldn't normally be a problem, if you want your
builds to be 100% deterministic, you can pin the dependency version like below: 

Gradle
```groovy
dependencies {
    testImplementation 'io.github.neboskreb:red-and-blue:1.4.0'
    // pin the version of EqualsVerifier:
    testImplementation 'nl.jqno.equalsverifier:equalsverifier:4.2.2'
    ...
}
```

If in your project you already used EqualsVerifier, you probably have it pinned already.