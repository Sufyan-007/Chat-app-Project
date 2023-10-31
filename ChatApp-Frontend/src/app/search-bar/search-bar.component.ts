import { Component } from '@angular/core';
import { UserServiceService } from '../service/user-service.service';
import { Observable, Subject, catchError, debounceTime, of, switchMap } from 'rxjs';
import { User } from '../interface/user';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css']
})
export class SearchBarComponent {
  shown=false;
  Results:User[]=[];
  searchPipe= new Subject<string>();
  results:Observable<User[]>= this.searchPipe.pipe(
    switchMap(search=>this.userService.findUsers(search)),
    debounceTime(500) 
    // catchError(error=>{
    //   console.log(error);
      
    // })
    
  )
  constructor(private userService: UserServiceService){
    this.results.subscribe(results =>this.Results=results);
  }

 
  search(event:any){
    
    if(event.target.value){
      this.shown=true
      this.searchPipe.next(event.target.value);
    }else{
      this.shown=false
    }
    console.log(this.shown)
  }
}
