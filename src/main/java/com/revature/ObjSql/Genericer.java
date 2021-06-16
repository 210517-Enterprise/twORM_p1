package com.revature.ObjSql;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import com.revature.annotations.SerialPK;

public abstract class Genericer {

	protected final Pattern regex = Pattern.compile("[^\\d]+");
	
	protected String getArgs(final int length) {
        if(length <=0 ) {
            return " ?";
        }
        return String.join(",", Collections.nCopies(length,"?")) + ",? ";
    }
	
	protected void setStatement(final PreparedStatement pstmt, final ParameterMetaData pd, final Method getter, final Object obj, final int index) {
        try {
            setPreparedStatementByType(pstmt, pd.getParameterTypeName(index),String.valueOf(getter.invoke(obj)), index);
        }catch(SQLException | IllegalAccessException | InvocationTargetException e){
            e.getStackTrace();
        }
    }
	
	protected Optional<String> getSerialName(final Class<?> clazz) {
	       return Arrays.stream(clazz.getDeclaredFields())
	                    .filter(f -> f.getDeclaredAnnotation(SerialPK.class) != null)
	                    .map(f -> f.getDeclaredAnnotation(SerialPK.class).name())
	                    .findFirst();
	    }
	
	protected Optional<Map.Entry<Method,String[]>> getSerialKeyEntry(final Optional<String> name,final HashMap<Method,String[]> setters) {
        return setters.entrySet().stream()
                .filter(e -> e.getValue()[0].equals(name.orElse("null")))
                .findFirst();
    }
	
	 protected void setPreparedStatementByType(final PreparedStatement pstmt, final String type,final String input,final int index) {
	        try {
	            Matcher match = regex.matcher(type);
	            if (match.find()) {
	                switch (match.group()) {
	                    case "text":
	                    case "String":
	                    case "varchar":
	                        pstmt.setString(index, input);
	                        break;
	                    case "int":
	                        pstmt.setInt(index, Integer.parseInt(input));
	                        break;
	                    case "float":
	                        pstmt.setFloat(index, Float.parseFloat(input));
	                        break;
	                    case "double":
	                        pstmt.setDouble(index, Double.parseDouble(input));
	                        break;
	                    case "timestamp":
	                    case "timestamptz":
	                        pstmt.setTimestamp(index, Timestamp.valueOf(input));
	                        break;
	                    default:
	                        break;
	                }
	            }
	      }catch(SQLException e) {
	            e.printStackTrace();
	        }
	    }
	 
	 protected String parseColumns(final String[] columns, final String[] operators) {
	        if(operators != null && operators.length > 0 && !"".equals(operators[0].trim()) ) {
	            final StringBuilder str = new StringBuilder();
	            for (int i = 0; i < operators.length; i++) {
	                str.append(columns[i]).append(" = ? ").append(operators[i]).append(" ");
	            }
	            str.append(columns[columns.length - 1]).append(" = ?");
	            return str.toString();
	        }
	        return columns[0] + " = ? ";
	    }
	 
	 //Cache function goes here
	
	
}
