import React, { useState } from 'react';
import { useNavigate } from 'react-router-dom';
import authService from '../../services/auth.service';


function ActivateAccount() {
    const [token, setToken] = useState("");
    const [showError, setShowError] = useState(false);
    const navigate = useNavigate();

    function handleChange(event) {
        setToken(event.target.value);
    }

    function handleActivate(event) {
        event.preventDefault();
        authService.activateAccount(token).then(response => {
            navigate('/login');
        }).catch(error => {
            if (error.response && (error.response.status === 400 || error.response.status === 403 || error.response.status === 401)) {
                setShowError(true);
            }
        });
    }

    return (
        <div className="form-activate-container text-center h-75 w-50">
            <form className="form-activate mt-5" onSubmit={handleActivate}>
                <label htmlFor="activationCode" className="sr-only">Activation Code</label>
                <input type="text" id="activationCode" className="form-control" placeholder="Activation Code" required
                       autoFocus name="activationCode" onChange={handleChange} />
                {showError && <label className="text-danger">Invalid activation code</label>}
                <button className="btn btn-lg btn-primary btn-block" type="submit">Activate</button>
            </form>
        </div>
    );
}

export default ActivateAccount;
