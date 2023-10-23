import { HttpClient, HttpHeaders } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-signup',
  templateUrl: './signup.component.html',
  styleUrls: ['./signup.component.css']
})
export class SignupComponent {
  signupDetails={
    "username":"",
    "firstName":"",
    "lastName":"",
    "email":"",
    "password":""
  }
  private url:string="http://localhost:8080/register"
  
  


  constructor(private http:HttpClient, private router:Router) {
    
  }


  onSubmit (){
    console.log(this.signupDetails);
    const data=JSON.stringify(this.signupDetails);
    const headers=new HttpHeaders({
      'Content-Type':'application/json',
      
    })

    this.http.post(this.url,data,{headers,responseType:'text'}).subscribe(response=>{
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
