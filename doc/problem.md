
# How the data loss occurs

In the beginning, all works well and no data is lost. But no code is carved in stone - after written, it evolves and mutates as the project develops. 
Sometimes a seemingly benign change to a data object, like an added field, can result in data loss or performance degradation. Not every such case is
easily detected by the unit tests.

One infamous example is the `equals` method: adding a new field to the class without having the `equals` updated is notoriously
difficult to catch. Even if your test manually checks each field it will stay green - thus failing to detect the bug.
This happens because your test simply doesn't know that something was added, hence doesn't check it!
For this reason, libraries like EqualsVerifier were created to automatically find and check fields.

# Path to the pitfall

But `equals` is not the only scenario. Other areas of risk include:
* builders
* mappers
* copiers
* mutator methods
* etc.

All of the above can result in a data loss if the bug is not caught in time.

**Data loss is not a joke. You want to be protected against it.**

# Solution

Cover your mappers/builders/etc with unit tests.

For this you will need two instances of each class under test. Yes, two - "Red" and "Blue".

# Why one simple `equals` check is not enough?

Because `equals` only tests that fields are equal but doesn't assure that the field has been copied. If in your test the original's
field had default value (i.e., 0 for `int` or `false` for `boolean`) the `equals` doesn't see a fault because the copy's field is
initialized to the same default value, so they match.

To catch this bug you need to check the filed **two times** with different values: this way either the first or second check will
catch the discrepancy (because a type can have only one default value).

Here is the [example](faulty-example.md) demonstrating how the single check fails but the dual check wins.

**One check is not enough. You need two.**
