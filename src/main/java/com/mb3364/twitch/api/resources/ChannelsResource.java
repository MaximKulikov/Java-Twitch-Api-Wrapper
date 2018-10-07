package com.mb3364.twitch.api.resources;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import com.mb3364.http.RequestParams;
import com.mb3364.twitch.api.auth.Scopes;
import com.mb3364.twitch.api.handlers.*;
import com.mb3364.twitch.api.models.Channel;
import com.mb3364.twitch.api.models.ChannelFollows;
import com.mb3364.twitch.api.models.ChannelSubscriptions;
import com.mb3364.twitch.api.models.Community;
import com.mb3364.twitch.api.models.Editors;
import com.mb3364.twitch.api.models.Subscription;
import com.mb3364.twitch.api.models.Teams;
import com.mb3364.twitch.api.models.Videos;
import org.apache.http.HttpResponse;

/**
 * The {@link ChannelsResource} provides the functionality
 * to access the <code>/channels</code> endpoints of the Twitch API.
 *
 * @author Matthew Bell (original author)
 * @author Ague Mort (contributing author)
 */
public class ChannelsResource extends AbstractResource {

    private HttpResponse response;

    /**
     * Construct the resource using the Twitch API base URL and specified API version.
     *
     * @param baseUrl    the base URL of the Twitch API
     * @param apiVersion the requested version of the Twitch API
     */
    public ChannelsResource(String baseUrl, int apiVersion) {
        super(baseUrl, apiVersion);
    }

