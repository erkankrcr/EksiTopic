package model;

public class TopicModel {
    private String Title;
    private String Url;

    public TopicModel(String title, String url) {
        Title = title;
        Url = url;
    }

    public String getTitle() {
        return Title;
    }

    public void setTitle(String title) {
        Title = title;
    }

    public String getUrl() {
        return Url;
    }

    public void setUrl(String url) {
        Url = url;
    }
}
