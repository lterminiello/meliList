
package com.lmterminiello.melilist.model; ;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Attribute {

    @SerializedName("attribute_group_id")
    private String attributeGroupId;
    @SerializedName("attribute_group_name")
    private String attributeGroupName;
    @SerializedName("id")
    private String id;
    @SerializedName("name")
    private String name;
    @SerializedName("value_id")
    private String valueId;
    @SerializedName("value_name")
    private String valueName;
    @SerializedName("value_struct")
    private Object valueStruct;

    public String getAttributeGroupId() {
        return attributeGroupId;
    }

    public void setAttributeGroupId(String attributeGroupId) {
        this.attributeGroupId = attributeGroupId;
    }

    public String getAttributeGroupName() {
        return attributeGroupName;
    }

    public void setAttributeGroupName(String attributeGroupName) {
        this.attributeGroupName = attributeGroupName;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValueId() {
        return valueId;
    }

    public void setValueId(String valueId) {
        this.valueId = valueId;
    }

    public String getValueName() {
        return valueName;
    }

    public void setValueName(String valueName) {
        this.valueName = valueName;
    }

    public Object getValueStruct() {
        return valueStruct;
    }

    public void setValueStruct(Object valueStruct) {
        this.valueStruct = valueStruct;
    }

}
