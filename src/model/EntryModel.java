package model;

public class EntryModel {
    private String author;
    private String entry;
    private String entryDate;

    public EntryModel(String author, String entry, String entryDate) {
        this.author = author;
        this.entry = entry;
        this.entryDate = entryDate;
    }

    public EntryModel() {
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getEntry() {
        return entry;
    }

    public void setEntry(String entry) {
        this.entry = entry;
    }

    public String getEntryDate() {
        return entryDate;
    }

    public void setEntryDate(String entryDate) {
        this.entryDate = entryDate;
    }
}
