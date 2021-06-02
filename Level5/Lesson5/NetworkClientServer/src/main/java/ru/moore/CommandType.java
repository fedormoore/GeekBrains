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
    DELETE_FOLDER
}
