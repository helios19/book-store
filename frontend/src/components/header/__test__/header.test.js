import React from "react";
import ReactDOM from 'react-dom';
import Header from '../Header';
import {act} from "@testing-library/react";
import renderer from "react-test-renderer";
import BookItem from "../../book-item/BookItem";

let container;

//Context Management
beforeEach(()=>{
    container = document.createElement('div');
    document.body.appendChild(container);
});

afterEach(()=>{
    document.body.removeChild(container);
    container = null //resetting the DOM to empty
});

it("renders Header without crashing", ()=>{
    //Test if Component is rendering
    act(()=>{
        ReactDOM.render(<Header/>,container);
    });
    expect(container.textContent).toBe("Book Store");
});

it("matches snapshot with no callback prop", ()=>{
    const tree = renderer.create(<Header/>).toJSON();
    expect(tree).toMatchSnapshot();

});