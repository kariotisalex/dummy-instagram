import {inject, Injectable} from '@angular/core';
import {User} from "./interfaces/user";
import {HttpClient} from "@angular/common/http";



@Injectable({
  providedIn: 'root'
})
export class UserService {

  user : User
    = {
    uid:"12cfaf83-6928-4bd6-9886-a17b77c5e626",
    username:"asdf"
  }
  ;

  constructor(
    private http: HttpClient
  ) { }

  logIn(username: String, password: String){
    const body = { username: username,
                   password: password }
    return this.http.post<User>("/api/user/login", body);
  }

  loggedinUser(x : User){
    this.user = x;
  }

  signup(username: String, password: String){
    const body = { username: username,
                   password: password }
    return this.http.post("/api/user/register", body);
  }

  changePassword(uid : String, currentPassword : String, newPassword : String){
    const body = { current : currentPassword,
                   new     : newPassword     }
    return this.http.put(`/api/user/${uid}/password`, body);
  }

  delete(uid : String){
    return this.http.delete(`/api/user/${uid}`);

  }

  logout(): boolean{
    this.user = {
      uid:'',
      username:''
    };
    return true;
  }



  getUid(){
    return this.user.uid;
  }
  getUsername(){
    return this.user.username;
  }
  getUser(){
    return this.user;
  }

}
