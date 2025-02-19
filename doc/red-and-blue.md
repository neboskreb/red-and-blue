
# What are "Red" and "Blue" objects?

Objects known as "Red" and "Blue" are two instances of the same class which have each field different from the same field in their counterpart. 

E.g. if class contains two String fields and one int field, like this,
```java
public class A {
    private String first;
    private String second;
    private int third;
}
```
then the red and blue instances could be like these:
<pre><code>
|    red               |   blue               |
|----------------------|----------------------|
| {                    |   {                  |
|   "first": "one",    |     "first": "two",  |
|   "second": "one",   |     "second": "two", |
|   "third": 1000      |     "third": 2000    |
| }                    |   }                  |
</code></pre>

_NOTE that fields `first` and `second` are not required to differ from each other in the same instance._



