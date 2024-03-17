import React from "react";
import ReactDOM from 'react-dom';
import { unmountComponentAtNode} from 'react-dom';
import BookForm from "../BookForm";
import {act} from "@testing-library/react";

//for Snapshot testing
import  renderer from 'react-test-renderer';
let container;

beforeEach(()=>{
    container = document.createElement('div')
    document.body.appendChild(container);
});

afterEach(()=>{
    //clean up code after exiting
    unmountComponentAtNode(container);
    document.body.removeChild(container);
    container = null;
});

it('renders BookForm component with or without addBook function callback prop without crusing',  ()=> {
    const addBook = jest.fn();
    act(()=>{
        ReactDOM.render(<BookForm addBook={addBook}/>,container);
    });

});

it("matches snapshot with No Prop", ()=>{
    const tree= renderer.create(<BookForm/>).toJSON();
    expect(tree).toMatchSnapshot();

})

it("matches snapshot with Exactly one addBook callback prop", ()=>{
    const addBook = jest.fn();
    const tree = renderer.create(<BookForm addBook={addBook}/>).toJSON();
    expect(tree).toMatchSnapshot();

});