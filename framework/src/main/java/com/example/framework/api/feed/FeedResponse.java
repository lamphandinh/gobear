package com.example.framework.api.feed;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;
import org.simpleframework.xml.Root;

@Root(name = "rss", strict = false)
public class FeedResponse {
    @Element(name = "channel")
    public Channel channel;

    public class Channel {
        @ElementList
        public List<FeedRssItem> feedRssItemList;
    }
}
