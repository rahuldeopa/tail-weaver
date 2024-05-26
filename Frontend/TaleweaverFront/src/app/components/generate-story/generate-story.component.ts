import { Component,NgModule } from '@angular/core';
import { StoryService } from '../../services/Story/story.service';
import { MatFormField, MatFormFieldModule, MatLabel } from '@angular/material/form-field';
import {  FormControl, FormGroup, FormsModule, NgModel, ReactiveFormsModule, Validators } from '@angular/forms';
import { CommonModule, JsonPipe, NgClass } from '@angular/common';
import { MatButton } from '@angular/material/button';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import { MatIcon } from '@angular/material/icon';
import {ProgressSpinnerMode, MatProgressSpinnerModule} from '@angular/material/progress-spinner';
import { MatInputModule } from '@angular/material/input';
@Component({
  selector: 'app-generate-story',
  standalone: true,
  imports: [MatFormField,NgClass,FormsModule,MatInputModule,MatIcon,MatProgressSpinnerModule,ReactiveFormsModule,FormsModule,CommonModule,MatFormFieldModule,MatLabel,MatButton,MatButtonModule,MatCardModule],
  templateUrl: './generate-story.component.html',
  styleUrl: './generate-story.component.scss'
})
export class GenerateStoryComponent {
  storyReturn:string='as';
  prompt:string='';
  isLoading:Boolean = false;
 
  
  story:any ={
    title : '' ,
    content :'',
    email : ''

  }

  storyForm = new FormGroup({

    title : new FormControl("",[Validators.required , Validators.minLength(5)]),
    content : new FormControl(this.storyReturn,[Validators.required])

  })


  constructor(private storyService:StoryService){}
  handleStoryGeneration()
  {
     if(this.prompt==null || this.prompt.trim().length==0) 
       {return;}
       
  this.isLoading=true
  this.storyService.getstory(this.prompt).subscribe(
    (data:any)=>{

      alert("story generated");
      console.log(data.message);
      
      this.storyReturn = data.message;
      this.storyForm.controls['content'].setValue(this.storyReturn);
      this.isLoading=false
      
    }
  )
  }

  handleReset()
  {
    this.isLoading=false
    this.storyReturn=''
  }
  handleSave()
  {
  
    console.log(this.storyReturn);
    this.story.title = this.storyForm.value.title;
    this.story.content = this.storyForm.value.content;
     

    
    console.log(this.story);
    
    this.storyService.saveStory(this.story).subscribe(
      (data:any)=>{

        alert(data.message);

      }
    );

  }
  
  saveStory()
  {

  }
}
