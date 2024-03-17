import React from 'react';
import MenuIcon from '@material-ui/icons/Menu';
function Header(){
    return(
        <header style={header}>
            <MenuIcon/>
            <h2>Book Store</h2>
            <p></p>
        </header>
    );
}

const header = {
    background:"#337ab7",
    color: "#fff",
    padding:"10px",
    display:"flex",
    alignItems:'center',
    justifyContent:'space-between',

}
export default Header;