package com.pavel.jbsrm.common.postgres;

import com.querydsl.core.types.dsl.BooleanExpression;
import com.querydsl.core.types.dsl.Expressions;
import com.querydsl.core.types.dsl.StringPath;

import java.util.stream.Stream;

public class FtsExpression {

    public static BooleanExpression ftr(String searchValue, StringPath... textPaths) {
        StringBuilder sb = new StringBuilder("fts(");
        Stream.of(textPaths).forEach(path -> sb.append(path).append(","));
        return Expressions.booleanTemplate(sb.append(" {0}) = true").toString(), searchValue);
    }
}
