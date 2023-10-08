import com.github.neboskreb.red.and.blue.RedAndBlueExtension;
import com.github.neboskreb.red.and.blue.annotation.BlueInstance;
import com.github.neboskreb.red.and.blue.annotation.RedInstance;
import com.github.neboskreb.red.and.blue.dto.EntityDTO;
import com.github.neboskreb.red.and.blue.mapper.EntityMapper;
import com.github.neboskreb.red.and.blue.model.ImmutableEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith({RedAndBlueExtension.class})
public class MapperTest {

    private final EntityMapper mapper = new EntityMapper();

    /**
     * Protection against: Data loss.
     * <p>
     * This test guards against incompleteness of DTO and/or mapper, which could happen when a new field added to
     * the Entity but the DTO and/or mapper was not updated. This can happen when you write mappers manually.
     * <p>
     * To simulate this bug, use the to-do items [1], [2] or [6]
     */
    @Test
    void testDTOisComplete(@RedInstance ImmutableEntity red, @BlueInstance ImmutableEntity blue) {
        // WHEN
        ImmutableEntity redCopy = mapper.toEntity(mapper.toEntityDTO(red));
        ImmutableEntity blueCopy = mapper.toEntity(mapper.toEntityDTO(blue));

        // THEN
        assertThat(redCopy).as("DTO or mapper incomplete").isEqualToComparingFieldByFieldRecursively(red);
        assertThat(blueCopy).as("DTO or mapper incomplete").isEqualToComparingFieldByFieldRecursively(blue);
    }

    /**
     * Protection against: Performance degradation.
     * <p>
     * This test guards against atavism field in DTO after the original field in the Entity was removed but the DTO
     * was not clean up. This can happen when you use generated mappers (e.g. Mapstruct) with the relaxed strictness.
     * Such an excessive DTO will require the other end to populate and transfer the fields which you're not using.
     * <p>
     * To simulate this bug, use the to-do item [5]
     */
    @Test
    void testDTOisNotExcessive(@RedInstance EntityDTO redDto, @BlueInstance EntityDTO blueDto) {
        // WHEN
        EntityDTO redDtoCopy = mapper.toEntityDTO(mapper.toEntity(redDto));
        EntityDTO blueDtoCopy = mapper.toEntityDTO(mapper.toEntity(blueDto));

        // THEN
        assertThat(redDtoCopy).as("DTO excessive").isEqualToComparingFieldByFieldRecursively(redDto);
        assertThat(blueDtoCopy).as("DTO excessive").isEqualToComparingFieldByFieldRecursively(blueDto);
    }
}
