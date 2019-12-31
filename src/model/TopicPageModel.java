package model;

import java.util.ArrayList;
import java.util.List;

public class TopicPageModel {
    private int pageCount;
    private String topicHeader;
    private List<EntryModel> entry;

    public TopicPageModel(){
        this.entry = new ArrayList<>();
    }
    public int getPageCount() {
        return pageCount;
    }

    public void setPageCount(int pageCount) {
        this.pageCount = pageCount;
    }

    public String getTopicHeader() {
        return topicHeader;
    }

    public void setTopicHeader(String topicName) {
        this.topicHeader = topicName;
    }

    public List<EntryModel> getEntry() {
        return entry;
    }

    public void setEntry(List<EntryModel> entry) {
        this.entry = entry;
    }
}