    /**
     * Gets a channel object based on a specified OAuth token.
     * Get Channel returns more data than Get Channel by ID because Get Channel is privileged.
     * <p>
     * <p>Authentication: Required Scope: {@link Scopes#CHANNEL_READ}</p>
     *
     * @param handler the response handler
     */
    public void get(final ChannelResponseHandler handler) {
        String url = String.format("%s/channel", getBaseUrl());

        http.get(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Channel value = objectMapper.readValue(content, Channel.class);
                    handler.onSuccess(value);
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    /**
     * Gets a specified {@link Channel} object.
     *
     * @param channelName the name of the Channel
     * @param handler     the response handler
     */
    public void get(final String channelName, final ChannelResponseHandler handler) {
        String url = String.format("%s/channels/%s", getBaseUrl(), getChannelId(channelName).get(0));

        http.get(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Channel value = objectMapper.readValue(content, Channel.class);
                    handler.onSuccess(value);
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    /**
     * Updates specified properties of a specified channel.
     * In the request, the new properties are specified as a JSON object or a form-encoded representation.
     * <p>
     * <p>Authentication: Required Scope: {@link Scopes#CHANNEL_EDITOR}</p>
     *
     * @param channelName the name of the Channel
     * @param params      At least one parameter must be specified:
     *                    <ul>
     *                    <li><code>status</code>: Description of the broadcaster’s status, displayed as a title on
     *                    the channel page.</li>
     *                    <li><code>game</code>: Name of game.</li>
     *                    <li><code>delay</code>: Channel delay, in seconds. This inserts a delay in the live feed.
     *                    Requires the channel owner’s OAuth token.</li>
     *                    <li><code>channel_feed_enabled</code>: Boolean: If true, the channel’s feed is turned on.
     *                    Requires the channel owner’s OAuth token. Default: <code>false</code>.</li>
     *                    </ul>
     * @param handler     the response handler
     */
    public void put(final String channelName, final RequestParams params, final ChannelResponseHandler handler) {
        String url = String.format("%s/channels/%s", getBaseUrl(), getChannelId(channelName).get(0));

        if (params.containsKey("status")) {
            params.put("channel[status]", params.getString("status"));
            params.remove("status");
        }

        if (params.containsKey("game")) {
            params.put("channel[game]", params.getString("game"));
            params.remove("game");
        }

        if (params.containsKey("delay")) {
            params.put("channel[delay]", params.getString("delay"));
            params.remove("delay");
        }

        if (params.containsKey("channel_feed_enabled")) {
            params.put("channel[channel_feed_enabled]", params.getString("channel_feed_enabled"));
            params.remove("channel_feed_enabled");
        }

        http.put(url, params, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Channel value = objectMapper.readValue(content, Channel.class);
                    handler.onSuccess(value);
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    /**
     * Gets a list of users who are editors for a specified channel.
     * <p>
     * <p>Authentication: Required Scope: {@link Scopes#CHANNEL_READ}</p>
     *
     * @param channelName the name of the Channel
     * @param handler     the response handler
     */
    public void getEditors(final String channelName, final UsersResponseHandler handler) {
        String url = String.format("%s/channels/%s/editors", getBaseUrl(), getChannelId(channelName).get(0));

        http.get(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Editors value = objectMapper.readValue(content, Editors.class);
                    handler.onSuccess(value.getUsers());
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    /**
     * Gets a list of users who follow a specified channel, sorted by the date when they started following the channel
     * (newest first, unless specified otherwise).
     *
     * @param channelName the name of the Channel
     * @param params      the optional request parameters:
     *                    <ul>
     *                    <li><code>limit</code>: Integer: Maximum number of objects to return. Default: 25. Maximum: 100.</li>
     *                    <li><code>offset</code>: Integer: Object offset for pagination of results. Default: 0.</li>
     *                    <li><code>cursor</code>: String: Tells the server where to start fetching the next set of
     *                    results, in a multi-page response.</li>
     *                    <li><code>direction</code>: String: Direction of sorting.
     *                    Valid values: <code>asc</code>, <code>desc</code> (newest first). Default: <code>desc</code>.</li>
     *                    </ul>
     * @param handler     the response handler
     */
    public void getFollows(final String channelName, final RequestParams params, final ChannelFollowsResponseHandler handler) {
        String url = String.format("%s/channels/%s/follows", getBaseUrl(), getChannelId(channelName).get(0));

        http.get(url, params, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    ChannelFollows value = objectMapper.readValue(content, ChannelFollows.class);
                    handler.onSuccess(value.getTotal(), value.getFollows(), value.getCursor());
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    /**
     * Returns a list of follow objects representing the followers of a channel.
     *
     * @param channelName the name of the Channel
     * @param handler     the response handler
     */
    public void getFollows(final String channelName, final ChannelFollowsResponseHandler handler) {
        getFollows(channelName, new RequestParams(), handler);
    }

    /**
     * Gets a list of teams to which a specified channel belongs.
     *
     * @param channelName the name of the Channel
     * @param handler     the response handler
     */
    public void getTeams(final String channelName, final TeamsResponseHandler handler) {
        String url = String.format("%s/channels/%s/teams", getBaseUrl(), getChannelId(channelName).get(0));

        http.get(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Teams value = objectMapper.readValue(content, Teams.class);
                    handler.onSuccess(value.getTeams());
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    /**
     * Gets a list of users subscribed to a specified channel, sorted by the date when they subscribed.
     *
     * <p>Authentication: Required Scope: {@link Scopes#CHANNEL_SUBSCRIPTIONS}</p>
     *
     * @param channelName the name of the Channel
     * @param params      the optional request parameters:
     *                    <ul>
     *                    <li><code>limit</code>: Integer: Maximum number of objects to return. Default: 25. Maximum: 100.</li>
     *                    <li><code>offset</code>: Integer: Object offset for pagination of results. Default: 0.</li>
     *                    <li><code>direction</code>: String: Sorting direction.
     *                    Valid values: <code>asc</code>, <code>desc</code>. Default: <code>asc</code> (oldest first).</li>
     *                    </ul>
     * @param handler     the response handler
     */
    public void getSubscriptions(final String channelName, final RequestParams params, final ChannelSubscriptionsResponseHandler handler) {
        String url = String.format("%s/channels/%s/subscriptions", getBaseUrl(), getChannelId(channelName).get(0));

        http.get(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    ChannelSubscriptions value = objectMapper.readValue(content, ChannelSubscriptions.class);
                    handler.onSuccess(value.getTotal(), value.getSubscriptions());
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    /**
     * Require number id
     *
     * @param channelId
     * @param handler
     */
    public void getSubscriptionByChannelId(final int channelId, final ChannelSubscriptionsResponseHandler handler){
        String url = String.format("%s/channels/%s/subscriptions/", getBaseUrl(), channelId);

        http.get(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    ChannelSubscriptions value = objectMapper.readValue(content, ChannelSubscriptions.class);
                    handler.onSuccess(value.getTotal(), value.getSubscriptions());
                } catch (IOException e) {
                    handler.onFailure(e);
                }

            }
        });

    }


    /**
     * Gets a list of users subscribed to a specified channel, sorted by the date when they subscribed.
     *
     * <p>Authentication: Required Scope: {@link Scopes#CHANNEL_SUBSCRIPTIONS}</p>
     *
     * @param channelName the name of the Channel
     * @param handler     the response handler
     */
    public void getSubscriptions(final String channelName, final ChannelSubscriptionsResponseHandler handler) {
        getSubscriptions(channelName, new RequestParams(), handler);
    }

    /**
     * Checks if a specified channel has a specified user subscribed to it. Intended for use by channel owners.
     * Returns a subscription object which includes the user if that user is subscribed. Requires authentication for the channel.
     *
     * <p>Authentication: Required Scope: {@link Scopes#CHANNEL_CHECK_SUBSCRIPTION}</p>
     *
     * @param channelName the name of the channel
     * @param user        the user to check
     * @param handler     the response handler
     */
    public void getSubscription(final String channelName, final String user, final ChannelSubscriptionResponseHandler handler) {
        String url = String.format("%s/channels/%s/subscriptions/%s", getBaseUrl(), getChannelId(channelName).get(0), getChannelId(user).get(0));

        http.get(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Subscription value = objectMapper.readValue(content, Subscription.class);
                    handler.onSuccess(value);
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    /**
     * Gets a list of videos from a specified channel.
     *
     * @param channelName the name of the Channel
     * @param params      the optional request parameters:
     *                    <ul>
     *                    <li><code>limit</code>: Integer: Maximum number of objects to return. Default: 10. Maximum: 100.</li>
     *                    <li><code>offset</code>: Integer: Object offset for pagination of results. Default: 0.</li>
     *                    <li><code>broadcast_type</code>: Comma-Separated List: Constrains the type of videos returned.
     *                    Valid values: (any combination of) <code>archive</code>, <code>highlight</code>, <code>upload</code>.
     *                    Default: <code>highlight</code>.
     *                    </li>
     *                    <li><code>language</code>: Comma-Separated List: Constrains the language of the videos that are returned;
     *                    for example, <code>en,es</code>. Default: all languages.
     *                    <li><code>sort</code>: String: Sorting order of the returned objects.
     *                    Valid values: <code>views</code>, <code>time</code>. Default: <code>time</code> (most recent first).</li>
     *                    </ul>
     * @param handler     the response handler
     */
    public void getVideos(final String channelName, final RequestParams params, final VideosResponseHandler handler) {
        String url = String.format("%s/channels/%s/videos", getBaseUrl(), getChannelId(channelName).get(0));

        http.get(url, params, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Videos value = objectMapper.readValue(content, Videos.class);
                    handler.onSuccess(value.getTotal(), value.getVideos());
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    /**
     * Returns a list of videos ordered by time of creation, starting with
     * the most recent from specified channel.
     *
     * @param channelName the name of the Channel
     * @param handler     the response handler
     */
    public void getVideos(final String channelName, final VideosResponseHandler handler) {
        getVideos(channelName, new RequestParams(), handler);
    }

    /**
     * Starts a commercial (advertisement) on a specified channel.
     * This is valid only for channels that are Twitch partners.
     * You cannot start a commercial more often than once every 8 minutes.
     *
     * The length of the commercial (in seconds) is specified in the request body, with a required duration parameter.
     * Valid values are 30, 60, 90, 120, 150, and 180.
     *
     * <p>Authentication: Required Scope: {@link Scopes#CHANNEL_COMMERCIAL}</p>
     *
     * @param channelName the name of the channel
     * @param length      Length of commercial break in seconds. Default value is <code>30</code>.
     *                    Valid values are <code>30</code>, <code>60</code>, <code>90</code>,
     *                    <code>120</code>, <code>150</code>, and <code>180</code>
     * @param handler     the response handler
     */
    public void startCommercial(final String channelName, final int length, final CommercialResponseHandler handler) {
        String url = String.format("%s/channels/%s/commercial", getBaseUrl(), getChannelId(channelName).get(0));

        RequestParams params = new RequestParams();
        params.put("length", Integer.toString(length));

        http.post(url, params, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                handler.onSuccess();
            }
        });
    }

    /**
     * Deletes the stream key for a specified channel. Once it is deleted, the stream key is automatically reset.
     *
     * <p>Authentication: Required Scope: {@link Scopes#CHANNEL_STREAM}</p>
     *
     * @param channelName the name of the Channel
     * @param handler     the response handler
     */
    public void resetStreamKey(final String channelName, final ChannelResponseHandler handler) {
        String url = String.format("%s/channels/%s/stream_key", getBaseUrl(), getChannelId(channelName).get(0));

        http.delete(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Channel value = objectMapper.readValue(content, Channel.class);
                    handler.onSuccess(value);
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    /**
     * Gets the community for a specified channel.
     *
     * @param channelName the name of the Channel
     * @param handler     the response handler
     */
    public void getChannelCommunity(final String channelName, final CommunityResponseHandler handler) {
        String url = String.format("%s/channels/%s/community", getBaseUrl(), getChannelId(channelName).get(0));

        http.get(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                try {
                    Community value = objectMapper.readValue(content, Community.class);
                    handler.onSuccess(value);
                } catch (IOException e) {
                    handler.onFailure(e);
                }
            }
        });
    }

    /**
     * Sets a specified channel to be in a specified community.
     * <p>Authentication: Required scope: Any token for the specified channel</p>
     *  @param channelName the name of the Channel
     * @param communityId
     * @param handler     the response handler
     */
    public void put(final String channelName, com.mb3364.http.RequestParams communityId, final CommunityResponseHandler handler) {
        String url = String.format("%s/channels/%s/community/%s", getBaseUrl(), getChannelId(channelName).get(0), communityId);

        http.put(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                // There is no proper response.  Just code 204
            }
        });
    }

    /**
     * Deletes a specified channel from its community.
     *
     * @param channelName the name of the Channel
     * @param handler     the response handler
     */
    public void put(final String channelName, final CommunityResponseHandler handler) {
        String url = String.format("%s/channels/%s/community", getBaseUrl(), getChannelId(channelName).get(0));

        http.delete(url, new TwitchHttpResponseHandler(handler) {
            @Override
            public void onSuccess(int statusCode, Map<String, List<String>> headers, String content) {
                // There is no proper response.  Just code 204
            }
        });
    }
}
