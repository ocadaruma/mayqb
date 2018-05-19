package com.mayreh.mayqb;

import java.sql.Connection;

public interface DBContext extends AutoCloseable {

    Connection conn();
}
