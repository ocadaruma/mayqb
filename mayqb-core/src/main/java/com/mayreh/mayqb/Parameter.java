package com.mayreh.mayqb;

import java.sql.PreparedStatement;
import java.sql.SQLException;

/**
 * Every SQL parameters must implement this interface
 */
public interface Parameter {

    void bind(PreparedStatement stmt, int idx) throws SQLException;
}
