package vn.kms.launch.cleancode.example;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

public class MyBook {
    private String authorName;
    private String bookName;
    private Integer pageCount;
    private Integer publishingYear;

    public MyBook(String authorName, String bookName, Integer pageCount, Integer publishingYear) {
        this.authorName = authorName;
        this.bookName = bookName;
        this.pageCount = pageCount;
        this.publishingYear = publishingYear;
    }

    public String getAuthorName() {
        return authorName;
    }

    public void setAuthorName(String authorName) {
        this.authorName = authorName;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public Integer getPageCount() {
        return pageCount;
    }

    public void setPageCount(Integer pageCount) {
        this.pageCount = pageCount;
    }

    public Integer getPublishingYear() {
        return publishingYear;
    }

    public void setPublishingYear(Integer publishingYear) {
        this.publishingYear = publishingYear;
    }

    public void showInfo(){
        System.out.println("Information about books:");
    }

    public Integer countOfYear(Integer publishingYear){
        Calendar now = Calendar.getInstance();
        int currentYear = now.get(Calendar.YEAR);
        return currentYear = publishingYear;
    }

    public List<MyBook> booksOfMyFavouriteAuthor(String favouriteAuthor, List<MyBook> books){
        List<MyBook> booksOfMyFavouriteAuthor = new ArrayList<>();
        for (MyBook item : books){
            if(item.authorName.equals(favouriteAuthor)) {
                booksOfMyFavouriteAuthor.add(item);
            }
        }
        return booksOfMyFavouriteAuthor;
    }
}
