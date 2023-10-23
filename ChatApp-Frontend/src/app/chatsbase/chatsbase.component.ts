import { Component, OnInit } from '@angular/core';
import { Router } from '@angular/router';

@Component({
  selector: 'app-chatsbase',
  templateUrl: './chatsbase.component.html',
  styleUrls: ['./chatsbase.component.css']
})
export class ChatsbaseComponent implements OnInit{

  constructor(private router:Router){}

  ngOnInit(): void {
    if(localStorage.getItem("token") == null){
      console.log("Init")
      this.router.navigate(["/"]);
    }
    else{
      console.log("else")
    }

  }


}
