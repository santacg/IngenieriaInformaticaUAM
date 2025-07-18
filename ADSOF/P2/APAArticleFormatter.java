import java.util.List;

public class APAArticleFormatter extends ArticleFormatter {
  public APAArticleFormatter() {
    super("APA");
  }

  @Override
  public String formatAuthorsList(List<Author> authors) {
    StringBuffer sb = new StringBuffer();
    for (Author a : authors) {
      sb.append((sb.length() > 0) ? ", " : "");
      sb.append(a.getLastName() + ", " + a.getInitial() + ".");
    }
    return sb.toString();
  }

  @Override
  public String formatReference(Article a) {
    return formatAuthorsList(a.getAuthors()) + " " +
        "(" + a.getYear() + "). " +
        a.getTitle() + ". " + a.getJournal() + ", " +
        a.getVolume() + "(" + a.getIssue() + ").";
  }
}
