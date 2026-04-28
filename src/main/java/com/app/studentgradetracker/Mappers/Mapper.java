package com.app.studentgradetracker.Mappers;

import com.app.studentgradetracker.model.Student;

import java.sql.ResultSet;
import java.sql.SQLException;

public interface Mapper<Dto, Entity> {

    Entity mapTo(Dto dto);
    Dto mapFrom(Entity entity);
}
