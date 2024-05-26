import { Component } from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {MatSidenavModule} from '@angular/material/sidenav';
import { AuthService } from '../../services/Auth/auth.service';
import { Userdetails } from '../../interfaces/userdetails';
import { MatIcon } from '@angular/material/icon';
import { JsonPipe } from '@angular/common';
import { Router, RouterLink, RouterOutlet } from '@angular/router';
import { StoryService } from '../../services/Story/story.service';
@Component({
  selector: 'app-userhome',
  standalone: true,
  imports:[MatSidenavModule, MatButtonModule,MatIcon,JsonPipe,RouterOutlet,RouterLink],
  templateUrl: './userhome.component.html',
  styleUrl: './userhome.component.scss'
})
export class UserhomeComponent {
  showFiller = false;
    userDetail : any;
   
  constructor(private authService : AuthService,private router:Router){

  }
  user:any
ngOnInit(): void {
  //Called after the constructor, initializing input properties, and the first call to ngOnChanges.
  //Add 'implements OnInit' to the class.
  this.authService.getUserProfile().subscribe();
  this.authService.authSubject.subscribe(
    
      (data)=>{

        console.log("profile",data);
        
        this.user = data.user.user;
      }
    
  )
}
 logout()
 {
  this.authService.logout();

 }
 handleCreateStory()
 {
 
  this.router.navigateByUrl("/userhome/generateStory");
 
 }
 handleViewStory()
 {
  this.router.navigateByUrl("/userhome/viewStory");
 }

}