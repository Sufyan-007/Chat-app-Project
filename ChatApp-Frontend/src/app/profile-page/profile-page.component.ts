import { Component, Inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../interface/user';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';
import { HttpClient, HttpHeaders } from '@angular/common/http';


@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css']
})
export class ProfilePageComponent implements OnInit {
  user!: User;
  updatedUser: User|null = null;
  profilePicture=null;
  profileImg:String;

  constructor(@Inject(MAT_DIALOG_DATA) private data:{user:User},private http:HttpClient ,private dialogRef:MatDialogRef<ProfilePageComponent> ){
    this.user = data.user;
    this.profileImg=this.user.profilePictureUrl
  }

  ngOnInit(){
  }

  close(){
    if (this.updatedUser!=null){
        this.dialogRef.close(this.updatedUser);
    }
    else{
      this.dialogRef.close();
    }
  }

  

  selectProfileImage(event:any){
    const file = event.target.files[0];
    const allowedType = ['image/jpeg', 'image/png'];
    if (allowedType.includes(file.type)) {
      if (file.size <= 5 * 1024 * 1024) {
        this.profilePicture = file;
        this.profileImg=URL.createObjectURL(file);
      } else {
        alert('File size too large (max: 3MB)');
      }
    } else {
      alert('Please select an image file');
    }
  }

  openFile(){
    document.getElementById("profilePicture")?.click()
  }

  update(){
    const body = JSON.stringify(this.user);
    const headers = new HttpHeaders({
      'Authorization':localStorage.getItem("token") as string,
      
    });

    const data = new FormData();
    data.append('body', body);
    if (this.profilePicture) {
      data.append('file', this.profilePicture);
    }
    this.http.post<User>("http://localhost:8080/user/update",data,{headers}).subscribe((response)=>{
      this.updatedUser=response
      this.close()
    })
  }


}
