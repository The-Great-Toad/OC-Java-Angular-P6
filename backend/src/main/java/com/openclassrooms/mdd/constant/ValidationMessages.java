package com.openclassrooms.mdd.constant;

public final class ValidationMessages {

    // Generic messages
    public static final String REQUIRED_FIELD = "Required field";
    public static final String INVALID_FORMAT = "Invalid format";

    // Specific messages
    public static final String EMAIL_ALREADY_USED = "Email already used";
    public static final String TITLE_SIZE = "Title must be between 3 and 255 characters";
    public static final String POST_CONTENT_SIZE = "Content must be above 10 characters minimun";
    public static final String COMMENT_CONTENT_SIZE = "Content must be between 1 and 2000 characters";

    // Authentication messages
    public static final String INVALID_CREDENTIALS = "Invalid credentials";
    public static final String EMAIL_ALREADY_EXISTS = "Email already exists";
    public static final String PASSWORD_TOO_SHORT = "Password must be at least 8 characters";
    public static final String PASSWORD_INVALID_FORMAT = "Password must contain at least one digit, one lowercase, one uppercase, and one special character";
    public static final String USER_NOT_FOUND = "User not found";

    private ValidationMessages() {}
}