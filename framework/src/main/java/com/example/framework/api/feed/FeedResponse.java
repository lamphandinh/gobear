package com.example.framework.api.feed;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Root(name = "rss", strict = false)
public class FeedResponse {
    @Element(name = "channel")
    public FeedChannel channel;
}
