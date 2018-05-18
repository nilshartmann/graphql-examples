

/* tslint:disable */
// This file was automatically generated and should not be edited.

// ====================================================
// GraphQL query operation: BooksAppQuery
// ====================================================

export interface BooksAppQueryResult_booksServiceStatus {
  name: string;
  javaVersion: string;
  uptime: string;
}

export interface BooksAppQueryResult_reviewsServiceStatus {
  name: string;
  nodeJsVersion: string;
  uptime: string;
}

export interface BooksAppQueryResult_books_reviews {
  id: string;      // An immutable unique Id
  author: string;  // Who has written this review?
  review: string;  // The review itself
}

export interface BooksAppQueryResult_books {
  id: string;                              // Unique, immutable Id, that identifies this Book
  title: string;                           // The title of the book
  author: string;                          // Author of the book
  reviews: BooksAppQueryResult_books_reviews[];  // All Reviews for this book
}

export interface BooksAppQueryResult {
  booksServiceStatus: BooksAppQueryResult_booksServiceStatus;      // Returns some information about the running **Books** process
  reviewsServiceStatus: BooksAppQueryResult_reviewsServiceStatus;  // Returns health information about the running **Review** process
  books: BooksAppQueryResult_books[];                              // Returns all books in our store
}

//==============================================================
// START Enums and Input Objects
// All enums and input objects are included in every output file
// for now, but this will be changed soon.
// TODO: Link to issue to fix this.
//==============================================================

//==============================================================
// END Enums and Input Objects
//==============================================================