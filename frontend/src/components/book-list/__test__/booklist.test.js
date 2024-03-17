import React from "react";
import ReactDOM, { unmountComponentAtNode } from "react-dom";
import { act } from "@testing-library/react";
import BookList from "../BookList";
import pretty from "pretty";
import renderer from "react-test-renderer";
import BookItem from "../../book-item/BookItem";

let container;

beforeEach(() => {
    container = document.createElement("div");
    document.body.appendChild(container);
});

afterEach(() => {
    unmountComponentAtNode(container);
    document.body.removeChild(container);
    container = null;
});

it("renders Books component correctly", () => {
    const fakeBooks = [
        {
            id: '0',
            title:'Title 0',
            author:'Author 0',
            country:'Country 0',
            genre:'Genre 0',
            year:'Year 0'
        },
        {
            id: '1',
            title:'Title 1',
            author:'Author 1',
            country:'Country 1',
            genre:'Genre 1',
            year:'Year 1'
        },
        {
            id: '2',
            title:'Title 2',
            author:'Author 2',
            country:'Country 2',
            genre:'Genre 2',
            year:'Year 2'
        },
    ];
    act(() => {
        ReactDOM.render(<BookList books={fakeBooks} />, container);
    });
    expect(container.textContent).toContain(fakeBooks[0].title);
    expect(container.textContent).toContain(fakeBooks[1].title);
    expect(container.textContent).toContain(fakeBooks[2].title);
});
