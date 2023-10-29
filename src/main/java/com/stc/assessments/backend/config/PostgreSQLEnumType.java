package com.stc.assessments.backend.config;

import org.hibernate.HibernateException;
import org.hibernate.engine.spi.SharedSessionContractImplementor;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Types;

import org.hibernate.type.EnumType;


public class PostgreSQLEnumType extends EnumType {


    @Override
    public void nullSafeSet( PreparedStatement ps, Object obj, int index,
                             SharedSessionContractImplementor session) throws HibernateException, SQLException {
        ps.setObject(index,obj!=null? ((Enum)obj).name():null,Types.OTHER);
    }
}
