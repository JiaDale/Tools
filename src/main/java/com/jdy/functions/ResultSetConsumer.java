package com.jdy.functions;

import java.sql.ResultSet;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public interface ResultSetConsumer<T> extends Consumer<T> , Function<ResultSet, T> {

    List<T> getList();

}
