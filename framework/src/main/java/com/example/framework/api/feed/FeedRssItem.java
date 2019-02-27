package com.example.framework.api.feed;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;
import org.simpleframework.xml.Root;

@Element(name = "item")
public class FeedRssItem {
    @Element(name = "title")
    public String title;
    @Element(name = "description")
    public String description;
    @Element(name = "link")
    public String detailUrl;
    @Element(name = "thumbnail")
    public FeedThumbnail thumbnail;
}
