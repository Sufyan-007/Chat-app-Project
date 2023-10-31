import { NgModule } from '@angular/core';
import { RouterModule, Routes } from '@angular/router';
import { ChatsbaseComponent } from './chatsbase/chatsbase.component';
import { LoginComponent } from './login/login.component';
import { SearchBarComponent } from './search-bar/search-bar.component';

const routes: Routes = [
  {path: 'home', component:ChatsbaseComponent},
  {path: '', component:LoginComponent},
  {path: 'search', component:SearchBarComponent},

  
  {path:'**',redirectTo:""}
];

@NgModule({
  imports: [RouterModule.forRoot(routes)],
  exports: [RouterModule],
  
})
export class AppRoutingModule { }
