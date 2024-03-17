import logo from './logo.svg';
import './App.css';

import React, {Component, useCallback} from "react";
import ReactDOM from "react-dom";
import Header from './components/header/Header';
import BookForm from './components/book-form/BookForm';
import BookList from './components/book-list/BookList';
import axios from "axios";

export class App extends Component {
  constructor(props) {
    super(props);
    this.state = {
      books:[{
          id:0,
          title:'Title test',
          author:'Author test',
          country:'Country test',
          genre:'Genre test',
          year:'Year test'
      }
      ],
      successMessage:'',
      errorMessage:''
    }
    axios.defaults.baseURL = 'http://localhost:8081';
  }

  componentDidMount() {
      this.getAllBooks();
  }

  // Getting All Books
  getAllBooks = () => {
      axios.get('/books/?sort=title,asc')
          .then(response => this.setState({books:response.data})
          )
          .catch((error) => {
              if (error.response) {
                  console.error('Server Error:', error.response.status);
              }
              console.error('Error:', error.message);
              this.setState({books:this.state.books, errorMessage :'Error while loading books: ' + JSON.stringify(error.message)})
          });
  }

  //Deleting Book
  removeBook = (id) =>{
    axios.delete(`/books/${id}`)
        .then(
            (response) => {
            //     this.setState( //Updating UI
            //     {books: [...this.state.books.filter(
            //           book => book.id !== id
            //       )]}
            // )
            this.getAllBooks();

            }
        )
        .catch((error) => {
            if (error.response) {
                console.error('Server Error:', error.response.status);
            }
            console.error('Error:', error.message);
            this.setState({books:this.state.books, errorMessage :'Error while deleting books: ' + JSON.stringify(error.message)})
        });
  }

  //Saving Book
  addBook = (newBook) =>{
    axios.post('/books/',newBook)
        .then(
            (response) =>{
              console.log(response.data);
              this.getAllBooks();
                // this.setState({books:[...this.state.books,response.data]})
            }
        )
        .catch((error) => {
            if (error.response) {
                console.error('Server Error:', error.response.status);
            }
            console.error('Error:', error.message);
            this.setState({books:this.state.books, errorMessage :'Error while creating books: ' + JSON.stringify(error.message)})
        });
  }

  //Updating Book
  updateBook = (book) => {
      axios.put('/books/' + book.id, book)
          .then(
              (response) => {
                  console.log(response.data);
                  this.getAllBooks();
              }
          )
          .catch((error) => {
              if (error.response) {
                  console.error('Server Error:', error.response.status);
              }
              console.error('Error:', error.message);
              this.setState({books:this.state.books, errorMessage :'Error while updating books: ' + JSON.stringify(error.message)})
          });
  }

  // Book availability update
  bookAvailabilityChange = (book,event) => {
      book.borrowed = event.target.checked;
      this.updateBook(book)
  };

  render() {
    return (
        <div className="container">
          <Header/>
          <BookForm addBook={this.addBook}/>
          <BookList books={this.state.books} removeBook={this.removeBook} bookAvailabilityChange={this.bookAvailabilityChange}/>
        </div>
    );
  }
}


export default App;

ReactDOM.render(<App />, document.querySelector("#root"));