package com.github.neboskreb.red.and.blue.example;

import static org.assertj.core.api.Assertions.assertThat;

import com.github.neboskreb.red.and.blue.RedAndBlueExtension;
import com.github.neboskreb.red.and.blue.annotation.BlueInstance;
import com.github.neboskreb.red.and.blue.annotation.RedInstance;
import com.github.neboskreb.red.and.blue.dto.EntityDTO;
import com.google.gson.Gson;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

@ExtendWith({RedAndBlueExtension.class})
public class SerializationTest {

    private final Gson gson = new Gson();

    // TODO description
    /**
     * Protection against: Data loss.
     * <p>
     * This test guards against incompleteness of DTO and/or mapper, which could happen when a new field added to
     * the Entity but the DTO and/or mapper was not updated. This can happen when you write mappers manually.
     * <p>
     * To simulate this bug, use the to-do items [1], [2] or [6]
     */
    @Test
    void testTransition(@RedInstance EntityDTO redDto, @BlueInstance EntityDTO blueDto) {
        // WHEN
        EntityDTO redCopy = gson.fromJson(gson.toJson(redDto), EntityDTO.class);
        EntityDTO blueCopy = gson.fromJson(gson.toJson(blueDto), EntityDTO.class);

        // THEN
        assertThat(redCopy).as("Incomplete/Incorrect serialization").usingRecursiveComparison().isEqualTo(redDto);
        assertThat(blueCopy).as("Incomplete/Incorrect serialization").usingRecursiveComparison().isEqualTo(blueDto);
    }
}
