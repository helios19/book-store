import React, {Component} from 'react';
import PropTypes from "prop-types";
import BookTwoToneIcon from '@material-ui/icons/BookTwoTone';
import DeleteIcon from '@material-ui/icons/Delete';
import IconButton from '@material-ui/core/IconButton';
import Checkbox from '@material-ui/core/Checkbox';
class BookItem extends Component{

    //dynamic Styling
    infoStyle = () =>{
        return {
            backgroundColor: (this.props.book.id %2) === 0? '#c8d4f7cc' :'',
            padding:'10px',
            borderBottom: '1px #ccc dotted',
            display:'flex',
            alignItems:'center',
            justifyContent:'space-between',
            justifyItems: 'flex-start',
        }
    }

    render() {
        //Destructuring instead of using this.props.book.<variable> you can now use name | surname
        const {id} = this.props.book;
        return(

            <tr>
                <td>
                    <div style={iconBook}>
                        <BookTwoToneIcon style={{color:"#138a04", margin:"0px 10px 0px 0px"}}/>
                    </div>
                </td>
                <td>{this.props.book.title}</td>
                <td>{this.props.book.author}</td>
                <td>{this.props.book.country}</td>
                <td>{this.props.book.genre}</td>
                <td>{this.props.book.year}</td>
                <td>
                    <div>
                        <Checkbox
                            checked={this.props.book.borrowed}
                            onChange={(e) =>this.props.bookAvailabilityChange(this.props.book,e)}
                        />
                    </div>
                </td>
                <td>
                    <div style={buttons}>
                        {/*<IconButton color="secondary" onClick={this.props.removeBook.bind(this,id )} >  Because binding in faling in Jest i have used arrow function binding*/}
                        <IconButton color="secondary" onClick={(e) =>this.props.removeBook(id,e)} >
                            <DeleteIcon />
                        </IconButton>
                    </div>
                </td>

            </tr>

        );
    }
}

const iconBook = {
    display: 'flex',
    justifyContent:'space-between',

}

const buttons = {
    display: 'flex'
}
//PropTypes
BookItem.propTypes = {
    book : PropTypes.object.isRequired
}
export  default  BookItem;