package android.com.mmassignment.model;

/**
 * Created by Ajit on 6/14/2016.
 */

public class Comment {
    private String commentDate;
    private String commentStr;

    public Comment(String commentDate, String commentStr){
        setCommentStr(commentStr);
        setCommentDate(commentDate);
    }

    public String getCommentDate() {
        return commentDate;
    }

    public void setCommentDate(String commentDate) {
        this.commentDate = commentDate;
    }

    public String getCommentStr() {
        return commentStr;
    }

    public void setCommentStr(String comment) {
        this.commentStr = comment;
    }
}
