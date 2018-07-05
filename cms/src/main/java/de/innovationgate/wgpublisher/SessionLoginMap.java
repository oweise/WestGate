package de.innovationgate.wgpublisher;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import de.innovationgate.utils.TransientObjectWrapper;

/**
 * Map to store session logins, either transient or persistent depending on the serializability of their credentials
 * The map may return null for values that were created transiently on another cluster node.
 */
public class SessionLoginMap implements Map<Object,DBLoginInfo>, Serializable {

    private static final long serialVersionUID = 1L;

    private Map<Object,TransientObjectWrapper<DBLoginInfo>> _map = new HashMap<Object, TransientObjectWrapper<DBLoginInfo>>();


    @Override
    public DBLoginInfo put(Object key, DBLoginInfo value) {
        TransientObjectWrapper<DBLoginInfo> wrapper = wrap(value);

        DBLoginInfo oldValue = get(key);
        if (oldValue != null) {

            if (oldValue.equals(value)) {
                return oldValue;
            }

            if (oldValue.getAccessFilter() != null && value.getAccessFilter() == null) {
                value.setAccessFilter(oldValue.getAccessFilter());
            }
            for (Entry<String,String> dbFilter : oldValue.getDbAccessFilters().entrySet()) {
                if (!value.getDbAccessFilters().containsKey(dbFilter.getKey())) {
                    value.getDbAccessFilters().put(dbFilter.getKey(), dbFilter.getValue());
                }
            }

        }

        return unwrap(_map.put(key, wrapper));
    }

    private TransientObjectWrapper<DBLoginInfo> wrap(DBLoginInfo value) {
        TransientObjectWrapper<DBLoginInfo> wrapper;
        if (value.getCredentials() == null || value.getCredentials() instanceof Serializable) {
            wrapper = new TransientObjectWrapper<DBLoginInfo>(false);
        }
        else {
            wrapper = new TransientObjectWrapper<DBLoginInfo>(true);
        }
        wrapper.set(value);
        return wrapper;
    }


    private DBLoginInfo unwrap(TransientObjectWrapper<DBLoginInfo> wrapper) {
        if (wrapper == null) {
            return null;
        }
        else {
            return wrapper.get();
        }
    }

    @Override
    public void clear() {
        _map.clear();
    }

    @Override
    public boolean containsKey(Object arg0) {
        return _map.containsKey(arg0);
    }

    @Override
    public boolean containsValue(Object arg0) {
        for (TransientObjectWrapper<DBLoginInfo> wrapper : _map.values()) {
            if (arg0.equals(wrapper.get())) {
                return true;
            }
        }
        return false;
    }

    @Override
    public Set<Entry<Object, DBLoginInfo>> entrySet() {

        Set<Entry<Object, DBLoginInfo>> set = new HashSet<Entry<Object,DBLoginInfo>>();
        for (final Entry<Object,TransientObjectWrapper<DBLoginInfo>> entry : _map.entrySet()) {
            set.add(new Entry<Object, DBLoginInfo>() {

                @Override
                public Object getKey() {
                    return entry.getKey();
                }

                @Override
                public DBLoginInfo getValue() {
                    return unwrap(entry.getValue());
                }

                @Override
                public DBLoginInfo setValue(DBLoginInfo value) {
                    return unwrap(entry.setValue(wrap(value)));
                }


            });
        }
        return set;
    }

    @Override
    public DBLoginInfo get(Object arg0) {
        return unwrap(_map.get(arg0));
    }

    @Override
    public boolean isEmpty() {
        return _map.isEmpty();
    }

    @Override
    public Set<Object> keySet() {
        return _map.keySet();
    }

    @Override
    public void putAll(Map<? extends Object, ? extends DBLoginInfo> map) {
        for (Object key : map.keySet()) {
            put(key, map.get(key));
        }
    }

    @Override
    public DBLoginInfo remove(Object arg0) {
        return unwrap(_map.remove(arg0));
    }

    @Override
    public int size() {
        return _map.size();
    }

    @Override
    public Collection<DBLoginInfo> values() {

        List<DBLoginInfo> values = new ArrayList<DBLoginInfo>();
        for (TransientObjectWrapper<DBLoginInfo> wrapper : _map.values()) {
            values.add(wrapper.get());
        }
        return values;

    }

}
