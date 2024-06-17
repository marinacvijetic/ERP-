import { useState } from 'react';
import authService from '../../services/auth.service';
import { login } from '../../state/logged-user.state';
import { useNavigate } from 'react-router-dom';


function Login(props){

    const[requestBody, setRequestBody] = useState({
        'userEmail': "",
        'password': ""
    });
    
    const [showError, setShowError] = useState(false);
    const navigate = useNavigate();

    function handleChange(event) {
        setRequestBody({
            ...requestBody,
            [event.target.name]: event.target.value
        })
    }

    function handleLogin(event) {
        event.preventDefault();
        authService.login(requestBody)
        .then((response) => {
            if(response.status === 200){
                console.log("Full response:", response.data);
                localStorage.setItem('token', response.data.token);
                login({
                    token: response.data.token
                });
                navigate('/');
            }
        }).catch(error => {
            if (error.response && (error.response.status === 400 || error.response.status === 403 || error.response.status === 401)) {
                setShowError(true);
            }
        });
    }



  return (
    <div className="form-signin-container text-center h-75 w-50">
        <form className="form-signin mt-5" onSubmit={event => handleLogin(event)}>
        <label htmlFor="text" className="sr-only">Email</label>
        <input type="text" id="inputEmail" className="form-control" placeholder="Email" required
               autoFocus name="userEmail" onChange={(event => handleChange(event))}   />
        <label htmlFor="inputPassword" className="sr-only">Password</label>
        <input type="password" id="inputPassword" className="form-control" placeholder="Password"
               required name="password" onChange={(event => handleChange(event))} />
        {showError?<label className="text-danger">Username or password is not correct</label>:null}

        <button className="btn btn-lg btn-primary btn-block" type="submit">Login</button>
    </form>
    </div>
)}


export default Login;