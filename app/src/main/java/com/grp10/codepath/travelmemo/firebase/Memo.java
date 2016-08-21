package com.grp10.codepath.travelmemo.firebase;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by qiming on 8/19/2016.
 */
public class Memo {

    // TODO : maybe use an enum
    public final static String TYPE_PHOTO = "photo";
    public final static String TYPE_NOTE = "note";
    public final static String TYPE_AUDIO_CLIP = "audio_clip";

    private User owner;          // person who create the memo
    private String type;           // memo type photo, or text
    private String text;            // trip description
    private long create_at;      // the timestamp when create the memo
    private String media_url;       // the URI of memo photo or audio clip
    private String latitude;
    private String longitude;

    public Memo() {
    }

    public Memo(User owner, String text) {
        this.owner = owner;
        this.text = text;
        this.type = TYPE_NOTE;
        this.create_at = System.currentTimeMillis();
    }

    public Memo(User owner, String media_url, String text, String type) {
        this.owner = owner;
        this.create_at = System.currentTimeMillis();
        this.media_url = media_url;
        if (!text.isEmpty())
            this.text = text;
        this.type = type;
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public long getCreateAt() {
        return create_at;
    }

    public void setCreateAt(long create_at) {
        this.create_at = create_at;
    }

    public String getMediaUrl() {
        return media_url;
    }

    public void setMediaUrl(String media_url) {
        this.media_url = media_url;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Map<String, Object> toMap() {
        HashMap<String, Object> result = new HashMap<>();
        result.put("mediaUrl",media_url);
        result.put("text", text);
        result.put("latitude",latitude);
        result.put("longitude",longitude);
        result.put("type",type);
        result.put("createdAt",create_at);
        result.put("owner", owner);
        return result;
    }
    @Override
    public String toString() {
        final StringBuffer sb = new StringBuffer("Memo{");
        sb.append("owner=").append(owner.getName());
        sb.append(", type='").append(type).append('\'');
        sb.append(", media_url='").append(media_url).append('\'');
        sb.append('}');
        return sb.toString();
    }
}
