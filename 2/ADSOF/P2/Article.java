import java.util.List;

public class Article {
  private List<Author> authors;
  private int year, volume, issue;
  private String title, journal;

  public Article(List<Author> authors, int year, String title, String journal,
      int vol, int issue) {
    this.authors = authors;
    this.year = year;
    this.title = title;
    this.journal = journal;
    this.volume = vol;
    this.issue = issue;
  }

  public List<Author> getAuthors() {
    return authors;
  }

  public int getYear() {
    return year;
  }

  public String getTitle() {
    return title;
  }

  public String getJournal() {
    return journal;
  }

  public int getVolume() {
    return volume;
  }

  public int getIssue() {
    return issue;
  }
}
