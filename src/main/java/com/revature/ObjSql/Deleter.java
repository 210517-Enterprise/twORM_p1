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

    /**
     * Return the getter method for the primary key.
     * @param pk name of primary key in a particular.
     * @param getters HashMap of getters in a particular class.
     * @return Getter method for primary key.
     */
    private static Method getGetter(final String pk,final HashMap<String,Method> getters) {
        return getters.get(pk);

    }

    /**
     * Remove an object form the database.
     * @param obj object to remove from database.
     * @param conn connection to the database.
     * @return boolean indicated success/failure of operation.
     */
    public boolean removeObjectFromDB(final Object obj, final Connection conn) {
        try {
            final MetaModel<?> model                = MetaConstructor.getInstance().getModels().get(obj.getClass().getSimpleName());
            final String primary_key                = model.getPrimary_key_name();
            final Method getter                     = getGetter(primary_key,model.getGetters());
            final String sql                        = "DELETE from " + model.getEntity() + " WHERE "+ primary_key + " = ? ";
            final PreparedStatement pstmt           = conn.prepareStatement(sql);
            final ParameterMetaData pd              = pstmt.getParameterMetaData();
            setStatement(pstmt, pd, getter, obj, 1);
            pstmt.executeUpdate();
            //also remove object from cache.
            Cacher.getInstance().removeObjFromCache(obj);
            return true;
        }catch(SQLException sqle) {
            log.error(sqle);
        }
        return false;
    }

}
