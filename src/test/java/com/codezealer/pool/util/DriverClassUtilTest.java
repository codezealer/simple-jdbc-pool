package com.codezealer.pool.util;

import org.junit.Test;

public class DriverClassUtilTest {

    @Test
    public void test() {
        DriverClassUtil.loadDriverClass(null, "jdbc:mysql://127.0.0.1:3306/test?serverTimezone=GMT&useUnicode=true&characterEncoding=utf-8");
    }

}
