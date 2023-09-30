import com.github.neboskreb.red.and.blue.RedAndBlueFactoryExtension;
import com.github.neboskreb.red.and.blue.annotation.BlueInstance;
import com.github.neboskreb.red.and.blue.annotation.RedInstance;
import com.github.neboskreb.red.and.blue.mapper.EntityMapper;
import com.github.neboskreb.red.and.blue.model.EncryptedEntity;
import com.github.neboskreb.red.and.blue.model.ImmutableEntity;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@ExtendWith({RedAndBlueFactoryExtension.class})
public class IdentityTest {
    @RedInstance EncryptedEntity left;
    @RedInstance EncryptedEntity right;
    @BlueInstance EncryptedEntity other;

    @Test
    void testEqual() {
        // GIVEN
        assertNotSame(left, right);
        left.setEncryptedContent(other.getEncryptedContent());
        assertNotEquals(left.getEncryptedContent(), right.getEncryptedContent(), "The encrypted content must be different");

        // THEN
        assertEquals(left, right);
    }

    @Test
    void testUnequal() {
        // GIVEN
        left.setEncryptedContent(other.getEncryptedContent());
        assertEquals(left.getEncryptedContent(), other.getEncryptedContent(), "The encrypted content could be same");

        // THEN
        assertNotEquals(left, other);
    }
}
