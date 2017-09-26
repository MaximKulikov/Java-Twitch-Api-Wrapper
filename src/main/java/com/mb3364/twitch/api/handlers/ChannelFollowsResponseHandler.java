package com.mb3364.twitch.api.handlers;

import java.util.List;
import com.mb3364.twitch.api.models.FollowUsers;

public interface ChannelFollowsResponseHandler extends BaseFailureHandler {

    void onSuccess(int total, List<FollowUsers> follows, String cursor);

}
