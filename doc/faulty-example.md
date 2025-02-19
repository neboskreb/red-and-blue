# Example of code evolution

Assume you have class A, and a new field `newField` is added. But due to a human mistake, only the normal constructor is updated but the copy constructor is not:
```java
public class A {
    private int     oldField;
    private boolean newField;

    public A(int oldField, boolean newField) {
        this.oldField = oldField;
        this.newField = newField;
    }

    /** Copy constructor. */
    public A(A original) {
        this.oldField = original.oldField;
        // Note that `newField` is not copied. It's a bug!
    }

    @Override
    public final boolean equals(Object other) {
        if (this == other) return true;
        if (!(other instanceof A otherA)) return false;

        return oldField == otherA.oldField && newField == otherA.newField;
    }
}
```

If your test has only one check, there is a big risk such bug will go undetected:
```java
@Test
void incorrect() {
    // GIVEN
    A original = new A(1, false);

    // WHEN
    A copy = new A(original);

    // THEN
    assertEquals(original, copy);
}
```

You need two checks to reliably detect this bug:
```java
@Test
void correct() {
    // GIVEN
    A originalRed = new A(1, false);
    A originalBlue = new A(2, true);

    // WHEN
    A copyRed = new A(originalRed);
    A copyBlue = new A(originalBlue);

    // THEN
    assertEquals(originalRed, copyRed);
    assertEquals(originalBlue, copyBlue);
}
```

So you need two objects having every field different from its counterpart. Such two objects are known as "Red" and "Blue" objects. Each field in Red object is non-equal to this field in Blue object.

This extension offers an easy way of creating the Red and Blue objects of arbitrary class.
