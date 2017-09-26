package com.mb3364.twitch.api.models;

import java.util.HashMap;
import java.util.Map;
import com.fasterxml.jackson.annotation.JsonAnyGetter;
import com.fasterxml.jackson.annotation.JsonAnySetter;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.fasterxml.jackson.annotation.JsonPropertyOrder;
import org.apache.commons.lang3.builder.EqualsBuilder;
import org.apache.commons.lang3.builder.HashCodeBuilder;
import org.apache.commons.lang3.builder.ToStringBuilder;

@JsonInclude(JsonInclude.Include.NON_NULL)
@JsonPropertyOrder({
        "mature",
        "status",
        "broadcaster_language",
        "display_name",
        "game",
        "language",
        "_id",
        "name",
        "created_at",
        "updated_at",
        "partner",
        "logo",
        "video_banner",
        "profile_banner",
        "profile_banner_background_color",
        "url",
        "views",
        "followers"
})
public class Channel {

    @JsonProperty("mature")
    private Boolean mature;
    @JsonProperty("status")
    private String status;
    @JsonProperty("broadcaster_language")
    private String broadcasterLanguage;
    @JsonProperty("display_name")
    private String displayName;
    @JsonProperty("game")
    private String game;
    @JsonProperty("language")
    private String language;
    @JsonProperty("_id")
    private Integer id;
    @JsonProperty("name")
    private String name;
    @JsonProperty("created_at")
    private String createdAt;
    @JsonProperty("updated_at")
    private String updatedAt;
    @JsonProperty("partner")
    private Boolean partner;
    @JsonProperty("logo")
    private String logo;
    @JsonProperty("video_banner")
    private String videoBanner;
    @JsonProperty("profile_banner")
    private String profileBanner;
    @JsonProperty("profile_banner_background_color")
    private String profileBannerBackgroundColor;
    @JsonProperty("url")
    private String url;
    @JsonProperty("views")
    private Integer views;
    @JsonProperty("followers")
    private Integer followers;
    @JsonIgnore
    private Map<String, Object> additionalProperties = new HashMap<String, Object>();

    /**
     * No args constructor for use in serialization
     */
    public Channel() {
    }

    /**
     * @param logo
     * @param status
     * @param profileBanner
     * @param game
     * @param url
     * @param videoBanner
     * @param id
     * @param updatedAt
     * @param followers
     * @param views
     * @param mature
     * @param createdAt
     * @param name
     * @param partner
     * @param language
     * @param displayName
     * @param broadcasterLanguage
     * @param profileBannerBackgroundColor
     */
    public Channel(Boolean mature, String status, String broadcasterLanguage, String displayName, String game,
                   String language, Integer id, String name, String createdAt, String updatedAt, Boolean partner,
                   String logo, String videoBanner, String profileBanner,
                   String profileBannerBackgroundColor, String url, Integer views, Integer followers) {
        super();
        this.mature = mature;
        this.status = status;
        this.broadcasterLanguage = broadcasterLanguage;
        this.displayName = displayName;
        this.game = game;
        this.language = language;
        this.id = id;
        this.name = name;
        this.createdAt = createdAt;
        this.updatedAt = updatedAt;
        this.partner = partner;
        this.logo = logo;
        this.videoBanner = videoBanner;
        this.profileBanner = profileBanner;
        this.profileBannerBackgroundColor = profileBannerBackgroundColor;
        this.url = url;
        this.views = views;
        this.followers = followers;
    }

    @JsonAnyGetter
    public Map<String, Object> getAdditionalProperties() {
        return this.additionalProperties;
    }

    @JsonProperty("broadcaster_language")
    public String getBroadcasterLanguage() {
        return broadcasterLanguage;
    }

    @JsonProperty("broadcaster_language")
    public void setBroadcasterLanguage(String broadcasterLanguage) {
        this.broadcasterLanguage = broadcasterLanguage;
    }

    @JsonProperty("created_at")
    public String getCreatedAt() {
        return createdAt;
    }

    @JsonProperty("created_at")
    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    @JsonProperty("display_name")
    public String getDisplayName() {
        return displayName;
    }

    @JsonProperty("display_name")
    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @JsonProperty("followers")
    public Integer getFollowers() {
        return followers;
    }

