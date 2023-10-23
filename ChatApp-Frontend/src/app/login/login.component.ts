import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent {

  signupDetails={
    "username":"",
    "firstName":"",
    "lastName":"",
    "email":"",
    "password":""
  }
  private signup_url:string="http://localhost:8080/register"
  


  login_page = true;

  loginDetails={
    username:"",
    password:""
  }
  

  private login_url:string="http://localhost:8080/login"
  
  


  constructor(private http:HttpClient, private router:Router) {
    
  }
  toggle_page(){
    console.log(this.login_page);
    this.login_page = !this.login_page;
  }

  login (){
    console.log(this.loginDetails);
    const data=JSON.stringify(this.loginDetails);
    const headers=new HttpHeaders({
      'Content-Type':'application/json',
      
    })

    this.http.post(this.login_url,data,{headers,responseType:'text'}).subscribe(response=>{
      console.log(data);
      localStorage.setItem("token","Bearer "+response)
        
        var url=localStorage.getItem("redirectUrl")
        if(url==null)
        {
          url="/home"
        }
        localStorage.removeItem("redirectUrl")
        this.router.navigate([url])
    })

  }

  signup(){
    console.log(this.signupDetails);
    const data=JSON.stringify(this.signupDetails);
    const headers=new HttpHeaders({
      'Content-Type':'application/json',
      
    })

    this.http.post(this.signup_url,data,{headers,responseType:'text'}).subscribe(response=>{
      console.log(data);
      localStorage.setItem("token","Bearer "+response)
        
        var url=localStorage.getItem("redirectUrl")
        if(url==null)
        {
          url="/home"
        }
        localStorage.removeItem("redirectUrl")
        this.router.navigate([url])
    })

  }

}
