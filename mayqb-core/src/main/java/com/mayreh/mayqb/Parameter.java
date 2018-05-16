package com.mayreh.mayqb;

import java.sql.PreparedStatement;
import java.sql.SQLException;

public interface Parameter {

    void bind(PreparedStatement stmt, int idx) throws SQLException;
}
