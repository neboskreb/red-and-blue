package com.github.neboskreb.red.and.blue.mapper;

import com.github.neboskreb.red.and.blue.dto.EntityDTO;
import com.github.neboskreb.red.and.blue.model.ImmutableEntity;

public class EntityMapper {
    public EntityDTO toEntityDTO(ImmutableEntity entity) {
        EntityDTO result = new EntityDTO();
        result.setFieldString(entity.getFieldString()); // <-- TODO [1] Comment this line out to simulate a bug
        result.setFieldInteger(entity.getFieldInteger());

        return result;
    }

    public ImmutableEntity toEntity(EntityDTO dto) {
        return new ImmutableEntity.Builder()
                .setFieldString(dto.getFieldString())
                .setFieldInteger(dto.getFieldInteger()) // <-- TODO [2] Comment this line out to simulate a bug
                .build();
    }
}
