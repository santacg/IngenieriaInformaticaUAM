import java.util.List;

public abstract class ArticleFormatter {
  protected String name;

  public ArticleFormatter(String formatterName) {
    this.name = formatterName;
  }

  public abstract String formatAuthorsList(List<Author> authors);

  public abstract String formatReference(Article a);

  public String getName() {
    return this.name;
  }

  public String format(List<Article> articles) {
    String result = "";
    for (Article a : articles)
      result += this.formatReference(a) + "\n";
    return result;
  }
}
