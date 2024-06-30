import { BehaviorSubject } from 'rxjs';

const initialState = {
  token: localStorage.getItem('token'),
  role: localStorage.getItem('role'),
  userId: localStorage.getItem('userId'),
  username: localStorage.getItem('username')
}

const loggedUserSubject = new BehaviorSubject(initialState);

export const login = (user) => {
  loggedUserSubject.next(user);
};

export const logout = () => {
  localStorage.removeItem('token');
  localStorage.removeItem('role');
  localStorage.removeItem('userId');
  localStorage.removeItem('username');


  loggedUserSubject.next({
    token: null,
    role: null,
    userId: null,
    username: null
  });
};

export const loggedUser$ = loggedUserSubject.asObservable();
