import React, {Component} from 'react';
import PropTypes from "prop-types";
class BookForm extends Component{
    constructor(props) {
        super(props);
        this.state = {
            title:'',
            author:'',
            country:'',
            genre:'',
            year:'',
            borrowed: false
        }
        //If you dont use arrow function you will have to manually bind like this
        //If you dont bind you wont be able to access items in the state of this component because it wont be recognised in lifecycle
        //this.onChange = this.onChange.bind(this);
        this.onSubmit = this.onSubmit.bind(this);
    }

    onChange =(e) =>{
        //This is what you do for individual target
        //this.setState({name:e.target.value});
        //But if you have plenty
        this.setState({[e.target.name]:e.target.value})
    }

    onSubmit(e){
        e.preventDefault();
        //Copying state object to newBook
        let newBook = this.state;
        this.props.addBook(newBook);
        //Resetting the fields
        this.setState({
            title:'',
            author:'',
            country:'',
            genre:'',
            year:''
        });
    }
    render() {
        return(
            <form onSubmit={this.onSubmit}>
                <div style={{display:'flex' }}>
                    <input
                        type = "text"
                        name = "title"
                        placeholder="Title"
                        style={leftInput}
                        className="form-control input-sm"
                        value={this.state.title}
                        onChange={this.onChange}
                        required
                    />

                    <input
                        type = "text"
                        name = "author"
                        placeholder="Author"
                        style={rightInput}
                        className="form-control input-sm"
                        value={this.state.author}
                        onChange={this.onChange}
                        required
                    />
                </div>
                <br/>
                <div style={{display:'flex', }}>
                    <input
                        type = "text"
                        name = "genre"
                        placeholder="Genre"
                        style={leftInput}
                        className="form-control input-sm"
                        value={this.state.genre}
                        onChange={this.onChange}
                        required
                    />

                    <input
                        type = "text"
                        name = "year"
                        placeholder="Year"
                        style={rightInput}
                        className="form-control input-sm"
                        value={this.state.year}
                        onChange={this.onChange}
                        required
                    />
                </div>
                <br/>
                <div style={{display:'flex' }}>
                    <input
                        type = "text"
                        name = "country"
                        placeholder="Country"
                        style={leftInput}
                        className="form-control input-sm"
                        value={this.state.country}
                        onChange={this.onChange}
                        required
                    />

                    <span style={rightInput}/>
                </div>
                <br/>
                <br/>
                <input
                    type="submit"
                    value="Submit"
                    className="btn btn-primary btn-sm"
                />
                <br/>
                <br/>
            </form>

        // <div className="panel panel-default">
        //     <div className="panel-heading">
        //         <span className="lead">Book Form</span>
        //     </div>
        //
        //     <div className="panel-body">
        //         <div className="formcontainer">
        //             <form onSubmit={this.onSubmit} name="myForm" className="form-horizontal">
        //                 <div className="row">
        //                     <div className="form-group col-md-12">
        //                         <label className="col-md-2 control-label" htmlFor="title">Title</label>
        //                         <div className="col-md-10">
        //                             <input type="text" value={this.state.title} onChange={this.onChange} id="title"
        //                                    className="form-control input-sm"
        //                                    placeholder="Enter book title" required/>
        //                         </div>
        //                     </div>
        //                 </div>
        //
        //                 <div className="row">
        //                     <div className="form-group col-md-12">
        //                         <label className="col-md-2 control-label" htmlFor="author">Author</label>
        //                         <div className="col-md-10">
        //                             <input type="text" value={this.state.author} onChange={this.onChange} id="author"
        //                                    className="form-control input-sm"
        //                                    placeholder="Enter book author" required/>
        //                         </div>
        //                     </div>
        //                 </div>
        //
        //                 <div className="row">
        //                     <div className="form-group col-md-12">
        //                         <label className="col-md-2 control-label" htmlFor="genre">Genre</label>
        //                         <div className="col-md-10">
        //                             <input type="text" value={this.state.genre} onChange={this.onChange} id="genre"
        //                                    className="form-control input-sm"
        //                                    placeholder="Enter book genre" required/>
        //                         </div>
        //                     </div>
        //                 </div>
        //
        //                 <div className="row">
        //                     <div className="form-group col-md-12">
        //                         <label className="col-md-2 control-label" htmlFor="year">Year</label>
        //                         <div className="col-md-10">
        //                             <input type="text" value={this.state.year} onChange={this.onChange} id="year"
        //                                    className="form-control input-sm"
        //                                    placeholder="Enter book year" required/>
        //                         </div>
        //                     </div>
        //                 </div>
        //
        //                 <div className="row">
        //                     <div className="form-group col-md-12">
        //                         <label className="col-md-2 control-label" htmlFor="country">Country</label>
        //                         <div className="col-md-10">
        //                             <input type="text" value={this.state.country} onChange={this.onChange} id="country"
        //                                    className="form-control input-sm"
        //                                    placeholder="Enter book country" required/>
        //                         </div>
        //                     </div>
        //                 </div>
        //
        //                 <div className="row">
        //                     <div className="form-actions floatRight">
        //                         <input type="submit" value="Submit" className="btn btn-primary btn-sm"/>
        //                     </div>
        //                 </div>
        //             </form>
        //         </div>
        //     </div>
        // </div>

    )
    }
}

const leftInput = {
    flex:'5',
    padding:'5px',
    margin:'10px 10px 0px 0px'
}

const rightInput = {
    flex:'5',
    padding:'5px',
    margin:'10px 0px 0px 10px'
}

const rightBtnInput = {
    flex:'5',
    padding:'5px',
    margin:'10px 0px 0px 10px'
}

BookForm.propTyoes = {
    addBook:PropTypes.func.isRequired,
}

export default BookForm;