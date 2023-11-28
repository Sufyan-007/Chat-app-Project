import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable, filter, map } from 'rxjs';
import { User } from '../interface/user';

@Injectable({
  providedIn: 'root'
})
export class UserService {

  constructor(private http:HttpClient) { }

  findUsers(username:string):Observable<User[]> {
    const url="http://localhost:8080/users"
    const headers = new HttpHeaders({
      'Content-Type': 'application/plain',
      'Authorization': String(localStorage.getItem('token'))
    })
    return  this.http.post<User[]>(url,username, {headers: headers}).pipe(map(response => {
      
      response = response.filter((user)=>{
        return user.username !== localStorage.getItem('username') as string;
         
      })
      console.log(response)
      return response
    }))
  }

  findUser(username:string):Observable<User> {
    const url="http://localhost:8080/user/"+username
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
      'Authorization': String(localStorage.getItem('token'))
    })
    return this.http.get<User>(url, {headers})
  }


}
