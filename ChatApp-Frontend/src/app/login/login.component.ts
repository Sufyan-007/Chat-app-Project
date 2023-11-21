import {
  HttpClient,
  HttpErrorResponse,
  HttpHeaders,
} from '@angular/common/http';
import { Component, OnInit, ViewChild } from '@angular/core';
import { NgForm } from '@angular/forms';
import { Router } from '@angular/router';
import { Validator } from '@angular/forms';
import { catchError } from 'rxjs';
@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css'],
})
export class LoginComponent implements OnInit {
  ngOnInit(): void {
    if (localStorage.getItem('token') != null) {
      console.log('Init');
      this.router.navigate(['/home']);
    } else {
      console.log('else');
    }
  }

  constructor(private http: HttpClient, private router: Router) {}

  signupDetails = {
    username: '',
    name: '',
    email: '',
    password: '',
  };
  private signup_url: string = 'http://localhost:8080/register';

  login_page = true;

  loginDetails = {
    username: '',
    password: '',
  };

  private login_url: string = 'http://localhost:8080/login';

  toggle_page() {
    console.log(this.login_page);
    this.login_page = !this.login_page;
  }

  login(loginForm: NgForm) {
    // loginForm.form.controls['username'].setErrors({'incorrect': true});
    // console.log(loginForm.form.controls['username'].invalid);

    console.log(this.loginDetails);
    const data = JSON.stringify(this.loginDetails);
    const headers = new HttpHeaders({
      'Content-Type': 'application/json',
    });

    this.http
      .post(this.login_url, data, { headers, responseType: 'text' })
      .subscribe(
        (response) => {
          console.log(data);
          localStorage.setItem('token', 'Bearer ' + response);
          localStorage.setItem(
            'username',
            this.loginDetails.username.toLowerCase()
          );
          var url = localStorage.getItem('redirectUrl');
          if (url == null) {
            url = '/home';
          }
          localStorage.removeItem('redirectUrl');
          this.router.navigate([url]);
        },
        (error) => {
          if (error.status == 401) {
            loginForm.form.setErrors({ incorrect: true });
          }
        }
      );
  }

  profilePicture = null;

  signup() {
    console.log(this.signupDetails);
    const body = JSON.stringify(this.signupDetails);
    const headers = new HttpHeaders({});
    const data = new FormData();
    data.append('body', body);
    if (this.profilePicture) {
      data.append('file', this.profilePicture);
    }

    this.http
      .post(this.signup_url, data, { headers, responseType: 'text' })
      .pipe(
        catchError((error) => {
          if (error.status===409){
            alert("Username already in use")
          } return error;
        })
      )
      .subscribe((response: any) => {
        localStorage.setItem('token', 'Bearer ' + response);
        localStorage.setItem('username', this.signupDetails.username.toLowerCase());

        var url = localStorage.getItem('redirectUrl');
        if (url == null) {
          url = '/home';
        }
        localStorage.removeItem('redirectUrl');
        this.router.navigate([url]);
      });
  }

  onFileSelected(event: any) {
    const file = event.target.files[0];
    const allowedType = ['image/jpeg', 'image/png'];
    if (allowedType.includes(file.type)) {
      if (file.size <= 5 * 1024 * 1024) {
        this.profilePicture = file;
      } else {
        alert('File size too large (max: 3MB)');
      }
    } else {
      alert('Please select an image file');
    }
  }

  openFileInput() {
    // Trigger the hidden file input element
    document.getElementById('profilePicture')!.click();
  }

  getProfilePictureUrl() {
    // Return the URL for displaying the selected profile picture
    return this.profilePicture
      ? URL.createObjectURL(this.profilePicture)
      : '../../assets/Icons/person-circle.svg';
  }
}
