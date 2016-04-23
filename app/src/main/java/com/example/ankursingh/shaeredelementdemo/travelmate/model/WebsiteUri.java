package com.example.ankursingh.shaeredelementdemo.travelmate.model;

/**
 * Created by Ankur Singh on 23/04/16.
 */

import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class WebsiteUri implements Parcelable {

    @SerializedName("cachedFsi")
    @Expose
    private Integer cachedFsi;
    @SerializedName("cachedSsi")
    @Expose
    private Integer cachedSsi;
    @SerializedName("scheme")
    @Expose
    private String scheme;
    @SerializedName("uriString")
    @Expose
    private String uriString;
    @SerializedName("host")
    @Expose
    private String host;
    @SerializedName("port")
    @Expose
    private Integer port;

    /**
     *
     * @return
     * The cachedFsi
     */
    public Integer getCachedFsi() {
        return cachedFsi;
    }

    /**
     *
     * @param cachedFsi
     * The cachedFsi
     */
    public void setCachedFsi(Integer cachedFsi) {
        this.cachedFsi = cachedFsi;
    }

    /**
     *
     * @return
     * The cachedSsi
     */
    public Integer getCachedSsi() {
        return cachedSsi;
    }

    /**
     *
     * @param cachedSsi
     * The cachedSsi
     */
    public void setCachedSsi(Integer cachedSsi) {
        this.cachedSsi = cachedSsi;
    }

    /**
     *
     * @return
     * The scheme
     */
    public String getScheme() {
        return scheme;
    }

    /**
     *
     * @param scheme
     * The scheme
     */
    public void setScheme(String scheme) {
        this.scheme = scheme;
    }

    /**
     *
     * @return
     * The uriString
     */
    public String getUriString() {
        return uriString;
    }

    /**
     *
     * @param uriString
     * The uriString
     */
    public void setUriString(String uriString) {
        this.uriString = uriString;
    }

    /**
     *
     * @return
     * The host
     */
    public String getHost() {
        return host;
    }

    /**
     *
     * @param host
     * The host
     */
    public void setHost(String host) {
        this.host = host;
    }

    /**
     *
     * @return
     * The port
     */
    public Integer getPort() {
        return port;
    }

    /**
     *
     * @param port
     * The port
     */
    public void setPort(Integer port) {
        this.port = port;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(this.cachedFsi);
        dest.writeValue(this.cachedSsi);
        dest.writeString(this.scheme);
        dest.writeString(this.uriString);
        dest.writeString(this.host);
        dest.writeValue(this.port);
    }

    public WebsiteUri() {
    }

    protected WebsiteUri(Parcel in) {
        this.cachedFsi = (Integer) in.readValue(Integer.class.getClassLoader());
        this.cachedSsi = (Integer) in.readValue(Integer.class.getClassLoader());
        this.scheme = in.readString();
        this.uriString = in.readString();
        this.host = in.readString();
        this.port = (Integer) in.readValue(Integer.class.getClassLoader());
    }

    public static final Parcelable.Creator<WebsiteUri> CREATOR = new Parcelable.Creator<WebsiteUri>() {
        public WebsiteUri createFromParcel(Parcel source) {
            return new WebsiteUri(source);
        }

        public WebsiteUri[] newArray(int size) {
            return new WebsiteUri[size];
        }
    };
}