import { Component, ElementRef, HostListener } from '@angular/core';
import { UserService } from '../service/user.service';
import {
  Observable,
  Subject,
  catchError,
  debounceTime,
  of,
  switchMap,
} from 'rxjs';
import { User } from '../interface/user';
import { Router } from '@angular/router';

@Component({
  selector: 'app-search-bar',
  templateUrl: './search-bar.component.html',
  styleUrls: ['./search-bar.component.css'],
})
export class SearchBarComponent {
  
  shown = false;
  Results: User[] = [];
  searchPipe = new Subject<string>();
  results: Observable<User[]> = this.searchPipe.pipe(
    switchMap((search) => this.userService.findUsers(search)),
    debounceTime(500)
    // catchError(error=>{
    //   console.log(error);

    // })
  );
  
  constructor(
    private elementRef: ElementRef,
    private userService: UserService,
    private router: Router
    
  ) {
    this.results.subscribe((results) => (this.Results = results));
  }

  @HostListener('document:click', ['$event'])
  onDocumentClick(event: Event): void {
    const clickedInside = this.elementRef.nativeElement.contains(event.target);
    if (clickedInside) {
      this.shown = this.Results.length > 0;
    } else {
      console.log('Clicked outside ');
      this.shown = false;
    }
  }

  search(event: any) {
    if (event.target.value) {
      this.shown = true;
      this.searchPipe.next(event.target.value);
    } else {
      this.shown = false;
    }
    console.log(this.shown);
  }


  openNewChat(user: User) {
    localStorage.setItem("newChat", JSON.stringify(user));
    this.router.navigate(['/home/new-dm/'+user.username]);
  }
}
