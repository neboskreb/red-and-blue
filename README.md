# red-and-blue-factory-extension
JUnit 5 extension for easy injection of Red and Blue objects


# Version matching
Red-and-Blue Extension relies on internal object factories from [EqualsVerifier library](https://github.com/jqno/equalsverifier) to populate the objects. This internal API might change between the versions.
Hence, newer versions of Red-and-Blue extension are not compatible with older versions of EqualsVerifier, and vice versa.

| Red-and-Blue | Equals Verifier |
|--------------|-----------------|
|   1.1.0      |   < 3.17        |
|   1.2.0      |  >= 3.17        | 