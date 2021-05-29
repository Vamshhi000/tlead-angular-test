import { Injectable } from '@angular/core';
import { BehaviorSubject, Observable, Subject } from 'rxjs';
import { User } from './model/form';

@Injectable({
  providedIn: 'root'
})
export class commonServiceService {

  constructor() { }


  userSubject : Subject<User> = new Subject();
  userSubject$: Observable<User> = this.userSubject.asObservable();

  usersBeSubject = new BehaviorSubject<User>(null);
  usersob$ : Observable<User> = this.usersBeSubject.asObservable();

  otpBeSubject = new BehaviorSubject<boolean>(null);
  otpsob$ : Observable<boolean> = this.otpBeSubject.asObservable();
  numotpBeSubject = new BehaviorSubject<boolean>(null);
  numotpsob$ : Observable<boolean> = this.numotpBeSubject.asObservable();


  otpUserBeSubject = new BehaviorSubject<User>(null);
  otpUserusersob$ : Observable<User> = this.otpUserBeSubject.asObservable();

  verifiedBeSubject = new BehaviorSubject<boolean>(null);
  verifiedsob$ : Observable<boolean> = this.verifiedBeSubject.asObservable();


  messageBeSubject = new BehaviorSubject<any>(null);
  messageBe$ : Observable<any> = this.messageBeSubject.asObservable();
}
