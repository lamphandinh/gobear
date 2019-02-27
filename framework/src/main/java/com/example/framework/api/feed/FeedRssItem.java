package com.example.framework.api.feed;

import org.simpleframework.xml.Element;

@Element(name = "item")
public class FeedRssItem {
    @Element(name = "title")
    public String title;
    @Element(name = "description")
    public String description;
    @Element(name = "link")
    public String detailUrl;
    @Element(name = "media:thumbnail")
    public String thumbnailUrl;
}
