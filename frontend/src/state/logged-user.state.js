import { BehaviorSubject } from 'rxjs';

const initialState = {
  token: localStorage.getItem('token'),
  role: localStorage.getItem('role'),
  userId: localStorage.getItem('userId')
}

const loggedUserSubject = new BehaviorSubject(initialState);

export const login = (user) => {
  loggedUserSubject.next(user);
};

export const logout = () => {
  localStorage.removeItem('token');
  localStorage.removeItem('role');
  localStorage.removeItem('userId');


  loggedUserSubject.next({
    token: null,
    role: null,
    userId: null
  });
};

export const loggedUser$ = loggedUserSubject.asObservable();
