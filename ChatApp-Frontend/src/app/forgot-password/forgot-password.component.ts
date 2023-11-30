import { HttpClient } from '@angular/common/http';
import { Component } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-forgot-password',
  templateUrl: './forgot-password.component.html',
  styleUrls: ['./forgot-password.component.css']
})
export class ForgotPasswordComponent {
  emailOrUsername!: string;
  recoveryCode!:string
  passwordForm=false;
  newPassword!: string
  constructor(private http:HttpClient,private router:Router){}

  toggleForm(){
    this.passwordForm=!this.passwordForm;
  }

  sendReconveryRequest(){
    console.log(this.emailOrUsername)
    this.http.post("http://localhost:8080/recovery-request", this.emailOrUsername).subscribe((response)=>{
      alert("Instructions sent to your email")
      this.toggleForm()
    })
  }
  updatePassword(){
    const data=new FormData()
    data.append("token",this.recoveryCode)
    data.append("newPassword",this.newPassword)
    data.append("username",this.emailOrUsername)
    this.http.post("http://localhost:8080/update-password", data).subscribe((response)=>{
      alert("Password updated")
      this.router.navigate(["/login"])
    })
  }

  backToLogin(){
    this.router.navigate(['/login'])
  }


}
