import './App.css';
import { useEffect, useState } from 'react';
import { Link, Route, Routes } from 'react-router-dom';
import "bootstrap/dist/css/bootstrap.min.css";

import CategoriesList from './components/category/category-list.component';
import OrderList from './components/order/order-list.component';
import UsersList from './components/user/user-list.component';
import CartList from './components/cart/cart-list.component';
import OrderArrivalDetailsList from './components/orderArrivalDetails/arrivalDetails-list.component';
import PaymentList from './components/payment/payment-list.component';
import ProductList from './components/product/product-list';
import Component403 from './components/error/403.component';
import Component404 from './components/error/404.component';
import Login from './components/login/Login';
import { loggedUser$, logout } from './state/logged-user.state';
import Register from './components/register/Register';
import ActivateAccount from './components/activateAccount/activate-account';


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
    <Link to={"/product"} className="navbar-brand">
      Lilly's Bakery
    </Link>
    <div className="navbar-nav mr-auto">

    {user && user.role === 'ADMIN' &&
      <li className="nav-item">
        <Link to={"/category"} className="nav-link">
          Categories
        </Link>
      </li>}

      <li className="nav-item">
        <Link to={"/product"} className="nav-link">
          Products
        </Link>
      </li>


      {user && user.role === 'ADMIN' && 
      <li className="nav-item">
        <Link to={"/orderArrivalDetails"} className="nav-link">
          Order Arrival Details
        </Link>
      </li>}

      {user && user.role === 'ADMIN' &&
      <li className="nav-item">
        <Link to={"/order"} className="nav-link">
          Orders
        </Link>
      </li>}

      {user && user.role === 'ADMIN' && <li className="nav-item">
        <Link to={"/payments"} className="nav-link">
          Payments
        </Link>
      </li>}

      {user && user.role === 'ADMIN' && <li className="nav-item">
        <Link to={"/user"} className="nav-link">
          Users
        </Link>
      </li>}

      {user && user.role === 'USER' && <li className="nav-item">
        <Link to={"/cart"} className="nav-link">
          Cart
        </Link>
      </li>}

      <li className="nav-item">
      {user?.userId !== null ? 
        <button className="btn nav-link" onClick={logoutUser}>Log out</button>
         :
        <Link to={"/login"} className="nav-link">
          Login
        </Link>
      }
      </li>
      {user?.userId === null  &&
      <li className="nav-item">
      <Link to={"/register"} className="nav-link">
        Register
      </Link>
      </li>}
    </div>
  </nav>

      <div className="container mt-3">
        <Routes>
          <Route path="/" element={<ProductList/>} />

          <Route path="/category" element={<CategoriesList/>} />
          <Route path="/user" element={<UsersList/>} />
          <Route path="/product" element={<ProductList/>} />
          <Route path="/cart" element={<CartList/>} />
          <Route path="/orderArrivalDetails" element={<OrderArrivalDetailsList/>} />
          <Route path="/order" element={<OrderList/>} />
          <Route path="/payments" element={<PaymentList />} />
          
          <Route path="/login" element={<Login />} />
          <Route path="/activate-account" element={<ActivateAccount />} />
          <Route path="/register" element={<Register />} />

          <Route path="/403" element={<Component403 />} />
          <Route path="*" element={<Component404 />} />

        </Routes>
      </div>
    </div>
  );
};

export default App;
