import { useState } from "react";
import { useNavigate } from "react-router-dom";
import authService from "../../services/auth.service";

function Register(props) {
    const [requestBody, setRequestBody] = useState({
        'userEmail': "",
        'password': "",
        'firstname': "",
        'lastname': "",
        'phoneNumber': ""

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
        authService.register(requestBody).then(response => {
            navigate('/activate-account');
        }).catch(error => {
            if (error.response.status === 400 || error.response.status === 403 || error.response.status === 401) {
                setShowError(true);
            }
        })
    }

    return (
        <div className="form-signin-container text-center h-75 w-50">
            <form className="form-signin mt-5" onSubmit={event => handleLogin(event)}>
                <label htmlFor="text" className="sr-only">Email</label>
                <input type="email" id="inputEmail" className="form-control" placeholder="Email" required
                       autoFocus name="userEmail" onChange={(event => handleChange(event))}   />
                <label htmlFor="inputPassword" className="sr-only">Password</label>
                <input type="password" id="inputPassword" className="form-control" placeholder="Password"
                       required name="password" onChange={(event => handleChange(event))} />
                <label htmlFor="inputFirstName" className="sr-only">First name</label>
                <input type="text" id="inputFirstName" className="form-control" placeholder="First name" required
                        name="firstname" onChange={(event => handleChange(event))}   />
                <label htmlFor="inputLastName" className="sr-only">Last name</label>
                <input type="text" id="inputLastName" className="form-control" placeholder="Last name" required
                        name="lastname" onChange={(event => handleChange(event))}   />
                <label htmlFor="inputPhoneNumber" className="sr-only">Phone Number</label>
                <input type="text" id="inputPhoneNumber" className="form-control" placeholder="Phone Number" required
                        name="phoneNumber" onChange={(event => handleChange(event))}   />
                {showError?<label className="text-danger">Data invalid</label>:null}

                <button className="btn btn-lg btn-primary btn-block" type="submit">Register</button>

            </form>
        </div>
    )
}


export default Register;