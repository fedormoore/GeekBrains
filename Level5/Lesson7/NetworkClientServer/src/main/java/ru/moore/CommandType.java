package ru.moore;

public enum CommandType {
    REG_USER,
    REG_OK,
    REG_ERROR,

    AUTH_USER,
    AUTH_OK,
    AUTH_ERROR,

    GET_DIR,
    SET_DIR,

    CREATE_FOLDER,
    RENAME_FOLDER,
    DELETE_FOLDER,

    GET_FILES,
    SET_FILES,

    FILE_DOWN,
    DELETE_FILE,
    RENAME_FILE,
    FILE_UPLOADER_RESULT, FILE_UPLOADER
}
