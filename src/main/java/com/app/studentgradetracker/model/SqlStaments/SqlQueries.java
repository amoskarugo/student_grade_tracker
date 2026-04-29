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
                "UPDATE students SET name = ?, email = ? WHERE id = ?";
        public static final String INSERT =
                "INSERT INTO students (name, email) VALUES (?, ?)";

        public static final String EXISTS_BY_EMAIL =
                "SELECT COUNT(*) FROM students WHERE email = ?";
    }

    public static final class Course {
        private Course() {}
        public static final String FIND_ALL         = "SELECT * FROM courses ORDER BY name";
        public static final String FIND_BY_ID       = "SELECT * FROM courses WHERE id = ?";
        public static final String FIND_BY_DEPT     = "SELECT * FROM courses WHERE department = ?";
        public static final String EXISTS_BY_CODE   = "SELECT COUNT(*) FROM courses WHERE code = ?";
        public static final String INSERT           = "INSERT INTO courses (name, code, credits, department) VALUES (?, ?, ?, ?)";
        public static final String UPDATE           = "UPDATE courses SET name = ?, code = ?, credits = ?, department = ? WHERE id = ?";
        public static final String DELETE           = "DELETE FROM courses WHERE id = ?";
    }

}
