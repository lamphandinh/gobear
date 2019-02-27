package com.example.framework.api.feed;

import org.simpleframework.xml.Element;
import org.simpleframework.xml.ElementList;

import java.util.List;

@Element(name = "channel")
public class FeedChannel {
    @ElementList(entry = "item", inline = true)
    public List<FeedRssItem> feedRssItemList;
}
