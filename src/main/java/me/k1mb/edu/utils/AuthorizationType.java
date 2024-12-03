package me.k1mb.edu.utils;

public class AuthorizationType {
    public static final String ROLE_ADMIN = "hasRole('ROLE_ADMIN')";
    public static final String ROLE_USER = "hasRole('ROLE_USER')";

    public static final String CHECK_COURSE_AUTHOR = "authentication.name == @courseServiceImpl.checkAuthor(#course_id).toString()";
    public static final String IS_AUTHOR = "authentication.name == #course.authorId.toString()";

    public static final String AND = " and ";
    public static final String OR = " or ";
}
