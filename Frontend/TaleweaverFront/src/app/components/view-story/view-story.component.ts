import { Component } from '@angular/core';
import {MatButtonModule} from '@angular/material/button';
import {MatCardModule} from '@angular/material/card';
import {MatIconButton} from '@angular/material/button'
import {MatIcon} from '@angular/material/icon'
 
import { StoryService } from '../../services/Story/story.service';
 
import {MatPaginatorModule} from '@angular/material/paginator';
import { NgClass } from '@angular/common';
import { ConfirmDialogComponent } from '../confirm-dialog/confirm-dialog.component';
import { MatDialog, MatDialogModule } from '@angular/material/dialog';
import { ReadmoreComponent } from '../readmore/readmore.component';
import { UpdatedialogComponent } from '../updatedialog/updatedialog.component';
 
@Component({
  selector: 'app-view-story',
  standalone: true,
  imports: [MatCardModule,MatIcon, MatButtonModule,MatIconButton,MatPaginatorModule,NgClass,ConfirmDialogComponent,MatDialogModule],
  templateUrl: './view-story.component.html',
  styleUrl: './view-story.component.scss'
})
export class ViewStoryComponent {

  stories:any=[]
  totalPages:number=0;
  currentPages:number=1;
  constructor(private storyService : StoryService,private dialog: MatDialog){}

  handlePrev()
  {
    
    if(this.currentPages-1<=0) 
    {
      alert("You are already on the first page !");
      
      return;
    }
    this.currentPages--;
    this.getALlStories(this.currentPages-1);
   
  }
  handleNext()
  {
    
    if(this.currentPages>=this.totalPages)
    {
      alert("You are already on the last page!")
      return;
    }
    this.currentPages++;
    this.getALlStories(this.currentPages-1);
   
  }


  ngOnInit(): void {
    //Called after the constructor, initializing input properties, and the first call to ngOnChanges.
    //Add 'implements OnInit' to the class.

   
    this.getALlStories(this.currentPages-1);
    
  }

  getALlStories(pages:number)
  {
    this.storyService.getAllStories(pages).subscribe(
      (data:any)=>{

        this.stories = data.stories;
        this.totalPages=data.totalPages;
        
      }
    )
  }


  openConfirmDialog(storyId: number): void {
    const dialogRef = this.dialog.open(ConfirmDialogComponent);

    dialogRef.afterClosed().subscribe((result:any) => {
      if (result) {
        this.deleteStory(storyId);
      }
    });
  }

  deleteStory(storyId: number): void {
    this.storyService.deleteStory(storyId).subscribe(
      () => {
        this.stories = this.stories.filter((story:any)=> story.id !== storyId);
      },
      error => {
        console.error('Error deleting story:', error);
      }
    );
  }

  openReadmoreDialog(story:any)
  {
    this.dialog.open(ReadmoreComponent,{data:story} )
  }
  openUpdateDialog(story:any)
  {
    this.dialog.open(UpdatedialogComponent,{data:story})
  }

}
