import java.util.List;

public class IEEArticleFormatter extends ArticleFormatter {
  public IEEArticleFormatter() {
    super("IEE");
  }

  @Override
  public String formatAuthorList(List<Author> authors) {
    StringBuffer sb = new StringBuffer();
    for (Author a : authors) {
      sb.append((sb.length() > 0) ? ", " : "");
      sb.append(a.getInitial() + ". ", a.getLastName() + ",");
    }
    return sb.toString();
  }

  @Override
  public String formatReference(Article a) {
    return formatAuthorList(a.getAuthors()) + " " +
        a.getTitle() + ", " + a.getJournal() + ", " +
        a.getVolume() + ", " + a.getIssue() + ", " +
        a.getYear() + ". ";
  }
}
