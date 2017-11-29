package demo.demo.rss;

public class News {
    private String title;
    private String time;
    private String author;
    private String link;
    private String content;

    public String getTitle() {
        return title;
    }

    public News setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getTime() {
        return time;
    }

    public News setTime(String time) {
        this.time = time;
        return this;
    }

    public String getAuthor() {
        return author;
    }

    public News setAuthor(String author) {
        this.author = author;
        return this;
    }

    public String getLink() {
        return link;
    }

    public News setLink(String link) {
        this.link = link;
        return this;
    }

    public String getContent() {
        return content;
    }

    public News setContent(String content) {
        this.content = content;
        return this;
    }

    @Override
    public String toString() {
        return "News{" +
                "title='" + title + '\'' +
                ", time='" + time + '\'' +
                ", author='" + author + '\'' +
                ", content='" + content + '\'' +
                '}';
    }

}
