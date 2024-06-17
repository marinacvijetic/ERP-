import { BehaviorSubject } from 'rxjs';

const initialState = {
  token: localStorage.getItem('token')
}

const loggedUserSubject = new BehaviorSubject(initialState);

export const login = (user) => {
  loggedUserSubject.next(user);
};

export const logout = () => {
  localStorage.removeItem('token');


  loggedUserSubject.next({
    token: null
  });
};

export const loggedUser$ = loggedUserSubject.asObservable();
