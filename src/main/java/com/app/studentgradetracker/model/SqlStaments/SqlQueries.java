package com.app.studentgradetracker.model.SqlStaments;

public final class SqlQueries {

    private SqlQueries(){}

    public static final class Student{
        private Student() {}
        public static final String FIND_ALL =
                "SELECT * FROM students ORDER BY name";

        public static final String FIND_BY_ID =
                "SELECT * FROM students WHERE id = ?";

        public static final String FIND_BY_EMAIL =
                "SELECT * FROM students WHERE email = ?";
        public static final String DELETE_BY_ID =
                "DELETE FROM students WHERE id = ?";

        public static final String UPDATE =
                "UPDATE students SET name = ?, email = ? WHERE is = ?";
        public static final String INSERT =
                "INSERT INTO students (name, email) VALUES (?, ?)";

        public static final String EXISTS_BY_EMAIL =
                "SELECT COUNT(*) FROM students WHERE email = ?";
    }
}
