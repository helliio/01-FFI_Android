package edu.ntnu.sair.model;

import org.json.JSONObject;

/**
 * Created by chun on 3/5/15.
 */
public class Result {
    private String tag;
    private String description;
    private String type;
    private String object;

    public Result(String tag, String description) {
        this.tag = tag;
        this.description = description;
        this.type = "";
        this.object = "";
    }

    public Result(String tag, String description, String type, String object) {
        this.tag = tag;
        this.description = description;
        this.type = type;
        this.object = object;
    }

    public String getTag() {
        return tag;
    }

    public void setTag(String tag) {
        this.tag = tag;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getObject() {
        return object;
    }

    public void setObject(String object) {
        this.object = object;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String toString() {
        try {
            JSONObject jsonObject = new JSONObject();
            jsonObject.put("tag", this.tag);
            jsonObject.put("desc", this.description);
            jsonObject.put("obj", this.object);
            return jsonObject.toString();

        } catch (Exception e) {
            return null;
        }
    }
}
