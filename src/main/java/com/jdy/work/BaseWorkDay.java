package com.jdy.work;

import java.util.Date;

public class BaseWorkDay implements IWorkDay {

    @Override
    public boolean isWorkingDay(Date date) {
        return false;
    }

}
