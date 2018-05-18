import * as React from "react";

import {
  BooksAppQueryResult_books as BookData,
  BooksAppQueryResult_books_reviews as BookReviewData
} from "./__generated__/BooksAppQuery";

interface ReviewProps {
  review: BookReviewData;
}

const Review = ({ review: { id, author, review } }: ReviewProps) => (
  <em>
    <b>{author}</b>: {review}
  </em>
);

interface BookProps {
  book: BookData;
}
const Book = ({ book: { id, reviews, author, title } }: BookProps) => (
  <div>
    <h3>{author}</h3>
    <h1>{title}</h1>
    {reviews.map(review => <Review key={review.id} review={review} />)}
  </div>
);

interface BookListProps {
  books: BookData[];
}
const BookList = ({ books }: BookListProps) => <div>{books.map(book => <Book key={book.id} book={book} />)}</div>;

export default BookList;
