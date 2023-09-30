package com.github.neboskreb.red.and.blue.model;

import java.util.Objects;
import java.util.UUID;

/**
 * A piece of sensitive data owned by other module stored in encrypted form.
 * Our module cannot see inside the data, but the owner module gives us the entity's uuid so we could manage it.
 * <p>
 * For {@code equals} and {@code hashCode} including the {@code encryptedContent} makes little sense because it's different
 * every time. As long as our module concerns, the equality is based solely on identity.
 * <p>
 * Also assume we cannot mark {@code encryptedContent} transient, e.g. because we do need to store it in a table.
 */
public class EncryptedEntity {
    private UUID uuid;
    private byte[] encryptedContent;

    public void setUuid(UUID uuid) {
        this.uuid = uuid;
    }

    public void setEncryptedContent(byte[] encryptedContent) {
        this.encryptedContent = encryptedContent;
    }

    public UUID getUuid() {
        return uuid;
    }

    public byte[] getEncryptedContent() {
        return encryptedContent;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EncryptedEntity that = (EncryptedEntity) o;
        return uuid.equals(that.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }
}
