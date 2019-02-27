package com.example.framework.api.feed;

import org.simpleframework.xml.Attribute;
import org.simpleframework.xml.Element;

@Element(name = "thumbnail")
public class FeedThumbnail {
    @Attribute(name = "url")
    public String url;
}
