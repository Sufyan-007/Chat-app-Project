import { HttpClient, HttpErrorResponse, HttpHeaders } from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  ngOnInit(): void {
    if(localStorage.getItem("token") != null){
      console.log("Init")
      this.router.navigate(["/home"]);
    }
    else{
      console.log("else")
    }
  }

  constructor(private http:HttpClient, private router:Router) {
    
  }

  signupDetails={
    "username":"",
    "name":"",
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
  
  


  
  toggle_page(){
    console.log(this.login_page);
    this.login_page = !this.login_page;
  }

  login (loginForm:NgForm){
    // loginForm.form.controls['username'].setErrors({'incorrect': true});
    // console.log(loginForm.form.controls['username'].invalid);
    
    console.log(this.loginDetails);
    const data=JSON.stringify(this.loginDetails);
    const headers=new HttpHeaders({
      'Content-Type':'application/json',
      
    })

    this.http.post(this.login_url,data,{headers,responseType:'text'}).subscribe(response=>{
        console.log(data);
        localStorage.setItem("token","Bearer "+response)
        localStorage.setItem("username",this.loginDetails.username.toLowerCase())
        var url=localStorage.getItem("redirectUrl")
        if(url==null){
          url="/home"
        }
        localStorage.removeItem("redirectUrl")
        this.router.navigate([url])
      },
      error=>{
        if(error.status==401){
          loginForm.form.setErrors({'incorrect':true});
        }
      }

    );

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
