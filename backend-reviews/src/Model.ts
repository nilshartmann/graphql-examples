export interface Review {
  id: string;
  bookId: string;
  review: string;
}

export const REVIEWS: Review[] = [
  {
    id: "R1",
    bookId: "B1",
    review:
      "Mus elit natoque sem sociis penatibus tellus, magnis lorem hac sagittis nascetur lacus, justo ornare urna himenaeos phasellus. Odio elit dapibus habitasse blandit facilisi ornare justo, litora egestas nostra eu sapien vitae, gravida fusce condimentum curae vulputate faucibus. "
  },
  {
    id: "R2",
    bookId: "B1",
    review:
      "Nascetur taciti lacus massa pharetra eu rutrum, sodales non ut aliquam euismod netus, facilisis elit leo ridiculus sem."
  },
  {
    id: "R3",
    bookId: "B2",
    review:
      "Placerat cubilia euismod sem urna ligula montes nullam, primis ridiculus magnis facilisi luctus eget, condimentum malesuada semper ipsum justo pretium. "
  },
  {
    id: "R4",
    bookId: "B4",
    review:
      "Pretium justo et venenatis quis aliquam sociosqu sagittis lorem non nostra phasellus elementum, conubia at in potenti natoque tortor massa eget nulla accumsan sed."
  },
  {
    id: "R5",
    bookId: "B1",
    review:
      "Varius malesuada ligula facilisi nibh torquent lectus aliquam himenaeos class vulputate curae, rutrum fames quis sollicitudin eleifend porta cum suscipit neque blandit."
  },
  { id: "R6", bookId: "B2", review: "Etiam ultricies nisi vel augue. Curabitur ullamcorper ultricies nisi." },
  {
    id: "R7",
    bookId: "B3",
    review:
      "Nam eget dui. Etiam rhoncus. Maecenas tempus, tellus eget condimentum rhoncus, sem quam semper libero, sit amet adipiscing sem neque sed ipsum."
  },
  { id: "R8", bookId: "B2", review: "Nullam quis ante. Etiam sit amet orci eget eros faucibus tincidunt. Duis leo." },
  { id: "R9", bookId: "B1", review: "Aenean commodo ligula eget dolor. Aenean massa." },
  {
    id: "R10",
    bookId: "B3",
    review:
      "Cum sociis natoque penatibus et magnis dis parturient montes, nascetur ridiculus mus. Donec quam felis, ultricies nec, pellentesque eu, pretium quis, sem. Nulla consequat massa quis enim."
  },
  { id: "R11", bookId: "B2", review: "In enim justo, rhoncus ut, imperdiet a, venenatis vitae, justo." }
];
