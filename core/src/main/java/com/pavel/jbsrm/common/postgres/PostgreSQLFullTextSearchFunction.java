package com.pavel.jbsrm.common.postgres;

import org.hibernate.QueryException;
import org.hibernate.dialect.function.SQLFunction;
import org.hibernate.engine.spi.Mapping;
import org.hibernate.engine.spi.SessionFactoryImplementor;
import org.hibernate.type.BooleanType;
import org.hibernate.type.Type;

import java.util.List;
import java.util.ListIterator;

public class PostgreSQLFullTextSearchFunction implements SQLFunction {

    private final String to_tsvector = " to_tsvector (";
    private final String to_tsquery = ") @@ to_tsquery(";

    @Override
    public boolean hasArguments() {
        return true;
    }

    @Override
    public boolean hasParenthesesIfNoArguments() {
        return false;
    }

    @Override
    public Type getReturnType(Type firstArgumentType, Mapping mapping) throws QueryException {
        return new BooleanType();
    }

    @Override
    public String render(Type firstArgumentType, List arguments, SessionFactoryImplementor factory) throws QueryException {
        StringBuilder sb = new StringBuilder(" to_tsvector (");
        ListIterator<String> args = arguments.listIterator();

        while (args.hasNext()) {
            int pos = args.nextIndex();
            String value = args.next();

            if (pos != arguments.size() - 1) {
                sb.append(value);
                sb.append(pos == arguments.size() - 2 ? ")" : ",");
            } else {
                sb.append(" @@ to_tsquery(").append(value).append(")");
            }
        }

        return sb.toString();
    }
}
