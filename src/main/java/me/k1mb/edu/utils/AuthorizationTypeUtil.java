package me.k1mb.edu.utils;

import lombok.experimental.UtilityClass;

@UtilityClass
public class AuthorizationTypeUtil {
    public final String ROLE_ADMIN = "hasRole('ROLE_ADMIN')";
    public final String ROLE_USER = "hasRole('ROLE_USER')";

    public final String CHECK_COURSE_AUTHOR = "authentication.name == @courseServiceImpl.checkAuthor(#course_id).toString()";
    public final String IS_AUTHOR = "authentication.name == #course.authorId.toString()";

    public final String AND = " and ";
    public final String OR = " or ";
}
