package com.eq3.bibliotheque.presentateur;

import com.eq3.bibliotheque.activities.BookListActivity;
import com.eq3.bibliotheque.modele.BookModel;

public class BookListPresentateur {

    private BookModel bookModel;
    private BookListActivity bookListActivity;

    public BookListPresentateur(BookModel bookModel, BookListActivity bookListActivity) {
        this.bookModel = bookModel;
        this.bookListActivity = bookListActivity;
    }

    public void fetchBooks() {
        if (bookModel != null && bookListActivity != null) {
            bookModel.fetchBooks(bookListActivity);
        }
    }
}