package com.app.studentgradetracker.Mappers;

public interface Mapper<Dto, Entity> {
    Entity mapTo(Dto dto);
    Dto mapFrom(Entity entity);
}
