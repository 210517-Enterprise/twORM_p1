package com.revature.ObjSql;

import java.lang.reflect.Method;
import java.sql.Connection;
import java.sql.ParameterMetaData;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.util.HashMap;

import org.apache.log4j.Logger;

import com.revature.Meta.MetaConstructor;
import com.revature.Meta.MetaModel;

public class Deleter extends Genericer {
	
	private static final Deleter ObjDel = new Deleter();
	
	private static Logger log = Logger.getLogger(Deleter.class);

    private Deleter() {
        super();
    }

    public static Deleter getInstance() {
        return ObjDel;
    }

    private static Method getGetter(final String pk,final HashMap<String,Method> getters) {
        return getters.get(pk);

    }

    public boolean removeObjectFromDB(final Object obj, final Connection conn) {
        try {
            final MetaModel<?> model                = MetaConstructor.getInstance().getModels().get(obj.getClass().getSimpleName());
            final String primary_key                = model.getPrimary_key_name();
            final Method getter                     = getGetter(primary_key, model.getGetters());
            final String sql                        = "DELETE from " + model.getEntity() + " WHERE "+ primary_key + " = ? ";
            final PreparedStatement pstmt           = conn.prepareStatement(sql);
            final ParameterMetaData pd              = pstmt.getParameterMetaData();
            setStatement(pstmt, pd, getter, obj, 1);
            pstmt.executeUpdate();
            // TODO double check caching
            Cacher.getInstance().removeObjFromCache(obj);

            return true;
        }catch(SQLException e) {
            log.error("Error in removing object", e);
            e.printStackTrace();
        }
        return false;
    }

}
