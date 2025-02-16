package com.github.neboskreb.red.and.blue.example;

import com.github.neboskreb.red.and.blue.RedAndBlueExtension;
import com.github.neboskreb.red.and.blue.annotation.BlueInstance;
import com.github.neboskreb.red.and.blue.annotation.RedInstance;
import com.github.neboskreb.red.and.blue.model.ImmutableEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({RedAndBlueExtension.class})
public class CopyBuilderTest {

    /**
     * Protection against: Data loss.
     * <p>
     * This test guards against incompleteness of the copy builder, which can happen when a new field added to
     * the Entity but the builder was not updated.
     * <p>
     * To simulate this bug, use the to-do items [3] or [4]
     */
    @Test
    void testCopyBuilderIsComplete(@RedInstance ImmutableEntity red, @BlueInstance ImmutableEntity blue) {
        // WHEN
        ImmutableEntity redCopy = new ImmutableEntity.Builder(red).build();
        ImmutableEntity blueCopy = new ImmutableEntity.Builder(blue).build();

        // THEN
        assertThat(redCopy).as("Copy constructor incomplete").usingRecursiveComparison().isEqualTo(red);
        assertThat(blueCopy).as("Copy constructor incomplete").usingRecursiveComparison().isEqualTo(blue);
    }
}
