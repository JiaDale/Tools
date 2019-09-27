package com.jdy.entity;

import com.jdy.annotation.Column;
import com.jdy.annotation.PrimaryKey;
import com.jdy.functions.StringFunction;
import com.jdy.util.CollectionUtils;
import com.jdy.util.TextUtils;

import java.io.Serializable;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

public class BaseEntity extends AbstractEntity<BaseEntity> implements BasicGetter<String>, Variable, OnDataChangeListener<String, Object> {

    private static final long serialVersionUID = 720782987071826141L;

    private final Map<String, Object> organData;

    private final Map<String, String> OrganKeyMap = new TreeMap<>();

    private  Map<String, ChangeData> changeDataMap = new HashMap<>();

    private boolean hasChanged = false;

    public BaseEntity() {
        this(new ConcurrentHashMap<>());
    }

    public BaseEntity(Map<String, Object> map) {
        super(map);
        organData = map;
        addListener(this);
    }

    private static final String PRIMARY_KEY = "RowGuid";
    private static final String ROW_INDEX = "RowIndex";

    @Column("RowGuid")
    @PrimaryKey
    public String getRowGuid() {
        return getStr(PRIMARY_KEY);
    }

    @Column("RowIndex")
    public int getRowIndex() {
        return getInt(ROW_INDEX);
    }

    /**
     * 数据默认主键， 可以通过{@link UUID}生成
     *
     * @param rowGuid 主键值
     */
    public void setRowGuid(final String rowGuid) {
        set(PRIMARY_KEY, rowGuid);
    }

    @Override
    public String getStr(String key) {
        return get(key, "");
    }

    @Override
    public Byte getByte(String key) {
        return get(key, null);
    }

    @Override
    public Short getShort(String key) {
        return get(key, null);
    }

    @Override
    public Integer getInt(String key) {
        return get(key, null);
    }

    @Override
    public Long getLong(String key) {
        return get(key, null);
    }

    @Override
    public Boolean getBoolean(String key) {
        return get(key, false);
    }

    @Override
    public Character getChar(String key) {
        return get(key, null);
    }

    @Override
    public Float getFloat(String key) {
        return get(key, 0f);
    }

    @Override
    public Double getDouble(String key) {
        return get(key, 0d);
    }

    @Override
    public BigDecimal getBigDecimal(String key) {
        return get(key, null);
    }

    @Override
    public BigInteger getBigInteger(String key) {
        return get(key, null);
    }

    @Override
    public <E extends Enum<E>> E getEnum(Class<E> clazz, String key) {
        throw new UnsupportedOperationException("此方法暂时不支持！");
    }

    @Override
    public Date getDate(String key) {
        return get(key, new Date());
    }


    @Override
    public Entity setDataMap(Map<String, Object> map) {
        if (CollectionUtils.isEmpty(map)) return this;

        Iterator<Map.Entry<String, Object>> iterator = map.entrySet().iterator();
        while (iterator.hasNext()) {
            Map.Entry<String, Object> next = iterator.next();
            set(next.getKey(), next.getValue());
        }
        return this;
    }

    public Entity setDataMap(Entity entity) {
        if (Objects.isNull(entity)) return this;

        return setDataMap(entity.getDataMap());
    }

//    public String[] getColumnNames() {
//        Map<String, Object> dataMap = getDataMap();
//        if (null == dataMap)
//            return null;
//        Set<String> keySet = dataMap.keySet();
//        return keySet.toArray(new String[0]);
//    }
//
//    public Object[] getColumnValues() {
//        Map<String, Object> dataMap = getDataMap();
//        if (null == dataMap)
//            return null;
//        return dataMap.values().toArray();
//    }

    @Override
    public boolean hasChanged() {
        return hasChanged;
    }

    @Override
    public Map<String, Object> getChangeData() {
        return organData;
    }


    @Override
    public BaseEntity build() {
        return this;
    }

    @Override
    public void onDataChange(String key, Object value) {
        String tempKey = TextUtils.checkValue(OrganKeyMap.get(key.toUpperCase()), key);
        Object organValue = organData.get(tempKey);
        if (Objects.isNull(organValue)) {
            if (Objects.isNull(value)) return;

            collect(key, null, value);
            return;
        }

        if (organValue.equals(value)) return;

        collect(key, organValue, value);
    }


    private void collect(String tempKey, Object organValue, Object value) {
        changeDataMap.put(tempKey, new ChangeData(tempKey, null, value));
        OrganKeyMap.put(tempKey.toUpperCase(), tempKey);
        hasChanged = true;
    }

    public class ChangeData implements Serializable {

        private static final long serialVersionUID = -6406814491006475389L;
        private final String key;
        private final Object organValue, value;

        public ChangeData(String key, Object organValue, Object value) {
            this.key = key;
            this.organValue = organValue;
            this.value = value;
        }

        public String getKey() {
            return key;
        }

        public Object getOrganValue() {
            return organValue;
        }

        public Object getValue() {
            return value;
        }


        @Override
        public String toString() {
            return "[key : " + key + ", value: " + StringFunction.getInstance().apply(organValue) + "  --> " + StringFunction.getInstance().apply(value) + "]";
        }
    }
}
