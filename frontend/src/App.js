import './App.css';
import { useEffect, useState } from 'react';
import { Link, Route, Routes } from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";


import ProductList from './components/product/product-list';
import Login from './components/login/Login';
import { loggedUser$, logout } from './state/logged-user.state';


const  App = () => {

  const[user, setUser] = useState(null);

  useEffect(() => {
    const subscription = loggedUser$.subscribe(_user => {
      setUser(_user);
    });

    return () => {
      subscription.unsubscribe();
    };
  }, []);

  const logoutUser = () =>{
    logout();
  }


  return (
  <div>  
    <nav className="navbar navbar-expand navbar-dark bg-dark">
      <div>
      <li className="nav-item">
        {user?.userId !== null ? 
          <button className="btn nav-link" onClick={logoutUser}>Log out</button>
           :
          <Link to={"/auth/login"} className="nav-link">
            Login
          </Link>
        }
        </li>
      </div>
    </nav> 
    <div className="container mt-3">
    <Routes>
      <Route path='/' element={<ProductList/>}/>
      <Route path='/login' element={<Login/>}/>
    </Routes>
    </div>
  </div>
  );
}

export default App;
