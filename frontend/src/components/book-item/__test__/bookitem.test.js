import React from "react";
import ReactDOM, {unmountComponentAtNode} from "react-dom";
import BookItem from "../BookItem";
import { act } from "@testing-library/react";
import pretty from "pretty";
//For Snapshots Tests
import renderer from "react-test-renderer";
import BookForm from "../../book-form/BookForm";
let container;
beforeEach(() => {
    container = document.createElement("div");
    document.body.appendChild(container);
});

afterEach(() => {
    unmountComponentAtNode(container)
    document.body.removeChild(container);
    container = null;
});

//
it("Renders Book Info without crashing and Data Fetching Test", async () => {
    const fakeBook = {
        id: '0',
        title:'Title 0',
        author:'Author 0',
        country:'Country 0',
        genre:'Genre 0',
        year:'Year 0'
    };
    /*jest.spyOn(global, "fetch").mockImplementation(() =>
          Promise.resolve({
              json: () => Promise.resolve(fakeBook)
          })
      );*/
    // Simulate an async call
    global.fetch = jest.fn().mockImplementation(() =>
        Promise.resolve({
            json: () => Promise.resolve(fakeBook),
        })
    );

    // Use the asynchronous version of act to apply resolved promises

    await act(async () => {
        ReactDOM.render(<BookItem book={fakeBook} />, container);
    });

    expect(container.querySelectorAll("td")[1].textContent).toBe(fakeBook.title);
    expect(container.textContent).toContain(fakeBook.author);
    expect(container.textContent).toContain(fakeBook.country);
    expect(container.textContent).toContain(fakeBook.genre);
    expect(container.textContent).toContain(fakeBook.year);

    // remove the mock to ensure tests are completely isolated
    global.fetch.mockClear();
    delete global.fetch;
});

it("Snapshot Test with book Prop",  () => {
    const fakeBook = {
        id: '1',
        title:'Title 1',
        author:'Author 1',
        country:'Country 1',
        genre:'Genre 1',
        year:'Year 1'
    };

    // Simulate an async call
    global.fetch = jest.fn().mockImplementation(() =>
        Promise.resolve({
            json: () => Promise.resolve(fakeBook),
        })
    );

    act( () => {
        ReactDOM.render(<BookItem book={fakeBook} />, container);
    });

    expect(container.querySelectorAll("td")[1].textContent).toBe(fakeBook.title);

    expect(container.textContent).toContain(fakeBook.title);
});

it("matches snapshot with Exactly one book callback prop", ()=>{
    const fakeBook = jest.fn();
    const tree = renderer.create(<BookItem book={fakeBook}/>).toJSON();
    expect(tree).toMatchSnapshot();

});