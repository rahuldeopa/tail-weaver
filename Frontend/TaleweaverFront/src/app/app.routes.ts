import { Routes } from '@angular/router';
import { HomepageComponent } from './components/homepage/homepage.component';
import { AuthComponent } from './components/auth/auth.component';
import { UserhomeComponent } from './components/userhome/userhome.component';
import { WelcomeComponent } from './components/welcome/welcome.component';
import { GenerateStoryComponent } from './components/generate-story/generate-story.component';
import { ViewStoryComponent } from './components/view-story/view-story.component';

export const routes: Routes = [

    {
        path:'' , component:HomepageComponent
    },
    {
        path:'auth' , component:AuthComponent
    },
    {
        path:'userhome' , component:UserhomeComponent , children : [
            {path:'' , component:WelcomeComponent},
            {path:'generateStory',component:GenerateStoryComponent},
            {path:'viewStory' , component:ViewStoryComponent}
        ]
    },
   

];
