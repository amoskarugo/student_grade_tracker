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



    public static final class Grade {
        private Grade() {}
        public static final String FIND_BY_STUDENT  = "SELECT * FROM grades WHERE student_id = ? ORDER BY semester";
        public static final String FIND_BY_SEMESTER = "SELECT * FROM grades WHERE student_id = ? AND semester = ?";
        public static final String EXISTS           =
                "SELECT COUNT(*) FROM grades WHERE student_id = ? AND course_id = ? AND semester = ?";
        public static final String INSERT           = "INSERT INTO grades (student_id, course_id, grade, semester) VALUES (?, ?, ?, ?)";
        public static final String DELETE           = "DELETE FROM grades WHERE id = ?";
        public static final String FIND_WITH_DETAILS =
                """
                SELECT g.id, g.grade, g.semester,
                       s.name  AS student_name,
                       c.name  AS course_name,
                       c.code  AS course_code,
                       c.credits
                FROM   grades   g
                JOIN   students s ON s.id = g.student_id
                JOIN   courses  c ON c.id = g.course_id
                WHERE  g.student_id = ?
                ORDER  BY g.semester, c.name
                """;
        public static final String CALCULATE_GPA =
                """
                SELECT   g.semester,
                         s.name                               AS student_name,
                         ROUND(SUM(g.grade * c.credits) /
                               SUM(c.credits), 2)             AS gpa,
                         COUNT(*)                             AS total_courses
                FROM     grades   g
                JOIN     students s ON s.id = g.student_id
                JOIN     courses  c ON c.id = g.course_id
                WHERE    g.student_id = ?
                GROUP BY g.semester, s.name
                ORDER BY g.semester
                """;
    }

}