    @JsonProperty("followers")
    public void setFollowers(Integer followers) {
        this.followers = followers;
    }

    @JsonProperty("game")
    public String getGame() {
        return game;
    }

    @JsonProperty("game")
    public void setGame(String game) {
        this.game = game;
    }

    @JsonProperty("_id")
    public Integer getId() {
        return id;
    }

    @JsonProperty("_id")
    public void setId(Integer id) {
        this.id = id;
    }

    @JsonProperty("language")
    public String getLanguage() {
        return language;
    }

    @JsonProperty("language")
    public void setLanguage(String language) {
        this.language = language;
    }

    @JsonProperty("logo")
    public String getLogo() {
        return logo;
    }

    @JsonProperty("logo")
    public void setLogo(String logo) {
        this.logo = logo;
    }

    @JsonProperty("mature")
    public Boolean getMature() {
        return mature;
    }

    @JsonProperty("mature")
    public void setMature(Boolean mature) {
        this.mature = mature;
    }

    @JsonProperty("name")
    public String getName() {
        return name;
    }

    @JsonProperty("name")
    public void setName(String name) {
        this.name = name;
    }

    @JsonProperty("partner")
    public Boolean getPartner() {
        return partner;
    }

    @JsonProperty("partner")
    public void setPartner(Boolean partner) {
        this.partner = partner;
    }

    @JsonProperty("profile_banner")
    public String getProfileBanner() {
        return profileBanner;
    }

    @JsonProperty("profile_banner")
    public void setProfileBanner(String profileBanner) {
        this.profileBanner = profileBanner;
    }

    @JsonProperty("profile_banner_background_color")
    public String getProfileBannerBackgroundColor() {
        return profileBannerBackgroundColor;
    }

    @JsonProperty("profile_banner_background_color")
    public void setProfileBannerBackgroundColor(String profileBannerBackgroundColor) {
        this.profileBannerBackgroundColor = profileBannerBackgroundColor;
    }

    @JsonProperty("status")
    public String getStatus() {
        return status;
    }

    @JsonProperty("status")
    public void setStatus(String status) {
        this.status = status;
    }

    @JsonProperty("updated_at")
    public String getUpdatedAt() {
        return updatedAt;
    }

    @JsonProperty("updated_at")
    public void setUpdatedAt(String updatedAt) {
        this.updatedAt = updatedAt;
    }

    @JsonProperty("url")
    public String getUrl() {
        return url;
    }

    @JsonProperty("url")
    public void setUrl(String url) {
        this.url = url;
    }

    @JsonProperty("video_banner")
    public String getVideoBanner() {
        return videoBanner;
    }

    @JsonProperty("video_banner")
    public void setVideoBanner(String videoBanner) {
        this.videoBanner = videoBanner;
    }

    @JsonProperty("views")
    public Integer getViews() {
        return views;
    }

    @JsonProperty("views")
    public void setViews(Integer views) {
        this.views = views;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(mature).append(status).append(broadcasterLanguage).append(displayName).append(game).append(language).append(id).append(name).append(createdAt).append(updatedAt).append(partner).append(logo).append(videoBanner).append(profileBanner).append(profileBannerBackgroundColor).append(url).append(views).append(followers).append(additionalProperties).toHashCode();
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof Channel)) {
            return false;
        }
        Channel rhs = ((Channel) other);
        return new EqualsBuilder().append(mature, rhs.mature).append(status, rhs.status).append(broadcasterLanguage, rhs.broadcasterLanguage).append(displayName, rhs.displayName).append(game, rhs.game).append(language, rhs.language).append(id, rhs.id).append(name, rhs.name).append(createdAt, rhs.createdAt).append(updatedAt, rhs.updatedAt).append(partner, rhs.partner).append(logo, rhs.logo).append(videoBanner, rhs.videoBanner).append(profileBanner, rhs.profileBanner).append(profileBannerBackgroundColor, rhs.profileBannerBackgroundColor).append(url, rhs.url).append(views, rhs.views).append(followers, rhs.followers).append(additionalProperties, rhs.additionalProperties).isEquals();
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }

    @JsonAnySetter
    public void setAdditionalProperty(String name, Object value) {
        this.additionalProperties.put(name, value);
    }

}
