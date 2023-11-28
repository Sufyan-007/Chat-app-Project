import { Component, Inject, OnInit } from '@angular/core';
import { Router } from '@angular/router';
import { User } from '../interface/user';
import { MAT_DIALOG_DATA, MatDialogRef } from '@angular/material/dialog';


@Component({
  selector: 'app-profile-page',
  templateUrl: './profile-page.component.html',
  styleUrls: ['./profile-page.component.css']
})
export class ProfilePageComponent implements OnInit {
  user!: User;

  constructor(@Inject(MAT_DIALOG_DATA) private data:{user:User} ,private dialogRef:MatDialogRef<ProfilePageComponent> ){
    this.user = data.user;
  }

  ngOnInit(){
    console.log(this.user)
  }

  close(){
    this.dialogRef.close();
  }



}
