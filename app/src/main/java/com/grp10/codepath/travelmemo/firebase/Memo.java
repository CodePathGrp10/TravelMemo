package com.grp10.codepath.travelmemo.firebase;

/**
 * Created by qiming on 8/19/2016.
 */
public class Memo {
    public final static String TYPE_PHOTO = "photo";
    public final static String TYPE_NOTE = "note";
    final static String TYPE_AUDIO_CLIP = "audio_clip";
    private Author author;          // person who create the memo
    private String type;           // memo type photo, or text
    private String text;            // trip description
    private String profile_image_url;        // memo thumbnail
    private Object create_at;      // the timestamp when create the memo
    private String media_url;       // the URI of memo photo or audio clip
    private String latitude;
    private String longitude;

    public Memo(Author author, String text, Object create_at) {
        this.author = author;
        this.text = text;
        this.type = TYPE_NOTE;
        this.create_at = create_at;
    }

    public Memo(Author author, Object create_at, String media_url, String text, String type, String profile_image_url) {
        this.author = author;
        this.create_at = create_at;
        this.media_url = media_url;
        if (!text.isEmpty())
            this.text = text;
        this.type = type;
        this.profile_image_url = profile_image_url;
    }

    public Author getAuthor() {
        return author;
    }

    public void setAuthor(Author author) {
        this.author = author;
    }

    public Object getCreate_at() {
        return create_at;
    }

    public void setCreate_at(Object create_at) {
        this.create_at = create_at;
    }

    public String getMedia_url() {
        return media_url;
    }

    public void setMedia_url(String media_url) {
        this.media_url = media_url;
    }

    public String getProfile_image_url() {
        return profile_image_url;
    }

    public void setProfile_image_url(String profile_image_url) {
        this.profile_image_url = profile_image_url;
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
}
