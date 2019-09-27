package com.jdy.entity;

import java.util.Map;

public interface Variable {

    boolean hasChanged();

    Map<String, Object> getChangeData();
}
