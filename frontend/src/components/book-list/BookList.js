import React, {Component} from 'react';
import BookItem from "../book-item/BookItem";
import PropTypes from "prop-types";

class BookList extends Component{

    render() {

        return (

            <div className="panel panel-default">
                <div className="panel-heading"><span className="lead"></span></div>
                <div className="panel-body">
                    <div className="table-responsive">
                        <table class="table table-hover">
                            <thead>
                            <tr>
                                <th></th>
                                <th>Title</th>
                                <th>Author</th>
                                <th>Country</th>
                                <th>Genre</th>
                                <th>Year</th>
                                <th>Borrowed</th>
                                <th>#</th>
                            </tr>
                            </thead>
                            <tbody>
                            {this.props.books.map(book => (<BookItem book={book} key={book.id} removeBook = {this.props.removeBook} bookAvailabilityChange = {this.props.bookAvailabilityChange}/>))}
                            </tbody>
                        </table>
                    </div>
                </div>
            </div>
        )
    }
}

//PropTypes
BookList.propTypes = {
    books:PropTypes.array.isRequired
}
export default BookList;